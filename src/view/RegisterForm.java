package view;

import controller.RegisterController;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class RegisterForm extends JFrame {

    private JTextField txtName, txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnBack;

    public RegisterForm(){

        setTitle("SmartNotes Register");
        setSize(800,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        try {
            // Background Image URL
            String bgUrl = "https://images.unsplash.com/photo-1498050108023-c5249f4df085?auto=format&fit=crop&w=800&q=80";

            // Load and scale background image
            ImageIcon bgIcon = new ImageIcon(new URL(bgUrl));
            Image bgImage = bgIcon.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
            JLabel bg = new JLabel(new ImageIcon(bgImage));
            bg.setBounds(0,0,800,500);
            add(bg);

            // 🔹 BIGGER PANEL + semi-transparent color
            JPanel panel = new JPanel();
            panel.setBounds(220,50,360,400);
            panel.setBackground(new Color(20, 30, 60, 200)); // dark semi-transparent
            panel.setLayout(null);
            bg.add(panel);

            // Title
            JLabel lblTitle = new JLabel("Create Account");
            lblTitle.setFont(new Font("Segoe UI",Font.BOLD,26));
            lblTitle.setForeground(Color.WHITE);
            lblTitle.setBounds(80,20,250,40);
            panel.add(lblTitle);

            // Name
            JLabel lblName = new JLabel("Name");
            lblName.setForeground(Color.WHITE);
            lblName.setBounds(40,80,100,25);
            panel.add(lblName);

            txtName = new JTextField();
            txtName.setBounds(40,105,260,35);
            panel.add(txtName);

            // Email
            JLabel lblEmail = new JLabel("Email");
            lblEmail.setForeground(Color.WHITE);
            lblEmail.setBounds(40,150,100,25);
            panel.add(lblEmail);

            txtEmail = new JTextField();
            txtEmail.setBounds(40,175,260,35);
            panel.add(txtEmail);

            // Password
            JLabel lblPass = new JLabel("Password");
            lblPass.setForeground(Color.WHITE);
            lblPass.setBounds(40,220,100,25);
            panel.add(lblPass);

            txtPassword = new JPasswordField();
            txtPassword.setBounds(40,245,260,35);
            panel.add(txtPassword);

            // Register Button
            btnRegister = new JButton("Register");
            btnRegister.setBounds(60,300,240,40);
            btnRegister.setBackground(new Color(46,204,113));
            btnRegister.setForeground(Color.WHITE);
            btnRegister.setFocusPainted(false);
            panel.add(btnRegister);

            // Back Button
            btnBack = new JButton("Back to Login");
            btnBack.setBounds(60,350,240,35);
            btnBack.setBackground(new Color(52,152,219));
            btnBack.setForeground(Color.WHITE);
            btnBack.setFocusPainted(false);
            panel.add(btnBack);

            // Hover Effects
            btnRegister.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btnRegister.setBackground(new Color(39,174,96));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btnRegister.setBackground(new Color(46,204,113));
                }
            });

            btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btnBack.setBackground(new Color(41,128,185));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btnBack.setBackground(new Color(52,152,219));
                }
            });

            // Actions
            btnRegister.addActionListener(e -> {
                String name = txtName.getText();
                String email = txtEmail.getText();
                String pass = new String(txtPassword.getPassword());

                RegisterController rc = new RegisterController();
                boolean result = rc.register(name,email,pass);

                if(result){
                    JOptionPane.showMessageDialog(this,"Registered Successfully!");
                    new LoginForm().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,"Registration Failed!");
                }
            });

            btnBack.addActionListener(e -> {
                new LoginForm().setVisible(true);
                dispose();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}