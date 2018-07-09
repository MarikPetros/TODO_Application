package com.example.acastudent010.todo_application;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.UUID;

import static android.text.TextUtils.isEmpty;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ToDoItemFragment.OnDataSendListener} interface
 * to handle interaction events.
 * Use the {@link ToDoItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDoItemFragment extends android.app.Fragment {
    private static final String ITEM = "item";

    public static final int MODE_CREATION = 0;
    public static final int MODE_CHANGE = 1;

    String textTitle;
    String textDescription;
    int prior = ToDoItem.MIN_PRIORITY;
    long dateValueInMillis;
    private int mMode;

    TextView date, priority, prNum;
    EditText title, description;
    CheckBox reminder, repeat;
    RadioGroup radioFrequency;
    ImageButton increase, decrease;
    private ToDoItem mToDoItem;
    private OnDataSendListener mListener;

    public void setmListener(OnDataSendListener mListener) {
        this.mListener = mListener;
    }

    public ToDoItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ToDoItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToDoItemFragment newInstance(ToDoItem toDoItem1) {
        ToDoItemFragment fragment = new ToDoItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(ITEM, toDoItem1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mToDoItem = getArguments().getParcelable(ITEM);
            if (mToDoItem == null) {
                mMode = MODE_CREATION;
            } else {
                mMode = MODE_CHANGE;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View fragmentView = inflater.inflate(R.layout.fragment_to_do_item, container, false);
        Toolbar toolbar = (Toolbar) fragmentView.findViewById(R.id.toolbarToDoItem);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        fragmentView.setTag("ToDoItem");

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        init(view);

        setDateText();

        if (mToDoItem != null) {
            fillItem(mToDoItem);
        }
    }

    private void init(View view) {
        title = (EditText) view.findViewById(R.id.editTextTitle);
        description = (EditText) view.findViewById(R.id.etDescription);
        date = (TextView) view.findViewById(R.id.textDate);
        reminder = (CheckBox) view.findViewById(R.id.cbReminder);
        repeat = (CheckBox) view.findViewById(R.id.cbRepeat);
        radioFrequency = (RadioGroup) view.findViewById(R.id.radioFreq);
        radioFrequency.setVisibility(View.GONE);
        priority = (TextView) view.findViewById(R.id.textPriority);
        prNum = (TextView) view.findViewById(R.id.textViewPrNum);
        increase = (ImageButton) view.findViewById(R.id.imageButtonUp);
        decrease = (ImageButton) view.findViewById(R.id.imageButtonDown);

        date.setOnClickListener(onViewClickListener);
        increase.setOnClickListener(onViewClickListener);
        decrease.setOnClickListener(onViewClickListener);

        repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioFrequency.setVisibility(View.VISIBLE);
                } else {
                    radioFrequency.setVisibility(View.GONE);
                }
            }
        });
    }

    private void submit() {
        KeyboardUtil.hideKeyboardFrom(getActivity(), getView());
        if (checkInput()) {
            if (mListener != null) {
                switch (mMode) {
                    case MODE_CREATION:
                        mListener.onItemCreated(createToDoItem());
                        break;
                    case MODE_CHANGE:
                        mListener.onItemChanged(createToDoItem());
                        break;
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_todo_item, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.editToDo) {
            onEditClick();
            submit();
            item.setIcon(R.drawable.check);
            return true;
        }

        if (id == R.drawable.check) {
            onSaveClick();
            item.setIcon(android.R.drawable.ic_menu_edit);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Calendar for holding date and time values
    private Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            openTimePicker();
        }

    };

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);

            setDateText();
        }
    };

    private View.OnClickListener onViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.textDate:
                    openDatePicker();
                    break;
                case R.id.imageButtonUp:
                    if (prior < Integer.parseInt(String.valueOf(ToDoItem.MAX_PRIORITY))) {
                        ++prior;
                        prNum.setText(String.valueOf(prior));
                    }
                    break;
                case R.id.imageButtonDown:
                    if (prior > Integer.parseInt(String.valueOf(ToDoItem.MIN_PRIORITY))) {
                        --prior;
                        prNum.setText(String.valueOf(prior));
                    }
                    break;
            }
        }
    };

    private void fillItem(ToDoItem item) {
        title.setText(item.getTitle());
        description.setText(item.getDescription());
        myCalendar.setTime(item.getmDate());
        date.setText(DateUtil.formatDateToLongStile(myCalendar.getTime()));
        reminder.setChecked(item.isCheckRemind());
        if (item.getRepeatType() != null) {
            switch (item.getRepeatType()) {
                case NONE:
                    repeat.setChecked(false);
                    break;
                case DAILY:
                    radioFrequency.check(R.id.rbDaily);
                    repeat.setChecked(true);
                    break;
                case WEEKLY:
                    radioFrequency.check(R.id.rbWeekly);
                    repeat.setChecked(true);
                    break;
                case MONTHLY:
                    repeat.setChecked(true);
                    radioFrequency.check(R.id.rbMonthly);
                    break;
            }
        }
        priority.setText(String.valueOf(item.getPriority()));
    }

    private ToDoItem createToDoItem() {
        if (mToDoItem == null) {
            // If item is newly created initialize with uuid
            mToDoItem = new ToDoItem();
            mToDoItem.setId(UUID.randomUUID().toString());
        }
        mToDoItem.setTitle(title.getText().toString());
        mToDoItem.setDescription(description.getText().toString());
        mToDoItem.setmDate(myCalendar.getTime());
        mToDoItem.setCheckRemind(reminder.isChecked());
        mToDoItem.setPriority(prior);
        if (repeat.isChecked()) {
            switch (radioFrequency.getCheckedRadioButtonId()) {
                case R.id.rbDaily:
                    mToDoItem.setRepeatType(ToDoItem.Repeat.DAILY);
                    break;
                case R.id.rbWeekly:
                    mToDoItem.setRepeatType(ToDoItem.Repeat.WEEKLY);
                    break;
                case R.id.rbMonthly:
                    mToDoItem.setRepeatType(ToDoItem.Repeat.MONTHLY);
                    break;
                default:
                    mToDoItem.setRepeatType(ToDoItem.Repeat.NONE);
            }
        }

        return mToDoItem;

    }

   /* private void increasePriority() {
        mPriority = Math.min(++mPriority, TodoItem.PRIORITY_MAX);
        mPriorityLabel.setText(String.valueOf(mPriority));
    }

    private void decreasePriority() {
        mPriority = Math.max(--mPriority, TodoItem.PRIORITY_MIN);
        mPriorityLabel.setText(String.valueOf(mPriority));
    }*/

    private boolean checkInput() {
        boolean isValid;
        isValid = checkTitle();

        return isValid;
    }

    private boolean checkTitle() {
        boolean isValid;
        if (isEmpty(title)) {
            isValid = false;
            title.setError("Title field is mandatory");
        } else {
            isValid = true;
            title.setError(null);
        }
        return isValid;
    }

    private boolean isEmpty(EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString());
    }

    private void onEditClick() {
        title.setEnabled(true);
        date.setEnabled(true);
        description.setEnabled(true);
        reminder.setEnabled(true);
        repeat.setEnabled(true);
        increase.setEnabled(true);
        decrease.setEnabled(true);
    }

    private void onSaveClick() {
        textTitle = title.getText().toString();
        textDescription = description.getText().toString();
        prior = Integer.parseInt(prNum.getText().toString());
        dateValueInMillis = myCalendar.getTimeInMillis();


        /*if (!isEmpty(textTitle) || !isEmpty(textDescription) || !isEmpty(date.getText())) {
            if (mListener != null) {
                mListener.onDataSend(createToDoItem());
            }
        } else {
            Toast.makeText(getActivity(), "Title or description or date is not filled!", Toast.LENGTH_LONG).show();
            return;
        }*/
        title.setEnabled(false);
        date.setEnabled(false);
        description.setEnabled(false);
        reminder.setEnabled(false);
        repeat.setEnabled(false);
        increase.setEnabled(false);
        decrease.setEnabled(false);

    }

    private void openDatePicker() {
        new DatePickerDialog(getActivity(), onDateSetListener, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void openTimePicker() {
        new TimePickerDialog(getActivity(), timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE), true).show();
    }

    private void setDateText() {
        date.setText(DateUtil.formatDateToLongStile(myCalendar.getTime()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDataSendListener {
        void onItemCreated(ToDoItem item);
        void onItemChanged(ToDoItem item);
    }
}
