package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Dashboard extends JFrame {

    private User currentUser;

    public Dashboard(User user){
        this.currentUser = user;
        setTitle("Dashboard - SmartNotes");
        setSize(800,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null); // Absolute layout for modern design

        try {
            // Background Image
            String bgUrl = "https://images.unsplash.com/photo-1498050108023-c5249f4df085?auto=format&fit=crop&w=800&q=80";
            ImageIcon bgIcon = new ImageIcon(new URL(bgUrl));
            JLabel bg = new JLabel(bgIcon);
            bg.setBounds(0,0,800,500);
            add(bg);

            // Transparent Panel
            JPanel panel = new JPanel();
            panel.setBounds(150,50,500,380);
            panel.setBackground(new Color(0,0,0,150)); // semi-transparent black
            panel.setLayout(new GridLayout(2,2,30,30));
            bg.add(panel);

            // Buttons with modern colors
            JButton btnNotes = new JButton("Manage Notes");
            JButton btnTasks = new JButton("Manage Tasks");
            JButton btnReports = new JButton("Reports");
            JButton btnExit = new JButton("Exit");

            Color blue = new Color(52,152,219);
            Color green = new Color(46,204,113);
            Color red = new Color(231,76,60);

            // Set button styles
            JButton[] buttons = {btnNotes, btnTasks, btnReports, btnExit};
            Color[] colors = {blue, green, blue, red};
            for(int i=0;i<buttons.length;i++){
                buttons[i].setBackground(colors[i]);
                buttons[i].setForeground(Color.WHITE);
                buttons[i].setFont(new Font("Segoe UI", Font.BOLD, 18));
                buttons[i].setFocusPainted(false);
                buttons[i].setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                // Hover effect
                int finalI = i;
                buttons[i].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        buttons[finalI].setBackground(colors[finalI].darker());
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        buttons[finalI].setBackground(colors[finalI]);
                    }
                });
                panel.add(buttons[i]);
            }

            // Welcome Label
            JLabel lblWelcome = new JLabel("Welcome, "+currentUser.getName());
            lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
            lblWelcome.setForeground(Color.WHITE);
            lblWelcome.setBounds(0,10,800,40);
            lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
            bg.add(lblWelcome);

            // Button Actions
            btnNotes.addActionListener(e -> new NotesManagement(currentUser).setVisible(true));
            btnExit.addActionListener(e -> System.exit(0));
            btnTasks.addActionListener(e -> {
                new TaskManagement(currentUser).setVisible(true);
            });
            btnReports.addActionListener(e ->
                    new TaskReport(currentUser).setVisible(true));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}