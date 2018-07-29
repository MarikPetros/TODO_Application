package com.example.acastudent010.todo_application.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.acastudent010.todo_application.R;
import com.example.acastudent010.todo_application.adapter.ToDoAdapter;
import com.example.acastudent010.todo_application.model.ToDoItem;
import com.example.acastudent010.todo_application.room.ToDoDataBase;
import com.example.acastudent010.todo_application.util.DBAsyncHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.acastudent010.todo_application.room.ToDoDataBase.getTodoDataBase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnListInteractionListener} interface
 * to handle interaction events.
 *
 * create an instance of this fragment.
 */
public class ToDoListFragment extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private ToDoAdapter mAdapter;
    private OnListInteractionListener mListener;
    private List<ToDoItem> toDoItems = new ArrayList<>();
 //   public static DatabaseManager databaseManager;

    private ToDoAdapter.onItemSelectedListener mAdapterListener = new ToDoAdapter.onItemSelectedListener() {
        @Override
        public void onItemSelected(ToDoItem toDoItem) {
            if (mListener != null) {
                mListener.onItemSelected(toDoItem);
            }
        }

        @Override
        public void onDelete(ToDoItem toDoItem) {
           ToDoDataBase toDoDataBase = ToDoDataBase.getTodoDataBase(getActivity());
            DBAsyncHelper.deleteData(toDoDataBase,toDoItem);
        }

    };


    public ToDoListFragment() {
        // Required empty public constructor
    }

    public void setmListener(OnListInteractionListener mListener) {
        this.mListener = mListener;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ToDoListFragment.
     */
    /*// TODO: Rename and change types and number of parameters
    public static ToDoListFragment newInstance(ToDoItem newItem) {
        ToDoListFragment fragment = new ToDoListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, newItem);
        fragment.setArguments(args);
        return fragment;
    }*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_to_do_list, container, false);
        Toolbar toolbar = fragmentView.findViewById(R.id.toolbarList);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return fragmentView;
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        init(view);
    }


    private void init(View view){
        RecyclerView mRecyclerView = view.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    //    databaseManager = DatabaseManager.getDataBaseManagerInstance(getActivity());
        mAdapter = new ToDoAdapter();
        mAdapter.setOnItemSelectedListener(mAdapterListener);
        mRecyclerView.setAdapter(mAdapter);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Warning:(107, 28) `onTouch` should call `View#performClick` when a click is detected
                mAdapter.setDeleteCheckBoxVisible();
                return true;
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onAddButtonClicked();
            }
        });
    }

   /* private List<ToDoItem> fillList() {
        if (databaseManager.getAllTodoItems().size() != 0) {
            toDoItems.addAll(databaseManager.getAllTodoItems());
        }
        return toDoItems;
    }*/


    public void addItem(ToDoItem item) {
        if (item != null)
            mAdapter.addView(item);
    }

    public void editView(ToDoItem item) {
        mAdapter.updateItemView(item);
    }



    @Override
    public String toString() {
        return super.toString();
    }

    public ToDoAdapter getToDoAdapter() {
        return mAdapter;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_todo_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.sort:
                mAdapter.onSort();
                return true;
           /* case R.id.forDelete:
                mAdapter.removeSelectedItems(getA);
                return true;*//*
        }*/
        }
            return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.add(mAdapter.getItemsCountForDelete() + R.string.delete_message);
        super.onPrepareOptionsMenu(menu);
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
    public interface OnListInteractionListener {
        void onItemSelected(ToDoItem item);
        void onAddButtonClicked();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
