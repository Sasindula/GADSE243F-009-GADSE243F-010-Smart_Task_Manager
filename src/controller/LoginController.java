package controller;

import database.DBConnection;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class LoginController {

    public User login(String email, String password) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("name");
                return new User(id, name, email, password);
            } else {
                JOptionPane.showMessageDialog(null,"Invalid email or password!");
                return null;
            }
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Database Error!");
            return null;
        }
    }
}