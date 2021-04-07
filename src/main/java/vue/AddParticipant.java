/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import javax.swing.DefaultListModel;
import modele.DialogTools;
import modele.Session;
//import modele.CSVFileRead;
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
        nom.setText(null);
        prenom.setText(null);
        email.setText(null);
        birthDay.setDate(null);
        organization.setText(null);
        observation.setText(null);
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
            EventManagement em = new EventManagement();
            em.setDb();
            ResultSet result = em.selectLesEventNonArchiver();
            this.setDefaultValue();

            if (result != null) {
                boolean isExist = false;
                ((DefaultListModel) selectEvents.getModel()).remove(0);
                do {
                    String event = "N°" + result.getString("id_evenement") + " Intitulé : " + result.getString("intitule");
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
                em.closeAll();
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
        libelleNom.setVisible(visible);
        nom.setVisible(visible);
        prenom.setVisible(visible);
        email.setVisible(visible);
        birthDay.setVisible(visible);
        organization.setVisible(visible);
        observation.setVisible(visible);
        jScrollPane2.setVisible(visible);
        observation.setVisible(visible);
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
        } else if (nomParticipant.getText().isBlank()) {
            DialogTools.openMessageDialog("Veuillez entrez un nom !", "Erreur", DialogTools.ERROR_MESSAGE);
        } else if (prenomParticipant.getText().isBlank()) {
            DialogTools.openMessageDialog("Veuillez entrez un prénom !", "Erreur", DialogTools.ERROR_MESSAGE);
        } else if (adresseMailParticipant.getText().isBlank() || !EmailValidator.getInstance().isValid(adresseMailParticipant.getText())) {
            DialogTools.openMessageDialog("Veuillez entrez une adresse mail valide !", "Erreur", DialogTools.ERROR_MESSAGE);
        } else if (birthDay.getDate() == null || birthDay.getDate().after(new Date())) {
            if (birthDay.getDate() == null) {
                DialogTools.openMessageDialog("Veuillez entrez une date de naissance !", "Erreur", DialogTools.ERROR_MESSAGE);
            } else if (birthDay.getDate().after(new Date())) {
                DialogTools.openMessageDialog("La date de naissance ne peut pas être après la date actuelle", "Erreur", DialogTools.ERROR_MESSAGE);
            }
        } else if (organisationParticipant.getText().isBlank()) {
            DialogTools.openMessageDialog("Veuillez entrez l'organisation du participant !", "Erreur", DialogTools.ERROR_MESSAGE);
        } else if (observationsParticipant.getText().isBlank() || observationsParticipant.getText().length() > 255) {
            if (observationsParticipant.getText().isBlank()) {
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
                    em.insertParticipant(nomParticipant.getText(), prenomParticipant.getText(), adresseMailParticipant.getText(),
                            formatDate.format(birthDay.getDate()), organisationParticipant.getText(), observationsParticipant.getText());
                    em.closeMyStatement();

                    for (String selectEvent : selectEvents.getSelectedValuesList()) {
                        if (!selectEvent.equalsIgnoreCase("Aucun évènement !")) {
                            em.insertParticipation(selectEvent, adresseMailParticipant.getText());
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
                    em.setDb();

                    while (ChooseFileCSV.showOpenDialog(this) != JFileChooser.APPROVE_OPTION && ChooseFileCSV.showOpenDialog(this) != JFileChooser.CANCEL_OPTION) {
                        if (ChooseFileCSV.showOpenDialog(this) != JFileChooser.ERROR_OPTION) {
                            break;
                        }
                    }
                    if (ChooseFileCSV.getSelectedFile() != null) {
                        CSVFileRead csvFile = new CSVFileRead(ChooseFileCSV.getSelectedFile());
                        if (csvFile.readControlFile() != null) {
                            for (String[] uneLigne : csvFile.getLesLignes()) {
                                em.insertParticipant(uneLigne[0], uneLigne[1], uneLigne[2], uneLigne[3], uneLigne[4], uneLigne[5]);
                                em.closeMyStatement();

                                for (String selectEvent : selectEvents.getSelectedValuesList()) {
                                    if (!selectEvent.equalsIgnoreCase("Aucun évènement !")) {
                                        em.insertParticipation(selectEvent, uneLigne[3]);
                                        em.closeMyStatement();
                                    }
                                }
                            }
                            em.closeAll();
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

        navBar = new javax.swing.JMenuBar();
        accueilNav = new javax.swing.JMenu();
        inputEventNav = new javax.swing.JMenu();
        inputParticipantNav = new javax.swing.JMenu();
        DisplayEventNav = new javax.swing.JMenu();
        deconnexionNav = new javax.swing.JMenu();
        jPanel4 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        label1 = new java.awt.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        selectEvents = new javax.swing.JList<>();
        prenom = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        nom = new javax.swing.JTextField();
        organization = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        label5 = new java.awt.Label();
        label6 = new java.awt.Label();
        countChar = new java.awt.Label();
        jScrollPane2 = new javax.swing.JScrollPane();
        observation = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        footer2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        birthDay = new com.toedter.calendar.JDateChooser();
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

        jRadioButton1.setText("Form");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("CSV file");

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        label1.setText("Add participant");

        selectEvents.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(selectEvents);

        prenom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prenomActionPerformed(evt);
            }
        });

        nom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel1.setText("Last Name");

        label2.setText("Email");

        label3.setText("Organization");

        label4.setText("First Name ");

        label5.setText("Date Of Birth");

        label6.setText("Observation");

        countChar.setText("0/255");

        observation.setColumns(20);
        observation.setRows(5);
        jScrollPane2.setViewportView(observation);

        jButton1.setText("Add");

        jButton2.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel2.setText("Selectionner un ou plusieurs événements :");

        birthDay.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(footer2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jRadioButton1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jRadioButton2))
                        .addComponent(label5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(prenom, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(label3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(countChar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(organization)
                        .addComponent(nom, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jButton2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1))
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(birthDay, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(85, 85, 85))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prenom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(birthDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(organization, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(countChar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(footer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jRadioButton2.getAccessibleContext().setAccessibleName("CSVformat");

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
        AddParticipant fen = new AddParticipant();
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

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void nomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void prenomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prenomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prenomActionPerformed

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
    private com.toedter.calendar.JDateChooser birthDay;
    private java.awt.Label countChar;
    private javax.swing.JMenu deconnexionNav;
    private javax.swing.JMenu deconnexionNav1;
    private javax.swing.JTextField email;
    private javax.swing.JPanel footer2;
    private javax.swing.JMenu inputEventNav;
    private javax.swing.JMenu inputEventNav1;
    private javax.swing.JMenu inputParticipantNav;
    private javax.swing.JMenu inputParticipantNav1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private javax.swing.JMenuBar navBar;
    private javax.swing.JMenuBar navBar1;
    private javax.swing.JTextField nom;
    private javax.swing.JTextArea observation;
    private javax.swing.JTextField organization;
    private javax.swing.JTextField prenom;
    private javax.swing.JList<String> selectEvents;
    // End of variables declaration//GEN-END:variables
}
