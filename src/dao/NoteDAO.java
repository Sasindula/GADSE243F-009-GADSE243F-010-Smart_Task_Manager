package dao;

import database.DBConnection;
import model.Note;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class NoteDAO {

    public static boolean addNote(Note note){
        try{
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO notes(title,content,user_id,created_at) VALUES(?,?,?,NOW())"
            );

            ps.setString(1, note.getTitle());
            ps.setString(2, note.getContent());
            ps.setInt(3, note.getUserId());

            return ps.executeUpdate() > 0;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
