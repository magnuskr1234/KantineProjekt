package gwt.client.service;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.shared.ItemDTO;

public interface ItemServiceAsync {

	void saveItem(ItemDTO p, AsyncCallback<Void> callback);

	void updateItem(double saldo, int id, AsyncCallback<Void> callback);

	void getItems(AsyncCallback<List<ItemDTO>> callback);
	
	void getHistoryList(int i, AsyncCallback<List<ItemDTO>> callback);
	
	void getStatList(AsyncCallback<List<ItemDTO>> callback);
	
	void getMostSoldItems(AsyncCallback<List<ItemDTO>> callback);

	void deleteItem(int index, AsyncCallback<Void> callback);
	
	void saveItemToHistory(int c, String name, double price, double currentSaldo, AsyncCallback<Void> callback);
	
	void getItem(String name, AsyncCallback<ItemDTO> callback);
}
