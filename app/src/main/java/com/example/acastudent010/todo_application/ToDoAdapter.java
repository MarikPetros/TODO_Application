package com.example.acastudent010.todo_application;

import android.annotation.TargetApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ToDoAdapter extends RecyclerView.Adapter<ToDoViewHolder> {
    private List<ToDoItem> mData = new ArrayList<>();
    private onItemSelectedListener itemSelectedListener;

    public ToDoAdapter(List<ToDoItem> data) {
        mData = data;
    }


    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item_layout, parent, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, final int position) {
        final ToDoItem toDoItem = mData.get(position);
        holder.title.setText(toDoItem.getTitle());
        holder.description.setText(toDoItem.getDescription());
        holder.date.setText(toDoItem.getmDate().toString());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
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

   public void addView(ToDoItem item) {
        mData.add(item);
        this.notifyDataSetChanged();
    }

   public void updateItemView(ToDoItem item){
        // Find item and update
       // Not the best solution, it is possible update an item by accepting position
       // from outside. For current data structure and 'client' implementation
       // this is best of worst.
       for (int i = 0; i < mData.size(); i++) {
           if (Objects.equals(item, mData.get(i))) {
               mData.set(i, item);
               notifyItemChanged(i);
           }



       }
   }

    public void removeItem(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }

    @TargetApi(24)
    public void onSort() {
        ToDoItem.ToDoItemComparator toDoItemComparator = new ToDoItem.ToDoItemComparator();
        // Collections.sort(mData);
        mData.sort(toDoItemComparator);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemSelectedListener(onItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    public interface onItemSelectedListener {
        void onItemSelected(ToDoItem toDoItem);
    }
}
