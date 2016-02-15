import java.util.HashMap;


public class Destination
{
	private City city;
	private HashMap<Extras,String> extrasSelected;
	private boolean hasTransport;
	
	
	public Destination(City city,HashMap<Extras,String> extrasSelected,boolean hasTransport)
	{
		this.city = city;
		this.extrasSelected = extrasSelected;	
		this.hasTransport = hasTransport;
	}
	public Destination()
	{
		
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public HashMap<Extras,String> getExtrasSelected() {
		return extrasSelected;
	}


	public void addExtras(Extras extras,String str) {
		this.extrasSelected.put(extras,str);
	}

	public boolean getHasTransport() {
		return hasTransport;
	}

	public void setHasTransport(boolean hasTransport) {
		this.hasTransport = hasTransport;
	}
	
	

}
