package gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import gwt.shared.ItemDTO;





@RemoteServiceRelativePath("itemservice")
public interface ItemService extends RemoteService {
	//void setPerson(PersonDTO pDTO) throws Exception;
	//  PersonDTO getPerson() throws Exception;
	// note: announcing exception makes it possible to communicate 
	// user defined exceptions from the server side to the client side
	// otherwise only generic server exceptions will be send back
	// in the onFailure call back method
	
	public void saveItem(ItemDTO p) throws Exception;
	public void updateItem(ItemDTO p) throws Exception;
	public  List<ItemDTO> getItems() throws Exception;
	public void deleteItem(int id) throws Exception;
	public void saveItemToHistory (int c, int i) throws Exception;
}
