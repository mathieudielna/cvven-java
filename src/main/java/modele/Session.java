/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe abstraite permettant de gérer les sessions
 * @author senpai
 */
public abstract class Session {
    /**
     * Composé d'un nombre aléatoire + l'idUser
     */
    private static String idSession;
    private static ArrayList<Object> lesVarSession = new ArrayList<Object>();
    
        
    /**
    * Retourne la clé de session composé d'un nombre aléatoire + l'idUser si l'utilisateur est connecté
    * 
    * Génère une erreur si l'utilisateur n'est pas connecté !
    * 
    * @return la clé de session ou null si l'utilisateur n'est pas connecté
    */
    public static String getIdSession() {
        try {
            Session.controlSession();
            return idSession;
        } catch (Exception ex) {
            DialogTools.openMessageDialog(ex.getMessage(), "Erreur Session !", DialogTools.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
    * Retroune les variable session sous forme de tableau si l'utilisateur est connecté
    * 
    * Génère une erreur si l'utilisateur n'est pas connecté !
    * 
    * @return les variables sessions ou null si l'utilisateur n'est pas connecté
    */
    public static ArrayList<Object> getVariableSession() {
        try {
            Session.controlSession();
            return lesVarSession;
        } catch (Exception ex) {
            DialogTools.openMessageDialog(ex.getMessage(), "Erreur Session !", DialogTools.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
    * Retourne l'id de l'utilisateur stocké dans la variable d'id Session si l'utilisateur est connecté
    * 
    * Génère une erreur si l'utilisateur n'est pas connecté !
    * 
    * @return l'id de l'utilisateur ou -1 si l'utilisateur n'est pas connecté
    */
    public static int getIdUser(){
        try {
            Session.controlSession();
            return Integer.parseInt(String.valueOf(idSession.charAt(2)));
        } catch (Exception ex) {
            DialogTools.openMessageDialog(ex.getMessage(), "Erreur Session !", DialogTools.ERROR_MESSAGE);
            return -1;
        }  
    }

    /**
    * Défini les variable sessions
    * 
    * @param sessionVar tableau contenant des variable pour la sessions
    */
    public static void setVariableSession(ArrayList<Object> sessionVar) {
        lesVarSession = sessionVar;
    }
    
    /**
    * Initialise la session
    * 
    * @param idUser 
    */
    public static void initSession(int idUser){
        Random rand = new Random(); 
        int nombreAleatoire = rand.nextInt(99 - 10 + 1) + 10;
        idSession = String.valueOf(nombreAleatoire) + String.valueOf(idUser);
    }
    
    /**
    * Efface les données de la session
    */
    public static void destructSession(){
        idSession = null;
        lesVarSession.clear();
    } 
    
    /**
    * Controle si la session est correct, si elle ne l'est pas elle détruit les donnée et généère une exception
    * 
    * @throws Exception 
    */
    public static void controlSession() throws Exception{
        if(idSession.isBlank() || idSession == null){
            Session.destructSession();
            throw new Exception("Erreur : Vous n'êtes pas connecté ou votre session est incorrect !");
        }
    }
}
