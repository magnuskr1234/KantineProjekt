package gwt.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import gwt.shared.PersonDTO;
import gwt.shared.DALException;

/**
 * Class used to make SQL statements to database. Contains all statements used for users. 
 * @author magnusrasmussen
 *
 */
public class PersonDB {
	
	Connection connection;
	ConnectionDB connectionDB;
	private PreparedStatement savePersonStmt = null;
	private PreparedStatement updatePersonStmt = null;
	private PreparedStatement getPersonStmt = null;
	private PreparedStatement getPersonsStmt = null;
	private PreparedStatement deletePersonStmt = null;

	/**
	 * Contains SQL statements. 
	 * @param connectionDB
	 * @throws Exception
	 */
	public PersonDB(ConnectionDB connectionDB) throws Exception {
		try 
		{
			this.connectionDB = connectionDB;
			connection = connectionDB.getConnection();
			
			// create query that add a person to kartotek
			savePersonStmt = 
					connection.prepareStatement( "INSERT INTO customers ( email, password, admin, saldo ) VALUES ( ?, ?, ?, ? )" );

			// create query that updates a person
			updatePersonStmt = connection.prepareStatement( 
					"UPDATE customers set saldo = ?  WHERE id = ?" );

			// Create query that get 1 person 
			getPersonStmt = connection.prepareStatement(
					"SELECT * FROM customers where email = ? AND password = ?");
			
			// create query that get all persons from table "persons"
			getPersonsStmt = connection.prepareStatement( 
					"SELECT * FROM customers ORDER BY email "); 

			// create query that deletes a person in kartotek
			deletePersonStmt = connection.prepareStatement( 
					"DELETE FROM customers WHERE id =  ? ");


		} 
		catch ( SQLException sqlException )
		{
			throw new DALException("Kan ikke oprette forbindelse til database");
		}
		
	}


	/**
	 * Creates a new user in database
	 * @param p
	 * @throws Exception
	 */
	public void savePerson(PersonDTO p) throws Exception {
		// simulate server error
		// throw new RuntimeException(" \"savePerson\" fejlede");

		try {
			savePersonStmt.setString(1, p.getName());
			savePersonStmt.setString(2, p.getPassword());
			savePersonStmt.setInt(3, p.getAdminStatus());
			savePersonStmt.setDouble(4, p.getSaldo());

			savePersonStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(" \"savePerson\" fejlede");
		} 
	}

	/**
	 * Updates a person
	 * @param saldo
	 * @param id
	 * @throws Exception
	 */
	public void updatePerson(double saldo, int id) throws Exception {
		
		try {		
			updatePersonStmt.setDouble(1, saldo);
			updatePersonStmt.setInt(2, id);
			

			updatePersonStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(" \"updatePerson\" fejlede");
		} 
	}
	
	public void test(){
		System.out.println("test");
	}

	
	/**
	 * Get all person from database
	 * @return
	 * @throws Exception
	 */
	public List<PersonDTO> getPersons() throws Exception {
		List< PersonDTO > results = null;
		ResultSet resultSet = null;
		
		try 
		{
			resultSet = getPersonsStmt.executeQuery(); 
			results = new ArrayList< PersonDTO >();

			while ( resultSet.next() )
			{
				results.add( new PersonDTO(
						resultSet.getInt("id"),
						resultSet.getString( "email" ),
						resultSet.getString( "password" ),
						resultSet.getInt( "admin" ),
						resultSet.getDouble("saldo")));
			} 
		} 
		catch ( SQLException sqlException )
		{
			throw new DALException(" \"getPersons\" fejlede");
		} 
		finally
		{
			try 
			{
				resultSet.close();
			} 
			catch ( SQLException sqlException )
			{
				sqlException.printStackTrace();         
				connectionDB.close();
			} 
		}
		return results; 
	}

	/**
	 * Deletes a person from database
	 * @param id
	 * @throws Exception
	 */
	public void deletePerson(int id) throws Exception {
		
		try {
			deletePersonStmt.setInt(1, id);
		
			deletePersonStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(" \"deletePerson\" fejlede");
		} 
	}

	/**
	 *  Get currentperson from database. Checks if user exists
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */

	public PersonDTO getPerson(String username, String password) throws Exception {
		ResultSet resultSet = null;
		PersonDTO person = null;
		
		try{
		getPersonStmt.setString(1, username);
		getPersonStmt.setString(2, password);
		
		resultSet = getPersonStmt.executeQuery();
		
		while (resultSet.next()){
			person = new PersonDTO();
			
			person.setId(resultSet.getInt("id"));
			person.setName(resultSet.getString("email"));
			person.setPassword(resultSet.getString("password"));
			person.setAdminStatus(resultSet.getInt("admin"));
			person.setSaldo(resultSet.getDouble("saldo"));
		}
		// The catch which is used if either the statement or connection is failing
				} catch (SQLException e) {
					e.printStackTrace();
				// A finally try catch which closes the result set and it can't then close the db connection
				} finally {
					try {
						resultSet.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
						connectionDB.close();
					}
				}
		return person;
	}
	}