package gwt.server;


import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import gwt.client.service.PersonService;
import gwt.shared.PersonDTO;
import gwt.server.*;

public class PersonServiceImpl extends RemoteServiceServlet implements PersonService {

	
	/**
	 * 
	 */
	
	PersonDB db = new PersonDB();
	  
	// data store		
	public List<PersonDTO> pList;

	 public PersonServiceImpl() throws Exception{
	    // instantiate new java object on server
		pList = new ArrayList<PersonDTO>();
	    
		// Indset start data
	    
	  }

	  
	/*  public void setPerson(PersonDTO pDTO) throws Exception {
	    this.pList = pList;
	  }*/
	 
	  public List<PersonDTO> getPerson() throws RuntimeException {
	    return pList;
	  }

	@Override
	public void savePerson(PersonDTO p) throws RuntimeException {
		// TODO Auto-generated method stub
	
		try {
			db.savePerson(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pList.add(p);
	}

	@Override
	public void updatePerson(PersonDTO p) throws RuntimeException {
		// TODO Auto-generated method stub
		for (int i=0; i<pList.size();i++)
			if (pList.get(i).getId() == p.getId())	
				pList.set(i, p);
	}

	@Override
	public List<PersonDTO> getPersons() throws Exception {
		return db.getPersons();
		
	}

	@Override
	public void deletePerson(int id) throws Exception {
		// TODO Auto-generated method stub
		// find object with id and remove it
		//for (int i=0; i<getSize();i++)
		//	if (db.getPersons().get(i).getId() == id)	

				db.deletePerson(id);
		
	}

	@Override
	public int getSize() throws Exception {
		// TODO Auto-generated method stub
		//int i = db.getPersons().size();
		//int i = 4*4;
	//	db.getPersons().
		int i = 3;
		return i;
	}
	}
