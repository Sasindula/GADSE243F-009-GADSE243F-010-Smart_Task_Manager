package controller;

import model.Task;
import dao.TaskDAO;
import model.TaskReportModel;

import java.util.List;

public class TaskReportController {

    public TaskReportModel getSummary(int userId){
        List<Task> tasks = TaskDAO.getTasks(userId);
        int total = tasks.size();
        int completed = 0, pending = 0, overdue = 0;

        long now = System.currentTimeMillis();
        for(Task t : tasks){
            if(t.getStatus().equalsIgnoreCase("Completed")) completed++;
            else if(t.getStatus().equalsIgnoreCase("Pending")) pending++;

            if(t.getDueDate().getTime() < now && !t.getStatus().equalsIgnoreCase("Completed")){
                overdue++;
            }
        }
        return new TaskReportModel(total,completed,pending,overdue);
    }

    public List<Task> getAllTasks(int userId){
        return TaskDAO.getTasks(userId);
    }

    public List<Task> getUpcomingTasks(int userId){
        return TaskDAO.getUpcomingTasks(userId);
    }

    public void deleteTask(int taskId){
        TaskDAO.deleteTask(taskId);
    }
}