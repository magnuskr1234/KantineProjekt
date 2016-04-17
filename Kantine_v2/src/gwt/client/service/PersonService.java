package gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import gwt.shared.PersonDTO;





@RemoteServiceRelativePath("personservice")
public interface PersonService extends RemoteService {
	public PersonDTO getPerson(String username, String password) throws Exception;
	
	//void setPerson(PersonDTO pDTO) throws Exception;
	//  PersonDTO getPerson() throws Exception;
	// note: announcing exception makes it possible to communicate 
	// user defined exceptions from the server side to the client side
	// otherwise only generic server exceptions will be send back
	// in the onFailure call back method
	
	public void savePerson(PersonDTO p) throws Exception;
	public void updatePerson(double saldo, int id) throws Exception;
	
	
	
	public  List<PersonDTO> getPersons() throws Exception;
	public void deletePerson(int id) throws Exception; 
}
