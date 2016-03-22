package gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.shared.ItemDTO;

public interface ItemServiceAsync {

	void saveItem(ItemDTO p, AsyncCallback<Void> callback);

	void updateItem(double saldo, int id, AsyncCallback<Void> callback);

	void getItems(AsyncCallback<List<ItemDTO>> callback);

	void deleteItem(int index, AsyncCallback<Void> callback);
	
	void saveItemToHistory(int c, int i, AsyncCallback<Void> callback);
}
