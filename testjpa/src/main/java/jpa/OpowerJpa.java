package jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import domain.ElectronicDevice;
import domain.Heater;
import domain.Home;
import domain.Person;

/**
 * Classe couche BDD opower, contient les méthodes pour utiliser la bdd avec le servlet
 * @author PHILIP Mikael JELASSI Seifeddine
 */
public class OpowerJpa {
	EntityManagerFactory factory;
	EntityManager manager;
	EntityTransaction tx;
	
	/**
	 * Methode appellée quand le serveur servlet démarre
	 */
	public void init(){
		factory = Persistence
				.createEntityManagerFactory("mysql"); //C'est ici qu'on choisit la config crée dans persistence.xml
		manager = factory.createEntityManager();

		tx = manager.getTransaction();
	}
	
	/**
	 * Methode pour récuperer les personnes dans la BDD
	 * @return
	 */
	public List<Person> ListOfPersonne(){
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder(); //Création du criteria
		CriteriaQuery<Person> query = criteriaBuilder.createQuery(Person.class); //Indiquer quel entité
		Root<Person> person = query.from(Person.class); //Indiquer quel table
		query.select(person); //ce qu'on fait
		TypedQuery<Person> req = manager.createQuery(query); //créer la requete
		return (List<Person>)req.getResultList(); //recupérer résultat
	}
	
	/**
	 * Methode pour récuperer les chauffages dans la BDD
	 * @return
	 */
	public List<Heater> ListOfHeaters(){
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder(); //Création du critéria
		CriteriaQuery<Heater> query = criteriaBuilder.createQuery(Heater.class); //Indiquer quel entité
		Root<Heater> heater = query.from(Heater.class); //Indiquer quel table
		query.select(heater); //ce qu'on fait
		TypedQuery<Heater> req = manager.createQuery(query); //créer la requete
		return (List<Heater>)req.getResultList(); //recupérer résultat
	}
	
	/**
	 * Methode pour récuperer les objets electroniques dans la BDD
	 * @return
	 */
	public List<ElectronicDevice> ListOfDevice(){
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder(); //Création du critéria
		CriteriaQuery<ElectronicDevice> query = criteriaBuilder.createQuery(ElectronicDevice.class); //Indiquer quel entité
		Root<ElectronicDevice> ed = query.from(ElectronicDevice.class); //Indiquer quel table
		query.select(ed); //ce qu'on fait
		TypedQuery<ElectronicDevice> req = manager.createQuery(query); //créer la requete
		return (List<ElectronicDevice>)req.getResultList(); //recupérer résultat
	}
	
	/**
	 * Methode pour récuperer les objets maisons dans la BDD
	 * @return
	 */
	public List<Home> ListOfHome(){
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder(); //Création du critéria
		CriteriaQuery<Home> query = criteriaBuilder.createQuery(Home.class); //Indiquer quel entité
		Root<Home> home = query.from(Home.class); //Indiquer quel table
		query.select(home); //ce qu'on fait
		TypedQuery<Home> req = manager.createQuery(query); //créer la requete
		return (List<Home>)req.getResultList(); //recupérer résultat
	}
	
	/**
	 * Methode pour l'ajout d'un chauffage
	 * @param modelname
	 * @param eleconso
	 * @param home
	 */
	public void AddHeater(String modelname,int eleconso,Home home){
		tx.begin();
			//Ajout d'un heater
			Heater h = new Heater();
			h.setModelName(modelname);
			h.setElecCosume(eleconso);
			h.setHome(home);
			manager.persist(h);	//on indique que l'objet h est persistant: on l'neregistre dans la base de donnée (insert into)
		tx.commit();
	}
	
	/**
	 * Methode pour l'ajout d'une maison
	 * @param addr
	 * @param eleconso
	 * @param p
	 * @param heaters
	 */
	public void AddHome(String addr,Person p,List<Heater> heaters){
		tx.begin();
			Home mai = new Home();
			mai.setAdresse(addr);
			mai.setPersonne(p);
			for (Heater h: heaters){
				mai.getHeaters().add(h); //on rajoute un element dans la liste (avec l'annotation @oneToMany y'aura une clé étrangére)
			}
			manager.persist(mai);
		tx.commit();
	}
	
	/**
	 * Methode pour l'ajout d'un Electronique device
	 * @param modelname
	 * @param eleconso
	 * @param p
	 */
	public void AddElectronic(String modelname,int eleconso,Person p){
		tx.begin();
			ElectronicDevice el = new ElectronicDevice();
			el.setModelName(modelname);
			el.setElecCosume(eleconso);
			el.setPersonne(p);
			manager.persist(el);
		tx.commit();
	}
	
	/**
	 * Methode pour l'ajout d'une Personne
	 * @param nom
	 * @param homes
	 * @param devices
	 * @param amis
	 */
	public void AddPerson(String nom,ArrayList<Home> homes,ArrayList<ElectronicDevice> devices,ArrayList<Person> amis){
		tx.begin();
			Person p= new Person();
			p.setNom(nom);
			p.setDevices(devices);
			p.setAmis(amis);
			p.setMaisons(homes);	
			manager.persist(p);
		tx.commit();
	}
	
	/**
	 * Methode appellée quand le serveur servlet démarre
	**/
	public void stop(){
		manager.close();
		factory.close();
	}
	
	/**
	 * Methode  pour rajouter un jeu de données à utiliser pour créer/remplir la BDD d'Opower pour les tests
	 */
	public void addData(){
		tx.begin();
		try {
			//Ajout d'une maison
			Home mai = new Home();
			mai.setAdresse("15 rue paumé 44000 quelque part");
			
			Home mai2 = new Home();
			mai2.setAdresse("25 rue paumé 44000 quelque part");
			
			Home mai3 = new Home();
			mai3.setAdresse("18 rue paumé 44000 quelque part");
			
			//Création d'une personne
			Person p= new Person();
			p.setNom("Paul");
			
			//Création d'une personne
			Person p2= new Person();
			p2.setNom("Pierres");
			
			//Ajout d'un heater
			Heater h = new Heater();
			//Pas de h.setId(1); car @generatedValue (valeur generer automatiquement)
			h.setModelName("SesCho");
			h.setElecCosume(1000);
			h.setHome(mai);
			manager.persist(h);	//on indique que l'objet h est persistant: on l'neregistre dans la base de donnée (insert into)
			
			//Ajout d'un heater
			Heater h2 = new Heater();
			h2.setModelName("SaBrul");
			h2.setElecCosume(200);
			h2.setHome(mai);
			manager.persist(h2);	//on indique que l'objet h est persistant: on l'neregistre dans la base de donnée (insert into)
			
			//Ajout d'un heater
			Heater h3 = new Heater();
			h3.setModelName("SeFroi");
			h3.setElecCosume(500);
			h3.setHome(mai2);
			manager.persist(h3);	//on indique que l'objet h est persistant: on l'neregistre dans la base de donnée (insert into)
			
			//Ajout d'un heater
			Heater h4 = new Heater();
			h4.setModelName("Sebon");
			h4.setElecCosume(300);
			h4.setHome(mai3);
			manager.persist(h4);	//on indique que l'objet h est persistant: on l'neregistre dans la base de donnée (insert into)
			
			//Completer maison
			mai.setPersonne(p);
			mai.getHeaters().add(h); //on rajoute un element dans la liste (avec l'annotation @oneToMany y'aura une clé étrangére)
			mai.getHeaters().add(h2); //on rajoute un element dans la liste (avec l'annotation @oneToMany y'aura une clé étrangére)
			manager.persist(mai);
			
			mai2.setPersonne(p2);
			mai2.getHeaters().add(h3);
			manager.persist(mai2);
			
			mai3.setPersonne(p2);
			mai3.getHeaters().add(h4);
			manager.persist(mai3);
			
			//Ajout d'un device 
			ElectronicDevice el1 = new ElectronicDevice();
			el1.setModelName("Lave5000");
			el1.setElecCosume(150);
			el1.setPersonne(p);
			manager.persist(el1);
			
			//Ajout d'un device 
			ElectronicDevice el2 = new ElectronicDevice();
			el2.setModelName("PcGaming");
			el2.setElecCosume(1500);
			el2.setPersonne(p);
			manager.persist(el2);
			
			//Ajout d'un device 
			ElectronicDevice el3 = new ElectronicDevice();
			el3.setModelName("Frigo400");
			el3.setElecCosume(200);
			el3.setPersonne(p2);
			manager.persist(el3);
			
			//Ajout d'un device 
			ElectronicDevice el4 = new ElectronicDevice();
			el4.setModelName("Lave3000");
			el4.setElecCosume(100);
			el4.setPersonne(p2);
			manager.persist(el4);
			
			//Completer une personne
			ArrayList<Home> homes= new ArrayList<Home>();
			ArrayList<ElectronicDevice> devices= new ArrayList<ElectronicDevice>();
			ArrayList<Person> amis= new ArrayList<Person>();
			
			homes.add(mai);
			devices.add(el1);
			devices.add(el2);
			
			p.setDevices(devices);
			p.setAmis(amis);
			p.setMaisons(homes);
			manager.persist(p);
			
			//Completer une personne
			ArrayList<Home> homes2= new ArrayList<Home>();
			ArrayList<ElectronicDevice> devices2= new ArrayList<ElectronicDevice>();
			ArrayList<Person> amis2= new ArrayList<Person>();
			
			homes2.add(mai2);
			homes2.add(mai3);
			devices2.add(el3);
			devices2.add(el4);
			amis2.add(p);
			
			p2.setDevices(devices);
			p2.setAmis(amis2);
			p2.setMaisons(homes);	
			manager.persist(p2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();
	}
	
}
