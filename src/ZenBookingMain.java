import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import com.toedter.calendar.JCalendar;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JSeparator;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JTable;

import java.awt.GridLayout;

import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JPasswordField;
import javax.swing.JRadioButton;


public class ZenBookingMain 
{
	// Initialization of the GUI elements
	private static JFrame zenFrame;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem exitMenuItem;
	private static JPanel aboutPanel,bookingPanel,extrasPanel,newHolidayPanel,adminPanel;
	private JTextPane txtpnCopyrightRemusAlexandru,txtpnYouCanFind;
	private static JLabel lblNewLabel,destinationsLabel,lblNewLabel_1,lblNewLabel_2,lblNewLabel_3,logoLabel,photoLabel,lblTransport;
	private static JComboBox destinationsBox,typeBox,transportCombobox,noOfNightsComboBox,newTransportCombobox;
	private static JCalendar calendar;
	private static JCheckBox travelInsuranceCheckbox,airportTransferCheckbox,extraLuggageCheckbox,skiRentalCheckbox,transportCheckbox;
	private static Transport byPlane,byFerry;	
	private static AccommodationType Hotel,youthHostel,Villa,BNB,Chalet;
	private static ArrayList<AccommodationType> dubrovnikList,parisList,creteList,adelbodenList;
	private static Extras airportTransfer,travelInsurance,extraLuggage,skiHire,scubaDiving;
	private static DatabaseConnection db;
	private static Statement query;
	private static JSpinner adultsSpinner,otherSpinner;
	private static JTabbedPane detailsPane;
	// These are the variable stored for inserting in the holiday
	private static City selectedCity;
	private static AccommodationType selectedAccType;
	private static ArrayList<AccommodationType> availableAccType;
	private static Transport selectedTransport;
	private static Destination destSelected;
	private static HashMap<Extras,String> extrasAvailable;
	private static HashMap<Extras,String> extrasSelected;
	private static JLabel priceLabel,lblPrice;
	private static ArrayList<JCheckBox> extrasCheckboxes,extrasCheckboxesToAdd;
	private static ArrayList<JLabel> extrasPriceLabels;
	private static JLabel skiRentalPriceLabel,airportTransferPriceLabel,scubaDivingPriceLabel,snorkelingPriceLabel,extraLuggagePriceLabel,travelInsurancePriceLabel;
	private static JCheckBox snorkelingCheckBox,scubaDivingCheckbox,snorkelingAdd,scubaAdd,airportTransfAdd,travelInsAdd,extraLuggAdd,skiRntlAdd;
	private static JFormattedTextField calculatedPriceField,priceVATField,nights14Field,nights10Field,nights7Field;
	private static SimpleDateFormat formatter;
	private static Date peakDateBegin,peakDateEnd;
	private static JButton btnBookHoliday,btnCreateHoliday;
	private static Image parisHotel,parisYouthHostel,logoIcon,logoBig,dubrovnikVilla,dubrovnikBNB,creteHotel,creteVilla,geneveHotel,geneveSkiResort;
	private static JTextField nameField,emailField,destinationField,accType1Field;
	private static double priceNow;
	private static JTextField usernameField;
	private JLabel lblUsername;
	private static JPasswordField passwordField;
	private JLabel lblPassword;
	private static JButton btnLogout,btnLogin;
	private JLabel lblDestinationCity;
	private JLabel lblAccommodationType;
	private JLabel lblNights_1;



	public static void main(String[] args) 
	{

		ZenBookingMain window = new ZenBookingMain();
		window.zenFrame.setVisible(true);

		//  Set the new holiday pane tab 
		//   disabled till the admin logs in
		detailsPane.setEnabledAt(2, false);

		formatter = new SimpleDateFormat("dd-MMM-YYYY");


		// Set the peak time boundaries
		Calendar newC= Calendar.getInstance();
		newC.clear();
		newC.set(2015, Calendar.JUNE, 1);
		peakDateBegin = newC.getTime();

		Calendar newC2 = Calendar.getInstance();
		newC2.set(2015, Calendar.AUGUST, 31);
		peakDateEnd = newC2.getTime();


		// add all of the extras in a checkbox list
		//  to make it easier to reset when the destination
		//  is changed
		extrasCheckboxes = new ArrayList<JCheckBox>();
		extrasCheckboxes.add(transportCheckbox);
		extrasCheckboxes.add(travelInsuranceCheckbox);
		extrasCheckboxes.add(airportTransferCheckbox);
		extrasCheckboxes.add(extraLuggageCheckbox);
		extrasCheckboxes.add(skiRentalCheckbox);
		extrasCheckboxes.add(snorkelingCheckBox);
		extrasCheckboxes.add(scubaDivingCheckbox);

		// do the same as before with the prices
		extrasPriceLabels = new ArrayList<JLabel>();
		extrasPriceLabels.add(travelInsurancePriceLabel);
		extrasPriceLabels.add(airportTransferPriceLabel);
		extrasPriceLabels.add(extraLuggagePriceLabel);
		extrasPriceLabels.add(skiRentalPriceLabel);
		extrasPriceLabels.add(snorkelingPriceLabel);
		extrasPriceLabels.add(scubaDivingPriceLabel);	

		//  For the new holiday creation
		extrasCheckboxesToAdd = new ArrayList<JCheckBox>();
		extrasCheckboxesToAdd.add(travelInsAdd);
		extrasCheckboxesToAdd.add(airportTransfAdd);
		extrasCheckboxesToAdd.add(extraLuggAdd);
		extrasCheckboxesToAdd.add(skiRntlAdd);
		extrasCheckboxesToAdd.add(snorkelingAdd);
		extrasCheckboxesToAdd.add(scubaAdd);

		// create an instance of the database connection class
		//  for the database interaction
		db = new DatabaseConnection();
		selectedCity = new City();
		extrasSelected = new HashMap<Extras,String>();
		try 
		{
			query = db.link.createStatement();
		}
		catch (SQLException e)
		{			
			e.printStackTrace();
		}	

		initializeDefaults();
	}	

	public static void initializeDefaults()  // initialize the default destinations
	{
		try 
		{		
			Statement localQuery = db.link.createStatement();
			ResultSet rs = localQuery.executeQuery("Select name from City");
			while(rs.next())
			{
				String name = rs.getString("name");
				destinationsBox.addItem(name);
			}

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	public ZenBookingMain() 
	{
		createGUI();
	}	

	private void createGUI()  // Create the GUI
	{

		zenFrame = new JFrame();
		zenFrame.setFont(new Font("Garamond", Font.PLAIN, 12));
		zenFrame.getContentPane().setBackground(Color.WHITE);
		zenFrame.setTitle("Zen Booking System");
		zenFrame.getContentPane().setFont(new Font("Garamond", Font.PLAIN, 12));
		try // Create the images needed for the app
		{
			logoIcon = ImageIO.read(new File("res/logo.gif"));
			logoBig = ImageIO.read(new File("src/logoBig.png"));
			parisHotel =  ImageIO.read(new File("res/paris-hotel.jpg"));
			parisYouthHostel = ImageIO.read(new File("res/paris-hostel.jpg"));
			dubrovnikVilla = ImageIO.read(new File("res/dubrovnik-villa.jpg"));
			dubrovnikBNB = ImageIO.read(new File("res/dubrovnik-bnb.jpg"));
			creteHotel = ImageIO.read(new File("res/crete-hotel.jpg"));
			creteVilla = ImageIO.read(new File("res/crete-villa.jpg"));
			geneveHotel = ImageIO.read(new File("res/geneve-hotel.jpg"));
			geneveSkiResort = ImageIO.read(new File("res/geneve-skiresort.jpg"));

		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
		zenFrame.setIconImage(logoIcon);
		zenFrame.setBackground(new Color(240, 240, 240));
		zenFrame.setBounds(100, 100, 1050, 600);
		zenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuBar = new JMenuBar();
		zenFrame.setJMenuBar(menuBar);

		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		zenFrame.getContentPane().setLayout(null);
		zenFrame.getContentPane().setLayout(null);

		aboutPanel = new JPanel();
		aboutPanel.setBounds(0, 506, 1034, 32);
		aboutPanel.setBackground(Color.WHITE);
		zenFrame.getContentPane().add(aboutPanel);

		txtpnCopyrightRemusAlexandru = new JTextPane();
		txtpnCopyrightRemusAlexandru.setFont(new Font("Garamond", Font.ITALIC, 14));
		txtpnCopyrightRemusAlexandru.setText("Copyright Remus Alexandru Andor 2015");
		aboutPanel.add(txtpnCopyrightRemusAlexandru);

		bookingPanel = new JPanel();
		bookingPanel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLUE, null, new Color(30, 144, 255), null));
		bookingPanel.setBounds(22, 68, 693, 393);
		bookingPanel.setBackground(Color.WHITE);
		zenFrame.getContentPane().add(bookingPanel);
		bookingPanel.setLayout(null);

		lblNewLabel = new JLabel("Guests");
		lblNewLabel.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblNewLabel.setBounds(516, 0, 51, 25);
		bookingPanel.add(lblNewLabel);

		destinationsBox = new JComboBox();
		destinationsBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event)  // Change listener to 
			{											   // triggered by the changing 	
				String str; 							   // of the selected destination
				if(event.getStateChange() == ItemEvent.SELECTED)
				{
					populateVariables(event.getItem().toString());
					calculatePrice();
				}
			}
		});
		destinationsBox.setBounds(72, 96, 112, 24);
		//destinationsBox.setModel(new DefaultComboBoxModel(new String[] {"Bern", "Cairo", "Zurich"}));
		destinationsBox.setToolTipText("Destinations available");
		destinationsBox.setFont(new Font("Garamond", Font.ITALIC, 12));
		bookingPanel.add(destinationsBox);

		destinationsLabel = new JLabel("Destination");
		destinationsLabel.setLabelFor(destinationsBox);
		destinationsLabel.setFont(new Font("Garamond", Font.ITALIC, 14));
		destinationsLabel.setBounds(0, 99, 74, 16);
		bookingPanel.add(destinationsLabel);

		txtpnYouCanFind = new JTextPane();
		txtpnYouCanFind.setFont(new Font("Garamond", Font.ITALIC, 18));
		txtpnYouCanFind.setEditable(false);
		txtpnYouCanFind.setForeground(Color.BLACK);
		txtpnYouCanFind.setText("You can find our best deals here");
		txtpnYouCanFind.setBounds(12, 12, 248, 47);
		bookingPanel.add(txtpnYouCanFind);

		calendar = new JCalendar();
		calendar.getDayChooser().addPropertyChangeListener("day",new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt)   //  Detect if the date is in peak period
			{ 													  //   and if it is double the base price	
				calculatePrice();
				Date dateChosen = calendar.getDate();				
				if(peakDateBegin.before(dateChosen) && peakDateEnd.after(dateChosen))
				{
					JOptionPane.showMessageDialog(zenFrame, "The price now is double the base price because the date "
							+ "you have chosen falls within the peak holiday time.", "Price information",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		calendar.setTodayButtonText("");
		calendar.getDayChooser().setSundayForeground(new Color(30, 144, 255));
		calendar.getDayChooser().setWeekOfYearVisible(false);
		calendar.setBounds(296, 214, 214, 167);
		bookingPanel.add(calendar);

		lblNewLabel_1 = new JLabel("Adults");
		lblNewLabel_1.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblNewLabel_1.setBounds(474, 18, 55, 16);
		bookingPanel.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Children\r\n/Pensioner");
		lblNewLabel_2.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblNewLabel_2.setToolTipText("");
		lblNewLabel_2.setBounds(555, 12, 126, 30);
		bookingPanel.add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel("Type");
		lblNewLabel_3.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblNewLabel_3.setToolTipText("Type of accommodation");
		lblNewLabel_3.setBounds(208, 95, 39, 25);
		bookingPanel.add(lblNewLabel_3);

		typeBox = new JComboBox();
		typeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) 
			{
				if(typeBox.getItemCount() != 0)   // Update the photo and the price
				{								  //  if the typebox is populated
					setPhoto(typeBox.getSelectedItem().toString(), selectedCity.getName());
					calculatePrice();
				}
			}
		});
		typeBox.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblNewLabel_3.setLabelFor(typeBox);
		typeBox.setBounds(255, 95, 112, 25);
		bookingPanel.add(typeBox);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 69, 372, 2);
		bookingPanel.add(separator);

		adultsSpinner = new JSpinner();
		adultsSpinner.setFont(new Font("Garamond", Font.ITALIC, 14));
		adultsSpinner.setModel(new SpinnerNumberModel(2, 0, 10, 1));
		adultsSpinner.setValue(2);
		adultsSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) { // Update if adults spinner 
				calculatePrice();						 //	 changes value
			}
		});
		adultsSpinner.setBounds(484, 37, 27, 20);
		bookingPanel.add(adultsSpinner);

		otherSpinner = new JSpinner();
		otherSpinner.setFont(new Font("Garamond", Font.ITALIC, 14));
		otherSpinner.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		otherSpinner.addChangeListener(new ChangeListener()
		{

			@Override
			public void stateChanged(ChangeEvent e) {  // Calculate the price 
				calculatePrice();				     // if the child/pensioner spinner
			}										// changes its value	

		});
		otherSpinner.setBounds(583, 37, 27, 20);
		bookingPanel.add(otherSpinner);

		transportCombobox = new JComboBox();
		transportCombobox.setFont(new Font("Garamond", Font.ITALIC, 14));
		transportCombobox.setBounds(153, 143, 94, 25);
		bookingPanel.add(transportCombobox);

		transportCheckbox = new JCheckBox("");
		transportCheckbox.setFont(new Font("Garamond", Font.ITALIC, 14));
		transportCheckbox.addActionListener(new CheckboxListener());
		transportCheckbox.setToolTipText("Tick if you want transport");
		transportCheckbox.setBounds(255, 143, 21, 24);
		bookingPanel.add(transportCheckbox);

		lblTransport = new JLabel("Transport");
		lblTransport.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblTransport.setBounds(83, 147, 68, 16);
		bookingPanel.add(lblTransport);

		lblPrice = new JLabel("Transport Price");
		lblPrice.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblPrice.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblPrice.setBounds(163, 180, 84, 16);
		bookingPanel.add(lblPrice);

		priceLabel = new JLabel("");
		priceLabel.setFont(new Font("Garamond", Font.ITALIC, 14));
		priceLabel.setToolTipText("Price for the transport");
		priceLabel.setBounds(241, 180, 55, 16);
		bookingPanel.add(priceLabel);

		detailsPane = new JTabbedPane(JTabbedPane.TOP);
		detailsPane.setBounds(727, 56, 295, 405);
		zenFrame.getContentPane().add(detailsPane);

		ImageIcon parisHotelIcon = new ImageIcon(parisHotel);
		photoLabel = new JLabel(parisHotelIcon);
		photoLabel.setFont(new Font("Garamond", Font.ITALIC, 14));
		detailsPane.addTab("Photo", null, photoLabel, null);

		adminPanel = new JPanel();
		detailsPane.addTab("Admin", null, adminPanel, "For system administrators only!");
		adminPanel.setLayout(null);

		usernameField = new JTextField();
		usernameField.setFont(new Font("Garamond", Font.ITALIC, 14));
		usernameField.setBounds(88, 5, 190, 20);
		adminPanel.add(usernameField);
		usernameField.setColumns(10);

		lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblUsername.setBounds(12, 7, 70, 16);
		adminPanel.add(lblUsername);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Garamond", Font.ITALIC, 14));
		passwordField.setBounds(88, 33, 190, 20);
		adminPanel.add(passwordField);

		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblPassword.setBounds(12, 35, 70, 16);
		adminPanel.add(lblPassword);

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				checkLogin();
			}
		});
		btnLogin.setFont(new Font("Garamond", Font.ITALIC, 14));
		btnLogin.setBounds(180, 58, 98, 26);
		adminPanel.add(btnLogin);

		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				logout();
			}
		});
		btnLogout.setEnabled(false);
		btnLogout.setFont(new Font("Garamond", Font.ITALIC, 14));
		btnLogout.setBounds(180, 96, 98, 26);
		adminPanel.add(btnLogout);

		newHolidayPanel = new JPanel();
		detailsPane.addTab("New Holiday", null, newHolidayPanel, null);
		newHolidayPanel.setLayout(null);

		destinationField = new JTextField();
		destinationField.setFont(new Font("Garamond", Font.ITALIC, 14));
		destinationField.setBounds(122, 12, 156, 20);
		newHolidayPanel.add(destinationField);
		destinationField.setColumns(10);

		lblDestinationCity = new JLabel("Destination City");
		lblDestinationCity.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblDestinationCity.setBounds(12, 14, 97, 16);
		newHolidayPanel.add(lblDestinationCity);

		accType1Field = new JTextField();
		accType1Field.setFont(new Font("Garamond", Font.ITALIC, 14));
		accType1Field.setBounds(144, 40, 134, 20);
		newHolidayPanel.add(accType1Field);
		accType1Field.setColumns(10);

		lblAccommodationType = new JLabel("Accommodation Type 1");
		lblAccommodationType.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblAccommodationType.setBounds(12, 44, 134, 16);
		newHolidayPanel.add(lblAccommodationType);

		JLabel lblTransportProvided = new JLabel("Transport provided");
		lblTransportProvided.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblTransportProvided.setBounds(12, 96, 97, 16);
		newHolidayPanel.add(lblTransportProvided);

		newTransportCombobox = new JComboBox();
		newTransportCombobox.setFont(new Font("Garamond", Font.ITALIC, 14));
		newTransportCombobox.setBounds(117, 92, 132, 25);
		newHolidayPanel.add(newTransportCombobox);

		JLabel lblExtrasAvailable = new JLabel("Extras available");
		lblExtrasAvailable.setToolTipText("Please tick the extras that your holiday will provide:");
		lblExtrasAvailable.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblExtrasAvailable.setBounds(12, 125, 97, 17);
		newHolidayPanel.add(lblExtrasAvailable);

		snorkelingAdd = new JCheckBox("Snorkeling");
		snorkelingAdd.setFont(new Font("Garamond", Font.ITALIC, 14));
		snorkelingAdd.setBounds(12, 269, 135, 17);
		newHolidayPanel.add(snorkelingAdd);

		travelInsAdd = new JCheckBox("Travel Insurance");
		travelInsAdd.setFont(new Font("Garamond", Font.ITALIC, 14));
		travelInsAdd.setBounds(12, 149, 135, 17);
		newHolidayPanel.add(travelInsAdd);

		airportTransfAdd = new JCheckBox("Airport Transfer");
		airportTransfAdd.setFont(new Font("Garamond", Font.ITALIC, 14));
		airportTransfAdd.setBounds(12, 173, 135, 17);
		newHolidayPanel.add(airportTransfAdd);

		extraLuggAdd = new JCheckBox("Extra Luggage");
		extraLuggAdd.setFont(new Font("Garamond", Font.ITALIC, 14));
		extraLuggAdd.setBounds(12, 197, 135, 17);
		newHolidayPanel.add(extraLuggAdd);

		skiRntlAdd = new JCheckBox("Ski Rental");
		skiRntlAdd.setFont(new Font("Garamond", Font.ITALIC, 14));
		skiRntlAdd.setBounds(12, 221, 135, 17);
		newHolidayPanel.add(skiRntlAdd);

		scubaAdd = new JCheckBox("Scuba Diving");
		scubaAdd.setFont(new Font("Garamond", Font.ITALIC, 14));
		scubaAdd.setBounds(12, 245, 135, 17);
		newHolidayPanel.add(scubaAdd);

		JLabel lblBasePriceFor = new JLabel("Base Price for");
		lblBasePriceFor.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblBasePriceFor.setBounds(144, 129, 90, 16);
		newHolidayPanel.add(lblBasePriceFor);

		JLabel lblNewLabel_6 = new JLabel("7 Nights");
		lblNewLabel_6.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblNewLabel_6.setBounds(154, 149, 55, 16);
		newHolidayPanel.add(lblNewLabel_6);

		nights7Field = new JFormattedTextField();
		nights7Field.setFont(new Font("Garamond", Font.ITALIC, 14));
		nights7Field.setBounds(208, 147, 70, 20);
		newHolidayPanel.add(nights7Field);

		JLabel lblNights = new JLabel("10 Nights");
		lblNights.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblNights.setBounds(154, 176, 55, 16);
		newHolidayPanel.add(lblNights);

		nights10Field = new JFormattedTextField();
		nights10Field.setFont(new Font("Garamond", Font.ITALIC, 14));
		nights10Field.setBounds(208, 174, 70, 20);
		newHolidayPanel.add(nights10Field);

		lblNights_1 = new JLabel("14 Nights");
		lblNights_1.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblNights_1.setBounds(155, 206, 55, 16);
		newHolidayPanel.add(lblNights_1);

		nights14Field = new JFormattedTextField();
		nights14Field.setFont(new Font("Garamond", Font.ITALIC, 14));
		nights14Field.setBounds(209, 204, 70, 20);
		newHolidayPanel.add(nights14Field);

		btnCreateHoliday = new JButton("Create holiday");
		btnCreateHoliday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				addHoliday();
			}
		});
		btnCreateHoliday.setFont(new Font("Garamond", Font.ITALIC, 14));
		btnCreateHoliday.setBounds(144, 318, 134, 47);
		newHolidayPanel.add(btnCreateHoliday);
		newHolidayPanel.setVisible(false);

		ImageIcon logoBiig = new ImageIcon(logoBig);
		logoLabel = new JLabel(logoBiig);
		logoLabel.setBounds(22, 12, 206, 44);
		zenFrame.getContentPane().add(logoLabel);

		extrasPanel = new JPanel();
		extrasPanel.setBorder(new LineBorder(Color.BLUE, 1, true));
		extrasPanel.setBounds(12, 214, 272, 167);
		bookingPanel.add(extrasPanel);
		extrasPanel.setLayout(new GridLayout(0, 2, 0, 7));

		JLabel lblNewLabel_4 = new JLabel("Extra Name");
		lblNewLabel_4.setFont(new Font("Garamond", Font.ITALIC, 14));
		extrasPanel.add(lblNewLabel_4);

		JLabel lblPrice_1 = new JLabel("Price per Person");
		lblPrice_1.setFont(new Font("Garamond", Font.ITALIC, 14));
		extrasPanel.add(lblPrice_1);



		travelInsuranceCheckbox = new JCheckBox("Travel Insurance");
		travelInsuranceCheckbox.addActionListener(new CheckboxListener());
		travelInsuranceCheckbox.setFont(new Font("Garamond", Font.ITALIC, 14));
		extrasPanel.add(travelInsuranceCheckbox);

		travelInsurancePriceLabel = new JLabel("");
		travelInsurancePriceLabel.setFont(new Font("Garamond", Font.ITALIC, 14));
		extrasPanel.add(travelInsurancePriceLabel);

		airportTransferCheckbox = new JCheckBox("Airport Transfer");
		airportTransferCheckbox.setFont(new Font("Garamond", Font.ITALIC, 14));
		airportTransferCheckbox.addActionListener(new CheckboxListener());
		extrasPanel.add(airportTransferCheckbox);

		airportTransferPriceLabel = new JLabel("");
		airportTransferPriceLabel.setFont(new Font("Garamond", Font.ITALIC, 14));
		extrasPanel.add(airportTransferPriceLabel);

		extraLuggageCheckbox = new JCheckBox("Extra Luggage");
		extraLuggageCheckbox.addActionListener(new CheckboxListener());
		extraLuggageCheckbox.setFont(new Font("Garamond", Font.ITALIC, 14));
		extrasPanel.add(extraLuggageCheckbox);


		extraLuggagePriceLabel = new JLabel("");
		extraLuggagePriceLabel.setFont(new Font("Garamond", Font.ITALIC, 14));
		extrasPanel.add(extraLuggagePriceLabel);

		skiRentalCheckbox = new JCheckBox("Ski Rental");
		skiRentalCheckbox.addActionListener(new CheckboxListener());
		skiRentalCheckbox.setFont(new Font("Garamond", Font.ITALIC, 14));
		extrasPanel.add(skiRentalCheckbox);

		skiRentalPriceLabel = new JLabel("");
		skiRentalPriceLabel.setFont(new Font("Garamond", Font.ITALIC, 14));
		extrasPanel.add(skiRentalPriceLabel);

		scubaDivingCheckbox = new JCheckBox("Scuba Diving");
		scubaDivingCheckbox.addActionListener(new CheckboxListener());
		scubaDivingCheckbox.setFont(new Font("Garamond", Font.ITALIC, 14));
		extrasPanel.add(scubaDivingCheckbox);

		scubaDivingPriceLabel = new JLabel("");
		scubaDivingPriceLabel.setFont(new Font("Garamond", Font.ITALIC, 14));
		extrasPanel.add(scubaDivingPriceLabel);

		snorkelingCheckBox = new JCheckBox("Snorkeling");
		snorkelingCheckBox.addActionListener(new CheckboxListener());
		snorkelingCheckBox.setFont(new Font("Garamond", Font.ITALIC, 14));
		extrasPanel.add(snorkelingCheckBox);

		snorkelingPriceLabel = new JLabel("");
		snorkelingPriceLabel.setFont(new Font("Garamond", Font.ITALIC, 14));
		extrasPanel.add(snorkelingPriceLabel);

		noOfNightsComboBox = new JComboBox();
		noOfNightsComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) 
			{
				if(typeBox.getItemCount() != 0) // update price when the nights number changes
				{
					calculatePrice();
				}
			}
		});
		noOfNightsComboBox.setFont(new Font("Garamond", Font.ITALIC, 14));
		noOfNightsComboBox.setBounds(480, 95, 51, 25);
		noOfNightsComboBox.addItem(7);
		noOfNightsComboBox.addItem(10);
		noOfNightsComboBox.addItem(14);		
		bookingPanel.add(noOfNightsComboBox);

		JLabel lblNoOfNights = new JLabel("No of Nights");
		lblNoOfNights.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblNoOfNights.setBounds(401, 99, 74, 16);
		bookingPanel.add(lblNoOfNights);

		JLabel lblNewLabel_5 = new JLabel("The calculated price so far is:");
		lblNewLabel_5.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblNewLabel_5.setBounds(467, 165, 143, 16);
		bookingPanel.add(lblNewLabel_5);

		calculatedPriceField = new JFormattedTextField();
		calculatedPriceField.setEditable(false);
		calculatedPriceField.setBounds(610, 163, 71, 20);
		bookingPanel.add(calculatedPriceField);

		btnBookHoliday = new JButton("Book Holiday");
		btnBookHoliday.addActionListener(new BookHolidayListener());
		btnBookHoliday.setFont(new Font("Garamond", Font.ITALIC, 14));
		btnBookHoliday.setBounds(536, 321, 145, 60);
		bookingPanel.add(btnBookHoliday);		

		nameField = new JTextField();
		nameField.setFont(new Font("Garamond", Font.ITALIC, 14));
		nameField.setBounds(516, 243, 165, 16);
		bookingPanel.add(nameField);
		nameField.setColumns(10);

		emailField = new JTextField();
		emailField.setFont(new Font("Garamond", Font.ITALIC, 14));
		emailField.setBounds(516, 288, 165, 16);
		bookingPanel.add(emailField);
		emailField.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblName.setBounds(516, 224, 55, 16);
		bookingPanel.add(lblName);

		JLabel lblEmailAddress = new JLabel("E-mail address");
		lblEmailAddress.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblEmailAddress.setBounds(516, 271, 94, 16);
		bookingPanel.add(lblEmailAddress);

		JLabel lblVat = new JLabel("+ VAT 20% :");
		lblVat.setFont(new Font("Garamond", Font.ITALIC, 14));
		lblVat.setBounds(516, 196, 94, 16);
		bookingPanel.add(lblVat);

		priceVATField = new JFormattedTextField();
		priceVATField.setEditable(false);
		priceVATField.setBounds(610, 193, 71, 20);
		bookingPanel.add(priceVATField);

	}

	public static void populateTypes(ArrayList<AccommodationType> typesArray)
	{
		String str;   //populate the type of accommodation combo box with the values that
		int i = 0;	 //  the selected destination has available	
		System.out.println(typesArray.get(0).getName());
		typeBox.removeAllItems();
		while(typesArray.size() > i)
		{
			str = typesArray.get(i).getName();
			//System.out.println("Checking while " +str +typeBox.getItemCount());
			if(typeBox.getItemCount() == 0) // If there are no elements in the typebox
			{
				typeBox.addItem(str);
				continue;
			}
			else  // If there are elemetnts in the typebox already
			{
				boolean truth = false;
				for(int j = 0; j < typeBox.getItemCount() ; j++) // check all the elem from typebox
				{									

					if(typeBox.getItemAt(j).equals(str))  // if the item box element is the same 
					{									  // as the one currently selected from the type array...	
						truth=true;                    // set the boolean to true
					}

				}
				if(!truth)  // if the boolean is false add the item
				{
					//System.out.println("Checking for" +str );
					typeBox.addItem(str);
				}
			}
			i++;

		}		
	}

	public void populateVariables(String city) // Populate and set selectable variables when the city is changed
	{
		selectedCity.setName(city);		

		ArrayList<AccommodationType> typesArray = db.getAccType(city); // Get the acc types available from the db 
		populateTypes(typesArray); 									   // and store them in the list

		//System.out.println(typeBox.getSelectedItem().toString());

		setPhoto(typeBox.getSelectedItem().toString(),city);

		String toAdd;
		int j=0;
		ArrayList<Transport> allTrans = db.getTrans(); 
		if(newTransportCombobox.getItemCount() == 0) // If there are no elements in the newTransportCombobox
		{											//  add them so the admin can select it and add it	
			while(allTrans.size() > j)
			{
				toAdd = allTrans.get(j).getName();
				newTransportCombobox.addItem(toAdd);
				j++;
			}
		}

		selectedTransport = db.getTransport(city);                    // Get the tranport available from the db
		//System.out.println("Selected trans is "+selectedTransport.getName() + " " + selectedTransport.getPrice());

		transportCombobox.removeAllItems();                   // reset the transport box and add the available transport
		transportCombobox.addItem(selectedTransport.getName());

		priceLabel.setText(selectedTransport.getPrice()+"");  // display the price of the transport

		extrasSelected.clear();
		extrasAvailable = db.getExtras(city);            // Get the extras available for that specific city

		disableExtras();		                     // Disable and reset all of the extras to be able to repopulate them again
		Iterator<Extras> it = extrasAvailable.keySet().iterator();		
		while(it.hasNext())    // Iterate through the extras available for the selected city
		{                      //  and make them available for the user
			Extras thisExtra = it.next();
			for(int i = 0; i < extrasCheckboxes.size() ;i++)
			{
				if(extrasCheckboxes.get(i).getText().equals(thisExtra.getName()))
				{
					extrasCheckboxes.get(i).setEnabled(true);
					extrasPriceLabels.get(i-1).setText(thisExtra.getPrice()+"");
				}
				System.out.println(i+ " is " +thisExtra.getPrice()+"");				
			}
		}	
	}



	public static void calculatePrice()
	{
		double result=0.0;
		//System.out.println(noOfNightsComboBox.getSelectedItem());    
		// Get the base price from the database 
		result = db.getBasePrice(selectedCity.getName(),(Integer)noOfNightsComboBox.getSelectedItem(),typeBox.getSelectedItem().toString());

		Date dateChosen= calendar.getDate();		
		if(peakDateBegin.before(dateChosen) && peakDateEnd.after(dateChosen))  // If the date is within the peak period double the base price
		{
			result = result * 2;
		}


		result = (((Integer)adultsSpinner.getValue() * 0.5) + ((Integer)otherSpinner.getValue() * 0.25)) * result;   // Multiply according to the number of people

		Iterator<Extras> it = extrasSelected.keySet().iterator();		
		while(it.hasNext())    // Iterate through the extras available for the selected city
		{                      //  and make them available for the user
			Extras thisExtra = it.next();
			result = result + (thisExtra.getPrice() * ((Integer)adultsSpinner.getValue() + (Integer)otherSpinner.getValue()) );		
		}
		if(transportCheckbox.isSelected())        // add the transport price times the people if it is selected 
		{
			result = result + ( selectedTransport.getPrice() * ((Integer)adultsSpinner.getValue() + (Integer)otherSpinner.getValue()));
		}
		calculatedPriceField.setValue(result);
		result = result *1.2;         // Calculate the price with VAT
		priceVATField.setValue(result);

		priceNow = result;

		//System.out.println(result + " is result");

	}
	public void disableExtras() // Reset checkboxes and prices
	{
		for(int i =0 ; i< extrasCheckboxes.size() ; i++ )
		{
			extrasCheckboxes.get(i).setSelected(false);
			extrasCheckboxes.get(i).setEnabled(false);
		}		
		for(int i = 0; i< extrasPriceLabels.size(); i++)
		{
			extrasPriceLabels.get(i).setText("");
		}
		transportCheckbox.setEnabled(true);
	}

	public static class CheckboxListener implements ActionListener // Listener for the checkboxes
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			Object e= event.getSource();
			String str;
			if(e == travelInsuranceCheckbox)  // add or remove the extras dynamically to the hashmap according to
			{								  //  the user selection	
				str = travelInsuranceCheckbox.getText();
				if(!travelInsuranceCheckbox.isSelected())
				{
					extrasSelected.remove(getKeyFromMap(extrasAvailable,str));
				}
				else
				{
					extrasSelected.put((getKeyFromMap(extrasAvailable,str)),str);					
				}
				calculatePrice();
			}else if(e == airportTransferCheckbox)
			{
				str = airportTransferCheckbox.getText();
				if(!airportTransferCheckbox.isSelected())
				{
					extrasSelected.remove(getKeyFromMap(extrasAvailable,str));
				}
				else
				{
					extrasSelected.put((getKeyFromMap(extrasAvailable,str)),str);
				}
				calculatePrice();
			}else if(e == extraLuggageCheckbox)
			{
				str = extraLuggageCheckbox.getText();
				if(!extraLuggageCheckbox.isSelected())
				{
					extrasSelected.remove(getKeyFromMap(extrasAvailable,str));
				}
				else
				{
					extrasSelected.put((getKeyFromMap(extrasAvailable,str)),str);
				}
				calculatePrice();
			}else if(e == skiRentalCheckbox)
			{
				str = skiRentalCheckbox.getText();
				if(!skiRentalCheckbox.isSelected())
				{
					extrasSelected.remove(getKeyFromMap(extrasAvailable,str));
				}
				else
				{
					extrasSelected.put((getKeyFromMap(extrasAvailable,str)),str);
				}
				calculatePrice();
			}else if(e == scubaDivingCheckbox)
			{
				str = scubaDivingCheckbox.getText();
				if(!scubaDivingCheckbox.isSelected())
				{
					extrasSelected.remove(getKeyFromMap(extrasAvailable,str));
				}
				else
				{
					extrasSelected.put((getKeyFromMap(extrasAvailable,str)),str);
				}
				calculatePrice();
			}else if(e == snorkelingCheckBox)
			{
				str = snorkelingCheckBox.getText();
				if(!snorkelingCheckBox.isSelected())
				{
					extrasSelected.remove(getKeyFromMap(extrasAvailable,str));
				}
				else
				{
					extrasSelected.put((getKeyFromMap(extrasAvailable,str)),str);
				}
				calculatePrice();
			}
			else if(e == transportCheckbox)
			{
				selectedTransport.setName(e.toString());
				calculatePrice();
			}
		}

	}

	public static Extras getKeyFromMap(HashMap<Extras,String> map,String value)
	{
		Extras returnExtra = null;
		Extras compareExtra = null;
		Iterator<Extras> it = map.keySet().iterator();

		while(it.hasNext())
		{
			compareExtra = it.next();
			if(compareExtra.getName().equals(value))
			{
				returnExtra = compareExtra;
			}
		}
		return returnExtra;										
	}

	public class BookHolidayListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			//System.out.println("Button clicked");
			int response = JOptionPane.showConfirmDialog(
					zenFrame,
					"Are all the details corect??",
					"Holiday Details Confirmation",
					JOptionPane.YES_NO_OPTION);
			if(response == 0 ) // If the user clicks yes
			{
				bookHoliday();				
			}

		}

	}

	public void bookHoliday()
	{

		ArrayList<Person> persons = new ArrayList<Person>();
		Date dateChosen= calendar.getDate();
		Calendar now = Calendar.getInstance();
		Date today = now.getTime();
		if(dateChosen.before(today))
		{
			JOptionPane.showMessageDialog(zenFrame, "The starting date cannot be in the past!", "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
		else if(nameField.getText().equals(""))
		{
			JOptionPane.showMessageDialog(zenFrame, "Please write a name!", "Notice",
					JOptionPane.WARNING_MESSAGE);
			nameField.grabFocus();
			nameField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
		}
		else if(emailField.getText().equals("") || !emailField.getText().matches("^[a-zA-Z0-9_\\-\\.]+@[A-Za-z0-9\\-]+\\.[a-zA-Z]{2,10}$"))
		{
			JOptionPane.showMessageDialog(zenFrame, "The e-mail address is not valid. Please try again", "Notice",
					JOptionPane.WARNING_MESSAGE);
			emailField.grabFocus();
			emailField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
		}
		else
		{
			Destination destination = new Destination(selectedCity,extrasSelected,transportCheckbox.isSelected()); // create new destination object
			AccommodationType accType = new AccommodationType(typeBox.getSelectedItem().toString());
			if((Integer)adultsSpinner.getValue() != 0)  // add the persons in the arraylist taking into account if they are adults or not
			{
				for(int i = 0;i<(Integer)adultsSpinner.getValue() ; i++)
				{
					persons.add(new Person(true));
				}
			}
			if((Integer)otherSpinner.getValue() != 0)
			{
				for(int i = 0;i<(Integer)otherSpinner.getValue() ; i++)
				{
					persons.add(new Person(false));
				}
			}
			Holiday holiday = new Holiday(destination,(Integer)noOfNightsComboBox.getSelectedItem(),accType,persons,dateChosen); // Create new holiday with the values supplied
			Customer customer = new Customer(emailField.getText());  // Create new customer
			customer.setName(nameField.getText());

			int invNo = db.insertHoliday(destination,(Integer)noOfNightsComboBox.getSelectedItem(),persons,accType,dateChosen,customer,priceNow); // get the invoice no assigned to the purchase
																																				 //   from the database method which inserts the holiday details	
			Calendar nowDate = Calendar.getInstance();																							//     in the tables
			String nowString = nowDate.getTime().toString();

			JOptionPane.showMessageDialog(zenFrame, "Your invoice no for the purchase is "+ invNo +" at "+nowString +". Please write it down as it will be your proof of purchase.", "Notice",
					JOptionPane.INFORMATION_MESSAGE);
			writeInvoiceToFile(invNo,formatter.format(nowDate.getTime()),holiday,priceNow);  // Write the details of the purchase to the file
		} 

	}

	public static void setPhoto(String accType,String city)
	{
		if(accType.equals("Hotel") && city.equals("Paris"))
		{
			photoLabel.setIcon(new ImageIcon(parisHotel));
		}
		else if(accType.equals("Youth Hostel") && city.equals("Paris"))
		{
			photoLabel.setIcon(new ImageIcon(parisYouthHostel));
		}
		else if(accType.equals("Hotel") && city.equals("Crete"))
		{
			photoLabel.setIcon(new ImageIcon(creteHotel));
		}
		else if(accType.equals("Villa") && city.equals("Crete"))
		{
			photoLabel.setIcon(new ImageIcon(creteVilla));
		}
		else if(accType.equals("Bed and Breakfast") && city.equals("Dubrovnik"))
		{
			photoLabel.setIcon(new ImageIcon(dubrovnikBNB));
		}
		else if(accType.equals("Villa") && city.equals("Dubrovnik"))
		{
			photoLabel.setIcon(new ImageIcon(dubrovnikVilla));
		}
		else if(accType.equals("Hotel") && city.equals("Geneve"))
		{
			photoLabel.setIcon(new ImageIcon(geneveHotel));
		}
		else if(accType.equals("Ski Resort") && city.equals("Geneve"))
		{
			photoLabel.setIcon(new ImageIcon(geneveSkiResort));
		}
	}

	public static void checkLogin()
	{
		String username = usernameField.getText();
		char[] password = passwordField.getPassword();

		if(db.checkLogin(username,password))
		{
			usernameField.setEnabled(false);			
			passwordField.setEnabled(false);
			btnLogin.setEnabled(false);			
			btnLogout.setEnabled(true);			
			detailsPane.setEnabledAt(2, true);
		}
		else
		{
			usernameField.grabFocus();
			usernameField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
			passwordField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));

			JOptionPane.showMessageDialog(zenFrame, "Incorrect Credentials!", "Warning",
					JOptionPane.WARNING_MESSAGE);

		}

	}
	public static void logout()
	{
		usernameField.setEnabled(true);			
		passwordField.setEnabled(true);
		btnLogin.setEnabled(true);			
		btnLogout.setEnabled(false);			
		detailsPane.setEnabledAt(2, false);
	}


	public static boolean addHoliday()
	{
		boolean success = false;
		boolean alreadyExists = false;
		for(int i = 0 ; i < destinationsBox.getItemCount() ; i++)
		{
			if(destinationField.getText().equals(destinationsBox.getItemAt(i).toString()))
			{
				alreadyExists = true;
			}
		}

		if(destinationField.getText().equals("") || accType1Field.getText().equals("") || nights7Field.getText().equals("") 
				|| nights10Field.getText().equals("") || nights14Field.getText().equals(""))
		{
			JOptionPane.showMessageDialog(zenFrame, "Some fields are empty. Please fill them out and try again!", "Fields Empty",
					JOptionPane.WARNING_MESSAGE);
		}
		else if(alreadyExists)
		{
			destinationField.grabFocus();
			destinationField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
			JOptionPane.showMessageDialog(zenFrame, "Destination already exists!", "Warning",
					JOptionPane.WARNING_MESSAGE);	
		}
		else
		{
			ArrayList<AccommodationType> accMap = new  ArrayList<AccommodationType>();
			accMap.add(new AccommodationType(accType1Field.getText()));
			City addCity = new City(destinationField.getText(),new Transport(newTransportCombobox.getSelectedItem().toString()),accMap);
			ArrayList<String> extraToAdd = new ArrayList<String>();
			int i=0;

			while(extrasCheckboxesToAdd.size() > i)
			{
				if(extrasCheckboxesToAdd.get(i).isSelected())
				{
					extraToAdd.add(extrasCheckboxesToAdd.get(i).getText());
				}					
				i++;
			}		

			int price7 = Integer.parseInt(nights7Field.getText());
			int price10 = Integer.parseInt(nights10Field.getText());
			int price14 = Integer.parseInt(nights14Field.getText());
			if(db.createHoliday(addCity,accMap,extraToAdd,price7,price10,price14))
			{
				JOptionPane.showMessageDialog(zenFrame, "Holiday created successfully!", "Holiday Created",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		return success;

	}



	private void writeInvoiceToFile(int invNo, String datePurchased, Holiday holiday,double price) 
	{
		FileWriter writer = null;
		try 
		{
			writer = new FileWriter("Invoices.txt",true);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		PrintWriter toFile = new PrintWriter(writer);   // Record details to the invoices file
		toFile.println("The purchase made on "+ datePurchased  +" with inv no:"+invNo+" is made up of a holiday in " 
		                  + holiday.getDestination().getCity().getName() +" for " + holiday.getPeriodOfStay()
		                  + "nights at a/an "+holiday.getType().getName() +".The price before VAT is "+(price* 0.8)+",and the price after VAT is "+price);			
		
		toFile.close();
		try 
		{
			writer.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}
}
