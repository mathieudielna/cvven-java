/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

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
            for (String sE : selectEvents.getSelectedValuesList()) {
                if (sE.equalsIgnoreCase("Aucun évènement !")) {
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

                    //INSERTION DU PARTICIPANT 
                    em.insertParticipant(lastName.getText(), firstName.getText(), email.getText(),
                            formatDate.format(birth.getDate()), org.getText(), obs.getText());
                    em.closeMyStatement();

                    for (String sE : selectEvents.getSelectedValuesList()) {
                        if (!sE.equalsIgnoreCase("Aucun évènement !")) {
                            //System.out.println(sE);
                            em.insertTakePart(sE, email.getText());
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

    public void insertParticipantCSV() {
        if (selectEvents.getSelectedValuesList().isEmpty()) {
            if (selectEvents.getSelectedValuesList().isEmpty()) {
                DialogTools.openMessageDialog("Vous n'avez pas sélectionner d'évènement !", "Erreur", DialogTools.ERROR_MESSAGE);
            }
        } else {
            boolean isValid = true;
            for (String sE : selectEvents.getSelectedValuesList()) {
                if (sE.equalsIgnoreCase("Aucun évènement !")) {
                    isValid = false;
                }
            }
            if (!isValid) {
                DialogTools.openMessageDialog("Le participant ne peut pas être inscrit à aucun évènement !", "Erreur !", DialogTools.ERROR_MESSAGE);
            } else {
                try {
                    EventManagement EventManagement = new EventManagement();
                    EventManagement.setDb();

                    while (ChooseFileCSV.showOpenDialog(this) != JFileChooser.APPROVE_OPTION && ChooseFileCSV.showOpenDialog(this) != JFileChooser.CANCEL_OPTION) {
                        if (ChooseFileCSV.showOpenDialog(this) != JFileChooser.ERROR_OPTION) {
                            break;
                        }
                    }
                    if (ChooseFileCSV.getSelectedFile() != null) {
                        CSVFileRead csvFile = new CSVFileRead(ChooseFileCSV.getSelectedFile());
                        if (csvFile.readControlFile() != null) {
                            for (String[] uneLigne : csvFile.getLesLignes()) {
                                EventManagement.insertParticipant(uneLigne[0], uneLigne[1], uneLigne[2], uneLigne[3], uneLigne[4], uneLigne[5]);
                                EventManagement.closeMyStatement();

                                for (String sE : selectEvents.getSelectedValuesList()) {
                                    if (!sE.equalsIgnoreCase("Aucun évènement !")) {
                                        //System.out.println(selectEvent + " / " + uneLigne[3]);
                                        EventManagement.insertTakePart(sE, uneLigne[3]);
                                        EventManagement.closeMyStatement();
                                    }
                                }
                            }
                            EventManagement.closeAll();
                            DialogTools.openMessageDialog("L'ajout de participant est terminée !", "Ajout Terminée");
                            this.setValueParticipant();
                        } else {
                            DialogTools.openMessageDialog("Vous n'avez pas sélectionné de fichier !", "Erreur Select Fichier !", DialogTools.WARNING_MESSAGE);
                        }
                    }
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

        buttonGroup = new javax.swing.ButtonGroup();
        ChooseFileCSV = new javax.swing.JFileChooser();
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
        birth = new com.toedter.calendar.JDateChooser();
        emailL = new java.awt.Label();
        orgL = new java.awt.Label();
        firstNameL = new java.awt.Label();
        birthL = new java.awt.Label();
        obsL = new java.awt.Label();
        jScrollPane2 = new javax.swing.JScrollPane();
        obs = new javax.swing.JTextArea();
        add = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        countChar = new java.awt.Label();
        selectEvent = new javax.swing.JLabel();
        navBar1 = new javax.swing.JMenuBar();
        accueilNav1 = new javax.swing.JMenu();
        inputEventNav1 = new javax.swing.JMenu();
        DisplayEventNav1 = new javax.swing.JMenu();
        deconnexionNav1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        buttonGroup.add(insertEventText);
        insertEventText.setSelected(true);
        insertEventText.setText("Complete form");
        insertEventText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                insertEventTextMouseClicked(evt);
            }
        });
        insertEventText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertEventTextActionPerformed(evt);
            }
        });

        buttonGroup.add(insertCSV);
        insertCSV.setText("Set CSV file");
        insertCSV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                insertCSVMouseClicked(evt);
            }
        });
        insertCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertCSVActionPerformed(evt);
            }
        });

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        label1.setText("Add participant");

        selectEvents.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        selectEvents.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Aucun évènement !" };
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

        lastNameL.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lastNameL.setText("Last Name");

        birth.setDateFormatString("yyyy-MM-dd");

        emailL.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        emailL.setText("Email");

        orgL.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        orgL.setText("Organization");

        firstNameL.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        firstNameL.setText("First Name ");

        birthL.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        birthL.setText("Date Of Birth");

        obsL.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        obsL.setText("Observation");

        obs.setColumns(20);
        obs.setRows(5);
        obs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                obsKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                obsKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(obs);

        add.setText("Add");
        add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMouseClicked(evt);
            }
        });

        cancel.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        cancel.setText("Cancel");
        cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelMouseClicked(evt);
            }
        });
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        countChar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        countChar.setText("0/255");

        selectEvent.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        selectEvent.setText("Select event(s)");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(orgL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(birthL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(birth, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emailL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(firstNameL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(selectEvent)
                            .addComponent(lastNameL)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(insertEventText)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(insertCSV))
                                    .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addComponent(add)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cancel))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(obsL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(countChar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(org, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertEventText)
                    .addComponent(insertCSV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectEvent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lastNameL, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(firstNameL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add)
                    .addComponent(cancel))
                .addGap(65, 65, 65))
        );

        insertCSV.getAccessibleContext().setAccessibleName("CSVformat");

        navBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 711, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void DisplayEventNav1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DisplayEventNav1MouseClicked
        Events fen = new Events();
        if (fen.setValueEventArchivement()) {
            fen.setVisible(true);
            this.dispose();
        } else {
            fen.dispose();
        }
    }//GEN-LAST:event_DisplayEventNav1MouseClicked

    private void deconnexionNav1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deconnexionNav1MouseClicked
        Connection fen = new Connection();
        fen.setVisible(true);
        Session.destructSession();
        this.dispose();
    }//GEN-LAST:event_deconnexionNav1MouseClicked

    private void insertEventTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertEventTextActionPerformed
        //empty 
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

    private void insertEventTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_insertEventTextMouseClicked
        this.showField(true);
    }//GEN-LAST:event_insertEventTextMouseClicked

    private void insertCSVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_insertCSVMouseClicked
        this.showField(false);
        this.insertParticipantCSV();
    }//GEN-LAST:event_insertCSVMouseClicked

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
        if (insertEventText.isSelected()) {
            this.insertParticipantText();
        } else if (insertCSV.isSelected()) {
            this.insertParticipantCSV();
        }
    }//GEN-LAST:event_addMouseClicked

    private void obsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_obsKeyPressed
        countChar.setText(obs.getText().length() + "/255");
    }//GEN-LAST:event_obsKeyPressed
;
    private void obsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_obsKeyReleased
        countChar.setText(obs.getText().length() + "/255");
    }//GEN-LAST:event_obsKeyReleased

    private void cancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelMouseClicked
        // TODO add your handling code here:
        Home fen = new Home();
        fen.setVisible(true);
        this.setDefaultValue();
        this.dispose();
    }//GEN-LAST:event_cancelMouseClicked

    private void insertCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertCSVActionPerformed
       //empty
    }//GEN-LAST:event_insertCSVActionPerformed

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
            java.util.logging.Logger.getLogger(AddParticipant.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddParticipant.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddParticipant.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddParticipant.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JFileChooser ChooseFileCSV;
    private javax.swing.JMenu DisplayEventNav1;
    private javax.swing.JMenu accueilNav1;
    private javax.swing.JButton add;
    private com.toedter.calendar.JDateChooser birth;
    private java.awt.Label birthL;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JButton cancel;
    private java.awt.Label countChar;
    private javax.swing.JMenu deconnexionNav1;
    private javax.swing.JTextField email;
    private java.awt.Label emailL;
    private javax.swing.JTextField firstName;
    private java.awt.Label firstNameL;
    private javax.swing.JMenu inputEventNav1;
    private javax.swing.JRadioButton insertCSV;
    private javax.swing.JRadioButton insertEventText;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private java.awt.Label label1;
    private javax.swing.JTextField lastName;
    private javax.swing.JLabel lastNameL;
    private javax.swing.JMenuBar navBar1;
    private javax.swing.JTextArea obs;
    private java.awt.Label obsL;
    private javax.swing.JTextField org;
    private java.awt.Label orgL;
    private javax.swing.JLabel selectEvent;
    private javax.swing.JList<String> selectEvents;
    // End of variables declaration//GEN-END:variables
}
