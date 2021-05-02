/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.formatDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modele.DialogTools;
import modele.EventManagement;
import modele.Session;
import static org.apache.commons.validator.GenericTypeValidator.formatDate;
import static org.apache.commons.validator.GenericTypeValidator.formatDate;

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

    private void setDefaultValue() {
        tableEvent.clearSelection();
        entitled.setText(null);
        theme.setText(null);
        idEvent.setText(null);
        dateEvent.setDate(null);
        durationEvent.setValue(15);
        organisateur.setText(null);
        roomSelectEvent.setSelectedIndex(0);
        typeEvent.setSelectedIndex(0);
        descriptionEvent.setText(null);
        nbCharDescEvent.setText("0/255");
    }

    public final boolean setValueEventArchivement() {
        try {
            EventManagement EventManagement = new EventManagement();
            EventManagement.setDb();
            ResultSet rs = EventManagement.selectAllEventNArchive();
            //System.out.println(rs);
            DefaultTableModel tbModel = (DefaultTableModel) tableEvent.getModel();
            tbModel.setRowCount(0);

            if (rs != null) {
                //rs.absolute(1);
                tbModel.addRow(new Object[]{rs.getString("id_event"), rs.getString("theme"), rs.getString("date"),
                    rs.getString("duration"), rs.getString("participantmax"),
                    rs.getString("decription"), rs.getString("organisateur"),
                    rs.getString("type"), rs.getString("entitled"), rs.getString("id_room")});
                if (rs.next()) {
                    //set up objet
                    do {
                        tbModel.addRow(new Object[]{rs.getString("id_event"), rs.getString("theme"), rs.getString("date"),
                            rs.getString("duration"), rs.getString("participantmax"),
                            rs.getString("decription"), rs.getString("organisateur"),
                            rs.getString("type"), rs.getString("entitled"), rs.getString("id_room")});
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableEvent = new javax.swing.JTable();
        themeL = new javax.swing.JLabel();
        DurationL = new javax.swing.JLabel();
        dateL = new javax.swing.JLabel();
        pmL = new javax.swing.JLabel();
        organisateur = new javax.swing.JTextField();
        theme = new javax.swing.JTextField();
        dateEvent = new com.toedter.calendar.JDateChooser();
        durationEvent = new javax.swing.JSpinner();
        themeL2 = new javax.swing.JLabel();
        entitled = new javax.swing.JTextField();
        dateL2 = new javax.swing.JLabel();
        typeEvent = new javax.swing.JComboBox<>();
        pmL2 = new javax.swing.JLabel();
        roomSelectEvent = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        descriptionEvent = new javax.swing.JTextArea();
        nbCharDescEvent = new javax.swing.JLabel();
        modifBtn = new javax.swing.JButton();
        deleteEvent = new javax.swing.JButton();
        addBtn = new javax.swing.JButton();
        participeEvent = new javax.swing.JButton();
        label1 = new java.awt.Label();
        infoRoom = new javax.swing.JButton();
        idEvent = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        navBar = new javax.swing.JMenuBar();
        accueilNav = new javax.swing.JMenu();
        inputEventNav = new javax.swing.JMenu();
        inputParticipantNav = new javax.swing.JMenu();
        deconnexionNav = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tableEvent.setAutoCreateRowSorter(true);
        tableEvent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Theme", "Date", "Duration", "ParticipantMax", "Description", "Organisateur", "Type ", "Entitled", "id_room"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableEvent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableEventMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableEvent);

        themeL.setText("Theme : ");

        DurationL.setText("Duration :");

        dateL.setText("Date :");

        pmL.setText("Type de l'événement ");

        organisateur.setToolTipText("");

        dateEvent.setDateFormatString("yyyy-MM-dd");

        durationEvent.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        durationEvent.setModel(new javax.swing.SpinnerNumberModel(15, 15, 240, 1));

        themeL2.setText("Entitled : ");

        dateL2.setText("Organisateur : ");

        typeEvent.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        typeEvent.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "...", "colloques", "séminaires", "congrès" }));
        typeEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeEventActionPerformed(evt);
            }
        });

        pmL2.setText("Salle");

        roomSelectEvent.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        roomSelectEvent.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "...", "1", "2", "3", "4" }));
        roomSelectEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomSelectEventActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel9.setText("Description de l'évènement");

        descriptionEvent.setColumns(20);
        descriptionEvent.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        descriptionEvent.setRows(5);
        descriptionEvent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                descriptionEventKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                descriptionEventKeyTyped(evt);
            }
        });
        jScrollPane5.setViewportView(descriptionEvent);

        nbCharDescEvent.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        nbCharDescEvent.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nbCharDescEvent.setText("0/255");

        modifBtn.setText("Modifier");
        modifBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                modifBtnMouseClicked(evt);
            }
        });
        modifBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifBtnActionPerformed(evt);
            }
        });

        deleteEvent.setText("Supprimer");
        deleteEvent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteEventMouseClicked(evt);
            }
        });

        addBtn.setText("Ajouter un event");
        addBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addBtnMouseClicked(evt);
            }
        });
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        participeEvent.setText("Participant(s) à cette event");
        participeEvent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                participeEventMouseClicked(evt);
            }
        });
        participeEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                participeEventActionPerformed(evt);
            }
        });

        label1.setText("Tableau de bord de l'event");

        infoRoom.setText("info room");
        infoRoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoRoomMouseClicked(evt);
            }
        });
        infoRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoRoomActionPerformed(evt);
            }
        });

        idEvent.setEditable(false);

        jLabel1.setText("id : ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 787, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(dateL)
                                .addGap(24, 24, 24)
                                .addComponent(dateEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DurationL)
                                .addGap(3, 3, 3)
                                .addComponent(durationEvent))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(dateL2)
                                .addGap(18, 18, 18)
                                .addComponent(organisateur))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pmL)
                                    .addComponent(typeEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pmL2)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(roomSelectEvent, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(infoRoom))))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nbCharDescEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(modifBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(addBtn)
                                .addGap(18, 18, 18)
                                .addComponent(deleteEvent))
                            .addComponent(participeEvent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(themeL2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(entitled, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(themeL)
                                        .addGap(12, 12, 12)
                                        .addComponent(theme, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(idEvent))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(30, 30, 30))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(themeL2)
                    .addComponent(entitled)
                    .addComponent(themeL)
                    .addComponent(theme))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(durationEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DurationL)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dateEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateL))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateL2)
                    .addComponent(organisateur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pmL2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pmL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(typeEvent)
                            .addComponent(roomSelectEvent)
                            .addComponent(infoRoom))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(nbCharDescEvent))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modifBtn)
                    .addComponent(deleteEvent)
                    .addComponent(addBtn))
                .addGap(18, 18, 18)
                .addComponent(participeEvent)
                .addGap(163, 163, 163))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        navBar.setBackground(new java.awt.Color(254, 254, 254));
        navBar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        if (fen.setValueEvent()) {
            fen.setVisible(true);
            this.dispose();
        } else {
            fen.dispose();
        }
    }//GEN-LAST:event_inputEventNavMouseClicked

    private void inputParticipantNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inputParticipantNavMouseClicked
        AddParticipant fen = new AddParticipant();
        if (fen.setValueParticipant()) {
            fen.setVisible(true);
            this.dispose();
        } else {
            fen.dispose();
        }
    }//GEN-LAST:event_inputParticipantNavMouseClicked

    private void deconnexionNavMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deconnexionNavMouseClicked
        Connection fen = new Connection();
        fen.setVisible(true);
        Session.destructSession();
        this.dispose();
    }//GEN-LAST:event_deconnexionNavMouseClicked

    private void typeEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeEventActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeEventActionPerformed

    private void roomSelectEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomSelectEventActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roomSelectEventActionPerformed

    private void descriptionEventKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_descriptionEventKeyReleased
        nbCharDescEvent.setText(descriptionEvent.getText().length() + "/255");
    }//GEN-LAST:event_descriptionEventKeyReleased

    private void descriptionEventKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_descriptionEventKeyTyped
        nbCharDescEvent.setText(descriptionEvent.getText().length() + "/255");
    }//GEN-LAST:event_descriptionEventKeyTyped

    private void modifBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modifBtnActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addBtnActionPerformed

    private void participeEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_participeEventActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_participeEventActionPerformed

    private void tableEventMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEventMouseClicked
        // TODO add your handling code here:
        int i = tableEvent.getSelectedRow();
        TableModel model = tableEvent.getModel();

        idEvent.setText(model.getValueAt(i, 0).toString());
        entitled.setText(model.getValueAt(i, 8).toString());
        theme.setText(model.getValueAt(i, 1).toString());

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateEvent.setDate(format.parse(model.getValueAt(i, 2).toString()));
        } catch (ParseException ex) {
            Logger.getLogger(Events.class.getName()).log(Level.SEVERE, null, ex);
        }

        durationEvent.setValue(Integer.parseInt(model.getValueAt(i, 3).toString()));
        descriptionEvent.setText(model.getValueAt(i, 5).toString());
        organisateur.setText(model.getValueAt(i, 6).toString());

        //selection du type d'event
        String typeEv = model.getValueAt(i, 7).toString();

        if (typeEv.equals("colloques")) {
            typeEvent.setSelectedIndex(1);
        } else if (typeEv.equals("séminaires")) {
            typeEvent.setSelectedIndex(2);
        } else if (typeEv.equals("congrès")) {
            typeEvent.setSelectedIndex(3);
        } else {
            DialogTools.openMessageDialog("Le type d'événement ne correspond pas aux critères de l'application", "Erreur", DialogTools.ERROR_MESSAGE);
        }

        // choix salle
        String roomChoice = model.getValueAt(i, 9).toString();

        if (roomChoice.equals("1")) {
            roomSelectEvent.setSelectedIndex(1);
        } else if (roomChoice.equals("2")) {
            roomSelectEvent.setSelectedIndex(2);
        } else if (roomChoice.equals("3")) {
            roomSelectEvent.setSelectedIndex(3);
        } else if (roomChoice.equals("4")) {
            roomSelectEvent.setSelectedIndex(4);
        } else {
            DialogTools.openMessageDialog("Aucune salle ne correspond à la salle entré", "Erreur", DialogTools.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_tableEventMouseClicked

    private void modifBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modifBtnMouseClicked
        // Modification d'un evenement 
        int i = tableEvent.getSelectedRow();
        TableModel model = tableEvent.getModel();
        if (!idEvent.getText().isBlank()) {
            EventManagement em = new EventManagement();
            //valeur max participants par room
            boolean check = false;
            boolean checkV = false;
            boolean champs = false;
            int pm1 = 0;
            try {
                String roomS = (String) roomSelectEvent.getSelectedItem();
                //verif salle 
                if (roomS.equals("1")) {
                    pm1 = 196;
                    check = true;
                } else if (roomS.equals("2")) {
                    pm1 = 50;
                    check = true;
                } else if (roomS.equals("3")) {
                    pm1 = 25;
                    check = true;
                } else if (roomS.equals("4")) {
                    pm1 = 10;
                    check = true;
                } else {
                    DialogTools.openMessageDialog("Veuillez selectionner une salle !", "Erreur", DialogTools.ERROR_MESSAGE);
                    check = false;
                }

                //verif type 
                String typeS = typeEvent.getItemAt(typeEvent.getSelectedIndex());
                if (typeS.equals("...")) {
                    checkV = false;
                    DialogTools.openMessageDialog("Veuillez selectionner un event !", "Erreur", DialogTools.ERROR_MESSAGE);
                } else {
                    checkV = true;
                }

                //verifications des champs
                if (entitled.getText().isBlank()) {
                    champs = false;
                    DialogTools.openMessageDialog("Completer le champs entitled* !", "Erreur", DialogTools.ERROR_MESSAGE);
                } else if (theme.getText().isBlank()) {
                    champs = false;
                    DialogTools.openMessageDialog("Completer le champs theme* !", "Erreur", DialogTools.ERROR_MESSAGE);
                } else if (dateEvent.getDate() == null) {
                    champs = false;
                    DialogTools.openMessageDialog("Completer le champs date* ! !", "Erreur", DialogTools.ERROR_MESSAGE);
                } else if (durationEvent.getValue() == null) {
                    champs = false;
                    DialogTools.openMessageDialog("Completer le champs duration* ! !", "Erreur", DialogTools.ERROR_MESSAGE);
                } else if (organisateur.getText().isBlank()) {
                    champs = false;
                    DialogTools.openMessageDialog("Completer le champs organisateur* ! !", "Erreur", DialogTools.ERROR_MESSAGE);
                } else if (descriptionEvent.getText().isBlank()) {
                    champs = false;
                    DialogTools.openMessageDialog("Completer le champs desription* ! !", "Erreur", DialogTools.ERROR_MESSAGE);
                } else {
                    champs = true;
                }

                //si type event et salle ok
                if (check && checkV && champs) {
                    em.setDb();
                    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                    em.updateEvent(Integer.valueOf(idEvent.getText()), theme.getText(), formatDate.format(dateEvent.getDate()),
                            ((Integer) durationEvent.getValue()), pm1, descriptionEvent.getText(), organisateur.getText(),
                            typeEvent.getItemAt(typeEvent.getSelectedIndex()), entitled.getText(),
                            (Integer.valueOf(roomSelectEvent.getSelectedIndex())));
                    em.closeAll();
                    this.setValueEventArchivement();
                    DialogTools.openMessageDialog("Evénement modifié avec succès !", " Info", DialogTools.INFO_MESSAGE);
                    this.setDefaultValue();
                }

            } catch (SQLException | ClassNotFoundException ex) {
                DialogTools.openMessageDialog(ex.getMessage(), "Erreur", DialogTools.ERROR_MESSAGE);
            }

        } else {
            DialogTools.openMessageDialog("Veuillez selectionner un événement", "Erreur", DialogTools.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_modifBtnMouseClicked

    private void addBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBtnMouseClicked
        AddEvent fen = new AddEvent();
        if (fen.setValueEvent()) {
            fen.setVisible(true);
            this.dispose();
        } else {
            fen.dispose();
        }
    }//GEN-LAST:event_addBtnMouseClicked

    private void participeEventMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_participeEventMouseClicked
        // Ouvre une fenetre avec un tableau participant

        int i = tableEvent.getSelectedRow();
        TableModel model = tableEvent.getModel();
        if (!idEvent.getText().isBlank()) {
            int idE = Integer.parseInt(model.getValueAt(i, 0).toString());
            ParticipantEvent fen = new ParticipantEvent();
            if (fen.setValueEventParticipant(idE)) {
                fen.setVisible(true);
                this.dispose();
            } else {
                DialogTools.openMessageDialog("Aucun participant à cette événement", "Erreur", DialogTools.ERROR_MESSAGE);
                fen.dispose();
            }
        } else {
            DialogTools.openMessageDialog("Veuillez selectionner un événement", "Erreur", DialogTools.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_participeEventMouseClicked

    private void infoRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoRoomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_infoRoomActionPerformed

    private void infoRoomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoRoomMouseClicked
        // TODO add your handling code here:
        DialogTools.openMessageDialog("Salle : 1, Type : Conférence, Capacité : 196 \n"
                + "Salle : 2, Type : Commission, Capacité : 50 \n"
                + "Salle : 3, Type : Commission, Capacité : 25 \n"
                + "Salle : 4, Type : Commission, Capacité : 10 ", "Information salles", DialogTools.INFO_MESSAGE);
    }//GEN-LAST:event_infoRoomMouseClicked

    private void deleteEventMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteEventMouseClicked
        // TODO add your handling code here:
        int i = tableEvent.getSelectedRow();
        TableModel model = tableEvent.getModel();
        if (!idEvent.getText().isBlank()) {
            EventManagement em = new EventManagement();
            int idE = Integer.parseInt(idEvent.getText());
            System.out.println(idE);
            try {
                em.setDb();
                em.deleteEvent(idE);
                em.closeMyStatement();
                em.closeAll();
                this.setValueEventArchivement();
                DialogTools.openMessageDialog("Event supprimer avec succès", " événement supprimer", DialogTools.INFO_MESSAGE);
                this.setDefaultValue();
            } catch (SQLException | ClassNotFoundException ex) {
                DialogTools.openMessageDialog(ex.getMessage(), "Erreur Participant !", DialogTools.ERROR_MESSAGE);
            }
        } else {
            DialogTools.openMessageDialog("Veuillez selectionner un événement", "Erreur", DialogTools.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteEventMouseClicked

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
            java.util.logging.Logger.getLogger(Events.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Events.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Events.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Events.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JLabel DurationL;
    private javax.swing.JMenu accueilNav;
    private javax.swing.JButton addBtn;
    private com.toedter.calendar.JDateChooser dateEvent;
    private javax.swing.JLabel dateL;
    private javax.swing.JLabel dateL2;
    private javax.swing.JMenu deconnexionNav;
    private javax.swing.JButton deleteEvent;
    private javax.swing.JTextArea descriptionEvent;
    private javax.swing.JSpinner durationEvent;
    private javax.swing.JTextField entitled;
    private javax.swing.JTextField idEvent;
    private javax.swing.JButton infoRoom;
    private javax.swing.JMenu inputEventNav;
    private javax.swing.JMenu inputParticipantNav;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private java.awt.Label label1;
    private javax.swing.JButton modifBtn;
    private javax.swing.JMenuBar navBar;
    private javax.swing.JLabel nbCharDescEvent;
    private javax.swing.JTextField organisateur;
    private javax.swing.JButton participeEvent;
    private javax.swing.JLabel pmL;
    private javax.swing.JLabel pmL2;
    private javax.swing.JComboBox<String> roomSelectEvent;
    private javax.swing.JTable tableEvent;
    private javax.swing.JTextField theme;
    private javax.swing.JLabel themeL;
    private javax.swing.JLabel themeL2;
    private javax.swing.JComboBox<String> typeEvent;
    // End of variables declaration//GEN-END:variables
}
