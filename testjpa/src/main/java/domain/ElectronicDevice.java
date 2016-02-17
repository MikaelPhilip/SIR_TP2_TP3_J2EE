package domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Entity qui représente un objet electronique dans notre BDD/application
 * @author PHILIP Mikael JELASSI Seifeddine
 */
@Entity
public class ElectronicDevice extends SmartDevice{
	
	//Liste des variables/attributs (Ce qui est en commentaire est plus utile depuis l'heritage de SmartDevice)
	//private Long id; //id
		
	//private String name; //nom Model
		
	//private int elecCosume; //Conso electrique
	
	//Personne qui possede la maison
	private Person personne;

	/**
	 * Constructeur
	 * @param Person p : personne qui possede ce device
	 */
	public ElectronicDevice(Person p) {
		super();
		this.personne =p;
	}

	/**
	 * Constructeur simple
	 */
	public ElectronicDevice() {
		super();
	}
	
	/*Accesseurs pour les variables (indispensables pour que se soit interpreté comme des attributs)*/
	/*@Id
    @GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getElecCosume() {
		return elecCosume;
	}

	public void setElecCosume(int elecCosume) {
		this.elecCosume = elecCosume;
	}*/
	
	@ManyToOne
	public Person getPersonne() {
		return personne;
	}

	public void setPersonne(Person personne) {
		this.personne = personne;
	}
	
	
	
}
