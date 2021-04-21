package com.example.diary;

public class Event {
    private String mTitle;
    private int mDate;
    private int mTimeStart;
    private String mDescription;

    public Event(String title, int date, int timeStart, String description) {
        mTitle = title;
        mDate = date;
        mTimeStart = timeStart;
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getDate() {
        return mDate;
    }

    public void setDate(int mDate) {
        this.mDate = mDate;
    }

    public int getTimeStart() {
        return mTimeStart;
    }

    public void setTimeStart(int mTimeStart) {
        this.mTimeStart = mTimeStart;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
