package gwt.shared;

import java.io.Serializable;
import java.sql.Date;

/**
 * Item Data Transfer Object. Specifies the atributes for an item. 
 * @author magnusrasmussen
 *
 */
public class ItemDTO implements Serializable {
	
	private String name;
	private double price;
	private int count = 1;
	private int id;
	private Date date;
	private String user;
	private int timesSold;
	private double saldo;
	
	/**
	 * Empty constructor. 
	 */
	public ItemDTO(){
		
	}
	
	/**
	 * Contructor for showing item list. 
	 * @param id
	 * @param name
	 * @param price
	 */
	public ItemDTO(int id, String name, double price) 
	{
		this.id = id;
		this.name = name;
		this.price = price;

	}
	
	/**
	 * Constructor used for adding a new item. 
	 * @param name
	 * @param price
	 */
	public ItemDTO(String name, double price) 
	{
		this.name = name;
		this.price = price;
		
	}
	
	/**
	 * Constructor used for showing most sold items.
	 * @param name
	 * @param timesSold
	 */
	public ItemDTO(String name, int timesSold) 
	{
		this.name = name;
		this.timesSold = timesSold;
		
	}
	
	/**
	 * Constructor used for showing history list for user. 
	 * @param name
	 * @param price
	 * @param date
	 * @param saldo
	 */
	public ItemDTO(String name, double price, Date date, double saldo ){
		this.name = name;
		this.price = price;
		this.date = date;
		this.saldo = saldo;
	}
	
	/**
	 * Constructor used for statistics
	 * @param user
	 * @param name
	 * @param price
	 * @param date
	 */
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
