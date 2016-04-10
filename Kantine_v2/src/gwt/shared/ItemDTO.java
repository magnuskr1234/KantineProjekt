package gwt.shared;

import java.io.Serializable;
import java.sql.Date;

public class ItemDTO implements Serializable {
	
	private String name;
	private double price;
	private int count = 1;
	private int id;
	private Date date;
	private String user;
	private int timesSold;
	private double saldo;
	
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
	
	public ItemDTO(String name, int timesSold) 
	{
		this.name = name;
		this.timesSold = timesSold;
		
	}
	
	public ItemDTO(String name, double price, Date date, double saldo ){
		this.name = name;
		this.price = price;
		this.date = date;
		this.saldo = saldo;
	}
	
	public ItemDTO(String user, String name, double price, Date date ){
		this.user = user;
		this.name = name;
		this.price = price;
		this.date = date;
	}
	
	public String getUser(){
		return user;
	}
	
	public void setUser(String user){
		this.user = user;
	}
	

	public Date getDate(){
		return date;
	}
	
	public void setDate(Date date){
		this.date = date;
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
	
	public  double getSaldo()
	{
		return saldo;
	}
	
	public void setSaldo (double saldo)
	{
		this.saldo = saldo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public void setTimesSold(int timesSold){
		this.timesSold = timesSold;
	}
	
	public int getTimesSold(){
		return timesSold;
	}

}
