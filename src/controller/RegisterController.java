package controller;

import database.DBConnection;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterController {

    public boolean register(String name, String email, String password){
        try{
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO users(name,email,password) VALUES(?,?,?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,name);
            pst.setString(2,email);
            pst.setString(3,password);
            int rows = pst.executeUpdate();
            if(rows>0){
                JOptionPane.showMessageDialog(null,"Registration Successful");
                return true;
            } else {
                JOptionPane.showMessageDialog(null,"Registration Failed");
                return false;
            }
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Database Error");
            return false;
        }
    }
}