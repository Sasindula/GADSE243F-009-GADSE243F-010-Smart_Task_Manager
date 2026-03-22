package view;

import controller.NoteController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AddNoteForm extends JFrame {

    private NoteController controller = new NoteController();

    public AddNoteForm(User user, NotesManagement parent){

        setTitle("Add Note");
        setSize(400,300);
        setLocationRelativeTo(null);

        try{
            String bgUrl = "https://images.unsplash.com/photo-1498050108023-c5249f4df085";
            Image img = new ImageIcon(new URL(bgUrl))
                    .getImage().getScaledInstance(400,300,Image.SCALE_SMOOTH);

            JLabel bg = new JLabel(new ImageIcon(img));
            bg.setLayout(null);
            setContentPane(bg);

            JPanel panel = new JPanel();
            panel.setBounds(20,20,360,240);
            panel.setBackground(new Color(0,0,0,170));
            panel.setLayout(null);
            bg.add(panel);

            JLabel lbl = new JLabel("Add Note");
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Segoe UI",Font.BOLD,20));
            lbl.setBounds(120,10,200,30);
            panel.add(lbl);

            JLabel lbl1 = new JLabel("Title");
            lbl1.setForeground(Color.WHITE);
            lbl1.setBounds(30,40,100,20);
            panel.add(lbl1);

            JTextField txtTitle = new JTextField();
            txtTitle.setBounds(30,60,300,30);
            panel.add(txtTitle);

            JLabel lbl2 = new JLabel("Content");
            lbl2.setForeground(Color.WHITE);
            lbl2.setBounds(30,85,100,20);
            panel.add(lbl2);

            JTextArea txtContent = new JTextArea();
            JScrollPane scroll = new JScrollPane(txtContent);
            scroll.setBounds(30,105,300,60);
            panel.add(scroll);

            JButton btnSave = new JButton("Save");
            JButton btnCancel = new JButton("Cancel");

            btnSave.setBounds(50,180,100,30);
            btnCancel.setBounds(200,180,100,30);

            panel.add(btnSave);
            panel.add(btnCancel);

            btnCancel.addActionListener(e -> dispose());

            btnSave.addActionListener(e -> {

                boolean success = controller.saveNote(
                        txtTitle.getText(),
                        txtContent.getText(),
                        user.getUserId()
                );

                if(success){
                    JOptionPane.showMessageDialog(this,"Saved!");
                    parent.refreshTable();
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(this,"Error! Fill all fields.");
                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
