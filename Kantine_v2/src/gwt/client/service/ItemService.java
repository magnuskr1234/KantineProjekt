package gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import gwt.shared.ItemDTO;

/**
 * Methods used for RPC. 
 * @author magnusrasmussen
 *
 */
@RemoteServiceRelativePath("itemservice")
public interface ItemService extends RemoteService {
	public ItemDTO getItem(String name, double price) throws Exception;
	public void saveItem(ItemDTO p) throws Exception;
	public void updateItem(double price, int id) throws Exception;
	public  List<ItemDTO> getItems() throws Exception;
	public  List<ItemDTO> getHistoryList(int i) throws Exception;
	public  List<ItemDTO> getStatList() throws Exception;
	public  List<ItemDTO> getMostSoldItems() throws Exception;
	public void deleteItem(int id) throws Exception;
	public void saveItemToHistory (int c, String name, double price, double currentSaldo) throws Exception;
}
