package domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Entity qui représente une personne dans notre BDD/Application
 * @author PHILIP Mikael JELASSI Seifeddine
 */
@NamedQuery(name="Person.findAll", query="Select s from Person s") //Declaration requete nommée
@Entity
@XmlRootElement
public class Person {
	
	//Liste des variables/attributs
	private Long id; //id
	
	private String nom; //nom 
	
	private List<Home> maisons; //Liste des maisons
	
	private List<ElectronicDevice> devices; //Liste des devices
	
	private List<Person> amis; //Liste des amis
	
	/**
	 * Constructeur
	 */
	public Person() {
		super();
	}
	
	/**
	 * Constructeur avec paramêtre
	 * @param id
	 * @param nom
	 * @param maisons
	 * @param devices
	 * @param amis
	 */
	public Person(Long id, String nom, List<Home> maisons, List<ElectronicDevice> devices, List<Person> amis) {
		super();
		this.id = id;
		this.nom = nom;
		this.maisons = maisons;
		this.devices = devices;
		this.amis = amis;
	}

	//Accesseur
	@Id
    @GeneratedValue
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@OneToMany(mappedBy = "personne", cascade = CascadeType.PERSIST)
	@JsonIgnore
	@XmlTransient
	public List<Home> getMaisons() {
		return maisons;
	}


	public void setMaisons(List<Home> maisons) {
		this.maisons = maisons;
	}

	@OneToMany(mappedBy = "personne", cascade = CascadeType.PERSIST)
	@JsonIgnore
	@XmlTransient
	public List<ElectronicDevice> getDevices() {
		return devices;
	}

	public void setDevices(List<ElectronicDevice> devices) {
		this.devices = devices;
	}

	@JoinTable(name = "amis", joinColumns = { @JoinColumn(name = "friend", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
					@JoinColumn(name = "friendof", referencedColumnName = "id", nullable = false)})
	@ManyToMany
	@JsonIgnore
	@XmlTransient
	public List<Person> getAmis() {
		return amis;
	}

	public void setAmis(List<Person> amis) {
		this.amis = amis;
	}
}
