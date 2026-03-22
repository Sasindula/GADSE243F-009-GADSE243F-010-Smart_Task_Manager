package view;

import controller.LoginController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class LoginForm extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister;

    public LoginForm() {

        setTitle("SmartNotes Login");
        setSize(800,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        try {
            // 🌐 Background Image URL
            String bgUrl = "https://images.unsplash.com/photo-1498050108023-c5249f4df085?auto=format&fit=crop&w=800&q=80";

            // Background
            ImageIcon bgIcon = new ImageIcon(new URL(bgUrl));
            JLabel bg = new JLabel();
            bg.setIcon(new ImageIcon(bgIcon.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH)));
            bg.setBounds(0,0,800,500);
            add(bg);

            // 🔹 Bigger Panel with modern color
            JPanel panel = new JPanel();
            panel.setBounds(220,80,360,340); // bigger panel
            panel.setBackground(new Color(20, 30, 60, 200)); // semi-transparent dark blue
            panel.setLayout(null);
            bg.add(panel);

            // Title
            JLabel lblLogo = new JLabel("SmartNotes");
            lblLogo.setFont(new Font("Segoe UI",Font.BOLD,26));
            lblLogo.setForeground(Color.WHITE);
            lblLogo.setBounds(100,20,200,40);
            panel.add(lblLogo);

            // Email Label & Field
            JLabel lblEmail = new JLabel("Email");
            lblEmail.setForeground(Color.WHITE);
            lblEmail.setBounds(40,80,80,25);
            panel.add(lblEmail);

            txtEmail = new JTextField();
            txtEmail.setBounds(40,110,280,35);
            panel.add(txtEmail);

            // Password Label & Field
            JLabel lblPass = new JLabel("Password");
            lblPass.setForeground(Color.WHITE);
            lblPass.setBounds(40,160,80,25);
            panel.add(lblPass);

            txtPassword = new JPasswordField();
            txtPassword.setBounds(40,190,280,35);
            panel.add(txtPassword);

            // Login Button
            btnLogin = new JButton("Login");
            btnLogin.setBounds(60,250,260,40);
            btnLogin.setBackground(new Color(52,152,219));
            btnLogin.setForeground(Color.WHITE);
            btnLogin.setFocusPainted(false);
            panel.add(btnLogin);

            // Register Button
            btnRegister = new JButton("Register");
            btnRegister.setBounds(60,300,260,40);
            btnRegister.setBackground(new Color(46,204,113));
            btnRegister.setForeground(Color.WHITE);
            btnRegister.setFocusPainted(false);
            panel.add(btnRegister);

            // Hover Effects
            btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btnLogin.setBackground(new Color(41,128,185));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btnLogin.setBackground(new Color(52,152,219));
                }
            });

            btnRegister.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btnRegister.setBackground(new Color(39,174,96));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btnRegister.setBackground(new Color(46,204,113));
                }
            });

            // Actions
            btnLogin.addActionListener(e -> {
                String email = txtEmail.getText();
                String pass = new String(txtPassword.getPassword());

                LoginController lc = new LoginController();
                User user = lc.login(email, pass);

                if(user != null){
                    new Dashboard(user).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,"Invalid Login!");
                }
            });

            btnRegister.addActionListener(e -> {
                new RegisterForm().setVisible(true);
                dispose();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}