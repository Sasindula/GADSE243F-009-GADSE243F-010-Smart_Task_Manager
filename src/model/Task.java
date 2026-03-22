package model;

import java.util.Date;

public class Task {
    private int id;
    private String title;
    private String description;
    private Date dueDate;
    private String status;

    public Task(int id, String title, String description, Date dueDate, String status){
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Date getDueDate() { return dueDate; }
    public String getStatus() { return status; }
}