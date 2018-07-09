package com.example.acastudent010.todo_application;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnListInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ToDoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDoListFragment extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    RecyclerView mRecyclerView;
    ToDoAdapter mAdapter;
    private List<ToDoItem> mData = new ArrayList<>();
    private OnListInteractionListener mListener;


    private ToDoItem mReceivedToDoItem;

    private ToDoAdapter.onItemSelectedListener mAdapterListener = new ToDoAdapter.onItemSelectedListener() {
        @Override
        public void onItemSelected(ToDoItem toDoItem) {
            if (mListener != null) {
                mListener.onItemSelected(toDoItem);
            }
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
    // TODO: Rename and change types and number of parameters
    public static ToDoListFragment newInstance(ToDoItem newItem) {
        ToDoListFragment fragment = new ToDoListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, newItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ToDoAdapter(mData);
        mAdapter.setOnItemSelectedListener(mAdapterListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View fragmentView = inflater.inflate(R.layout.fragment_to_do_list, container, false);
        Toolbar toolbar = (Toolbar) fragmentView.findViewById(R.id.toolbarList);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        fragmentView.setTag("ToDoListFragment");
        return fragmentView;
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());//, LinearLayoutManager.VERTICAL, false

        mRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
       /* DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration();*/
        mRecyclerView.setAdapter(mAdapter);


        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemSelected(mToDoItemForSend);
            }
        });*/

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onAddButtonClicked();
            }
        });
    }

    public void addItem(ToDoItem item) {
        mAdapter.addView(item);
    }

    public void editview(ToDoItem item) {
        mAdapter.updateItemView(item);
    }
    /*// TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onItemSelected(mReceivedToDoItem);
        }
    }*/

    @Override
    public String toString() {
        return super.toString();
    }

    public ToDoAdapter getToDoAdapter() {
        return mAdapter;
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
    public interface OnListInteractionListener {
        void onItemSelected(ToDoItem item);

        void onAddButtonClicked();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.sort) {
            mAdapter.onSort();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
