package dao;

import database.DBConnection;
import model.EditTask;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class EditTaskDAO {

    public boolean updateTask(EditTask task) {

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE tasks SET task_title=?, description=?, due_date=?, status=? WHERE task_id=?"
            );

            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getDueDate());
            ps.setString(4, task.getStatus());
            ps.setInt(5, task.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
