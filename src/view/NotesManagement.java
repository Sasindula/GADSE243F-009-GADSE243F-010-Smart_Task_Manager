package view;

import database.DBConnection;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.sql.*;

public class NotesManagement extends JFrame {

    private User currentUser;
    private JTable notesTable;
    private JTextField txtSearch;

    public NotesManagement(User user){
        this.currentUser = user;

        setTitle("Manage Notes - SmartNotes");
        setSize(800,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try{
            // 🌄 Background Image
            String bgUrl = "https://images.unsplash.com/photo-1498050108023-c5249f4df085?auto=format&fit=crop&w=800&q=80";
            ImageIcon bgIcon = new ImageIcon(new URL(bgUrl));
            Image bgImg = bgIcon.getImage().getScaledInstance(800,500,Image.SCALE_SMOOTH);

            JLabel bg = new JLabel(new ImageIcon(bgImg));
            bg.setLayout(null);
            setContentPane(bg);

            // 🔳 Glass Panel
            JPanel panel = new JPanel();
            panel.setBounds(40,40,720,400);
            panel.setBackground(new Color(0,0,0,170));
            panel.setLayout(null);
            bg.add(panel);

            JLabel title = new JLabel("Notes Management");
            title.setFont(new Font("Segoe UI",Font.BOLD,26));
            title.setForeground(Color.WHITE);
            title.setBounds(220,10,300,40);
            panel.add(title);

            // 🔍 Search
            txtSearch = new JTextField();
            txtSearch.setBounds(30,60,400,30);
            panel.add(txtSearch);

            JButton btnSearch = new JButton("Search");
            btnSearch.setBounds(450,60,120,30);
            panel.add(btnSearch);

            // 📋 Table
            String[] cols = {"ID","Title","Content",};
            DefaultTableModel model = new DefaultTableModel(cols,0);
            notesTable = new JTable(model);

            JScrollPane scroll = new JScrollPane(notesTable);
            scroll.setBounds(30,110,650,180);
            panel.add(scroll);

            // 🔘 Buttons
            JButton btnAdd = new JButton("Add");
            JButton btnEdit = new JButton("Edit");
            JButton btnDelete = new JButton("Delete");
            JButton btnBack = new JButton("Back");

            btnAdd.setBounds(30,310,120,40);
            btnEdit.setBounds(180,310,120,40);
            btnDelete.setBounds(330,310,120,40);
            btnBack.setBounds(480,310,120,40);

            panel.add(btnAdd);
            panel.add(btnEdit);
            panel.add(btnDelete);
            panel.add(btnBack);

            // 🎨 Colors
            btnAdd.setBackground(new Color(46,204,113));
            btnEdit.setBackground(new Color(52,152,219));
            btnDelete.setBackground(new Color(231,76,60));
            btnBack.setBackground(Color.GRAY);

            // 🔄 Actions
            btnAdd.addActionListener(e -> new AddNoteForm(currentUser,this).setVisible(true));

            btnEdit.addActionListener(e -> {
                int row = notesTable.getSelectedRow();
                if(row == -1){
                    JOptionPane.showMessageDialog(this,"Select a note!");
                    return;
                }

                int id = (int) notesTable.getValueAt(row,0);
                String title1 = notesTable.getValueAt(row,1).toString();
                String content = notesTable.getValueAt(row,2).toString();

                new EditNoteForm(currentUser,this,id,title1,content).setVisible(true);
            });

            btnDelete.addActionListener(e -> deleteNote());

            btnBack.addActionListener(e -> dispose());

            btnSearch.addActionListener(e -> loadNotes());

            // 🔥 Load data
            loadNotes();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // 🔄 LOAD NOTES
    public void loadNotes(){
        try{
            Connection con = DBConnection.getConnection();

            String keyword = txtSearch.getText().trim();

            String sql;

            // 🔥 If number -> search by ID
            if(keyword.matches("\\d+")){
                sql = "SELECT * FROM notes WHERE user_id=? AND note_id=?";
            } else {
                sql = "SELECT * FROM notes WHERE user_id=? AND (title LIKE ? OR content LIKE ?)";
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,currentUser.getUserId());

            if(keyword.matches("\\d+")){
                ps.setInt(2,Integer.parseInt(keyword));
            } else {
                if(keyword.isEmpty()){
                    keyword = "%";
                } else {
                    keyword = "%" + keyword + "%";
                }
                ps.setString(2,keyword);
                ps.setString(3,keyword);
            }

            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) notesTable.getModel();
            model.setRowCount(0);

            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getInt("note_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                });
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // 🗑 DELETE
    private void deleteNote(){
        int row = notesTable.getSelectedRow();

        if(row == -1){
            JOptionPane.showMessageDialog(this,"Select a note!");
            return;
        }

        int id = (int) notesTable.getValueAt(row,0);

        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM notes WHERE note_id=?");
            ps.setInt(1,id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,"Deleted!");
            loadNotes();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void refreshTable(){
        loadNotes();
    }
}