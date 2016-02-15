
public class Person
{
	private String name;
	private boolean isAdult; // If the person is not an adult we assume 
                             //  that he/she is either a pensioner or child
	
	public Person(String name,boolean isAdult)
	{
		this.name = name;
		this.isAdult = isAdult;		
	}	
	public Person()
	{
		
	}
	public Person(boolean isAdult)
	{
		this.isAdult = isAdult;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAdult() {
		return isAdult;
	}
	public void setAdult(boolean isAdult) {
		this.isAdult = isAdult;
	}
}
