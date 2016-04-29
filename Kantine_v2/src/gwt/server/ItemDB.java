package gwt.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import gwt.shared.DALException;
import gwt.shared.ItemDTO;
import gwt.shared.PersonDTO;

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
	PreparedStatement deleteItemStmt;
	PreparedStatement getItemStmt;

	public ItemDB(ConnectionDB connectionDB) throws SQLException {
		this.connectionDB = connectionDB;
		connection = connectionDB.getConnection();

		saveItemStmt = connection.prepareStatement("INSERT INTO items ( title, price ) VALUES ( ?, ? )");

		// create query that updates an item
		updateItemStmt = connection.prepareStatement("UPDATE items SET price = ?  WHERE id = ?");

		// create query that deletes an item in database
		deleteItemStmt = connection.prepareStatement("DELETE FROM items WHERE id =  ? ");

		saveItemToHistoryStmt = connection
				.prepareStatement("INSERT INTO history (customer_id, item_name, item_price, customer_current_saldo) VALUES (?, ?, ?, ?)");

		showStatListStmt = connection.prepareStatement(
				"SELECT customers.email, history.item_name, history.item_price, history.date_ordered"
				+ " FROM history"
				+ " LEFT JOIN customers"
				+ " ON customers.id=history.customer_id"
				+ " WHERE history.item_name NOT LIKE '%saldo%'"
				+ " ORDER BY history.date_ordered DESC ");

		showHistoryListStmt = connection.prepareStatement(
				"SELECT item_name, item_price, date_ordered, customer_current_saldo FROM history WHERE customer_id = ? ORDER BY history.date_ordered DESC");

		// create query that get all items in database
		getItemsStmt = connection.prepareStatement("SELECT * FROM items ORDER BY title ");

		showMostSoldItemsStmt = connection.prepareStatement(
				"SELECT item_name, COUNT(*) AS total FROM history"
				+ " GROUP BY item_name"
				+ " ORDER BY total DESC");
		
		// Create query that get 1 person 
		getItemStmt = connection.prepareStatement(
				"SELECT * FROM items where title = ? AND price = ?");
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
			throw new DALException(" \"showMostSoldItems\" fejlede");
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
						resultSet.getDate("date_ordered"), resultSet.getDouble("customer_current_saldo")));
			}
		} catch (SQLException sqlException) {
			throw new DALException(" \"getHistoryList\" fejlede");
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
				results.add(new ItemDTO(resultSet.getString("email"), resultSet.getString("item_name"),
						resultSet.getDouble("item_price"), resultSet.getDate("date_ordered")));
			}
		} catch (SQLException sqlException) {
			throw new DALException(" \"getStatList\" fejlede");
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

	public void saveItemToHistory(int c, String name, double price, double currentSaldo) throws Exception {
		try {
			saveItemToHistoryStmt.setInt(1, c);
			saveItemToHistoryStmt.setString(2, name);
			saveItemToHistoryStmt.setDouble(3, price);
			saveItemToHistoryStmt.setDouble(4, currentSaldo);

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
	
	public ItemDTO getItem(String name, double price) throws Exception {
		ResultSet resultSet = null;
		ItemDTO item = null;
		
		try{
		getItemStmt.setString(1, name);
		getItemStmt.setDouble(2, price);
		
		resultSet = getItemStmt.executeQuery();
		
		while (resultSet.next()){
			item = new ItemDTO();
			
			item.setId(resultSet.getInt("title"));
			item.setPrice(resultSet.getDouble("price"));

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
		return item;
	}

}
