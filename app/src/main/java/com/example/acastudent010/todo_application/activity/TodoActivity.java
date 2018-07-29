package com.example.acastudent010.todo_application.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.acastudent010.todo_application.R;
import com.example.acastudent010.todo_application.model.ToDoItem;
import com.example.acastudent010.todo_application.fragment.ToDoItemFragment;
import com.example.acastudent010.todo_application.fragment.ToDoListFragment;
import com.example.acastudent010.todo_application.room.ToDoDataBase;
import com.example.acastudent010.todo_application.util.DBAsyncHelper;

import java.util.List;

public class TodoActivity extends AppCompatActivity {
    ToDoListFragment toDoListFragment;
    public ToDoDataBase db;

    ToDoItemFragment.OnDataSendListener itemEditListener = new ToDoItemFragment.OnDataSendListener() {
        @Override
        public void onItemCreated(ToDoItem item) {
            ToDoItem[] toDoItems = {item};
            DBAsyncHelper.insertData(db, toDoItems);
            toDoListFragment.addItem(item);
            getFragmentManager().popBackStack();
        }

        @Override
        public void onItemChanged(ToDoItem item) {
            DBAsyncHelper.updateData(db,item);
            toDoListFragment.editView(item);
            getFragmentManager().popBackStack();
        }

    };
    ToDoListFragment.OnListInteractionListener listInteractionListener = new ToDoListFragment.OnListInteractionListener() {
        @Override
        public void onItemSelected(ToDoItem item) {
            openFragmentInContainer(ToDoItemFragment.newInstance(item), true);
        }

        @Override
        public void onAddButtonClicked() {
            openFragmentInContainer(ToDoItemFragment.newInstance(null), true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = ToDoDataBase.getTodoDataBase(getApplicationContext());
        toDoListFragment =  (ToDoListFragment)(getFragmentManager().findFragmentById(R.id.list_fragment));
        List<ToDoItem> toDoItems = DBAsyncHelper.retrieveData(db);
        toDoListFragment.getToDoAdapter().setmData(toDoItems);
        toDoListFragment.setmListener(listInteractionListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openFragmentInContainer(Fragment fragment, boolean addToBackStack) {
        if (fragment instanceof ToDoItemFragment) {
            ((ToDoItemFragment) fragment).setmListener(itemEditListener);
        } else {
            ((ToDoListFragment) fragment).setmListener(listInteractionListener);
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().toString());
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToDoDataBase.destroyInstance();
    }
}
