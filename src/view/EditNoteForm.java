package view;

import controller.Noteeditcontroller;
import model.Noteedit;
import model.User;

import javax.swing.*;
import java.awt.*;

public class EditNoteForm extends JFrame {

    public EditNoteForm(User user, NotesManagement parent,
                        int noteId, String title, String content){

        setTitle("Edit Note");
        setSize(500,400);
        setLocationRelativeTo(null);

        try {
            String bgUrl = "https://images.unsplash.com/photo-1498050108023-c5249f4df085";
            ImageIcon bgIcon = new ImageIcon(new java.net.URL(bgUrl));
            Image img = bgIcon.getImage().getScaledInstance(500,400,Image.SCALE_SMOOTH);

            JLabel bg = new JLabel(new ImageIcon(img));
            bg.setLayout(null);
            setContentPane(bg);

            JPanel panel = new JPanel();
            panel.setBounds(40,30,420,320);
            panel.setBackground(new Color(0,0,0,170));
            panel.setLayout(null);
            bg.add(panel);

            JLabel lblMain = new JLabel("Edit Note");
            lblMain.setFont(new Font("Segoe UI",Font.BOLD,22));
            lblMain.setForeground(Color.WHITE);
            lblMain.setBounds(140,10,200,30);
            panel.add(lblMain);

            JLabel lblTitle = new JLabel("Title");
            lblTitle.setForeground(Color.WHITE);
            lblTitle.setBounds(20,50,100,25);
            panel.add(lblTitle);

            JTextField txtTitle = new JTextField(title);
            txtTitle.setBounds(20,75,370,30);
            panel.add(txtTitle);

            JLabel lblContent = new JLabel("Content");
            lblContent.setForeground(Color.WHITE);
            lblContent.setBounds(20,115,100,25);
            panel.add(lblContent);

            JTextArea txtContent = new JTextArea(content);
            JScrollPane scroll = new JScrollPane(txtContent);
            scroll.setBounds(20,140,370,100);
            panel.add(scroll);

            JButton btnUpdate = new JButton("Update");
            btnUpdate.setBounds(60,260,140,35);
            panel.add(btnUpdate);

            JButton btnCancel = new JButton("Cancel");
            btnCancel.setBounds(220,260,140,35);
            panel.add(btnCancel);

            btnCancel.addActionListener(e -> dispose());

            // ✅ MVC Update
            btnUpdate.addActionListener(e -> {

                Noteedit note = new Noteedit(
                        noteId,
                        txtTitle.getText(),
                        txtContent.getText()
                );

                Noteeditcontroller controller = new Noteeditcontroller();

                boolean success = controller.updateNote(note);

                if(success){
                    JOptionPane.showMessageDialog(this,"Note updated!");
                    parent.loadNotes();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,"Update failed!");
                }
            });

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
