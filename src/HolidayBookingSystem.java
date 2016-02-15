import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class HolidayBookingSystem extends JFrame
{
	private JFrame window; 
	private final Image logoIcon = new ImageIcon(getClass().getResource("./res/logoIcon.jpg")).getImage();
	
	private JPanel aboutPanel,bookingPanel,descriptionPanel;

	public static void main(String[] args) 
	{
		HolidayBookingSystem mainWindow = new HolidayBookingSystem();		
		mainWindow.createGUI();
		

	}

	public void createGUI()
	{
		window = new JFrame();
		window.setTitle("Zen Booking System");
		window.setSize(700, 450);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLayout(new GridLayout(2, 2,10,10));
		window.setFont(new Font("Garamond",Font.CENTER_BASELINE,12));
		
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  // Set the look and feel to match the operating system
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		centreWindow(window);
		
		try
		{
			window.setIconImage(logoIcon);
		}
		catch(NullPointerException E)
		{			
			System.out.println("The image was not found -"+E.getMessage());
		}
		
		window.setLayout(new FlowLayout());
		
		// About Panel Creation
		
		aboutPanel = new JPanel();
		aboutPanel.setSize(700,200);
		aboutPanel.setBackground(Color.CYAN);		
		
		JLabel picLabel = new JLabel(new ImageIcon(getClass().getResource("./res/logoImage.jpg")));
		add(picLabel);
		
		aboutPanel.add(picLabel);
		
		
		
		
		
		
		
		
		window.add(aboutPanel);
		
		// Booking Panel Creation
		
		bookingPanel = new JPanel();
		bookingPanel.setSize(350,250);
		bookingPanel.setBackground(Color.magenta);
		
		
		
		window.add(bookingPanel);
		
		
		
		// Description Panel 
		
		descriptionPanel = new JPanel();
		descriptionPanel.setSize(350, 250);
		descriptionPanel.setBackground(Color.YELLOW);
		
		
		window.add(descriptionPanel);
		
		
		
		window.setVisible(true);
	}
	
	
	
	
	
	
	
	
	
	
	public static void centreWindow(Window frame) // This method centres the window on the display
	{
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);


	}
}
