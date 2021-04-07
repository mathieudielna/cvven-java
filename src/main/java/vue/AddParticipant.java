/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import javax.swing.DefaultListModel;
import modele.DialogTools;
import modele.Session;
import modele.CSVFileRead;
import modele.EventManagement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Classe Métier Autogénérer Héritière de JFrame
 *
 * @author senpai
 */
public class AddParticipant extends javax.swing.JFrame {

    /**
     * Creates new form AddParticipant
     */
    public AddParticipant() {
        initComponents();
    }

    /**
     * Efface tous les champs et la selection
     */
    public void setDefaultValue() {
        selectEvents.setModel(new DefaultListModel());
        ((DefaultListModel) selectEvents.getModel()).addElement("Aucun évènement !");
        lastName.setText(null);
        firstName.setText(null);
        email.setText(null);
        birth.setDate(null);
        org.setText(null);
        obs.setText(null);
        countChar.setText("0/255");
    }

    /**
     * Met les valeur par défault récupéré dans la BDD dans la liste de choix
     * d'évènement
     *
     * Affiche les exception dans une JDialog
     *
     * @return Un boolean selon si le remplissage des champs se sont bien passé.
     */
    public final boolean setValueParticipant() {
        try {
            EventManagement EventManagement = new EventManagement();
            EventManagement.setDb();
            ResultSet result = EventManagement.selectLesEventNonArchiver();
            this.setDefaultValue();

            if (result != null) {
                boolean isExist = false;
                ((DefaultListModel) selectEvents.getModel()).remove(0);
                do {
                    String event = "N°" + result.getString("id_event") + " Intitulé : " + result.getString("entitled");
                    for (int i = 0; i < selectEvents.getModel().getSize(); i++) {
                        if (selectEvents.getModel().getElementAt(i).equalsIgnoreCase(event)) {
                            isExist = true;
                        }
                    }
                    if (!isExist) {
                        ((DefaultListModel) selectEvents.getModel()).addElement(event);
                    }
                    isExist = false;
                } while (result.next());
                EventManagement.closeAll();
                return true;
            } else {
                DialogTools.openMessageDialog("Aucun évènement n'a été inséré !", "Avertissement !", DialogTools.WARNING_MESSAGE);
                return true;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            DialogTools.openMessageDialog(ex.getMessage(), "Erreur", DialogTools.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Cache ou montre les champs de la fenêtre participant.
     *
     * @param visible
     */
    private void showField(boolean visible) {
        firstNameL.setVisible(visible);
        firstName.setVisible(visible);
        lastNameL.setVisible(visible);
        lastName.setVisible(visible);
        emailL.setVisible(visible);
        email.setVisible(visible);
        birthL.setVisible(visible);
        birth.setVisible(visible);
        orgL.setVisible(visible);
        org.setVisible(visible);
        obsL.setVisible(visible);
        obs.setVisible(visible);
        jScrollPane2.setVisible(visible);
        obs.setVisible(visible);
        countChar.setVisible(visible);
    }

    /**
     * Ajoute un participant lorsque que tous les champs sont remplis sinon on
     * affiche un message d'erreur sur une fenêtre JDialog. -Pour le champ
     * "Observation" il est aussi vérifé que le nombre de caractère ne dépassent
     * pas 255 caractères
     *
     * Une fois vérifié on insère les données en capturant les evenutels
     * erreurs; -Si une erreur est capturé alors on affiche le message sur une
     * JDialog
     *
     * @param evt
     *
     * @see clearField#setDefaultValue
     * @see JDateChooser
     * @see DialogTools
     * @see SQLException
     * @see ClassNotFoundException
     */
    private void insertParticipantText() {
        if (selectEvents.getSelectedValuesList().isEmpty()) {
            if (selectEvents.getSelectedValuesList().isEmpty()) {
                DialogTools.openMessageDialog("Vous n'avez pas sélectionner d'évènement !", "Erreur", DialogTools.ERROR_MESSAGE);
            }
        } else if (lastName.getText().isBlank()) {
            DialogTools.openMessageDialog("Veuillez entrez un nom !", "Erreur", DialogTools.ERROR_MESSAGE);
        } else if (firstName.getText().isBlank()) {
            DialogTools.openMessageDialog("Veuillez entrez un prénom !", "Erreur", DialogTools.ERROR_MESSAGE);
        } else if (email.getText().isBlank() || !EmailValidator.getInstance().isValid(email.getText())) {
            DialogTools.openMessageDialog("Veuillez entrez une adresse mail valide !", "Erreur", DialogTools.ERROR_MESSAGE);
        } else if (birth.getDate() == null || birth.getDate().after(new Date())) {
            if (birth.getDate() == null) {
                DialogTools.openMessageDialog("Veuillez entrez une date de naissance !", "Erreur", DialogTools.ERROR_MESSAGE);
            } else if (birth.getDate().after(new Date())) {
                DialogTools.openMessageDialog("La date de naissance ne peut pas être après la date actuelle", "Erreur", DialogTools.ERROR_MESSAGE);
            }
        } else if (org.getText().isBlank()) {
            DialogTools.openMessageDialog("Veuillez entrez l'organisation du participant !", "Erreur", DialogTools.ERROR_MESSAGE);
        } else if (org.getText().isBlank() || org.getText().length() > 255) {
            if (org.getText().isBlank()) {
                DialogTools.openMessageDialog("Veuillez indiquez une observation", "Erreur", DialogTools.ERROR_MESSAGE);
            } else {
                DialogTools.openMessageDialog("Veuillez ne pas dépassez les 255 caractères", "Erreur", DialogTools.ERROR_MESSAGE);
            }
        } else {
            boolean isValid = true;
            for (String selectEvent : selectEvents.getSelectedValuesList()) {
                if (selectEvent.equalsIgnoreCase("Aucun évènement !")) {
                    isValid = false;
                }
            }
            if (!isValid) {
                DialogTools.openMessageDialog("Le participant ne peut pas être inscrit à aucun évènement !", "Erreur !", DialogTools.ERROR_MESSAGE);
            } else {
                try {
                    EventManagement em = new EventManagement();
                    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

                    em.setDb();
                    em.insertParticipant(lastName.getText(), firstName.getText(), email.getText(),
                            formatDate.format(birth.getDate()), org.getText(), obs.getText());
                    em.closeMyStatement();

                    for (String selectEvent : selectEvents.getSelectedValuesList()) {
                        if (!selectEvent.equalsIgnoreCase("Aucun évènement !")) {
                            em.insertParticipation(selectEvent, email.getText());
                        }
                    }

                    em.closeAll();
                    DialogTools.openMessageDialog("L'ajout de participant est terminée !", "Ajout Terminée");
                    this.setValueParticipant();
                } catch (SQLException | ClassNotFoundException ex) {
                    DialogTools.openMessageDialog(ex.getMessage(), "Erreur Participant !", DialogTools.ERROR_MESSAGE);
                }
            }
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        navBar = new javax.swing.JMenuBar();
        accueilNav = new javax.swing.JMenu();
        inputEventNav = new javax.swing.JMenu();
        inputParticipantNav = new javax.swing.JMenu();
        DisplayEventNav = new javax.swing.JMenu();
        deconnexionNav = new javax.swing.JMenu();
        jPanel4 = new javax.swing.JPanel();
        insertEventText = new javax.swing.JRadioButton();
        insertCSV = new javax.swing.JRadioButton();
        label1 = new java.awt.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        selectEvents = new javax.swing.JList<>();
        firstName = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        lastName = new javax.swing.JTextField();
        org = new javax.swing.JTextField();
        lastNameL = new javax.swing.JLabel();
        emailL = new java.awt.Label();
        orgL = new java.awt.Label();
        firstNameL = new java.awt.Label();
        birthL = new java.awt.Label();
        obsL = new java.awt.Label();
        countChar = new java.awt.Label();
        jScrollPane2 = new javax.swing.JScrollPane();
        obs = new javax.swing.JTextArea();
        add = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        footer2 = new javax.swing.JPanel();
        selectEvent = new javax.swing.JLabel();
        birth = new com.toedter.calendar.JDateChooser();
        navBar1 = new javax.swing.JMenuBar();
        accueilNav1 = new javax.swing.JMenu();
        inputEventNav1 = new javax.swing.JMenu();
        inputParticipantNav1 = new javax.swing.JMenu();
        DisplayEventNav1 = new javax.swing.JMenu();
        deconnexionNav1 = new javax.swing.JMenu();

        navBar.setBackground(new java.awt.Color(204, 204, 204));
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        insertEventText.setText("Form");
        insertEventText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertEventTextActionPerformed(evt);
            }
        });

        insertCSV.setText("CSV file");

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        label1.setText("Add participant");

        selectEvents.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        selectEvents.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "No event now!" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(selectEvents);

        firstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameActionPerformed(evt);
            }
        });

        lastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameActionPerformed(evt);
            }
        });

        lastNameL.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lastNameL.setText("Last Name");

        emailL.setText("Email");

        orgL.setText("Organization");

        firstNameL.setText("First Name ");

        birthL.setText("Date Of Birth");

        obsL.setText("Observation");

        countChar.setText("0/255");

        obs.setColumns(20);
        obs.setRows(5);
        jScrollPane2.setViewportView(obs);

        add.setText("Add");

        cancel.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        footer2.setBackground(java.awt.Color.black);
        footer2.setForeground(java.awt.Color.white);

        javax.swing.GroupLayout footer2Layout = new javax.swing.GroupLayout(footer2);
        footer2.setLayout(footer2Layout);
        footer2Layout.setHorizontalGroup(
            footer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        footer2Layout.setVerticalGroup(
            footer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        selectEvent.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        selectEvent.setText("Select event(s)");

        birth.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(footer2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(insertEventText)
                        .addGap(18, 18, 18)
                        .addComponent(insertCSV))
                    .addComponent(birthL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstNameL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(orgL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1)
                    .addComponent(lastNameL)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(obsL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(countChar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(org, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lastName)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(add)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancel))
                    .addComponent(selectEvent)
                    .addComponent(birth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(firstName)
                    .addComponent(email))
                .addGap(55, 55, 55))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertEventText)
                    .addComponent(insertCSV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selectEvent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lastNameL, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(firstNameL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(emailL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(birthL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(birth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(orgL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(org, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(countChar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(obsL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel)
                    .addComponent(add))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(footer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        insertCSV.getAccessibleContext().setAccessibleName("CSVformat");

        navBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        navBar1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N

        accueilNav1.setText("Home");
        accueilNav1.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        accueilNav1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accueilNav1MouseClicked(evt);
            }
        });
        navBar1.add(accueilNav1);

        inputEventNav1.setText("Add Event");
        inputEventNav1.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        inputEventNav1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inputEventNav1MouseClicked(evt);
            }
        });
        navBar1.add(inputEventNav1);

        inputParticipantNav1.setText("Add Participant");
        inputParticipantNav1.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        inputParticipantNav1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inputParticipantNav1MouseClicked(evt);
            }
        });
        navBar1.add(inputParticipantNav1);

        DisplayEventNav1.setText("Events");
        DisplayEventNav1.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        DisplayEventNav1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DisplayEventNav1MouseClicked(evt);
            }
        });
        navBar1.add(DisplayEventNav1);

        deconnexionNav1.setText("Disconnect");
        deconnexionNav1.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        deconnexionNav1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deconnexionNav1MouseClicked(evt);
            }
        });
        navBar1.add(deconnexionNav1);

        setJMenuBar(navBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

    private void accueilNav1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accueilNav1MouseClicked
        Home fen = new Home();
        fen.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_accueilNav1MouseClicked

    private void inputEventNav1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inputEventNav1MouseClicked
        AddEvent fen = new AddEvent();
        if (fen.setValueEvent()) {
            fen.setVisible(true);
            this.dispose();
        } else {
            fen.dispose();
        }
    }//GEN-LAST:event_inputEventNav1MouseClicked

    private void inputParticipantNav1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inputParticipantNav1MouseClicked
        AddParticipant fen = new AddParticipant();
        if (fen.setValueParticipant()) {
            fen.setVisible(true);
            this.dispose();
        } else {
            fen.dispose();
        }
    }//GEN-LAST:event_inputParticipantNav1MouseClicked

    private void DisplayEventNav1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DisplayEventNav1MouseClicked
        Events fen = new Events();
        fen.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_DisplayEventNav1MouseClicked

    private void deconnexionNav1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deconnexionNav1MouseClicked
        Connection fen = new Connection();
        fen.setVisible(true);
        Session.destructSession();
        this.dispose();
    }//GEN-LAST:event_deconnexionNav1MouseClicked

    private void insertEventTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertEventTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_insertEventTextActionPerformed

    private void lastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelActionPerformed

    private void firstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameActionPerformed

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
            java.util.logging.Logger.getLogger(AddParticipant.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddParticipant.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddParticipant.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddParticipant.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddParticipant().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu DisplayEventNav;
    private javax.swing.JMenu DisplayEventNav1;
    private javax.swing.JMenu accueilNav;
    private javax.swing.JMenu accueilNav1;
    private javax.swing.JButton add;
    private com.toedter.calendar.JDateChooser birth;
    private java.awt.Label birthL;
    private javax.swing.JButton cancel;
    private java.awt.Label countChar;
    private javax.swing.JMenu deconnexionNav;
    private javax.swing.JMenu deconnexionNav1;
    private javax.swing.JTextField email;
    private java.awt.Label emailL;
    private javax.swing.JTextField firstName;
    private java.awt.Label firstNameL;
    private javax.swing.JPanel footer2;
    private javax.swing.JMenu inputEventNav;
    private javax.swing.JMenu inputEventNav1;
    private javax.swing.JMenu inputParticipantNav;
    private javax.swing.JMenu inputParticipantNav1;
    private javax.swing.JRadioButton insertCSV;
    private javax.swing.JRadioButton insertEventText;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private java.awt.Label label1;
    private javax.swing.JTextField lastName;
    private javax.swing.JLabel lastNameL;
    private javax.swing.JMenuBar navBar;
    private javax.swing.JMenuBar navBar1;
    private javax.swing.JTextArea obs;
    private java.awt.Label obsL;
    private javax.swing.JTextField org;
    private java.awt.Label orgL;
    private javax.swing.JLabel selectEvent;
    private javax.swing.JList<String> selectEvents;
    // End of variables declaration//GEN-END:variables
}
