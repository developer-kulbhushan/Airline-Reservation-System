package admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import utilities.CreateConnection;

public class Login extends JFrame implements MouseListener{
	
	private static final long serialVersionUID = 3959650970660375819L;
	
	static JScrollPane sp;
	private JButton flights,users,feedback,booked_flights;
	private ImageIcon background,flights_bg,users_bg;
	private ImageIcon feedback_bg,booked_flights_bg;
	private Border hover;
	static JLabel bg;
	private JLabel myprofile,logout;
	private JPanel Flights,Users,Feedback,BookedFlights;
	private Connection con;

	public Login(Connection con) {
		
		this.con = con;
		
		//All images are loaded in this function
		imagesLoad();
		
		//Initializing Components
		intializeComonents();
		
		//Adding Components
		addComponents();
		
		//Configuring the Frame
		configureFrame();
		
	}

	private void imagesLoad() {
		
			background = new ImageIcon("res/admin/admin_bg.png");
			flights_bg = new ImageIcon("res/admin/flights_bg.png");
			users_bg = new ImageIcon("res/admin/users_bg.png");
			feedback_bg = new ImageIcon("res/admin/feedback_bg.png");
			booked_flights_bg = new ImageIcon("res/admin/booked_flights_bg.png");
			
	}
	
	private void intializeComonents() {
		
		bg = new JLabel(background);
		
		myprofile = new JLabel("My Account");
		myprofile.setBounds(615,10,66,30);
		myprofile.setForeground(Color.WHITE);
		
		logout = new JLabel("Logout");
		logout.setBounds(700,10,39,30);
		logout.setForeground(Color.WHITE);		
		
		sp = new JScrollPane(bg);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setWheelScrollingEnabled(true);
		
		hover = BorderFactory.createMatteBorder(0, 0, 3, 0, Color.red);
		
		Flights = new Flights(con);
		Users = new Users(con);
		Feedback = new Feedback(con);
		BookedFlights = new BookedFlights(con);
		
		flights = new JButton(flights_bg);
		flights.setOpaque(false);
		flights.setBorder(null);
		flights.setContentAreaFilled(false);
		flights.setBounds(20,270,64,64);
		
		users = new JButton(users_bg);
		users.setOpaque(false);
		users.setBorder(null);
		users.setContentAreaFilled(false);
		users.setBounds(20,115,64,64);
		
		feedback = new JButton(feedback_bg);
		feedback.setOpaque(false);
		feedback.setBorder(null);
		feedback.setContentAreaFilled(false);
		feedback.setBounds(160,115,64,64);
		
		booked_flights = new JButton(booked_flights_bg);
		booked_flights.setOpaque(false);
		booked_flights.setBorder(null);
		booked_flights.setContentAreaFilled(false);
		booked_flights.setBounds(160,270,64,64);
		
		users.addMouseListener(this);
		flights.addMouseListener(this);
		feedback.addMouseListener(this);
		booked_flights.addMouseListener(this);
		
		buttonHoverAnimation(flights);
		buttonHoverAnimation(users);
		buttonHoverAnimation(feedback);
		buttonHoverAnimation(booked_flights);
	}
	
	
	//Function to create Hover Animation On Buttons
	private void buttonHoverAnimation(JButton button) { 
		
		int x = button.getX();
		int y = button.getY();
		int height = button.getSize().height;
		int width = button.getSize().width;
		
		button.addMouseListener(new MouseAdapter() {
			
			public void mouseEntered(MouseEvent e) {
				button.setBounds(x - 1,
								 y - 6,
								 height + 2,
								 width + 12
								 );
				button.setBorder(hover);
			}
			
			public void mouseExited(MouseEvent e) {
				
				button.setBounds(x,y,height,width);
				button.setBorder(null);
				
			}
		});
		
	}
	
	
	private void addComponents() {
		
		bg.add(myprofile);
		bg.add(logout);
		bg.add(users);
		bg.add(flights);
		bg.add(feedback);
		bg.add(booked_flights);
		
	}
	
	//Configuring JFrame
	private void configureFrame() {
		
		setTitle("Login - Admin");
		setSize(new Dimension(828,600));
		setLocationRelativeTo(null);
		setResizable(false);
		setContentPane(sp);			
		setVisible(true);
		
	}
	
	public static void main(String args[]) {
		
		FlatLightLaf.install();
		
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				CreateConnection.createConnection();
				new Login(CreateConnection.con);			
			}		
		});
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getSource() ==  users) {
			sp.setViewportView(Users);
			users.setBorder(null);
		}
		else if(e.getSource() ==  feedback) {
			sp.setViewportView(Feedback);
			feedback.setBorder(null);
			
		}else if(e.getSource() == flights ) {
			sp.setViewportView(Flights);
			flights.setBorder(null);
			
		}else if(e.getSource() == booked_flights){
			sp.setViewportView(BookedFlights);
			booked_flights.setBorder(null);
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//Do nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//Do nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//Do nothing	
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//Do nothing
	}
	
}