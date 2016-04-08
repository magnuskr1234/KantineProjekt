package gwt.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;

import gwt.shared.DALException;
import gwt.shared.ItemDTO;

public class ItemBla {
	
	Connection connection;
	
	
	ItemDB itemDB;
	PreparedStatement showMostSoldItemsStmt;
	
	
	public ItemBla(ItemDB itemDB) throws SQLException{
		this.itemDB = itemDB;
		connection = itemDB.getConnection();
		
		showMostSoldItemsStmt = connection.prepareStatement("SELECT item_name, count(*) AS total from history GROUP BY item_name ORDER BY total DESC");
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
				itemDB.close();
			}
		}
		return results;
	}

}
