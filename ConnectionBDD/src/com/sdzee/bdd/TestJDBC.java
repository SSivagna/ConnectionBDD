package com.sdzee.bdd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class TestJDBC 
{
    /* La liste qui contiendra tous les résultats de nos essais */
    private List<String> messages = new ArrayList<String>();

    public List<String> executerTests( HttpServletRequest request ) 
    {
        /* Chargement du driver JDBC pour MySQL */
        try
        {
            messages.add( "Chargement du driver..." );
            Class.forName( "com.mysql.jdbc.Driver" );
            messages.add( "Driver chargé mais pas engagé!" );
        } 
        catch ( ClassNotFoundException e ) 
        {
            messages.add( "Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! <br/>" + e.getMessage() );
        }

        /* Connexion à la base de données */
        String url = "jdbc:mysql://localhost:3306/bdd";
        String utilisateur = "root";
        String motDePasse = "root";
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
    
        try 
        {
            messages.add( "Connexion à la base de données..." );
            connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
            messages.add( "Connexion réussie !" );

            /* Création de l'objet gérant les requêtes */
            statement = connexion.createStatement();
            messages.add( "Objet requête créé !" );

            /* Exécution d'une requête de lecture */
            resultat = statement.executeQuery( "SELECT id, email, password, name FROM user;" );
            messages.add( "Requête \"SELECT id, email, password, name FROM user;\" effectuée !" );
            
            /* Récupération des données du résultat de la requête de lecture */
            while ( resultat.next() ) 
            {
                int idUtilisateur = resultat.getInt( "id" );
                String emailUtilisateur = resultat.getString( "email" );
                String motDePasseUtilisateur = resultat.getString( "password" );
                String nomUtilisateur = resultat.getString( "name" );
                /* Formatage des données pour affichage dans la JSP finale. */
                messages.add( "Données retournées par la requête : id = " + idUtilisateur + ", email = " + emailUtilisateur
                        + ", password = " + motDePasseUtilisateur + ", name = " + nomUtilisateur + "." );
            }
          
         /*   Exécution d'une requête d'écriture */
         int   statut = statement.executeUpdate("INSERT into bdd.user (id, email, password, name, date_inscription) values ('5','sivagnanasunda@gmail.fr', 'medecine14', 'Sinthuja14', '2012-11-26 11:05:25');");
    		messages.add("Requête \"INSERT");
    		
    		 resultat = statement.executeQuery("SELECT id, email, password, name, date_inscription from user where id='5'");
    		
    		while (resultat.next())
    		{
    			int idUser = resultat.getInt("id");
    			String emailUtilisateur = resultat.getString( "email" );
                String motDePasseUtilisateur = resultat.getString( "password" );
                String nomUtilisateur = resultat.getString( "name" );
                Date dateInscription = resultat.getDate("date_inscription");
                /*Formatage des données*/
                messages.add("Données retournées par la requête : id = " + idUser + ", email = " + emailUtilisateur + ", password = " + motDePasseUtilisateur + ", name = "+ nomUtilisateur + ", date_inscription = " + dateInscription + ".");
    		}
        } 
        catch ( SQLException e ) 
        {
            messages.add( "Erreur lors de la connexion : <br/>" + e.getMessage() );
        } 
        finally
        {
            messages.add( "Fermeture de l'objet ResultSet." );
            if ( resultat != null ) 
            {
                try 
                {
                    resultat.close();
                } 
                catch ( SQLException ignore ) 
                {
                }
            }
            messages.add( "Fermeture de l'objet Statement." );
            if ( statement != null ) 
            {
                try 
                {
                    statement.close();
                } 
                catch ( SQLException ignore ) 
                {
                }
            }
            messages.add( "Fermeture de l'objet Connection." );
            if ( connexion != null ) 
            {
                try 
                {
                    connexion.close();
                } 
                catch ( SQLException ignore ) 
                {
                }
            }
            System.out.println("Exécution de TestJDBC");
        }
        return messages;
    }
}