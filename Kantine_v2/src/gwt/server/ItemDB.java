package gwt.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import gwt.shared.DALException;
import gwt.shared.ItemDTO;

public class ItemDB {

	Connection connection;

	ConnectionDB connectionDB;
	PreparedStatement showMostSoldItemsStmt;
	PreparedStatement getItemsStmt;
	PreparedStatement showHistoryListStmt;
	PreparedStatement showStatListStmt;
	PreparedStatement saveItemStmt;
	PreparedStatement saveItemToHistoryStmt;
	PreparedStatement updateItemStmt;
	PreparedStatement getSizeStmt;
	PreparedStatement deleteItemStmt;

	public ItemDB(ConnectionDB connectionDB) throws SQLException {
		this.connectionDB = connectionDB;
		connection = connectionDB.getConnection();

		saveItemStmt = connection.prepareStatement("INSERT INTO items ( title, price ) VALUES ( ?, ? )");

		// create query that updates an item
		updateItemStmt = connection.prepareStatement("UPDATE items SET price = ?  WHERE id = ?");

		// create query that gets size of database
		getSizeStmt = connection.prepareStatement("SELECT COUNT(*) FROM items ");

		// create query that deletes an item in database
		deleteItemStmt = connection.prepareStatement("DELETE FROM items WHERE id =  ? ");

		saveItemToHistoryStmt = connection
				.prepareStatement("INSERT INTO history (customer_id, item_name, item_price) VALUES (?, ?, ?)");

		showStatListStmt = connection.prepareStatement(
				"SELECT customers.name, history.item_name, history.item_price, history.date_ordered FROM history LEFT JOIN customers ON customers.id=history.customer_id ORDER BY history.date_ordered DESC ");

		showHistoryListStmt = connection.prepareStatement(
				"SELECT item_name, item_price, date_ordered FROM history WHERE customer_id = ? ORDER BY history.date_ordered DESC");

		// create query that get all items in database
		getItemsStmt = connection.prepareStatement("SELECT * FROM items ORDER BY title ");

		showMostSoldItemsStmt = connection.prepareStatement(
				"SELECT item_name, count(*) AS total from history GROUP BY item_name ORDER BY total DESC");
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
				connectionDB.close();
			}
		}
		return results;
	}

	public List<ItemDTO> getItems() throws Exception {
		List<ItemDTO> results = null;
		ResultSet resultSet = null;

		try {
			resultSet = getItemsStmt.executeQuery();
			results = new ArrayList<ItemDTO>();

			while (resultSet.next()) {
				results.add(new ItemDTO(resultSet.getInt("id"), resultSet.getString("title"),
						resultSet.getDouble("price")));
			}
		} catch (SQLException sqlException) {
			throw new DALException(" \"getItems\" fejlede");
		} finally {
			try {
				resultSet.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				connectionDB.close();
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
				results.add(new ItemDTO(resultSet.getString("item_name"), resultSet.getDouble("item_price"),
						resultSet.getDate("date_ordered")));
			}
		} catch (SQLException sqlException) {
			throw new DALException(" \"getItems\" fejlede");
		} finally {
			try {
				resultSet.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				connectionDB.close();
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
				results.add(new ItemDTO(resultSet.getString("name"), resultSet.getString("item_name"),
						resultSet.getDouble("item_price"), resultSet.getDate("date_ordered")));
			}
		} catch (SQLException sqlException) {
			throw new DALException(" \"getItems\" fejlede");
		} finally {
			try {
				resultSet.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				connectionDB.close();
			}
		}
		return results;
	}

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

	public void saveItem(ItemDTO p) throws Exception {

		try {
			saveItemStmt.setString(1, p.getName());
			saveItemStmt.setDouble(2, p.getPrice());

			saveItemStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(" \"saveItem\" fejlede");
		}
	}

	public void deleteItem(int id) throws Exception {

		try {
			deleteItemStmt.setInt(1, id);

			deleteItemStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(" \"deletePerson\" fejlede");
		}
	}

	public void updateItem(double price, int id) throws Exception {
		try {
			updateItemStmt.setDouble(1, price);
			updateItemStmt.setInt(2, id);

			updateItemStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(" \"updateItem\" fejlede");
		}

	}

}
