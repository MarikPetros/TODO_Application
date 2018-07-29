package com.example.acastudent010.todo_application.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

@Entity(tableName = "toDoItem")
public class ToDoItem implements Parcelable {
    public static int MAX_PRIORITY = 5;
    public static int MIN_PRIORITY = 0;


    @PrimaryKey(autoGenerate = true)
    private int todoId;

    @ColumnInfo(name = "_title")
    private String title;

    @ColumnInfo(name = "_description")
    private String description;

    @ColumnInfo(name = "_date")
    private Date date;

    @ColumnInfo(name = "_check_remind")
    private boolean isCheckRemind;

    @ColumnInfo(name = "_isRepeat")
    private boolean isRepeat;

    @ColumnInfo(name = "_repeatType")
    private Repeat repeatType;

    @ColumnInfo(name = "_priority")
    private int priority;

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isCheckRemind() {
        return isCheckRemind;
    }

    public void setCheckRemind(boolean checkRemind) {
        isCheckRemind = checkRemind;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        if (priority < MIN_PRIORITY || priority > MAX_PRIORITY) {
            throw new IllegalArgumentException("Priority should be in range of " + MIN_PRIORITY
                    + " - " + MAX_PRIORITY);
        }
        this.priority = priority;
    }

    public Repeat getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(Repeat repeatType) {
        this.repeatType = repeatType;
    }

    public ToDoItem() {
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeByte((byte) (isCheckRemind ? 1 : 0));//
        dest.writeByte((byte) (isRepeat ? 1 : 0));//try coment this two
        dest.writeLong(date.getTime());
        dest.writeInt(isCheckRemind ? 1 : 0);
        dest.writeInt(isRepeat ? 1 : 0);
        dest.writeInt(priority);
        if (repeatType != null) {
            dest.writeInt(repeatType.ordinal());
        } else {
            dest.writeInt(-1);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<ToDoItem> CREATOR = new Parcelable.Creator<ToDoItem>() {
        @Override
        public ToDoItem createFromParcel(Parcel in) {
            return new ToDoItem(in);
        }

        @Override
        public ToDoItem[] newArray(int size) {
            return new ToDoItem[size];
        }
    };

    private ToDoItem(Parcel in) {
        title = in.readString();
        description = in.readString();
        date = new Date(in.readLong());
        isCheckRemind = in.readInt() == 1;
        priority = in.readInt();
        isRepeat = in.readInt() == 1;
        int repeatTypeOrdinal = in.readInt();
        if (repeatTypeOrdinal > 0) {
            repeatType = Repeat.values()[repeatTypeOrdinal];
        }
    }


    public enum Repeat {
        NONE, DAILY, WEEKLY, MONTHLY
    }

    /*@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        // --- Attribute id
        result = prime * result + ((todoId == null) ? 0 : todoId.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        ToDoItem other = (ToDoItem) obj;
        // --- Attribute mId
        if (id == null) {
            return other.id == null;
        } else {
            return id.equals(other.id);
        }
    }
*/
    @Override
    public String toString() {
        return title + "|"
                + description + "|"
                + date + "|"
                + isCheckRemind + "|"
                + priority + "|"
                + repeatType;
    }
}



