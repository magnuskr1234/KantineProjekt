package gwt.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import gwt.client.service.PersonService;
import gwt.shared.PersonDTO;
import gwt.shared.DALException;


public class PersonDB extends RemoteServiceServlet implements PersonService  {

	private static final String URL = "jdbc:mysql://52.37.223.129:3306/kantinen";
	private static final String USERNAME = "dummy";
	private static final String PASSWORD = "gruppe3";

	private Connection connection = null; // manages connection

	private PreparedStatement savePersonStmt = null;
	private PreparedStatement updatePersonStmt = null;
	private PreparedStatement getPersonsStmt = null;
	private PreparedStatement getSizeStmt = null;
	private PreparedStatement deletePersonStmt = null;

	public PersonDB() throws Exception {
		try 
		{
			connection = 
					DriverManager.getConnection( URL, USERNAME, PASSWORD );

			// create query that add a person to kartotek
			savePersonStmt = 
					connection.prepareStatement( "INSERT INTO person " + 
							"( navn, alder ) " + 
							"VALUES ( ?, ? )" );

			// create query that updates a person
			updatePersonStmt = connection.prepareStatement( 
					"UPDATE person SET navn = ?, alder = ?  WHERE id = ?" );

			// create query that get all persons in kartotek
			getPersonsStmt = connection.prepareStatement( 
					"SELECT * FROM person "); 

			// create query that gets size of kartotek
			getSizeStmt = connection.prepareStatement( 
					"SELECT COUNT(*) FROM person ");

			// create query that deletes a person in kartotek
			deletePersonStmt = connection.prepareStatement( 
					"DELETE FROM person WHERE id =  ? ");


		} 
		catch ( SQLException sqlException )
		{
			throw new DALException("Kan ikke oprette forbindelse til database");
		}
	}

	@Override
	public void savePerson(PersonDTO p) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePerson(PersonDTO p) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PersonDTO> getPersons() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePerson(int id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSize() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	}