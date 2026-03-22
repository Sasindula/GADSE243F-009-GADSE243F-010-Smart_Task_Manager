package view;

import controller.TaskReportController;
import model.TaskReportModel;
import model.Task;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class TaskReport extends JFrame {

    private User currentUser;
    private JLabel lblTotal, lblCompleted, lblPending, lblOverdue, lblRate;
    private JProgressBar progressBar;
    private JTextArea upcomingArea;

    public TaskReport(User user){
        this.currentUser = user;
        setTitle("Task Report Dashboard");
        setSize(900,600);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try {
            // 🌄 Background image
            String bgUrl = "https://images.unsplash.com/photo-1517245386807-bb43f82c33c4";
            Image img = new ImageIcon(new java.net.URL(bgUrl))
                    .getImage().getScaledInstance(900,600,Image.SCALE_SMOOTH);
            JLabel background = new JLabel(new ImageIcon(img));
            background.setBounds(0,0,900,600);
            setContentPane(background);
            background.setLayout(null);

            // 🔳 Transparent summary panel
            JPanel summaryPanel = new JPanel(null);
            summaryPanel.setBounds(30,30,830,160);
            summaryPanel.setBackground(new Color(255,255,255,180));
            background.add(summaryPanel);

            lblTotal = createCard(summaryPanel,"Total Tasks",30,20);
            lblCompleted = createCard(summaryPanel,"Completed",220,20);
            lblPending = createCard(summaryPanel,"Pending",410,20);
            lblOverdue = createCard(summaryPanel,"Overdue",600,20);

            lblRate = new JLabel("Completion Rate:",SwingConstants.CENTER);
            lblRate.setBounds(30,100,540,30);
            lblRate.setFont(new Font("Segoe UI",Font.BOLD,16));
            summaryPanel.add(lblRate);

            progressBar = new JProgressBar();
            progressBar.setBounds(600,100,200,30);
            progressBar.setStringPainted(true);
            progressBar.setForeground(new Color(76,175,80));
            summaryPanel.add(progressBar);

            // 📋 Table panel
            JPanel tablePanel = new JPanel(null);
            tablePanel.setBounds(30,210,830,300);
            tablePanel.setBackground(new Color(255,255,255,200));
            background.add(tablePanel);

            JLabel tblLabel = new JLabel("Task Details");
            tblLabel.setFont(new Font("Segoe UI",Font.BOLD,18));
            tblLabel.setBounds(10,10,200,25);
            tablePanel.add(tblLabel);

            String[] cols = {"Title","Due Date","Status"};
            JTable table = new JTable(new javax.swing.table.DefaultTableModel(cols,0));
            JScrollPane scroll = new JScrollPane(table);
            scroll.setBounds(10,45,810,220);
            tablePanel.add(scroll);

            // 📋 Upcoming tasks panel
            JPanel upcomingPanel = new JPanel(null);
            upcomingPanel.setBounds(30,520,830,60);
            upcomingPanel.setBackground(new Color(255,255,255,180));
            background.add(upcomingPanel);

            JLabel upLabel = new JLabel("Upcoming Tasks (next 3 days):");
            upLabel.setBounds(10,10,300,25);
            upcomingPanel.add(upLabel);

            upcomingArea = new JTextArea();
            upcomingArea.setBounds(300,10,520,40);
            upcomingArea.setEditable(false);
            upcomingPanel.add(upcomingArea);

            // Load data
            TaskReportController controller = new TaskReportController();
            TaskReportModel data = controller.getSummary(currentUser.getUserId());

            if(data != null){
                lblTotal.setText("Total: " + data.getTotal());
                lblCompleted.setText("Completed: " + data.getCompleted());
                lblPending.setText("Pending: " + data.getPending());
                lblOverdue.setText("Overdue: " + data.getOverdue());

                int rate = (data.getTotal()==0) ? 0 : (data.getCompleted()*100/data.getTotal());
                lblRate.setText("Completion Rate: " + rate + "%");
                progressBar.setValue(rate);
            }

            // Fill task table
            List<Task> tasks = controller.getAllTasks(currentUser.getUserId());
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel)table.getModel();
            model.setRowCount(0);
            for(Task t : tasks){
                model.addRow(new Object[]{
                        t.getTitle(),
                        sdf.format(t.getDueDate()),
                        t.getStatus()
                });
            }

            // Fill upcoming tasks
            List<Task> upcoming = controller.getUpcomingTasks(currentUser.getUserId());
            StringBuilder sb = new StringBuilder();
            for(Task t : upcoming){
                sb.append("• ").append(t.getTitle()).append(" - ").append(sdf.format(t.getDueDate())).append("  ");
            }
            upcomingArea.setText(sb.toString());

        }catch(Exception e){ e.printStackTrace(); }
    }

    private JLabel createCard(JPanel panel, String title, int x, int y){
        JLabel lbl = new JLabel(title+": 0",SwingConstants.CENTER);
        lbl.setBounds(x,y,180,50);
        lbl.setFont(new Font("Segoe UI",Font.BOLD,16));
        lbl.setOpaque(true);
        lbl.setBackground(new Color(255,255,255,200));
        lbl.setBorder(BorderFactory.createLineBorder(new Color(200,200,200),1));
        panel.add(lbl);
        return lbl;
    }
}