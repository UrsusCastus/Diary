package com.example.diary;

public class Event {
    private String mTitle;
    private long mTimeStart;
    private long mTimeEnd;
    private String mDescription;

    public Event(String title, long timeStart, long timeEnd, String description) {
        mTitle = title;
        mTimeStart = timeStart;
        mTimeEnd = timeEnd;
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public long getTimeStart() {
        return mTimeStart;
    }

    public void setTimeStart(int timeStart) {
        mTimeStart = timeStart;
    }

    public long getTimeEnd() {
        return mTimeEnd;
    }

    public void setTimeEnd(int timeEnd) {
        mTimeEnd = timeEnd;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
