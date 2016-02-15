import java.awt.List;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;

public class DatabaseConnection
{
	public Connection link;
	public Statement query;
	public SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-YYYY");

	public DatabaseConnection()
	{
		link = null;
		try {
			Class.forName("org.sqlite.JDBC");
			link = DriverManager.getConnection("jdbc:sqlite:test.db");
			query = link.createStatement();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}

	public ArrayList<AccommodationType> getAccType(String city)
	{
		ArrayList<AccommodationType> accTypeArray = new ArrayList<AccommodationType>();
		AccommodationType type;
		ResultSet accTypes = null;
		try 
		{
			accTypes = link.createStatement().executeQuery("select Distinct AccommodationType.name from BaseHolidayPrice "
					+ "join AccommodationType using(accTypeId) join city using (cityId) where City.name = '"+city+"'");

			while(accTypes.next())
			{
				type = new AccommodationType(accTypes.getString("name"));
				accTypeArray.add(type);
			}
		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		}

		return accTypeArray;
	}

	public Transport getTransport(String city)
	{
		Transport transport = null;		
		ResultSet transResult = null;

		try 
		{
			transResult = link.createStatement().executeQuery("select Transport.transName,Transport.transPrice from Transport join City using(transportId) where City.name = '"+city+"'");

			while(transResult.next())
			{
				transport = new Transport(transResult.getString("transName"),transResult.getDouble("transPrice"));
			}
		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		}

		return transport;

	}

	public HashMap<Extras,String> getExtras(String city)
	{
		HashMap<Extras,String> extrasAvailable = new HashMap<Extras,String>();
		Extras extra ;
		ResultSet extrasResult = null;

		try 
		{
			extrasResult = link.createStatement().executeQuery("select Extras.name,Extras.price from Extras join CityHasExtras using(extrasId) join City using (cityId) where City.name ='"+city+"'");

			while(extrasResult.next())
			{
				extra = new Extras(extrasResult.getString("name"),extrasResult.getDouble("price"));
				extrasAvailable.put(extra, extra.getName());
			}
		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		}


		return extrasAvailable;	
	}

	public double getBasePrice(String city,int nights,String accType)
	{
		int result = 0;

		ResultSet priceResult = null;

		try 
		{
			priceResult = link.createStatement().executeQuery("select price from City "
					+ "join BaseHolidayPrice using(cityId) join AccommodationType using(accTypeId) "
					+ "where BaseHolidayPrice.noOfNights="+nights+" and City.name='"+city+"' and accommodationType.name='"+accType+"'");

			while(priceResult.next())
			{
				result = priceResult.getInt("price");
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return result;	
	}

	public int insertHoliday(Destination destination,Integer noOfNights,ArrayList<Person> persons,AccommodationType accType,Date dateChosen,Customer customer,double price)
	{
		HashMap<Extras, String> extraMap = destination.getExtrasSelected();
		Iterator<Extras> it = extraMap.keySet().iterator();
		ArrayList<Integer> extrasList = new ArrayList<Integer>();
		int transport = 2;
		if(destination.getHasTransport())
		{
			transport =1;
		}
		else
		{
			transport =0;
		}

		//  First extract the data from the arguments passed
		//   and store them in variables to insert into the 
		//     holiday table
		while(it.hasNext())  // get the ids for the extras selected
		{
			ResultSet idResult = null;
			Extras thisExtra = it.next();
			try 
			{

				idResult = link.createStatement().executeQuery("select extrasId from Extras where name = '"+ thisExtra.getName() +"'");

				while(idResult.next())
				{
					extrasList.add(idResult.getInt("extrasId"));
					//System.out.println("extra id "+idResult.getInt("extrasId"));
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}

		}
		int destId = 0;
		ResultSet tempResult = null;
		try  // get the max id for the destination so it can be used to insert the extras in the table
		{
			tempResult = link.createStatement().executeQuery("select ifnull(max(destinationId),0) as id from Destination");

			while(tempResult.next())
			{

				destId = tempResult.getInt("id") + 1;
				//System.out.println("max dest id "+destId);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		tempResult = null;
		int cityId =0;
		try  // get the city id for based on the name
		{
			tempResult = link.createStatement().executeQuery("select cityId from City where name='"+destination.getCity().getName()+"'");
			cityId = tempResult.getInt("cityId");
			//System.out.println("city id "+cityId);

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}



		tempResult = null; // insert the destination id with city and transport choice
		try 
		{
			System.out.println("db-insert destination"+ transport + destination.getCity().getName());
			link.createStatement().execute("insert into Destination values("+destId+","+cityId+","+transport +")");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		

		int i =0;
		while(extrasList.size() > i)   // insert the extras with their respective id and the dest id
		{
			tempResult = null;
			try 
			{
				System.out.println("db-insert extras"+extrasList.get(i)+destId);
				link.createStatement().execute("insert into DestHasExtras values("+destId+","+extrasList.get(i)+")");
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}			
			i++;
		}
		int adults = 0;
		int others = 0;
		i=0;
		while (persons.size() > i )
		{
			if(persons.get(i).isAdult())
			{
				adults = adults + 1;
			}
			else 
			{
				others = others + 1;

			}
			i++;
		}
		
		int accTypeId = 0;
		tempResult = null;
		try  // get the id for the accommodation type 
		{
			tempResult = link.createStatement().executeQuery("select AccommodationType.accTypeId from AccommodationType where name = '"+accType.getName()+"'");

			while(tempResult.next())
			{
				accTypeId = tempResult.getInt("accTypeId");
				System.out.println("acc type id "+tempResult.getInt("accTypeId"));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		int priceInt = (int)price;
		tempResult = null;
		int holidayId=0;
		try 
		{
			link.createStatement().execute("INSERT INTO Holiday VALUES(null,"+destId+","+noOfNights+","+adults+","+others+","+accTypeId+",'"+formatter.format(dateChosen)+"',"+priceInt+")");
			tempResult = query.executeQuery("Select max(holidayId) as holidayId from Holiday");
			holidayId = tempResult.getInt("holidayId");	
			//System.out.println("holiday id "+holidayId);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}	
		tempResult = null;
		try 
		{
			System.out.println("db-insert customer"+customer.getEmail() +" "+customer.getName());
			link.createStatement().execute("INSERT OR IGNORE INTO Customer VALUES('"+customer.getEmail()+"','"+customer.getName()+"')");		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		try 
		{
			//System.out.println("db-insert booking"+holidayId);
			link.createStatement().execute("INSERT INTO Booking VALUES(null,"+holidayId+",'"+customer.getEmail()+"')");		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
		Calendar now = Calendar.getInstance();
		String nowString = now.getTime().toString();
		int invNo = -1;
		try  // record the invoice to the database 
		{
			link.createStatement().execute("INSERT INTO Invoice VALUES((select (max(invNo)+1) from Invoice),(select max(bookingId) from Booking),'"+nowString+"')");	
			ResultSet rs = link.createStatement().executeQuery("Select max(invNo) as invNo from Invoice");
			invNo = rs.getInt("invNo");			
		}  
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return invNo;
	}

	public boolean checkLogin(String username,char[] password)
	{
		boolean truth = false;
		ResultSet res = null;
		System.out.println(username);
		String pass2 =null;
		try  // check the credentials
		{
			res = link.createStatement().executeQuery("select password from Admin where username='"+username+"'");

			while(res.next())
			{
				pass2 = res.getString("password");
			}
			if(pass2 != null)
			{
				//System.out.println(pass2);
				char[] passArray = pass2.toCharArray();
				if(Arrays.equals(password, passArray))
				{
					truth = true;
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return truth;
	}
	
	public ArrayList<Transport> getTrans()
	{
		ArrayList<Transport> list = new ArrayList<Transport>();	
		ResultSet res = null;
		Transport trans;
		try  // get the transport methods available
		{
			res = link.createStatement().executeQuery("select * from Transport");

			while(res.next())
			{
				trans = new Transport(res.getString("transName"),res.getInt("transPrice"));
				trans.setId(res.getInt("transportId"));
				list.add(trans);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		
		return list;		
	}
	
	public boolean createHoliday(City city, ArrayList<AccommodationType> typeList,
			                     ArrayList<String> extras,int price7,int price10,int price14)
	{
		boolean success = false;
		int cityId = 0;
		ResultSet res = null;
		ResultSet res2 = null;
		try  // insert into city if not exists and store the city Id
		{
			res = link.createStatement().executeQuery("select * from city where name = '"+city.getName()+"'");
			if(res.next())  // If it's in the db already
			{
				cityId = res.getInt("cityId");
				System.out.println("cityId exists"+cityId);
			}
			else
			{				
				link.createStatement().execute("insert into City values( (Select (Max(cityId)+1) from City) ,'"+city.getName()+"',(select transportId from Transport where transName='"+city.getTransport().getName()+"')) ");
				ResultSet res3 = link.createStatement().executeQuery("Select MAX(cityId) as cityId from City");
				cityId = res3.getInt("cityId");
				System.out.println("cityId not exists"+cityId);
			}				
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		
		
		res = null;
		res2 = null;
		int accTypeId = -1;
		try  // insert into accommodation type if it doesn't exist
		{ 	//   and store the accId
			res = link.createStatement().executeQuery("select * from AccommodationType where name = '"+typeList.get(0).getName()+"'");
			if(res.next())  // If it's in the db already
			{
				accTypeId = res.getInt("accTypeId");
				//System.out.println("accType exists"+accTypeId);
			}
			else
			{				
				link.createStatement().execute("insert into AccommodationType values((Select (Max(accTypeId)+1) from AccommodationType),'"+typeList.get(0).getName()+"')");
				ResultSet res3 = link.createStatement().executeQuery("Select MAX(accTypeId) as accTypeId from AccommodationType");
				accTypeId = res3.getInt("accTypeId");	
				//System.out.println("accType not exists"+accTypeId);
			}				
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		
		
		int x = 0;
		while(extras.size() > x)
		{
			try 
			{
				link.createStatement().execute("INSERT INTO CityHasExtras VALUES("+cityId+", (select extrasId from Extras where name = '"+extras.get(x)+"') ) ");
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			
			x++;
		}
		
		ArrayList<Integer> pricesArray = new ArrayList<Integer>();
		pricesArray.add(price7);
		pricesArray.add(price10);
		pricesArray.add(price14);
		
		int[] nights = new int[3];
		nights[0] = 7;
		nights[1] = 10;
		nights[2] = 14;
		
		for(int z = 0; z < 3 ; z++)
		{
			try 
			{
				link.createStatement().execute("INSERT INTO BaseHolidayPrice VALUES("+cityId+","+nights[z]+","+accTypeId+","+pricesArray.get(z)+") ");
				success = true;
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			
		}
		
		return success;
	}

}

