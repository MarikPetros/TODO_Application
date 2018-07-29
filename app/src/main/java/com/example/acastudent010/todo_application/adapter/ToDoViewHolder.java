package com.example.acastudent010.todo_application.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.acastudent010.todo_application.R;


public class ToDoViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView description;
    TextView date;
    ImageButton delete;
    CheckBox chbDelete;

    public ToDoViewHolder(View itemView) {

        super(itemView);

        title =  itemView.findViewById(R.id.Title);
        description =  itemView.findViewById(R.id.Description);
        date =  itemView.findViewById(R.id.Date);
        delete =  itemView.findViewById(R.id.imageButtonDelete);
        chbDelete =  itemView.findViewById(R.id.chbDelete);
        chbDelete.setVisibility(View.GONE);
    }
}
