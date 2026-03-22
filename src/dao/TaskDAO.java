package dao;

import database.DBConnection;
import model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public static List<Task> getTasks(int userId){
        List<Task> tasks = new ArrayList<>();
        try{
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM tasks WHERE user_id=? ORDER BY due_date ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                tasks.add(new Task(
                        rs.getInt("task_id"),
                        rs.getString("task_title"),
                        rs.getString("description"),
                        rs.getDate("due_date"),
                        rs.getString("status")
                ));
            }

        }catch(Exception e){ e.printStackTrace(); }
        return tasks;
    }

    public static void deleteTask(int taskId){
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM tasks WHERE task_id=?");
            ps.setInt(1,taskId);
            ps.executeUpdate();
        }catch(Exception e){ e.printStackTrace(); }
    }

    public static List<Task> getUpcomingTasks(int userId){
        List<Task> tasks = new ArrayList<>();
        try{
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM tasks WHERE user_id=? AND due_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 3 DAY) ORDER BY due_date ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                tasks.add(new Task(
                        rs.getInt("task_id"),
                        rs.getString("task_title"),
                        rs.getString("description"),
                        rs.getDate("due_date"),
                        rs.getString("status")
                ));
            }
        }catch(Exception e){ e.printStackTrace(); }
        return tasks;
    }
}