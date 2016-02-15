
public class Price 
{
	private double basePrice;
	private double multiplier;
	private final double VAT = 0.2;
	
	
	public double getTotalPrice()
	{
		double total = basePrice * multiplier;
		total  = ( total * VAT ) + total ;		
		return total;	
	}
	public double getTotalBeforeVAT()
	{
		double total = basePrice * multiplier;	
		return total;		
	}
	
	public double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}
	public double getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}
	

}
