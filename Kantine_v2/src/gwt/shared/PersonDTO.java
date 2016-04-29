package gwt.shared;

import java.io.Serializable;
/**
 * PersonDTO defines the Person Data Trasnfer Object. 
 * @author magnusrasmussen
 *
 */
@SuppressWarnings("serial")
public class PersonDTO implements Serializable {
	
	
	private int id;
	private String name;
	private String password;
	private int admin; 
	private double saldo;
	
	/**
	 *  Default constructor - must be defined
	 */
	public PersonDTO() {		
	}
	
	/**
	 * Contructor for showing person list
	 * @param id
	 * @param name
	 * @param saldo
	 */
	public PersonDTO(int id, String name, double saldo){
		super();
		this.id = id;
		this.name = name;
		this.saldo = saldo;
	}
	
	/**
	 * Contructor used to log in an user. 
	 * @param name
	 * @param password
	 * @param admin
	 * @param saldo
	 */
	public PersonDTO(String name, String password, int admin, double saldo) {
		super();
		
		this.name = name;
		this.password = password;
		this.admin = admin;
		this.saldo = saldo;
	}
	
	/**
	 * Constructor used for creating a new user.
	 * @param id
	 * @param name
	 * @param password
	 * @param admin
	 * @param saldo
	 */
	public PersonDTO(int id, String name, String password, int admin, double saldo) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.admin = admin;
		this.saldo = saldo;
	}
	
	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String navn) {
		this.name = navn;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getAdminStatus() {
		return admin;
	}

	public void setAdminStatus(int admin) {
		this.admin = admin;
	}
	public double getSaldo(){
		return saldo;
	}
	
	public void setSaldo(double saldo){
		this.saldo = saldo;
	}

}
