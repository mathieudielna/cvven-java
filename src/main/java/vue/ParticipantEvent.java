/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import modele.DialogTools;
import modele.EventManagement;

/**
 *
 * @author senpai
 */
public class ParticipantEvent extends javax.swing.JFrame {

    /**
     * Creates new form ParticipantEvent
     */
    public ParticipantEvent() {
        initComponents();
    }

    public final boolean setValueEventParticipant(int id) {
        try {
            EventManagement EventManagement = new EventManagement();
            EventManagement.setDb();
            
            //fonction à modfiier ici 
            ResultSet rs = EventManagement.selectParticipantEventById(id);
           
            DefaultTableModel participantE = (DefaultTableModel) participantEvent.getModel();
            participantE.setRowCount(0);

            if (rs != null) {
               
                participantE.addRow(new Object[]{rs.getString("id_participant"), rs.getString("firstname"), rs.getString("lastname"),
                    rs.getString("birthd"), rs.getString("organisation"),
                    rs.getString("observation"), rs.getString("email"),
                    rs.getString("id_user")});
                if (rs.next()) {
                    //set up objet
                    do {
                        participantE.addRow(new Object[]{rs.getString("id_participant"), rs.getString("firstname"), rs.getString("lastname"),
                            rs.getString("birthd"), rs.getString("organisation"),
                            rs.getString("observation"), rs.getString("email"),
                            rs.getString("id_user")});
                    } while (rs.next());
                }
                EventManagement.closeAll();
                return true;
            }

        } catch (SQLException | ClassNotFoundException ex) {
            DialogTools.openMessageDialog(ex.getMessage(), "Erreur", DialogTools.ERROR_MESSAGE);
            return false;
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        participantEvent = new javax.swing.JTable();
        retour = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        participantEvent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "firstName", "lastName", "birthD", "organisation", "observation", "email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(participantEvent);

        retour.setText("retour");
        retour.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                retourMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGap(392, 392, 392)
                .addComponent(retour)
                .addContainerGap(394, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(retour)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void retourMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_retourMouseClicked
        Events fen = new Events();
        AddEvent fen2 = new AddEvent();
        if (fen.setValueEventArchivement()) {
            fen.setVisible(true);
            this.dispose();
        } else {
            fen.dispose();
            DialogTools.openMessageDialog("Aucun evenement, veuillez en insérer un !!!", "INFO", DialogTools.WARNING_MESSAGE);
            fen2.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_retourMouseClicked

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
            java.util.logging.Logger.getLogger(ParticipantEvent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ParticipantEvent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ParticipantEvent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ParticipantEvent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ParticipantEvent().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable participantEvent;
    private javax.swing.JButton retour;
    // End of variables declaration//GEN-END:variables
}