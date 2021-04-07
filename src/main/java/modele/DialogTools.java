/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *Classe Technique abstraite outils permettant de gérer l'ouverture de fenêtre de dialogue
 * @author senpai
 */
public abstract class DialogTools {
    /**
     * Correspond au code de message d'informations de JOptionPane
     */
    public final static int INFO_MESSAGE = JOptionPane.INFORMATION_MESSAGE;
    /**
     * Correspond au code de message d'avertissement de JOptionPane
     */
    public final static int WARNING_MESSAGE = JOptionPane.WARNING_MESSAGE;
    /**
     * Correspond au code de message d'erreur de JOptionPane
     */
    public final static int ERROR_MESSAGE = JOptionPane.ERROR_MESSAGE;
    
    
    /**
     * Ouvre une fenetre de dialogue avec les variable précisée
     * 
     * Le code pour le message peut être sélectionné directement via les propriété de DialogTools
     * 
     * @param message Message écrit à l'utilisateur
     * @param header En tête de la fenêtre de dialogue
     * @param codeMessage Code définissant le type de message par la classe JOptionPane exemple JOptionPane.INFORMATION_MESSAGE
     */
    public static void openMessageDialog(String message, String header, int codeMessage){
        JOptionPane dialogue = new JOptionPane(message, codeMessage);
        JDialog boite = dialogue.createDialog(header);
        boite.setVisible(true);
    }
    
    /**
     * Ouvre une fenetre de dialogue de type information
     * 
     * @param message Message écrit à l'utilisateur
     * @param header En tête de la fenêtre de dialogue
     */
    public static void openMessageDialog(String message, String header){
        JOptionPane dialogue = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog boite = dialogue.createDialog(header);
        boite.setVisible(true);
    }
    
    /**
     * Ouvre une fenetre de dialogue de type information
     * 
     * @param message Message écrit à l'utilisateur
     */
    public static void openMessageDialog(String message){
        JOptionPane dialogue = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog boite = dialogue.createDialog(message);
        boite.setVisible(true);
    }
}
