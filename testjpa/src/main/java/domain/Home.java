package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Home {
	
	//Liste des variables/attributs
	private Long id; //id
	
	private String adresse; //id
	
	private List<Heater> heaters = new ArrayList<Heater>(); //Chauffages

	//Personne qui possede la maison
	private Person personne;
	/**
	 * Constructeur basique
	 */
	public Home() {
		super();
	}
	
	/**
	 * Constructeur 
	 */
	public Home(Long id, String adresse, List<Heater> heaters) {
		super();
		this.id = id;
		this.adresse = adresse;
		this.heaters = heaters;
	}

	//Acceseurs
	@Id
    @GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	@OneToMany(mappedBy = "home", cascade = CascadeType.PERSIST)
	public List<Heater> getHeaters() {
		return heaters;
	}

	public void setHeaters(List<Heater> heaters) {
		this.heaters = heaters;
	}
	
	@ManyToOne
	public Person getPersonne() {
		return personne;
	}

	public void setPersonne(Person personne) {
		this.personne = personne;
	}

}
