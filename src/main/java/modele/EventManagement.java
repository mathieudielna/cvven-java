/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Sert à la gestion des événements de notre application
 *
 * @author senpai
 */
public final class EventManagement extends ConnectBDD {

    /**
     * Constructeur par défaut
     */
    public EventManagement() {
        super();
    }

    /**
     * Constructeur par défaut avec des paramètres
     *
     * @param urlBdd L'adresse de l' hôte de la bdd sous forme : hôte/nomBDD
     * @param user Le nom d'utilisateur de la BDD
     * @param password Le mot de passe de la BDD
     */
    public EventManagement(String urlBdd, String user, String password) {
        super(urlBdd, user, password);
    }

    /*-----------------------------------Table participer--------------------------------*/
    /**
     * Selectionne les informations nécessaire pour l'affichage des évènement
     * ayant au moins une participation. Les informations retournés sont :
     * -l'entitled -le type -la date de l'évènement -le thème -le nombre de
     * participant. -l'organisateur -l'état d'archivage de l'évènement
     *
     * @return le résultat de la requête
     * @throws SQLException
     */
    public ResultSet selectInfoTableEventWithParticipation() throws SQLException {
        super.setMyStatement("SELECT event.entitled, event.type, event.date, event.duration, event.theme, " //select champs
                + "COUNT(takepart.id_participant) AS nbParticipant, event.organisateur, event.archive " //select champs
                + "FROM public.takepart INNER JOIN public.event ON takepart.id_event = event.id_event " //from + inner
                + "INNER JOIN public.participant ON takepart.id_participant = participant.id_participant " //second + inner 
                + "GROUP BY event.entitled, event.type, event.datedebut, event.duration, event.theme, event.organisateur, event.archive " //group by
                + "ORDER BY event.archive DESC;"); //order by
        return super.getResult();
    }

    /**
     * Inscrit un participant à un évènement
     *
     * @param entitled
     * @param email
     * @throws SQLException
     */
    public void insertTakePart(String entitled, String email) throws SQLException {
        int idEvent = Integer.parseInt(String.valueOf(entitled.substring(2, 4)));
        super.setMyStatement("INSERT INTO public.takepart(id_event, id_participant) VALUES(?, (SELECT id_participant FROM public.participant WHERE email = ?));");
        super.getMyStatement().setInt(1, idEvent);
        super.getMyStatement().setString(2, email);
        super.execSQLWithouthResult();
    }

    /*----------------------------------Table Salle--------------------------------------*/
    /**
     * Permet de récupérer les informations de toutes les salles. Les
     * informations retournés sont : -L'id de la salle -Le type de la salle
     *
     * @return le résultat de la requête
     * @throws SQLException
     */
    public ResultSet selectAllRoom() throws SQLException {
        super.setMyStatement("SELECT * FROM public.room;");
        return super.getResult();
    }

    /**
     * Permet de récupérer les information d'une salle avec son id. Les
     * informations retournés sont : -L'id de la salle -Le type de la salle
     *
     * @param idSalle L'id de la salle
     * @return le résultat de la requête
     * @throws SQLException
     */
    public ResultSet selectInfoSalle(int idSalle) throws SQLException {
        super.setMyStatement("SELECT * FROM public.room;");
        return super.getResult();
    }

    /*----------------------------------Table évènement----------------------------------*/
    /**
     * Retourne les informations de l'évènement à partir de l'intitulé. Les
     * informations retournés sont : -l'entitled -le type -la date de
     * l'évènement -le thème -le nombre de participant. -l'organisateur -l'état
     * d'archivage de l'évènement -L'id de l'utilisateur -L'id de la salle
     *
     * @param entitled
     * @return
     * @throws SQLException
     */
    public ResultSet selectInfoEventWithentitled(String entitled) throws SQLException {
        super.setMyStatement("SELECT event.entitled, event.theme, evenemenent.dateEvent, event.duration, event.decription, event.organisateur, event.type,"
                + "event.archive, event.id_user, event.idSalle FROM public.event WHERE event.entitled = ?");
        super.getMyStatement().setString(1, entitled);
        return super.getResult();
    }

    /**
     * Selectionne les évènement non archivé existant avec uniquement l'id et
     * l'entitled. Les informations retournés sont : -L'id de l'évènement
     * -L'intitulé de lévènement
     *
     * @return le résultat de la requête
     * @throws SQLException
     */
    public ResultSet selectLesEventNonArchiver() throws SQLException {
        super.setMyStatement("SELECT event.id_event, event.entitled  FROM public.event WHERE event.archive IS NULL;");
        return super.getResult();
    }

    /**
     * Compte le nombre d'évènement avec l'intitulé de l'évènement.
     *
     * @param entitled
     * @return le résultat de la requête
     * @throws SQLException
     */
    public ResultSet countEventWithentitled(String entitled) throws SQLException {
        super.setMyStatement("SELECT COUNT(entitled) AS nbEvent FROM event WHERE entitled = ?;");
        super.getMyStatement().setString(1, entitled);
        return super.getResult();
    }

    /**
     * Insère un évènement à la BDD.
     *
     * Un trigger vérifie si l'intitulé de l'évènement n'existe pas déja.
     *
     * @param entitled Intitulé de l'évènement
     * @param theme Theme de l'évènement
     * @param dateEvent Date à laquelle se déroule l'évènement
     * @param duration Durée de l'évènement
     * @param decription decription de l'évènement
     * @param organisateur Organisateur de l'évènement
     * @param typeEvent Type de l'évènement
     * @param roomChoice La salle choisis par l'utilisateur le champs est sous
     * format : 'N°' + id_room + ' Salle de ' + typesalle + '(' + capacite +
     * 'personne)'
     * @throws SQLException
     *
     */
    public void insertEvent(String entitled, String theme, String dateEvent, int duration, String decription,
            String organisateur, String typeEvent, String roomChoice) throws SQLException {

        super.setMyStatement("INSERT INTO event(entitled, theme, date, duration, participantmax , decription, organisateur, type, id_user ,id_room)"
                + " VALUES(?, ?, ?, ?, (SELECT room.capacity FROM public.room WHERE room.id_room = ?), ?, ?, ?, ?, ?);");

        int idSalle = Integer.parseInt(String.valueOf(roomChoice.charAt(2)));
        //Définitions des paramètres
        super.getMyStatement().setString(1, entitled);
        super.getMyStatement().setString(2, theme);
        super.getMyStatement().setObject(3, dateEvent, Types.DATE);
        super.getMyStatement().setInt(4, duration);
        super.getMyStatement().setInt(5, idSalle);
        super.getMyStatement().setString(6, decription);
        super.getMyStatement().setString(7, organisateur);
        super.getMyStatement().setString(8, typeEvent);
        super.getMyStatement().setInt(9, Session.getIdUser());
        super.getMyStatement().setInt(10, idSalle);
        super.execSQLWithouthResult();
    }

    /**
     * Selectionne les éléments non archivés de la BDD
     *
     * @return le résultat de la requête
     * @throws SQLException
     */
    public ResultSet selectAllEventNArchive() throws SQLException {
        super.setMyStatement("SELECT * FROM public.event WHERE archive IS NULL");
        return super.getResult();
    }

    /**
     * Modifie un event
     *
     * @param id_event id de l'event
     * @param theme theme de l'event
     * @param date date de l'event
     * @param duration duree de l'event
     * @param participantmax nb participant de l'event
     * @param decription description de l'event
     * @param organisateur organisateur de l'event
     * @param type type d'event
     * @param entitled intule de l'event
     * @param id_room id de la salle
     * @throws SQLException
     */
    public void updateEvent(int id_event, String theme, String date, int duration, int participantmax,
            String decription, String organisateur, String type, String entitled, int id_room) throws SQLException {
        super.setMyStatement("UPDATE event SET (theme, date, duration, participantmax, "
                + "decription, organisateur, type, entitled, id_room)="
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?) WHERE id_event=?");
        
        
        super.getMyStatement().setString(1, theme);
        super.getMyStatement().setObject(2, date, Types.DATE);
        super.getMyStatement().setInt(3, duration);
        super.getMyStatement().setInt(4, participantmax);
        super.getMyStatement().setString(5, decription);
        super.getMyStatement().setString(6, organisateur);
        super.getMyStatement().setString(7, type);
        super.getMyStatement().setString(8, entitled);
        super.getMyStatement().setInt(9, id_room);
        super.getMyStatement().setInt(10, id_event);
        super.execSQLWithouthResult();
    }
    
    /**
     * Supprimer un event 
     * 
     * @param id_event id de l'event
     * @throws SQLException
     */
    public void deleteEvent(int id_event) throws SQLException {
        super.setMyStatement("DELETE FROM takepart WHERE id_event=?;"
                + "DELETE FROM event WHERE id_event=?;");
        super.getMyStatement().setInt(1, id_event);
        super.getMyStatement().setInt(2, id_event);
        super.execSQLWithouthResult();
    }

    /*----------------------------------Table participant--------------------------------*/
    /**
     * Retourne les informations du participants à partir de son email. Les
     * informations retournée sont : -Le nom du participant -Le prénom du
     * participant -La date de naissance du participant -L'organisation du
     * participant -L'observation du partcipant -L'email du participant
     *
     * @param email L'email du participant
     * @return le résultat de la requête sur les informations du participant.
     * @throws SQLException
     */
    public ResultSet selectInfoParticipantWithEmail(String email) throws SQLException {
        super.setMyStatement("SELECT participant.nom, participant.prenom, participant.date_naissance, participant.organisation, participant.observations, participant.email FROM "
                + "public.participant WHERE participant.email = ?");
        super.getMyStatement().setString(1, email);
        return super.getResult();
    }

    /**
     * Compte le nombre d'email existant des participants.
     *
     * @param email
     * @return le résultat de la requête
     * @throws SQLException
     */
    public ResultSet countEmailParticipant(String email) throws SQLException {
        super.setMyStatement("SELECT COUNT(id_participant) AS nbParticipant FROM public.participant WHERE participant.email;");
        return super.getResult();
    }

    /**
     * Insert un nouveau participant à la base de données
     *
     * @param lastname Nom du participant
     * @param firstname Prénom du participant
     * @param email Email du participant
     * @param birthd Date de naissance du participant
     * @param organisation
     * @param observations
     * @throws SQLException
     */
    public void insertParticipant(String lastname, String firstname, String email, String birthd, String organisation, String observations)
            throws SQLException {
        super.setMyStatement("INSERT INTO public.participant(lastname, firstname, birthd, organisation, observation, email, id_user) VALUES(?, ?, ?, ?, ?, ?, ?);");
        super.getMyStatement().setString(1, lastname);
        super.getMyStatement().setString(2, firstname);
        super.getMyStatement().setObject(3, birthd, Types.DATE);
        super.getMyStatement().setString(4, organisation);
        super.getMyStatement().setString(5, observations);
        super.getMyStatement().setString(6, email);
        super.getMyStatement().setInt(7, Session.getIdUser());
        super.execSQLWithouthResult();
    }

    /**
     * Selectionne les particpants a un événement par id de l'event
     *
     * @param id_event id de l'event
     * @return le résultat de la requête
     * @throws SQLException
     */
    public ResultSet selectParticipantEventById(int id_event) throws SQLException {
        super.setMyStatement("SELECT * FROM public.participant inner join takepart on participant.id_participant = takepart.id_participant and takepart.id_event = ?");
        super.getMyStatement().setInt(1, id_event);
        return super.getResult();
    }

    /*-----------------------------------Table user--------------------------------------*/
    /**
     * Retourne l'id de l'utilisateur ayant le login et le mot de passe indiqué.
     *
     * @param login
     * @param pswd
     * @return le résultat de la requête
     * @throws SQLException
     */
    public ResultSet selectIdUser(String login, String pswd) throws SQLException {
        this.setMyStatement("SELECT id_user FROM public.user WHERE login = ? AND pswd = ?;");
        this.getMyStatement().setString(1, login);
        this.getMyStatement().setString(2, pswd);
        return this.getResult();
    }

    /**
     * Compte le nombre d'utilisateur ayant le login et le mot de passe indiqué
     *
     * @param login
     * @param pswd
     * @return le résultat de la requête
     * @throws SQLException
     */
    public ResultSet countUserLogin(String login, String pswd) throws SQLException {
        this.setMyStatement("SELECT COUNT(*) AS nbUser FROM public.user WHERE login = ? AND pswd = ?;");
        this.getMyStatement().setString(1, login);
        this.getMyStatement().setString(2, pswd);
        return this.getResult();
    }
}
