package gwt.shared;

public class ItemDTO {
	
	private String name;
	private double price;
	private int count = 10;
	
	public ItemDTO(String name, double price) 
	{
		this.name = name;
		this.price = price;
		
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
