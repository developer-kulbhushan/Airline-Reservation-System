package home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import utilities.CreateConnection;
import utilities.DateLabelFormatter;
import utilities.MixUtilities;
import utilities.RouteFinder;


//This Class is for creating HomePage Frame
public class Homepage extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 6708248817603050747L;
	
	private JLabel bg;
	private String cities[] = {"New Delhi","Mumbai","Bangalore","Chennai",
            "Hyderabad","Goa","Kolkata","Patna","Jaipur","Lucknow"};
	private JComboBox<String> from,to;
	private JPanel flightSearch;
	private static Connection con;
	private JLabel login,aboutus,contactus;
	private JDialog d; 
	private JTable table;
	
	//Components for flightSearch panel
	private JButton search;
	private JDatePickerImpl arrival,departure;
	private JRadioButton oneway,twoway;
	private JRadioButton business,economy;
	private JSpinner seats;
	private ButtonGroup travelClass;
	
	public Homepage() {
		
	}
	
	private void loadImages() {
		
	}
		
		//Function for initializing components
		private void intializeComponents() {
			
			con = CreateConnection.con;
			
			bg =  new JLabel(new ImageIcon("res/home/homepage.png"));
			bg.setBounds(0, 0, 800, 500);
			
			from = new JComboBox<String>(cities);
			to = new JComboBox<>(cities);
			
			login = new JLabel("Login/Signup");
			login.setBounds(484,90,120,30);
			login.setForeground(new Color(217,223,247));		
			login.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					new LoginPanel();
				}
			});
			
			aboutus = new JLabel("About us");
			aboutus.setBounds(400,90,100,30);
			aboutus.setForeground(new Color(217,223,247));
			
			aboutus.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					aboutus();
				}
			});
			
			contactus = new JLabel("Contact us");
			contactus.setBounds(305,90,100,30);
			contactus.setForeground(new Color(217,223,247));
			
			search =  new JButton();
			search.setBounds(360,413,85,35);
			search.setContentAreaFilled(false);
			search.setBorder(null);
			
			search.addMouseListener(new MouseAdapter() {;
			public void mouseClicked(MouseEvent e) {
				availableFlightList();
			}
			});
			
			
			flightSearch = flightSearch();
			flightSearch.setBounds(143,180,514,235);

		}
		
		public void aboutus() {
			d = new JDialog(this,"About Us",false);
			d.setSize(new Dimension(518,345));
			d.setLocationRelativeTo(null);
			d.setLayout(new FlowLayout());
			
			JLabel bg = new JLabel(new ImageIcon("res/home/aboutus.png"));
	     	
			d.add(bg);
			d.show();
		}
		
		//Adding components to Frame
		private void addComponents() {
			
			bg.add(login);
			bg.add(aboutus);
			bg.add(contactus);
			bg.add(search);
			bg.add(flightSearch);
			
			add(bg);
		}
		
		
		//Configuring window 
		private void configureJFrame() {
			
			setName("SARK Airlines");
			setSize(816, 539);
			setLayout(null);
			setLocationRelativeTo(null);
			setResizable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
			
			setVisible(true);
			
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
			      CreateConnection.close();
			    }
			});			
			
		}
		
		//Panel for Flight Search
		private JPanel flightSearch() {
			JPanel pnl = new JPanel();	
			pnl.setPreferredSize(new Dimension(500,300));
			pnl.setOpaque(false);
			pnl.setLayout(null);
			
			// DatePicker
			UtilDateModel model = new UtilDateModel();
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");
			
			JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		    arrival = new JDatePickerImpl(datePanel, new DateLabelFormatter());
			arrival.getComponent(0).setPreferredSize(new Dimension(120,30));
			arrival.getComponent(1).setPreferredSize(new Dimension(30,30));
			
			arrival.setBounds(60,107,150,30);
			pnl.add(arrival);
			
			from.setBounds(60,45,150,30);
			pnl.add(from);
			
			to.setBounds(300,45,150,30);
			pnl.add(to);
			
			UtilDateModel model2 = new UtilDateModel();
			JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
		    departure = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
			departure.getComponent(0).setPreferredSize(new Dimension(120,30));
			departure.getComponent(1).setPreferredSize(new Dimension(30,30));
			
			departure.setBounds(300,107,150,30);
			pnl.add(departure);
			
			oneway = new JRadioButton();
			oneway.setBounds(56,150,20,20);
			oneway.setSelected(true);
			oneway.setContentAreaFilled(false);
			pnl.add(oneway);
			
			twoway = new JRadioButton();
			twoway.setBounds(145,150,20,20);
			twoway.setContentAreaFilled(false);
			pnl.add(twoway);
			
			ButtonGroup type = new ButtonGroup();
			type.add(oneway);
			type.add(twoway);
			
			
			business = new JRadioButton("Business");
			business.setBounds(56,177,20,20);
			business.setSelected(true);
			business.setContentAreaFilled(false);
			pnl.add(business);
			
			economy = new JRadioButton("Economy");
			economy.setBounds(144,177,20,20);
			economy.setContentAreaFilled(false);
			pnl.add(economy);
			
			travelClass = new ButtonGroup();
			travelClass.add(business);
			travelClass.add(economy);
			
			SpinnerModel value =  
		             new SpinnerNumberModel(1, //initial value  
		                1, //minimum value  
		                20, //maximum value  
		                1); //step
			
		    seats = new JSpinner(value);
		    
		    seats.setBounds(100,207,50,30);
		    pnl.add(seats);	
			
			return pnl;
		}
		
		// Function for checking available flights
		private void availableFlightList() {
				
			//Checking if same origin and destination is selected
			if(from.getSelectedItem().equals(to.getSelectedItem())) {
				JOptionPane.showMessageDialog(this, "Origin and destination must be different!");
				return;
			}
			
			RouteFinder obj = new RouteFinder(con);
			table = obj.findRoots((String)from.getSelectedItem(),
					(String)to.getSelectedItem(),
					MixUtilities.getSelectedButtonText(travelClass),
					(int) seats.getValue());
			
			MixUtilities.TableDesinger(table);
			
			if(table.getRowCount() == 0) {
				JOptionPane.showMessageDialog(this, "No Flights available for selected cities!");
			}
			else {
				showResult();
			}	
		}
		
		
		private void showResult() {
			
			d = new JDialog(this,"Search Results",false);
			d.setSize(new Dimension(500,200));
			d.setLocationRelativeTo(null);
			d.setLayout(new FlowLayout());
			
	     	JScrollPane sp = new JScrollPane(table);	
	     	table.setDefaultEditor(Class.class, null);
	     	
			d.add(sp);
			d.show();
		}
		
		@Override
		public void run() {
			
			//For Loading Images
			loadImages();
			
			//Initializing components
			intializeComponents();
			
			//Adding components
			addComponents();
			    
			//Configuring Frame
			configureJFrame();
		}


}
