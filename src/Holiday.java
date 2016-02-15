import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Holiday
{
	private Destination destination;
	private int periodOfStay;
	private AccommodationType type;
	private ArrayList<Person> personsBooked;
	private Date startDate;
	private SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-YYYY");
	
	public Holiday(Destination destination,int periodOfStay,AccommodationType type,ArrayList<Person> personsBooked,Date startDate)
	{
		this.destination = destination;
		this.periodOfStay = periodOfStay;
		this.type = type;
		this.personsBooked = personsBooked;
		this.startDate = startDate;
	}
	public Holiday()
	{
		
	}	
	public Destination getDestination() 
	{
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	public int getPeriodOfStay() {
		return periodOfStay;
	}
	public void setPeriodOfStay(int periodOfStay) {
		this.periodOfStay = periodOfStay;
	}
	public AccommodationType getType() {
		return type;
	}
	public void setType(AccommodationType type) {
		this.type = type;
	}
	public ArrayList<Person> getPersonsBooked() {
		return personsBooked;
	}
	public void addPersons(Person person) {
		this.personsBooked.add(person);
	}
	
	public String toString()
	{
		String str = " The holiday destination is " + destination.getCity().getName() +" "
	                      + destination.getExtrasSelected().size() + ", the period of stay is "+
				           periodOfStay + ",the acc type is "+ type.getName() + " and the number of"
				           		+ " persons booked is"+personsBooked.size() + ". The starting date will be " + formatter.format(startDate);
		return str;
	}
	

}
