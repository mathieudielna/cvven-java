/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Classe technique permettant de lire les fichiers CSV
 *
 * @author Mathieu
 */
public class CSVFileRead {

    /**
     * Le fichier à lire
     */
    private File file;
    /**
     * Le fichier CSV
     */
    private CSVReader fileRead;

    private ArrayList<String[]> lesLignes;

    /**
     * Constructeur par défaut
     *
     * @param file le fichier à lire
     */
    public CSVFileRead(File file) {
        this.file = file;
    }

    /**
     * Retourne le fichier
     *
     * @return
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Retourne le fichier en mode lecture
     *
     * @return le fichier à lire
     */
    public CSVReader getFileRead() {
        return this.fileRead;
    }

    /**
     * Retournes les lignes du fichier CSV si le fichier à été précedemment lu
     * sinon elle ne renvoie rien.
     *
     * Les lignes du fichier CSV, chacune de ces lignes contients diverses
     * données placée dans cet ordre : 1 : Le nom du participant. 2 : Le prénom
     * du participant. 3 : L'adresse e-mail du partcipant. 4 : La date de
     * naissance du participant. 5 : L'organisation du participant. 6 : La
     * description du participant.
     *
     * @return Les lignes contenant les données
     */
    public ArrayList<String[]> getLesLignes() {
        return this.lesLignes;
    }

    /**
     * Ouvre le fichier.
     *
     * @throws FileNotFoundException
     * @throws Exception
     */
    public void openFile() throws FileNotFoundException, Exception {
        if (!this.file.canRead()) {
            throw new Exception("Le fichier ne peut pas être lu !");
        }
        this.fileRead = new CSVReader(new FileReader(this.file));
    }

    /**
     * Ferme le fichier.
     *
     * @throws IOException
     */
    public void closeFile() throws IOException {
        this.getFileRead().close();
    }

    /**
     * Lit le contenu du fichier, stock les informations du fichier et retourne
     * les informations.
     *
     * Le fichier est ouvert et fermé automatiquement.
     *
     * @return Toutes les lignes du fichier
     */
    public ArrayList<String[]> readFile() {
        try {
            this.openFile();
            String[] lines;
            //System.out.println(this.getFileRead() + " " + this.getFileRead().readNext()) ;
            while ((lines = this.getFileRead().readNext()) != null) {
                this.lesLignes.add(lines);
                for (String token : lines){
                    System.out.println(token);
                } 
                System.out.print("\n"); 
            }
            this.closeFile();
            return this.lesLignes;
        } catch (FileNotFoundException ex) {
            DialogTools.openMessageDialog(ex.toString(), "Erreur Fichier !", DialogTools.WARNING_MESSAGE);
            return null;
        } catch (IOException ex) {
            DialogTools.openMessageDialog(ex.toString(), "Erreur Fichier !", DialogTools.WARNING_MESSAGE);
            return null;
        } catch (Exception ex) {
            DialogTools.openMessageDialog(ex.toString(), "Erreur Fichier !", DialogTools.WARNING_MESSAGE);
            return null;
        }
    }

    /**
     * Lit le contenu du fichier, vérifie les informations, stock les
     * informations vérifié et retourne les informations.Le fichier est ouvert
     * et fermé automatiquement.
     *
     *
     * @return Les lignes du fichier controlés.
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public ArrayList<String[]> readControlFile() throws SQLException, ClassNotFoundException {
        if (this.readFile() == null) {
            return null;
        } else { 
            this.controlData();
            return this.lesLignes;
        }
    }

    /**
     * Controle les données du fichier
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void controlData() throws SQLException, ClassNotFoundException {
        ArrayList<String[]> lesLignesValides = new ArrayList<>();
        System.out.println("Arrive 2 setp 1");
        for (String[] uneLigne : this.lesLignes) {
            boolean dataIsBlank = false;
            for (String data : uneLigne) {
                if (data.isBlank()) {
                    dataIsBlank = true;
                    break;
                }
            }
            if (!dataIsBlank) {
                System.out.println("Arrive 2 -2 ");
                //L'émail du participant
                if (EmailValidator.getInstance().isValid(uneLigne[2]) && this.controlDate(uneLigne[3]) && uneLigne[5].length() <= 255 && this.controlEmail(uneLigne[2])) {
                    lesLignesValides.add(uneLigne);
                }
            }
        }
        this.lesLignes = lesLignesValides;
    }

    /**
     * Controle la date
     *
     * @param date
     * @return si la date est correcte
     */
    private boolean controlDate(String date) {
        if (date.length() != 10) {
            return false;
        } else {
            return !(date.charAt(4) != '-' && date.charAt(7) != '-');
        }

    }

    /**
     * Controle si l'email est déja existant
     *
     * @param email l'email
     * @return si l'email est existant ou non
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private boolean controlEmail(String email) throws SQLException, ClassNotFoundException {
        EventManagement EventManagement = new EventManagement();
        EventManagement.setDb();
        return EventManagement.countEmailParticipant(email).getInt("email") == 0;
    }

}
