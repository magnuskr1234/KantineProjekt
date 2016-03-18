package gwt.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import gwt.client.service.ItemService;
import gwt.client.ui.admin.ShowUserListView;
import gwt.shared.ItemDTO;
import gwt.shared.DALException;

public class ItemDB extends RemoteServiceServlet implements ItemService {

	private static final String URL = "jdbc:mysql://52.58.62.183:3306/kantinen";
	private static final String USERNAME = "dummy";
	private static final String PASSWORD = "gruppe3";

	private static Connection connection = null; // manages connection

	private PreparedStatement saveItemStmt = null;
	private PreparedStatement updateItemStmt = null;
	private PreparedStatement getItemsStmt = null;
	private PreparedStatement getSizeStmt = null;
	private PreparedStatement deleteItemStmt = null;

	ShowUserListView su;

	public ItemDB() throws Exception {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// create query that add a person to kartotek
			saveItemStmt = connection.prepareStatement("INSERT INTO items ( title, price ) VALUES ( ?, ? )");

			// create query that updates a person
			updateItemStmt = connection.prepareStatement("UPDATE customers SET title = ?, price = ?  WHERE id = ?");

			// create query that get all persons in kartotek
			getItemsStmt = connection.prepareStatement("SELECT * FROM items ");

			// create query that gets size of kartotek
			getSizeStmt = connection.prepareStatement("SELECT COUNT(*) FROM items ");

			// create query that deletes a person in kartotek
			deleteItemStmt = connection.prepareStatement("DELETE FROM items WHERE id =  ? ");

		} catch (SQLException sqlException) {
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
	public void saveItem(ItemDTO p) throws Exception {
		// simulate server error
		// throw new RuntimeException(" \"savePerson\" fejlede");

		try {
			saveItemStmt.setString(1, p.getName());
			saveItemStmt.setDouble(2, p.getPrice());

			saveItemStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(" \"savePerson\" fejlede");
		}
	}

	@Override
	public void updateItem(ItemDTO p) throws Exception {
		// TODO Auto-generated method stub

	}

	public void test() {
		System.out.println("test");
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
			throw new DALException(" \"getPersons\" fejlede");
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
}