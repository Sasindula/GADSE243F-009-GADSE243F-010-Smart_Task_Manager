package view;

import database.DBConnection;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.sql.*;
import java.util.Date;

public class TaskManagement extends JFrame {

    private User currentUser;
    private JTable taskTable;
    private JTextField txtSearch;
    private JTextArea upcomingArea;

    public TaskManagement(User user){
        this.currentUser = user;

        setTitle("Manage Tasks");
        setSize(950,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try{
            // 🌄 Background
            String bgUrl = "https://images.unsplash.com/photo-1484480974693-6ca0a78fb36b";
            Image img = new ImageIcon(new URL(bgUrl))
                    .getImage().getScaledInstance(950,500,Image.SCALE_SMOOTH);

            JLabel bg = new JLabel(new ImageIcon(img));
            bg.setLayout(null);
            setContentPane(bg);

            // 🔳 PANEL
            JPanel panel = new JPanel();
            panel.setBounds(20,20,900,430);
            panel.setBackground(new Color(0,0,0,180));
            panel.setLayout(null);
            bg.add(panel);

            // Title
            JLabel title = new JLabel("Task Management");
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Segoe UI",Font.BOLD,26));
            title.setBounds(320,10,300,40);
            panel.add(title);

            // Search
            txtSearch = new JTextField();
            txtSearch.setBounds(30,60,350,30);
            panel.add(txtSearch);
            txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                public void insertUpdate(javax.swing.event.DocumentEvent e) { loadTasks(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { loadTasks(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { loadTasks(); }
            });
            JButton btnSearch = new JButton("Search");
            btnSearch.setBounds(400,60,100,30);
            panel.add(btnSearch);

            // Table
            String[] cols = {"ID","Title","Description","Due Date","Status"};
            DefaultTableModel model = new DefaultTableModel(cols,0);
            taskTable = new JTable(model);
            taskTable.setDefaultRenderer(Object.class, new TaskCellRenderer());

            JScrollPane scroll = new JScrollPane(taskTable);
            scroll.setBounds(30,110,520,220);
            panel.add(scroll);

            // Upcoming panel
            JPanel upcomingPanel = new JPanel();
            upcomingPanel.setBounds(580,110,280,220);
            upcomingPanel.setBackground(new Color(255,255,255,230));
            upcomingPanel.setLayout(null);
            panel.add(upcomingPanel);

            JLabel upTitle = new JLabel("Upcoming Tasks");
            upTitle.setFont(new Font("Segoe UI",Font.BOLD,16));
            upTitle.setBounds(20,10,200,25);
            upcomingPanel.add(upTitle);

            upcomingArea = new JTextArea();
            upcomingArea.setBounds(15,40,250,160);
            upcomingArea.setEditable(false);
            upcomingArea.setBackground(new Color(245,245,245));
            upcomingPanel.add(upcomingArea);

            // Buttons
            JButton btnAdd = new JButton("Add");
            JButton btnEdit = new JButton("Edit");
            JButton btnDelete = new JButton("Delete");
            JButton btnBack = new JButton("Back");

            btnAdd.setBounds(120,350,120,35);
            btnEdit.setBounds(260,350,120,35);
            btnDelete.setBounds(400,350,120,35);
            btnBack.setBounds(540,350,120,35);

            panel.add(btnAdd);
            panel.add(btnEdit);
            panel.add(btnDelete);
            panel.add(btnBack);

            // Actions
            btnSearch.addActionListener(e -> loadTasks());

            btnAdd.addActionListener(e ->
                    new AddTaskForm(currentUser,this).setVisible(true)
            );

            // ✅ FIXED EDIT BUTTON
            btnEdit.addActionListener(e -> {
                int row = taskTable.getSelectedRow();
                if(row == -1){
                    JOptionPane.showMessageDialog(this,"Select a task!");
                    return;
                }

                int id = getSelectedTaskId(row);

                String t = taskTable.getValueAt(row,1).toString();
                String d = taskTable.getValueAt(row,2).toString();
                String date = taskTable.getValueAt(row,3).toString();
                String status = taskTable.getValueAt(row,4).toString();

                new EditTaskForm(currentUser,this,id,t,d,date,status).setVisible(true);
            });

            // ✅ FIXED DELETE BUTTON
            btnDelete.addActionListener(e -> deleteTask());

            btnBack.addActionListener(e -> dispose());

            // Load
            loadTasks();
            loadUpcomingTasks();
            checkReminders();

            // Timer
            Timer timer = new Timer(60000, e -> {
                loadUpcomingTasks();
                checkReminders();
            });
            timer.start();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // ✅ SAFE ID METHOD
    private int getSelectedTaskId(int row){
        Object val = taskTable.getValueAt(row,0);

        if(val instanceof Integer){
            return (int) val;
        }else{
            return Integer.parseInt(val.toString());
        }
    }

    // LOAD TASKS
    public void loadTasks(){
        try{
            Connection con = DBConnection.getConnection();

            String keyword = txtSearch.getText().trim();
            String sql;

            // 🔥 NUMBER → ID SEARCH
            if(keyword.matches("\\d+")){
                sql = "SELECT * FROM tasks WHERE user_id=? AND task_id=?";
            } else {
                sql = "SELECT * FROM tasks WHERE user_id=? AND (task_title LIKE ? OR description LIKE ?)";
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,currentUser.getUserId());

            if(keyword.matches("\\d+")){
                ps.setInt(2, Integer.parseInt(keyword));
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

            DefaultTableModel model = (DefaultTableModel) taskTable.getModel();
            model.setRowCount(0);

            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getInt("task_id"),
                        rs.getString("task_title"),
                        rs.getString("description"),
                        rs.getString("due_date"),
                        rs.getString("status")
                });
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // UPCOMING
    public void loadUpcomingTasks(){
        try{
            Connection con = DBConnection.getConnection();

            String sql = "SELECT task_title, due_date FROM tasks WHERE user_id=? ORDER BY due_date ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, currentUser.getUserId());

            ResultSet rs = ps.executeQuery();

            StringBuilder msg = new StringBuilder();

            while(rs.next()){
                String title = rs.getString("task_title");
                Date dueDate = rs.getDate("due_date");

                long diff = dueDate.getTime() - new Date().getTime();
                long days = diff / (1000 * 60 * 60 * 24);

                if(days >= 0 && days <= 3){
                    msg.append("• ").append(title)
                            .append(" - ")
                            .append(days == 0 ? "Today" :
                                    days == 1 ? "Tomorrow" :
                                            days + " days left")
                            .append("\n");
                }
            }

            upcomingArea.setText(msg.toString());

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // REMINDER
    public void checkReminders(){
        try{
            Connection con = DBConnection.getConnection();

            String sql = "SELECT task_title, due_date FROM tasks WHERE user_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, currentUser.getUserId());

            ResultSet rs = ps.executeQuery();

            StringBuilder msg = new StringBuilder();

            while(rs.next()){
                String title = rs.getString("task_title");
                Date dueDate = rs.getDate("due_date");

                long diff = dueDate.getTime() - new Date().getTime();
                long days = diff / (1000 * 60 * 60 * 24);

                if(days == 0){
                    msg.append("🔔 Today: ").append(title).append("\n");
                } else if(days == 1){
                    msg.append("⏰ Tomorrow: ").append(title).append("\n");
                }
            }

            if(msg.length() > 0){
                JOptionPane.showMessageDialog(this, msg.toString(),
                        "Task Reminder", JOptionPane.INFORMATION_MESSAGE);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // DELETE
    private void deleteTask(){
        int row = taskTable.getSelectedRow();

        if(row == -1){
            JOptionPane.showMessageDialog(this,"Select a task!");
            return;
        }

        int id = getSelectedTaskId(row);

        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM tasks WHERE task_id=?");
            ps.setInt(1,id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,"Deleted!");
            loadTasks();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void refreshTable(){
        loadTasks();
        loadUpcomingTasks();
    }
}

// 🎨 HIGHLIGHT
class TaskCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        try {
            String dateStr = table.getValueAt(row, 3).toString();
            Date dueDate = java.sql.Date.valueOf(dateStr);

            long diff = dueDate.getTime() - new Date().getTime();
            long days = diff / (1000 * 60 * 60 * 24);

            if (days == 0) {
                c.setBackground(Color.RED);
                c.setForeground(Color.WHITE);
            } else if (days == 1) {
                c.setBackground(Color.ORANGE);
            } else {
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
            }

        } catch (Exception e) {
            c.setBackground(Color.WHITE);
        }

        return c;
    }
}