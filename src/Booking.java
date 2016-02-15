
public class Booking 
{
	private Customer customer;
	private Holiday holidayNo;
	
	public Booking(Customer customer,Holiday holidayNo)
	{
		this.customer = customer;
		this.holidayNo = holidayNo;		
	}
	public Booking()
	{
		
	}
	public Holiday getHolidayNo() {
		return holidayNo;
	}
	public void setHolidayNo(Holiday holidayNo) {
		this.holidayNo = holidayNo;
	}

}
