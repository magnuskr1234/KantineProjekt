import java.util.List;

import gwt.shared.PersonDTO;

public interface InterfacePersonDB {
	public void savePerson(PersonDTO p) throws Exception;
	public void updatePerson(PersonDTO p) throws Exception;
	public List<PersonDTO> getPersons() throws Exception;
	public void deletePerson(int index) throws Exception;
	public int getSize() throws Exception;
}