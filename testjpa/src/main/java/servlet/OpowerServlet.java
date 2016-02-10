package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.ElectronicDevice;
import domain.Heater;
import domain.Home;
import domain.Person;
import jpa.OpowerJpa;

/**
 * Partie servlet de Opower
 * @author PHILIP Mikael JELASSI Seifeddine
 */
@WebServlet(name="opower",
urlPatterns={"/opower"})
public class OpowerServlet extends HttpServlet {
	
	private static final long serialVersionUID = -7736106884246445031L;
	
	/**Jpa utilisé pour notre application**/
	private OpowerJpa jpa;
	
	/**
	 * Méthode appellée au lancement de l'application (lancement du jpa)
	 */
	@Override
	public void init() throws ServletException {
		jpa= new OpowerJpa();
		super.init();
		jpa.init(); //Lancer le jpa (donc les entitymanager)
		//jpa.addData(); //rajouter données de base (inutile si on en a déja dans la BDD)
	}
	
	/**
	 * Méthode get pour récuperer l'nesmeble des données pour les afficher sur une page HTML
	 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	//Récuperer liste des heaters,homes,persons,devices
    	List<Heater> heaters =jpa.ListOfHeaters();
    	List<Home> homes =jpa.ListOfHome();
    	List<Person> persons =jpa.ListOfPersonne();
    	List<ElectronicDevice> elecDevices =jpa.ListOfDevice();
    	
    	resp.setContentType("text/html");

    	PrintWriter out = resp.getWriter();
   
    	/*Affichage d'un contenu html qui affiche toute les données)*/
	    out.println("<HTML>\n<BODY>\n <H1>Recapitulatif des informations</H1>\n");
	    
	    out.println("<H1>Chauffage</H1>\n");
	    for (Heater h: heaters){
	    	  out.println("<h2>"+h.getModelName()+"</h2>\n");
	    	  out.println("<p>"+h.getElecCosume()+"</p>\n");
	    	  out.println("<p>"+h.getHome().getId()+"</p>\n");
	    	  out.println("<p>----------------------------</p>\n");
	    }
	    out.println("<H1>Maison</H1>\n");
	    for (Home h: homes){
	    	  out.println("<h2> Id: "+h.getId()+"</h2>\n");
	    	  out.println("<p>"+h.getAdresse()+"</p>\n");
	    	  out.println("<p>"+h.getPersonne().getNom()+"</p>\n");
	    	  out.println("<p>----------------------------</p>\n");
	    }
	    out.println("<H1>Personnes</H1>\n");
	    for (Person p: persons){
	    	  out.println("<h2>"+p.getNom()+"</h2>\n");
	    	  out.println("<h3>amis</h3>\n");
	    	  for (Person a: p.getAmis()){
	    		  out.println("<p>Amis: "+a.getNom()+"</p>\n");
	    	  }
	    	  out.println("<p>----------</p>\n");
	    	  out.println("<p>----------------------------</p>\n");
	    }
	    out.println("<H1>ElectronicDevices</H1>\n");
	    for (ElectronicDevice elec: elecDevices){
	    	  out.println("<h2>"+elec.getModelName()+"</h2>\n");
	    	  out.println("<p>"+elec.getElecCosume()+"</p>\n");
	    	  out.println("<p>"+elec.getPersonne().getNom()+"</p>\n");
	    	  out.println("<p>----------------------------</p>\n");
	    }
	    out.println("</BODY></HTML>");
      
    }

    /**
     * Méthode fait pour rjaouter des élements.Note: ici on fait juste l'ajout d'une personne pour tester le fonctionnement du post
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	/*Version trés simplifié pour tester la communcation entre l'interface, le servlet et le jpa*/
    	String name = req.getParameter("name"); //On récupére les données du formulaire
    	//Ajout d'une personne à partir des données du formulaire
    	jpa.AddPerson(name, new ArrayList<Home>(), new ArrayList<ElectronicDevice>(),  new ArrayList<Person>());
      
    	//Rendre une réponse au serveur (ici une redirection en get)
	    resp.sendRedirect("/opower");	    
    }
    
    /**
     * Méthode appellée à l'arret de l'application (arret du jpa)
     */
    @Override
    public void destroy() {
    	jpa.stop(); //Stopper le jpa (entitymanager)
    	super.destroy();
    }
}
