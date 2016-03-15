package gwt.shared;

import java.io.Serializable;
/**
 * PersonDTO klassen definerer de bruger vi har i kantinen og indeholder id, navn samt kodeord til disse. 
 * @author magnusrasmussen
 *
 */
public class PersonDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String password;
	
	// default constructor - must be defined
	public PersonDTO() {
		name = "udefineret";
		password = "999";
		
	}
	
	public PersonDTO(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}
	
	public PersonDTO(int id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
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

  @Override
  public String toString() {
    return "PersonDTO [id=" + id + ", name=" + name + ", password=" + password + "]";
  }
}
