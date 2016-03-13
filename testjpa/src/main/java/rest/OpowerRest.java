package rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import domain.ElectronicDevice;
import domain.Heater;
import domain.Home;
import domain.Person;
import jpa.OpowerJpa;

/**
 * Exemple d'utilisation de l'API Rest remplace les servlets
 *  @author PHILIP Mikael JELASSI Seifeddine
 */
@Path("/opower")
public class OpowerRest {
	
	/**Jpa utilisé pour notre application**/
	private OpowerJpa jpa= new OpowerJpa();
	
    @GET
    @Path("/data")
    @Produces(MediaType.TEXT_HTML)
    public String getList() {
    	
    	//Récuperer liste des heaters,homes,persons,devices
    	List<Heater> heaters =jpa.ListOfHeaters();
    	List<Home> homes =jpa.ListOfHome();
    	List<Person> persons =jpa.ListOfPersonne();
    	List<ElectronicDevice> elecDevices =jpa.ListOfDevice();
    	
    	String resp=new String();
   
    	//Affichage d'un contenu html qui affiche toute les données)
    	resp+="<HTML>\n<BODY>\n <H1>Recapitulatif des informations</H1>\n";
	    
	    resp+=("<H1>Chauffage</H1>\n");
	    for (Heater h: heaters){
	    	  resp+=("<h2>"+h.getModelName()+"</h2>\n");
	    	  resp+=("<p>"+h.getElecCosume()+"</p>\n");
	    	  resp+=("<p>"+h.getHome().getId()+"</p>\n");
	    	  resp+=("<p>----------------------------</p>\n");
	    }
	    resp+=("<H1>Maison</H1>\n");
	    for (Home h: homes){
	    	  resp+=("<h2> Id: "+h.getId()+"</h2>\n");
	    	  resp+=("<p>"+h.getAdresse()+"</p>\n");
	    	  resp+=("<p>"+h.getPersonne().getNom()+"</p>\n");
	    	  resp+=("<p>----------------------------</p>\n");
	    }
	    resp+=("<H1>Personnes</H1>\n");
	    for (Person p: persons){
	    	  resp+=("<h2>"+p.getNom()+"</h2>\n");
	    	  resp+=("<h3>amis</h3>\n");
	    	  for (Person a: p.getAmis()){
	    		  resp+=("<p>Amis: "+a.getNom()+"</p>\n");
	    	  }
	    	  resp+=("<p>----------</p>\n");
	    	  resp+=("<p>----------------------------</p>\n");
	    }
	    resp+=("<H1>ElectronicDevices</H1>\n");
	    for (ElectronicDevice elec: elecDevices){
	    	  resp+=("<h2>"+elec.getModelName()+"</h2>\n");
	    	  resp+=("<p>"+elec.getElecCosume()+"</p>\n");
	    	  resp+=("<p>"+elec.getPersonne().getNom()+"</p>\n");
	    	  resp+=("<p>----------------------------</p>\n");
	    }
	    resp+=("</BODY></HTML>");
	    return resp;
   
    }
    
    @GET
    @Path("/dataHeater")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Heater> getListHeater() {
    	//Récuperer liste des heaters
    	List<Heater> heaters =jpa.ListOfHeaters();
	    return heaters;
    }
    
    @GET
    @Path("/dataPerson")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getListPerson() {
    	//Récuperer liste des heaters
    	List<Person> persons =jpa.ListOfPersonne();
	    return persons;
    }
    
    /**
     * Méthode pour rajouter une perssone en recevant les paramêtres depuis un formulaire
     * @param name
     * @return
     * @throws URISyntaxException
     */
    @POST
    @Path("/person")
    @Produces(MediaType.TEXT_HTML)
    public Response addPerson(@FormParam("name") String name ) throws URISyntaxException {
    	/*Version trés simplifié pour tester la communcation entre l'interface, le rest et le jpa*/
    	//Ajout d'une personne à partir des données du formulaire
    	jpa.AddPerson(name, new ArrayList<Home>(), new ArrayList<ElectronicDevice>(),  new ArrayList<Person>());
    	URI targetURIForRedirection = new URI("/opower/data");
    	//Rendre une réponse au serveur (ici une redirection en get)
    	return Response.seeOther(targetURIForRedirection).build();	
	    
	  
    }
    
    /**
     * Méthode pour rajouter une perssone en recevant les paramêtres depuis un JSON (Sert pour un site angular d'un autre projet)
     * @param name
     * @return
     * @throws URISyntaxException
     */
    @POST
    @Path("/personJSON")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON) //C'est comme cela qu'il sait qu'il va recevoir du json
    public Response addPersonJSON(String json ) throws URISyntaxException {
    	/*Version trés simplifié pour tester la communcation entre l'interface, le rest et le jpa*/
    	
    	try {
			JSONObject obj = new JSONObject(json); //On transforme notre paramêtre en object JSON
			String name= obj.getString("name"); //On récupére un element de notre JSON
			//Ajout d'une personne à partir des données du json
	    	jpa.AddPerson(name, new ArrayList<Home>(), new ArrayList<ElectronicDevice>(),  new ArrayList<Person>());
		} catch (JSONException e) {
		
			e.printStackTrace();
		}
    	
    	URI targetURIForRedirection = new URI("/opower/data");
    	//Rendre une réponse au serveur (ici une redirection en get)
    	return Response.seeOther(targetURIForRedirection).build();	
	    
	  
    }
}