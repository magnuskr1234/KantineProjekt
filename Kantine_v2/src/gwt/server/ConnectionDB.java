package gwt.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import gwt.client.service.ItemService;
import gwt.client.service.PersonService;
import gwt.shared.ItemDTO;
import gwt.shared.PersonDTO;
import gwt.shared.DALException;

/**
 * Connection for database. Used only for connection. 
 *
 */
@SuppressWarnings("serial")
public class ConnectionDB extends RemoteServiceServlet implements ItemService, PersonService {

	private static final String URL = "jdbc:mysql://52.58.62.183:3306/kantinen";
	private static final String USERNAME = "dummy";
	private static final String PASSWORD = "gruppe3";

	private static Connection connection = null; // manages connection

	private ItemDB itemDB;
	private PersonDB personDB;

	/**
	 * Constructor for establishing connection. 
	 * @throws Exception
	 */
	public ConnectionDB() throws Exception {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			// Create instance databases. 
			itemDB = new ItemDB(this);
			personDB = new PersonDB(this);
		} catch (SQLException sqlException) {
			throw new DALException("Kan ikke oprette forbindelse til database");
		}

	}

	public Connection getConnection() {
		return connection;
	}

	/**
	 * Method used to close the database connection
	 */
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Methods used for items. 
	 */
	@Override
	public List<ItemDTO> getMostSoldItems() throws Exception {
		List<ItemDTO> items = itemDB.getMostSoldItems();
		return items;
	}

	@Override
	public List<ItemDTO> getStatList() throws Exception {
		List<ItemDTO> items = itemDB.getStatList();
		return items;
	}

	@Override
	public List<ItemDTO> getHistoryList(int i) throws Exception {
		List<ItemDTO> items = itemDB.getHistoryList(i);
		return items;
	}

	@Override
	public void saveItemToHistory(int c, String name, double price, double currentSaldo) throws Exception {
		itemDB.saveItemToHistory(c, name, price, currentSaldo);
	}

	@Override
	public void saveItem(ItemDTO p) throws Exception {
		itemDB.saveItem(p);
	}

	@Override
	public void updateItem(double price, int id) throws Exception {
		itemDB.updateItem(price, id);
	}

	@Override
	public List<ItemDTO> getItems() throws Exception {
		List<ItemDTO> items = itemDB.getItems();
		return items;
	}

	@Override
	public ItemDTO getItem(String name) throws Exception {
		ItemDTO item = itemDB.getItem(name);
		return item;

	}

	@Override
	public void deleteItem(int id) throws Exception {
		itemDB.deleteItem(id);
	}

	/**
	 * Methods used for persons. 
	 */
	@Override
	public void savePerson(PersonDTO p) throws Exception {
		personDB.savePerson(p);

	}

	@Override
	public void updatePerson(double saldo, int id) throws Exception {
		personDB.updatePerson(saldo, id);

	}

	@Override
	public PersonDTO getPerson(String username, String password) throws Exception {
		PersonDTO person = personDB.getPerson(username, password);
		return person;

	}

	@Override
	public List<PersonDTO> getPersons() throws Exception {
		List<PersonDTO> persons = personDB.getPersons();
		return persons;
	}

	@Override
	public void deletePerson(int id) throws Exception {
		personDB.deletePerson(id);

	}
}