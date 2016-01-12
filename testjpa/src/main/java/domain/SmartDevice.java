package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
import javax.persistence.Inheritance;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SmartDevice {

	private Long id; 
	
	private String modelName; //nom Model
	
	private int ElecCosume; //Conso electrique

	/**
	 * Constructeurs avec param
	 * @param id
	 * @param modelName
	 * @param elecCosume
	 */
	public SmartDevice(Long id, String modelName, int elecCosume) {
		super();
		this.id = id;
		this.modelName = modelName;
		ElecCosume = elecCosume;
	}

	/**
	 * Constructeur
	 */
	public SmartDevice() {
		super();
	}
	
	@Id
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
	}

}
