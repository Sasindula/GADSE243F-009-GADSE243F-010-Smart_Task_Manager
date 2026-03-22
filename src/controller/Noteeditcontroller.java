package controller;

import database.DBConnection;
import model.Noteedit;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Noteeditcontroller {

    public boolean updateNote(Noteedit note) {

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE notes SET title=?, content=? WHERE note_id=?"
            );

            ps.setString(1, note.getTitle());
            ps.setString(2, note.getContent());
            ps.setInt(3, note.getNoteId());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
