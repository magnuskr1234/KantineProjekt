package gwt.client.service;

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import gwt.shared.PersonDTO;

/**
 * Methods used for item RPC call. 
 *
 */
@RemoteServiceRelativePath("personservice")
public interface PersonService extends RemoteService {
	public void savePerson(PersonDTO p) throws Exception;
	public void updatePerson(double saldo, int id) throws Exception;
	public  List<PersonDTO> getPersons() throws Exception;
	public void deletePerson(int id) throws Exception; 
	public PersonDTO getPerson(String username, String password) throws Exception;
}
