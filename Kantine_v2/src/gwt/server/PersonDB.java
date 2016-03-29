package gwt.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


import gwt.client.service.PersonService;
import gwt.shared.PersonDTO;
import gwt.shared.DALException;


public class PersonDB extends RemoteServiceServlet implements PersonService  {

	private static final String URL = "jdbc:mysql://52.58.62.183:3306/kantinen";
	private static final String USERNAME = "dummy";
	private static final String PASSWORD = "gruppe3";

	private static Connection connection = null; // manages connection

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
					connection.prepareStatement( "INSERT INTO customers ( name, password, admin, saldo ) VALUES ( ?, ?, ?, ? )" );

			// create query that updates a person
			updatePersonStmt = connection.prepareStatement( 
					"UPDATE customers set saldo = ?  WHERE id = ?" );

			// create query that get all persons in kartotek
			getPersonsStmt = connection.prepareStatement( 
					"SELECT * FROM customers ORDER BY name "); 

			// create query that gets size of kartotek
			getSizeStmt = connection.prepareStatement( 
					"SELECT COUNT(*) FROM customers ");

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
	 * Method used to close the database connection
	 */
	private static void close() {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
}

	@Override
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

	@Override
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
						resultSet.getString( "name" ),
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
				close();
			} 
		}
		return results; 
	}

	@Override
	public void deletePerson(int id) throws Exception {
	
		
	
		
		try {
			deletePersonStmt.setInt(1, id);
		
			deletePersonStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(" \"deletePerson\" fejlede");
		} 
	}
	}