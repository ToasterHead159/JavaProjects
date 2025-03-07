
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class DelF extends javax.swing.JFrame {

    public final String url = "jdbc:sqlite:TaskStorageDB";
    ArrayList<TaskClass> TaskList = new ArrayList<TaskClass>();

    public void Searching(String CatName, String UserValue) {
        File alive = new File("taskstoragedb");
        if (alive.exists()) {
            try {
                Connection connect = DriverManager.getConnection(url);
                String search = "SELECT * FROM TASKS WHERE " + CatName + " LIKE '" + UserValue + "%';";
                Statement SEARCHING = connect.createStatement();
                ResultSet SetResult = SEARCHING.executeQuery(search);
                DefaultComboBoxModel comboMod = (DefaultComboBoxModel) ResultCombo.getModel();
                int itemnum = 0;

                while (SetResult.next()) {
                    
                    TaskClass tasks = new TaskClass(SetResult.getInt("ID"), SetResult.getString("Name"), SetResult.getString("Description"), SetResult.getString("Status"), SetResult.getString("Category"));
                    TaskList.add(tasks);
                    comboMod.insertElementAt(tasks, itemnum);
                    itemnum++;
                }

                SetResult.close();
                SEARCHING.close();
                connect.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage());
            }
        }
    }

    public DelF() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        DelBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        SearchTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        colSelComb = new javax.swing.JComboBox<>();
        SearchBtn = new javax.swing.JButton();
        ResultCombo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();

        setTitle("Delete a Task");
        setLocation(new java.awt.Point(750, 150));
        setMaximumSize(new java.awt.Dimension(477, 412));
        setMinimumSize(new java.awt.Dimension(477, 412));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Franklin Gothic Demi", 0, 36)); // NOI18N
        jLabel1.setText("Delete a Task");

        DelBtn.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        DelBtn.setText("Delete");
        DelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DelBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Franklin Gothic Demi", 0, 18)); // NOI18N
        jLabel2.setText("Search:");

        jLabel3.setFont(new java.awt.Font("Franklin Gothic Demi", 0, 18)); // NOI18N
        jLabel3.setText("Search by:");

        colSelComb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Task Name", "Description", "Status", "Category" }));
        colSelComb.setSelectedIndex(-1);

        SearchBtn.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        SearchBtn.setText("Search");
        SearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBtnActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Franklin Gothic Demi", 0, 18)); // NOI18N
        jLabel4.setText("Select the task to delete:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(DelBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(18, 18, 18)
                                    .addComponent(colSelComb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(SearchBtn))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18)
                                    .addComponent(SearchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(ResultCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(SearchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(colSelComb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchBtn))
                .addGap(19, 19, 19)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ResultCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(DelBtn)
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBtnActionPerformed
        if (SearchTxt.getText().isBlank() || colSelComb.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Search or search by cannot be empty!");
        } else {
            ResultCombo.removeAllItems();
            if (colSelComb.getSelectedItem() == "Task Name") {
                Searching("Name", SearchTxt.getText());
            } else if (colSelComb.getSelectedItem() == "Description") {
                Searching("Description", SearchTxt.getText());
            } else if (colSelComb.getSelectedItem() == "Status") {
                Searching("Status", SearchTxt.getText());
            } else if (colSelComb.getSelectedItem() == "Category") {
                Searching("Category", SearchTxt.getText());
            }
        }
        SearchTxt.setText("");
        colSelComb.setSelectedIndex(-1);
    }//GEN-LAST:event_SearchBtnActionPerformed

    private void DelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DelBtnActionPerformed
        TaskClass task = (TaskClass) ResultCombo.getSelectedItem();
        int spectaskID = task.getID();
        File alive = new File("taskstoragedb");
        if (alive.exists()){
            try {
                Connection connect = DriverManager.getConnection(url);
                String DelRow = "Delete From Tasks Where ID = " + spectaskID;
                Statement DelStmt = connect.createStatement();
                DelStmt.execute(DelRow);
                JOptionPane.showMessageDialog(rootPane, "Task Deleted!");
                
                DelStmt.close();
                connect.close();
                
                ResultCombo.setSelectedIndex(-1);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage());
            }
        }
    }//GEN-LAST:event_DelBtnActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        ImageIcon icon = new ImageIcon("src/FA2.png");
        this.setIconImage(icon.getImage());
    }//GEN-LAST:event_formWindowActivated

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DelF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DelF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DelF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DelF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DelF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DelBtn;
    private javax.swing.JComboBox<String> ResultCombo;
    private javax.swing.JButton SearchBtn;
    private javax.swing.JTextField SearchTxt;
    private javax.swing.JComboBox<String> colSelComb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
