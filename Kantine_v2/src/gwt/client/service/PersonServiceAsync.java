package gwt.client.service;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.shared.PersonDTO;

/**
 * Async interface for person RPC. 
 *
 */
public interface PersonServiceAsync {
	
	void getPerson(String username, String password, AsyncCallback<PersonDTO> callback);

	void savePerson(PersonDTO p, AsyncCallback<Void> callback);

	void updatePerson(double saldo, int id, AsyncCallback<Void> callback);

	void getPersons(AsyncCallback<List<PersonDTO>> callback);

	void deletePerson(int index, AsyncCallback<Void> callback);
}
