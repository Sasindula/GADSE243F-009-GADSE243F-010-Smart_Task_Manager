package model;

public class TaskReportModel {
    private int total;
    private int completed;
    private int pending;
    private int overdue;

    public TaskReportModel(int total, int completed, int pending, int overdue){
        this.total = total;
        this.completed = completed;
        this.pending = pending;
        this.overdue = overdue;
    }

    public int getTotal() { return total; }
    public int getCompleted() { return completed; }
    public int getPending() { return pending; }
    public int getOverdue() { return overdue; }
}