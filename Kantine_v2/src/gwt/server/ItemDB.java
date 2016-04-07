package gwt.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import gwt.client.service.ItemService;
import gwt.client.service.PersonService;
import gwt.client.ui.admin.ShowUserListView;
import gwt.shared.ItemDTO;
import gwt.shared.PersonDTO;
import gwt.shared.DALException;

public class ItemDB extends RemoteServiceServlet implements ItemService, PersonService {

	private static final String URL = "jdbc:mysql://52.58.62.183:3306/kantinen";
	private static final String USERNAME = "dummy";
	private static final String PASSWORD = "gruppe3";

	private static Connection connection = null; // manages connection

	private PreparedStatement saveItemStmt = null;
	private PreparedStatement updateItemStmt = null;
	private PreparedStatement getItemsStmt = null;
	private PreparedStatement getSizeStmt = null;
	private PreparedStatement deleteItemStmt = null;
	private PreparedStatement saveItemToHistoryStmt = null;
	private PreparedStatement showHistoryListStmt = null;
	private PreparedStatement showStatListStmt = null;
	private PreparedStatement showMostSoldItemsStmt = null;

	ShowUserListView su;
	
	private ItemBla itemBla;

	public ItemDB() throws Exception {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// create query that add an item to database
			
			itemBla = new ItemBla(this);
			
			
			
			
			
			
			saveItemStmt = connection.prepareStatement("INSERT INTO items ( title, price ) VALUES ( ?, ? )");

			// create query that updates an item
			updateItemStmt = connection.prepareStatement("UPDATE items SET price = ?  WHERE id = ?");

			// create query that get all items in database
			getItemsStmt = connection.prepareStatement("SELECT * FROM items ORDER BY title ");

			// create query that gets size of database
			getSizeStmt = connection.prepareStatement("SELECT COUNT(*) FROM items ");

			// create query that deletes an item in database
			deleteItemStmt = connection.prepareStatement("DELETE FROM items WHERE id =  ? ");
			
			saveItemToHistoryStmt = connection.prepareStatement("INSERT INTO history (customer_id, item_name, item_price) VALUES (?, ?, ?)");
			
			showHistoryListStmt = connection.prepareStatement("SELECT item_name, item_price, date_ordered FROM history WHERE customer_id = ? ORDER BY history.date_ordered DESC");
			
			showStatListStmt = connection.prepareStatement("SELECT customers.name, history.item_name, history.item_price, history.date_ordered FROM history LEFT JOIN customers ON customers.id=history.customer_id ORDER BY history.date_ordered DESC ");

			showMostSoldItemsStmt = connection.prepareStatement("SELECT item_name, count(*) AS total from history GROUP BY item_name ORDER BY total DESC");
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
	
	public List<ItemDTO> getMostSoldItemsTest() throws Exception {
		List<ItemDTO> items = itemBla.getMostSoldItems();
		
		return items;
	}
	
	public List<ItemDTO> getMostSoldItems() throws Exception {
		List<ItemDTO> results = null;
		ResultSet resultSet = null;
		int count = 0;

		try {
			resultSet = showMostSoldItemsStmt.executeQuery();
			results = new ArrayList<ItemDTO>();
			
			
			while (resultSet.next() && count < 3) {
				results.add(new ItemDTO(resultSet.getString("item_name"), resultSet.getInt("total")));
				count++;
			}
		} catch (SQLException sqlException) {
			throw new DALException(" \"getItems\" fejlede");
		} finally {
			try {
				resultSet.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				close();
			}
		}
		return results;
	}
	
	public List<ItemDTO> getStatList() throws Exception {
		List<ItemDTO> results = null;
		ResultSet resultSet = null;

		try {
			resultSet = showStatListStmt.executeQuery();
			results = new ArrayList<ItemDTO>();
			
			
			while (resultSet.next()) {
				results.add(new ItemDTO(resultSet.getString("name"), resultSet.getString("item_name"), resultSet.getDouble("item_price"), resultSet.getDate("date_ordered")));
			}
		} catch (SQLException sqlException) {
			throw new DALException(" \"getItems\" fejlede");
		} finally {
			try {
				resultSet.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				close();
			}
		}
		return results;
	}
	
	public List<ItemDTO> getHistoryList(int i) throws Exception {
		List<ItemDTO> results = null;
		ResultSet resultSet = null;

		try {
			showHistoryListStmt.setInt(1, i);
			resultSet = showHistoryListStmt.executeQuery();
			results = new ArrayList<ItemDTO>();
			
			
			while (resultSet.next()) {
				results.add(new ItemDTO(resultSet.getString("item_name"), resultSet.getDouble("item_price"), resultSet.getDate("date_ordered")));
			}
		} catch (SQLException sqlException) {
			throw new DALException(" \"getItems\" fejlede");
		} finally {
			try {
				resultSet.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				close();
			}
		}
		return results;
	}
	
	@Override
	public void saveItemToHistory(int c, String name, double price) throws Exception {
		try {
			saveItemToHistoryStmt.setInt(1, c);
			saveItemToHistoryStmt.setString(2, name);
			saveItemToHistoryStmt.setDouble(3, price);

			saveItemToHistoryStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(" \"saveItemToHistory\" fejlede");
		}
	}

	@Override
	public void saveItem(ItemDTO p) throws Exception {

		try {
			saveItemStmt.setString(1, p.getName());
			saveItemStmt.setDouble(2, p.getPrice());

			saveItemStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(" \"saveItem\" fejlede");
		}
	}

	@Override
	public void updateItem(double price, int id) throws Exception {
		try {		
			updateItemStmt.setDouble(1, price);
			updateItemStmt.setInt(2, id);
			

			updateItemStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(" \"updateItem\" fejlede");
		} 

	}

	public List<ItemDTO> getItems() throws Exception {
		List<ItemDTO> results = null;
		ResultSet resultSet = null;

		try {
			resultSet = getItemsStmt.executeQuery();
			results = new ArrayList<ItemDTO>();

			while (resultSet.next()) {
				results.add(new ItemDTO(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getDouble("price")));
			}
		} catch (SQLException sqlException) {
			throw new DALException(" \"getItems\" fejlede");
		} finally {
			try {
				resultSet.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				close();
			}
		}
		return results;
	}

	@Override
	public void deleteItem(int id) throws Exception {

		try {
			deleteItemStmt.setInt(1, id);

			deleteItemStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(" \"deletePerson\" fejlede");
		}
	}

	@Override
	public void savePerson(PersonDTO p) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePerson(double saldo, int id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PersonDTO getPerson(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
}