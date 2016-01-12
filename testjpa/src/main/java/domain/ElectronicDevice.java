package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
public class ElectronicDevice extends SmartDevice{
	
	//Liste des variables/attributs
	//private Long id; //id
		
	//private String name; //nom Model
		
	//private int elecCosume; //Conso electrique
	
	//Personne qui possede la maison
	private Person personne;

	/**
	 * Constructeur
	 * @param id
	 * @param name
	 * @param elecCosume
	 */
	public ElectronicDevice(Person p) {
		super();
		this.personne =p;
	}

	/**
	 * constructeur simple
	 */
	public ElectronicDevice() {
		super();
	}
	
	/*Accesseurs pour les varaibles (indispenbles pour que se soit interpreté comme des attributs*/
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
