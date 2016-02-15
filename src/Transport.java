
public class Transport 
{
	private String name;
	private double price;
	private int id;
	
	public Transport(String name , double price,int id)
	{
		this.name = name;
		this.price= price;
		this.id = id;
	}
	public Transport(String name , double price)
	{
		this.name = name;
		this.price= price;
	}
	public Transport(String name)
	{
		this.name = name;
	}
	
	public Transport()
	{
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
