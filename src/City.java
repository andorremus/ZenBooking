import java.util.ArrayList;
import java.util.HashMap;


public class City 
{
	private String name;
	private Transport transport;
	private ArrayList<AccommodationType> types;
	
	public City(String name, Transport transport,  ArrayList<AccommodationType> types)
	{
		this.name = name;
		this.transport = transport;
		this.types = types;
	}
	public City()
	{
		
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Transport getTransport() {
		return transport;
	}
	public void setTransport(Transport transport) {
		this.transport = transport;
	}
	public  ArrayList<AccommodationType> getTypes() {
		return types;
	}
	public void addTypes(AccommodationType type) {
		this.types.add(type);
	}

}
