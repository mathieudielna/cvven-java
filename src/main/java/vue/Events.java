/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import modele.Session;

/**
 *
 * @author senpai
 */
public class Events extends javax.swing.JFrame {

    /**
     * Creates new form Events
     */
    public Events() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        navBar = new javax.swing.JMenuBar();
        accueilNav = new javax.swing.JMenu();
        inputEventNav = new javax.swing.JMenu();
        inputParticipantNav = new javax.swing.JMenu();
        DisplayEventNav = new javax.swing.JMenu();
        deconnexionNav = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Events");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        navBar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        navBar.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N

        accueilNav.setText("Home");
        accueilNav.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        accueilNav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accueilNavMouseClicked(evt);
            }
        });
        navBar.add(accueilNav);

        inputEventNav.setText("Add Event");
        inputEventNav.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        inputEventNav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inputEventNavMouseClicked(evt);
            }
        });
        navBar.add(inputEventNav);

        inputParticipantNav.setText("Add Participant");
        inputParticipantNav.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        inputParticipantNav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inputParticipantNavMouseClicked(evt);
            }
        });
        navBar.add(inputParticipantNav);

        DisplayEventNav.setText("Events");
        DisplayEventNav.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        DisplayEventNav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DisplayEventNavMouseClicked(evt);
            }
        });
        navBar.add(DisplayEventNav);

        deconnexionNav.setText("Disconnect");
        deconnexionNav.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        deconnexionNav.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deconnexionNavMouseClicked(evt);
            }
        });
        navBar.add(deconnexionNav);

        setJMenuBar(navBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void accueilNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accueilNavMouseClicked
        Home fen = new Home();
        fen.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_accueilNavMouseClicked

    private void inputEventNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inputEventNavMouseClicked
        AddEvent fen = new AddEvent();
        //if(fen.setValueEvent()){
            fen.setVisible(true);
            this.dispose();
        //}else {
            //fen.dispose();
        //}
    }//GEN-LAST:event_inputEventNavMouseClicked

    private void inputParticipantNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inputParticipantNavMouseClicked
        AddParticipant fen = new AddParticipant();
        //if(fen.setValueParticipant()){
            fen.setVisible(true);
            this.dispose();
        //}else{
            //fen.dispose();
        //}
    }//GEN-LAST:event_inputParticipantNavMouseClicked

    private void DisplayEventNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DisplayEventNavMouseClicked
        Events fen = new Events();
        fen.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_DisplayEventNavMouseClicked

    private void deconnexionNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deconnexionNavMouseClicked
        Connection fen = new Connection();
        fen.setVisible(true);
        Session.destructSession();
        this.dispose();
    }//GEN-LAST:event_deconnexionNavMouseClicked

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
            java.util.logging.Logger.getLogger(Events.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Events.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Events.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Events.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Events().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu DisplayEventNav;
    private javax.swing.JMenu accueilNav;
    private javax.swing.JMenu deconnexionNav;
    private javax.swing.JMenu inputEventNav;
    private javax.swing.JMenu inputParticipantNav;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JMenuBar navBar;
    // End of variables declaration//GEN-END:variables
}
