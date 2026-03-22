package view;

import controller.EditTaskController;
import model.EditTask;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class EditTaskForm extends JFrame {

    public EditTaskForm(User user, TaskManagement parent,
                        int id, String title, String desc,
                        String date, String status){

        setTitle("Edit Task");
        setSize(450,350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try{
            // 🌄 Background Image
            String bgUrl = "https://images.unsplash.com/photo-1498050108023-c5249f4df085";
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

            // 🔤 Title
            JLabel lblTitleTop = new JLabel("Edit Task");
            lblTitleTop.setForeground(Color.WHITE);
            lblTitleTop.setFont(new Font("Segoe UI",Font.BOLD,20));
            lblTitleTop.setBounds(130,10,200,30);
            panel.add(lblTitleTop);

            // 🏷 Labels
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

            // 🧾 Fields
            JTextField txtTitle = new JTextField(title);
            txtTitle.setBounds(120,50,220,25);
            panel.add(txtTitle);

            JTextArea txtDesc = new JTextArea(desc);
            JScrollPane scroll = new JScrollPane(txtDesc);
            scroll.setBounds(120,100,220,60);
            panel.add(scroll);

            JTextField txtDate = new JTextField(date);
            txtDate.setBounds(120,170,220,25);
            panel.add(txtDate);

            String[] statusList = {"Pending","Completed"};
            JComboBox<String> cmbStatus = new JComboBox<>(statusList);
            cmbStatus.setSelectedItem(status);
            cmbStatus.setBounds(120,210,220,25);
            panel.add(cmbStatus);

            // 🔘 Buttons (FIXED)
            JButton btnUpdate = new JButton("Update");
            btnUpdate.setBounds(60,240,100,30);
            btnUpdate.setBackground(new Color(46,204,113));
            btnUpdate.setForeground(Color.WHITE);
            btnUpdate.setFocusPainted(false);
            panel.add(btnUpdate);

            JButton btnCancel = new JButton("Cancel");
            btnCancel.setBounds(200,240,100,30);
            btnCancel.setBackground(new Color(231,76,60));
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setFocusPainted(false);
            panel.add(btnCancel);

            // ❌ Cancel
            btnCancel.addActionListener(e -> dispose());

            // ✅ Update (MVC + DAO)
            btnUpdate.addActionListener(e -> {

                EditTask task = new EditTask(
                        id,
                        txtTitle.getText(),
                        txtDesc.getText(),
                        txtDate.getText(),
                        cmbStatus.getSelectedItem().toString()
                );

                EditTaskController controller = new EditTaskController();

                boolean success = controller.updateTask(task);

                if(success){
                    JOptionPane.showMessageDialog(this,"Task Updated!");
                    parent.refreshTable();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,"Update Failed!");
                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
