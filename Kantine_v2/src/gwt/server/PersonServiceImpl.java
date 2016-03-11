package gwt.server;

import com.mysql.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import gwt.client.service.PersonService;
import gwt.shared.PersonDTO;

public class PersonServiceImpl extends RemoteServiceServlet implements PersonService {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int id = 0;
	  // data store
		private List<PersonDTO> pList;
		
	  public PersonServiceImpl() throws RuntimeException{
	    // instantiate new java object on server
	    pList = new ArrayList<PersonDTO>();
	  }

	  
	/*  public void setPerson(PersonDTO pDTO) throws Exception {
	    this.pList = pList;
	  }*/
	 
	  public List<PersonDTO> getPerson() throws Exception {
	    return pList;
	  }

	@Override
	public void savePerson(PersonDTO p) throws Exception {
		// TODO Auto-generated method stub
		p.setId(id++);
		pList.add(p);
	}

	@Override
	public void updatePerson(PersonDTO p) throws Exception {
		// TODO Auto-generated method stub
		for (int i=0; i<pList.size();i++)
			if (pList.get(i).getId() == p.getId())	
				pList.set(i, p);
	}

	@Override
	public List<PersonDTO> getPersons() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePerson(int id) throws Exception {
		// TODO Auto-generated method stub
		// find object with id and remove it
		for (int i=0; i<pList.size();i++)
			if (pList.get(i).getId() == id)	
				pList.remove(i);
		
	}

	@Override
	public int getSize() throws Exception {
		// TODO Auto-generated method stub
		return pList.size();
	}
	}
