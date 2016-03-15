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
					connection.prepareStatement( "INSERT INTO customers " + 
							"( name, password, admin, saldo ) " + 
							"VALUES ( ?, ?, ?, ? )" );

			// create query that updates a person
			updatePersonStmt = connection.prepareStatement( 
					"UPDATE customers SET name = ?, password = ?  WHERE id = ?" );

			// create query that get all persons in kartotek
			getPersonsStmt = connection.prepareStatement( 
					"SELECT * FROM customers "); 

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
			savePersonStmt.setString(2, p.getName());
			savePersonStmt.setString(3, p.getPassword());
			savePersonStmt.setInt(4, p.getAdminStatus());
			savePersonStmt.setDouble(5, p.getSaldo());

			savePersonStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(" \"savePerson\" fejlede");
		} 
	}

	@Override
	public void updatePerson(PersonDTO p) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void test(){
		System.out.println("test");
	}

	public List<PersonDTO> getPersons() throws Exception {
		ResultSet resultSet = null;
		PersonDTO user = null;
		ArrayList<PersonDTO> list = new ArrayList<PersonDTO>();
		
		
		//Try catch som laver en efterspørgsel til databasen. Hvis det mislykkes udskrives der en fejlbesked (catch)
		try{
			//Et prepared statement som består af et SQL statement
			PreparedStatement getUser = connection.prepareStatement("SELECT *  FROM customers");
			
			//Det første spørgsmåltegn i det ovenstående SQL statement
			//getUser.setString(1,  user);
			
			//Efterspørgslen til databasen eksekvers og resultatet indlæses i et ResultSet
			resultSet = getUser.executeQuery();
			
			// Om brugeren eksisterer i databasen oprettes et objekt af User ud fra ResultSet
			
			while (resultSet.next()){
				user = new PersonDTO(resultSet.getString("name"), resultSet.getString("password"), resultSet.getInt("adminStatus"), resultSet.getDouble("saldo"));
				list.add(user);	
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			try{
				resultSet.close();
			} catch (SQLException ex){
				ex.printStackTrace();
				close();
			}
		} return list;
	}

	@Override
	public void deletePerson(int id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSize() throws Exception {
		
		int i = 7;
		return i;
	}
	}