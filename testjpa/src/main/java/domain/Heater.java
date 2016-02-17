package domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity qui représente un chauffage dans notre BDD/application
 * @author PHILIP Mikael JELASSI Seifeddine
 */
@Entity
@XmlRootElement
public class Heater extends SmartDevice{
	
	//Liste des variables/attributs (Ce qui est en commentaire est plus utile depuis l'heritage de SmartDevice)
	//private Long id; //id
			
	//private String name; //nom Model
	
	//private int elecCosume; //Conso electrique
		
	private Home home; //maison où est rattachée le chauffage
	
	/**
	 * Constructeur par défault
	 */
	public Heater(){}

	/**
	 * Constructeur avec param
	 * @param home represente la maison où se trouve le chauffage
	 */
	public Heater(Home home) {
		super();
		this.home = home;
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

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public int getElecCosume() {
		return ElecCosume;
	}

	public void setElecCosume(int elecCosume) {
		ElecCosume = elecCosume;
	}*/

	@ManyToOne
	public Home getHome() {
		return home;
	}

	public void setHome(Home home) {
		this.home = home;
	}
	
	
}
