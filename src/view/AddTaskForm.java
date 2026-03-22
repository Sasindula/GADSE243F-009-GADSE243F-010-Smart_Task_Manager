package view;

import database.DBConnection;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddTaskForm extends JFrame {

    public AddTaskForm(User user, TaskManagement parent){

        setTitle("Add Task");
        setSize(450,350);
        setLocationRelativeTo(null);

        try{
            // 🌄 Background
            String bgUrl = "https://images.unsplash.com/photo-1492724441997-5dc865305da7";
            Image img = new ImageIcon(new URL(bgUrl))
                    .getImage().getScaledInstance(450,350,Image.SCALE_SMOOTH);

            JLabel bg = new JLabel(new ImageIcon(img));
            bg.setLayout(null);
            setContentPane(bg);

            // 🔳 Glass Panel
            JPanel panel = new JPanel();
            panel.setBounds(30,20,380,280);
            panel.setBackground(new Color(0,0,0,170));
            panel.setLayout(null);
            bg.add(panel);

            JLabel title = new JLabel("Add Task");
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Segoe UI",Font.BOLD,20));
            title.setBounds(140,10,200,30);
            panel.add(title);

            // Labels
            JLabel lbl1 = new JLabel("Title");
            lbl1.setForeground(Color.WHITE);
            lbl1.setBounds(20,50,100,20);
            panel.add(lbl1);

            JLabel lbl2 = new JLabel("Description");
            lbl2.setForeground(Color.WHITE);
            lbl2.setBounds(20,100,100,20);
            panel.add(lbl2);

            JLabel lbl3 = new JLabel("Due Date");
            lbl3.setForeground(Color.WHITE);
            lbl3.setBounds(20,170,100,20);
            panel.add(lbl3);

            JLabel lbl4 = new JLabel("Status");
            lbl4.setForeground(Color.WHITE);
            lbl4.setBounds(20,210,100,20);
            panel.add(lbl4);

            // Fields
            JTextField txtTitle = new JTextField();
            txtTitle.setBounds(120,50,220,25);
            panel.add(txtTitle);

            JTextArea txtDesc = new JTextArea();
            JScrollPane scroll = new JScrollPane(txtDesc);
            scroll.setBounds(120,100,220,60);
            panel.add(scroll);

            JTextField txtDate = new JTextField();
            txtDate.setBounds(120,170,220,25);
            txtDate.setToolTipText("YYYY-MM-DD");
            panel.add(txtDate);

            String[] statusList = {"Pending","Completed"};
            JComboBox<String> cmbStatus = new JComboBox<>(statusList);
            cmbStatus.setBounds(120,210,220,25);
            panel.add(cmbStatus);

            // Buttons
            JButton btnSave = new JButton("Save");
            btnSave.setBounds(60,240,100,30);
            btnSave.setBackground(new Color(46,204,113));
            btnSave.setForeground(Color.WHITE);
            panel.add(btnSave);

            JButton btnCancel = new JButton("Cancel");
            btnCancel.setBounds(200,240,100,30);
            btnCancel.setBackground(new Color(231,76,60));
            btnCancel.setForeground(Color.WHITE);
            panel.add(btnCancel);

            btnCancel.addActionListener(e -> dispose());

            btnSave.addActionListener(e -> {
                try{
                    Connection con = DBConnection.getConnection();

                    PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO tasks(task_title,description,due_date,status,user_id) VALUES(?,?,?,?,?)"
                    );

                    ps.setString(1,txtTitle.getText());
                    ps.setString(2,txtDesc.getText());
                    ps.setString(3,txtDate.getText());
                    ps.setString(4,cmbStatus.getSelectedItem().toString());
                    ps.setInt(5,user.getUserId());

                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(this,"Task Added!");
                    parent.refreshTable();
                    dispose();

                }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,"Error!");
                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}