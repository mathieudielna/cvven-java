/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *Gere la connexion à la base de donnée de l'application
 * @author senpai
 */
abstract class ConnectBDD {
    private String urlBdd;
    private String user;
    private String password;
    private Connection db;
    private PreparedStatement myStatement;
    
    /**
     * Contructeur par defaut sans paramètre
     */
    protected ConnectBDD() {
        this.urlBdd = "localhost/cvven-java";
        this.user = "bdd";
        this.password = "bdd";
    } 
    

   
     /**
     * Constructeur par défaut avec des paramètre
     * 
     * @param urlBdd L'adresse de l' hôte de la bdd sous forme : hôte/nomBDD
     * @param user Le nom d'utilisateur de la BDD
     * @param password Le mot de passe de la BDD
     */
        protected ConnectBDD(String urlHoteBdd, String user, String password) {
        this.urlBdd = urlHoteBdd;
        this.user = user;
        this.password = password;
    }
      
    /*----------------------------------Getter / Setter --------------------------------*/
        
       /**
     * Retourne l'url de l'hote de la bdd avec le nom de la base de données
     * 
     * @return L'adresse de l' hôte de la bdd sous forme : hôte/nomBDD
     */
    public String getUrlBdd() {
        return this.urlBdd;
    }

    /**
     * Retourne l'utilisateur de la base de données
     * 
     * @return l'utilisateur de la BDD
     */
    public String getUser() {
        return this.user;
    }

    /*----------------------------------  BDD Function() --------------------------------*/
    /**
     * Retourne l'objet connexion relié à la BDD
     * 
     * @return la connexion à la bdd
     */
    public Connection getDb() {
        return this.db;
    }
    
    /**
     * Retourne la délaration de requête réalisé préalablement
     * 
     * @return la déclaration de requête
     */
    public PreparedStatement getMyStatement() {
        return this.myStatement;
    }

    /**
     * Défini l'url de l'hôte
     * 
     * @param urlBdd L'adresse de l' hôte de la bdd sous forme : hôte/nomBDD
     */
    public void setUrlBdd(String urlBdd) {
        this.urlBdd = urlBdd;
    }

    /**
     * Défini l'utilisateur de la base de données
     * 
     * @param user Le nom d'utilisateur de la BDD
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Défini le mot de passe de l'utilisateur de la BDD
     * 
     * @param password Le mot de passe de la BDD
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Définit et Ouvre une connexion à la BDD postgreSQL avec le driver JDBC
     * 
     * @throws SQLException
     * @throws ClassNotFoundException 
     * 
     * @see org.postgresql.Driver
     */
    public void setDb() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.db = DriverManager.getConnection("jdbc:postgresql://"+this.urlBdd, this.user, this.password);
    }
      
    /**
     * Déclare une requête préparée
     * 
     * @throws SQLException 
     */
    protected void setMyStatement(String query) throws SQLException{
         this.myStatement = this.db.prepareStatement(query);
    }
    
    /**
     * Ferme la connexion à la bdd
     * 
     * @throws SQLException 
     */
    public void closeMaBdd() throws SQLException{
        this.db.close();
    }
    
    /**
     * Ferme la déclaration de la requête
     * 
     * @throws SQLException 
     */
    public void closeMyStatement() throws SQLException{
        this.myStatement.clearParameters();
        this.myStatement.close();
    }
    
     /**
     * Ferme la déclaration de la requête et la connexion vers la BDD
     * 
     * @throws SQLException 
     */
    public void closeAll() throws SQLException{
        this.myStatement.clearParameters();
        this.myStatement.close();
        this.db.close();
    }
    
     /**
     * Exécute la requête sans retourner de résultat
     * 
     * @throws SQLException 
     */
    protected void execSQLWithouthResult() throws SQLException{
        this.myStatement.execute();
    }
    
     /**
     * Retourne le résultat de la requête préparée
     * 
     * @return le résultat sous forme d'objet
     * @throws SQLException 
     */
    protected ResultSet getResult() throws SQLException{
        ResultSet result = this.myStatement.executeQuery();
        if(result.next()){
            return result;
        }else{
            return null;
        }
        
    } 
}
