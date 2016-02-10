package domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity qui reprï¿½sente un chauffage
 * @author 12000209
 *
 */
@Entity
@XmlRootElement
public class Heater extends SmartDevice{
	
	//Liste des variables/attributs
	//private Long id; //id
			
	//private String name; //nom Model
	
	//private int elecCosume; //Conso electrique
		
	private Home home; //maison ou est rattaché le chauffage
	
	/**
	 * Constructeur par dï¿½fault
	 */
	public Heater(){}

	/**
	 * Constructeur avec param
	 * @param id
	 * @param modelName
	 * @param elecCosume
	 */
	public Heater(Home home) {
		super();
		this.home = home;
	}

	/*Accesseurs pour les variables (indispenbles pour que se soit interpreté comme des attributs*/
	/**@Id
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
	}**/

	@ManyToOne
	public Home getHome() {
		return home;
	}

	public void setHome(Home home) {
		this.home = home;
	}
	
	
}
