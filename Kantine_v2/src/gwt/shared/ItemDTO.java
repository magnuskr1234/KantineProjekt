package gwt.shared;

import java.io.Serializable;

public class ItemDTO implements Serializable {
	
	private String name;
	private double price;
	private int count = 1;
	private int id;
	
	public ItemDTO(){
		
	}
	public ItemDTO(int id, String name, double price) 
	{
		this.id = id;
		this.name = name;
		this.price = price;
	
	}
	public ItemDTO(String name, double price) 
	{
		this.name = name;
		this.price = price;
		
	}
	

	
	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public  double getPrice()
	{
		return price;
	}
	
	public void setPrice (double price)
	{
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
