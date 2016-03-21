package gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.shared.PersonDTO;

public interface PersonServiceAsync {

	void savePerson(PersonDTO p, AsyncCallback<Void> callback);

	void updatePerson(double saldo, int id, AsyncCallback<Void> callback);

	void getPersons(AsyncCallback<List<PersonDTO>> callback);

	void deletePerson(int index, AsyncCallback<Void> callback);
}
