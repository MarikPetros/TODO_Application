package com.example.acastudent010.todo_application.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.acastudent010.todo_application.R;
import com.example.acastudent010.todo_application.model.ToDoItem;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoViewHolder> {
    private List<ToDoItem> mData = new ArrayList<>();
    private List<Long> checkedItemsForDelete = new ArrayList<>();
    private ToDoViewHolder mViewHolder;

    private onItemSelectedListener itemSelectedListener;

    public ToDoAdapter() {

    }


    public void setmData(List<ToDoItem> mData1){
        mData = mData1;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item_layout, parent, false);
        mViewHolder = new ToDoViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ToDoViewHolder holder,@NonNull final int position) {
        final ToDoItem toDoItem = mData.get(position);
        holder.title.setText(toDoItem.getTitle());
        holder.description.setText(toDoItem.getDescription());
        holder.date.setText(toDoItem.getDate().toString());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSelectedListener.onDelete(getItemByPosition(position));
                removeItem(position);
          //      databaseManager.deleteItem(position);
            }
        });
        holder.chbDelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkedItemsForDelete.add(getItemId(position));
                } else if (checkedItemsForDelete.contains(getItemId(position))) {
                    checkedItemsForDelete.remove(getItemId(position));
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSelectedListener.onItemSelected(toDoItem);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    private  ToDoItem getItemByPosition(int position){
        return mData.get(position);
    }


    public void addView(ToDoItem item) {
        if (item != null) {
            mData.add(item);
      //      databaseManager.insertTodoItem(item);

        }
        this.notifyDataSetChanged();
    }

    // setting item's CheckBox for delete visible
    public void setDeleteCheckBoxVisible() {
        mViewHolder.chbDelete.setVisibility(View.VISIBLE);
        notifyDataSetChanged();
    }

    public void updateItemView(ToDoItem item) {
        // Find item and update
        // Not the best solution, it is possible update an item by accepting position
        // from outside. For current data structure and 'client' implementation
        // this is best of worst.
        for (int i = 0; i < mData.size(); i++) {
            if (Objects.equals(item, mData.get(i))) {
                mData.set(i, item);
      //          databaseManager.updateToDoItem(item);
                notifyItemChanged(i);
            }
        }
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }

    /* public void removeSelectedItems() {
         for (ToDoItem item : mData) {
             if (checkedItemsForDelete.contains(item.getId())) {// SXAL A
                 mData.deleteItem();
             }
         }
         notifyDataSetChanged();
     }
 */
    public int getItemsCountForDelete() {

        return checkedItemsForDelete.size();
    }

    public void onSort() {

        Collections.sort(mData, new Comparator<ToDoItem>() {
            @Override
            public int compare(ToDoItem o1, ToDoItem o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return  mData.size();
    }

    public void setOnItemSelectedListener(onItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }


    public interface onItemSelectedListener {
        void onItemSelected(ToDoItem toDoItem);
        void onDelete(ToDoItem toDoItem);
    }
}
