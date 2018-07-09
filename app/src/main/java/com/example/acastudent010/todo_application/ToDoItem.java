package com.example.acastudent010.todo_application;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;
import java.util.Date;


public class ToDoItem implements Parcelable{
    public static int MAX_PRIORITY = 5;
    public static int MIN_PRIORITY = 0;

    private String id;


    private String title;
    private String description;
    private Date mDate;
    private boolean isCheckRemind;
    private int priority;
    private Repeat repeatType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isCheckRemind() {
        return isCheckRemind;
    }

    public void setCheckRemind(boolean checkRemind) {
        this.isCheckRemind = checkRemind;
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

    public ToDoItem(){}

    public static class ToDoItemComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            if (o1 != null && o2 != null && o1 instanceof ToDoItem && o2 instanceof ToDoItem)
                return ((ToDoItem) o1).mDate.compareTo(((ToDoItem) o2).mDate);

            return 0;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeByte((byte) (isCheckRemind ? 1 : 0));
        dest.writeLong(mDate.getTime());
        dest.writeInt(isCheckRemind ? 1 : 0);
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

    public static final Creator<ToDoItem> CREATOR = new Creator<ToDoItem>() {
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
        id = in.readString();
        title = in.readString();
        description = in.readString();
        mDate = new Date(in.readLong());
        isCheckRemind = in.readInt() == 1;
        priority = in.readInt();
        int repeatTypeOrdinal = in.readInt();
        if (repeatTypeOrdinal > 0) {
            repeatType = Repeat.values()[repeatTypeOrdinal];
        }
    }

    public enum Repeat{
        NONE, DAILY, WEEKLY, MONTHLY
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        // --- Attribute id
        result = prime * result + ((id == null) ? 0 : id.hashCode());

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

    @Override
    public String toString() {
        return  "[" + id + "]:"
                + title + "|"
                + description + "|"
                + mDate + "|"
                + isCheckRemind + "|"
                + priority + "|"
                + repeatType;
    }
}



