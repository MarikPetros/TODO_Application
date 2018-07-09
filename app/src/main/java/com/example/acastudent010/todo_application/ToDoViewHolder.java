package com.example.acastudent010.todo_application;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;


public class ToDoViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView description;
    TextView date;
    ImageButton delete;
    CheckBox chbDelete;

    public ToDoViewHolder(View itemView) {

        super(itemView);

        title = (TextView) itemView.findViewById(R.id.Title);
        description = (TextView) itemView.findViewById(R.id.Description);
        date = (TextView) itemView.findViewById(R.id.Date);
        delete = (ImageButton) itemView.findViewById(R.id.imageButtonDelete);
        chbDelete = (CheckBox) itemView.findViewById(R.id.chbDelete);
    }


}
