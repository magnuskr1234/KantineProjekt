package gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import gwt.shared.PersonDTO;





@RemoteServiceRelativePath("personservice")
public interface PersonService extends RemoteService {
	//void setPerson(PersonDTO pDTO) throws Exception;
	//  PersonDTO getPerson() throws Exception;
	// note: announcing exception makes it possible to communicate 
	// user defined exceptions from the server side to the client side
	// otherwise only generic server exceptions will be send back
	// in the onFailure call back method
	
	void savePerson(PersonDTO p) throws Exception;
	void updatePerson(PersonDTO p) throws Exception;
	List<PersonDTO> getPersons() throws Exception;
	void deletePerson(int id) throws Exception; 
	int getSize() throws Exception;
}
