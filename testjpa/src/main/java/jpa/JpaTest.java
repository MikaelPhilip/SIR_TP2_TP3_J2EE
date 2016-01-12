package jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import domain.ElectronicDevice;
import domain.Heater;
import domain.Home;
import domain.Person;

public class JpaTest {
	//TODO: rajouter du script pour tester nos entit�s
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("mysql"); //C'est ici qu'on choisit la config cr�er dans persistence.xml
		EntityManager manager = factory.createEntityManager();

		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		try {
			//Ajout d'une maison
			Home mai = new Home();
			mai.setAdresse("15 rue paum� 44000 quelque part");
			
			Home mai2 = new Home();
			mai2.setAdresse("25 rue paum� 44000 quelque part");
			
			Home mai3 = new Home();
			mai3.setAdresse("18 rue paum� 44000 quelque part");
			
			//Cr�ation d'une personne
			Person p= new Person();
			p.setNom("Paul");
			
			//Cr�ation d'une personne
			Person p2= new Person();
			p2.setNom("Pierres");
			
			//Ajout d'un heater
			Heater h = new Heater();
			//Pas de h.setId(1); car @generatedValue (valeur generer automatiquement)
			h.setModelName("SesCho");
			h.setElecCosume(1000);
			h.setHome(mai);
			manager.persist(h);	//on indique que l'objet h est persistant: on l'neregistre dans la base de donn�e (insert into)
			
			//Ajout d'un heater
			Heater h2 = new Heater();
			h2.setModelName("SaBrul");
			h2.setElecCosume(200);
			h2.setHome(mai);
			manager.persist(h2);	//on indique que l'objet h est persistant: on l'neregistre dans la base de donn�e (insert into)
			
			//Ajout d'un heater
			Heater h3 = new Heater();
			h3.setModelName("SeFroi");
			h3.setElecCosume(500);
			h3.setHome(mai2);
			manager.persist(h3);	//on indique que l'objet h est persistant: on l'neregistre dans la base de donn�e (insert into)
			
			//Ajout d'un heater
			Heater h4 = new Heater();
			h4.setModelName("Sebon");
			h4.setElecCosume(300);
			h4.setHome(mai3);
			manager.persist(h4);	//on indique que l'objet h est persistant: on l'neregistre dans la base de donn�e (insert into)
			
			//Completer maison
			mai.setPersonne(p);
			mai.getHeaters().add(h); //on rajoute un element dans la liste (avec l'annotation @oneToMany y'aura une cl� �trang�re)
			mai.getHeaters().add(h2); //on rajoute un element dans la liste (avec l'annotation @oneToMany y'aura une cl� �trang�re)
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
		/*Premier requete (question 1)
		String s = "SELECT e FROM Heater as e where e.modelName=:name"; // :name => pram�tre 
		
		Query q = manager.createQuery(s,Heater.class); //cr�er la requete ou on indique les entit�s manipul�s
		q.setParameter("name", "SesCho"); 
		List<Heater> res = q.getResultList(); //recup�rer r�sultat
		
		System.err.println("taille:" + res.size());
		System.err.println("id:" +res.get(0).getId());
		System.err.println("name:"+res.get(0).getModelName()); //get(i) obtenir le i-�me r�sultat)
		System.err.println("elec consume:"+res.get(0).getElecCosume()); */
		
		/*Seconde requete (question 2)*/
		/*String s = "SELECT e FROM Home as e";
		Query q = manager.createQuery(s,Home.class); //cr�er la requete ou on indique les entit�s manipul�s
		List<Home> res = q.getResultList(); //recup�rer r�sultat
		
		for(int i=0; i< res.size();i++){
			System.err.println("id:" +res.get(i).getId());
			System.err.println("adresse:"+res.get(i).getAdresse()); //get(i) obtenir le i-�me r�sultat)
			System.err.println("chauffage"+res.get(i).getHeaters());
		}*/
		
		
		/*Trosi�me requete (question 3)*/
		/*String s = "SELECT e FROM Person as e";
		Query q = manager.createQuery(s,Person.class); //cr�er la requete ou on indique les entit�s manipul�s
		List<Person> res = q.getResultList(); //recup�rer r�sultat
		
		for(int i=0; i< res.size();i++){
			System.err.println("id:" +res.get(i).getId());
			System.err.println("nom:"+res.get(i).getNom()); //get(i) obtenir le i-�me r�sultat)
			System.err.println("services:"+res.get(i).getDevices());
			System.err.println("maisons:"+res.get(i).getMaisons());
			System.err.println("amis:"+res.get(i).getAmis());
		}*/
		
		/*Test en requete criteria query*/
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder(); //Cr�ation du crit�ria
		CriteriaQuery<Heater> query = criteriaBuilder.createQuery(Heater.class); //Indiquer quel entit�
		Root<Heater> heater = query.from(Heater.class); //Indiquer quel table
		query.select(heater); //ce qu'on fait
		TypedQuery<Heater> req = manager.createQuery(query); //cr�er la requete
		List<Heater> resu = req.getResultList(); //la lancer
		
		System.out.println("taille:" + resu.size());
		System.out.println("id:" +resu.get(0).getId());
		System.out.println("name:"+resu.get(0).getModelName()); //get(i) obtenir le i-�me r�sultat)
		System.out.println("elec consume:"+resu.get(0).getElecCosume()); 
		
		/*Test requete nomm�*/
		Query q = manager.createNamedQuery("Person.findAll"); //utiliser requete nomm�
		List<Person> res = q.getResultList(); //recup�rer r�sultat
		
		for(int i=0; i< res.size();i++){
			System.err.println("id:" +res.get(i).getId());
			System.err.println("nom:"+res.get(i).getNom()); //get(i) obtenir le i-�me r�sultat)
			System.err.println("services:"+res.get(i).getDevices());
			System.err.println("maisons:"+res.get(i).getMaisons());
			System.err.println("amis:"+res.get(i).getAmis());
		}
		//fermeture du manager
		manager.close();
		factory.close();
	}

}
