package com.example.eksamenvinter2021.Models;

import java.sql.Time;

public class Task {
    private String title;
    private String description;
    private Time estimated_time;
    private Time timeUsed;
    private String status;

    //private Time startTime;
    //private Time endTIme; //update(status==complete)

    //TODO update time
    //man skal manuelt sætte tiden
    //officielt starttid
    //endTime deadline
    //actual EndTIme

    public Task() {
    }

    public Task(String title, String description, Time estimated_time, String status) {
        this.title = title;
        this.description = description;
        this.estimated_time = estimated_time;
        this.status = status;
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

    public Time getEstimatedTime() {
        return estimated_time;
    }

    public void setEstimatedTime(Time estimatedTime) {
        this.estimated_time = estimated_time;
    }

    public Time getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(Time timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
