package Configuration;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.formdev.flatlaf.FlatLightLaf;

import utilities.CreateConnection;
import utilities.DatabaseLoader;
import utilities.DateLabelFormatter;


public class Configure extends JFrame implements Runnable,MouseListener{

	private static final long serialVersionUID = 5521040424808496821L;
	private JScrollPane viewport;
	private JPanel database;
	private JLabel bg;
	private JButton finish;
	
	final private String titles[] = {"Mr","Miss","Master","Mrs","Ms"};
	final private String genders[] = {"Male","Female","Others"};
	final private String nations[] = {"India","USA","China","Japan"};
	final private String countryCodes[] = {" +91"};
	private JTextField fName,lName,fNameFather,lNameFather;
	private JTextField number,email,remail;
	private JTextField country,state,city,zipcode,landmark;
	private JTextField password,repassword;
	private JComboBox<String> title,gender,nation,countryCode;
	private JDatePickerImpl dob;
	
	private JTextField user,passwd,port;
	public static boolean configured = false;
	private static File file;
	
	private boolean databaseWritten = false;
	
	//Checking if Application is Configured
	public static boolean isConfigured() {
		
		//Opening configuration file
		file = new File("configuration/configure.xml");
		
		try {			
			//Checking whether configuration file exist
			//If not then creating one
			if(!file.exists()) {
				
				configured = false;
				file.createNewFile();
				
			} else {
				
				//XML Handling Components
				DocumentBuilderFactory factory;
				DocumentBuilder builder;
				Document doc;
				
				//Creating Document Builder to work with XML File
				factory = DocumentBuilderFactory.newInstance();
				builder = factory.newDocumentBuilder();
				
				doc = builder.parse(file);
				
				Element root = doc.getDocumentElement();
				
				String bool = root.getAttribute("configured");
				
				CreateConnection.createConnection();
				
				//Checking if Configuration file have valid
				//User name , password & port number
				if(CreateConnection.con != null) {
					configured =  Boolean.parseBoolean(bool);
				}		
			}			
			
			//Checking for Admin Details in Users Table
			if(configured) {
				try {
					Statement st = CreateConnection.con.createStatement();
					
					ResultSet rs = st.executeQuery("select * from users where isAdmin like 1");
					
					if(rs.next()) {
						//Do nothing
					}
					else {
						configured = false;
						System.out.println("Admin Not Registered!");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			//Checking if Flights table has at least 1 entry
			if(configured) {
				try {
					Statement st = CreateConnection.con.createStatement();
					
					ResultSet rs = st.executeQuery("select * from flights");
					
					if(rs.next()) {
						//Do nothing
					}
					else {
						configured = false;
						System.out.println("No Flights in System !");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			//Setting configured false
			//If Any Exception generates
		} catch (ParserConfigurationException e) {		
			e.printStackTrace();
			configured = false;
		} catch (IOException e) {			
			e.printStackTrace();
			configured = false;
		} catch (SAXException e) {			
			configured = false;
		}
		
		return configured;
		
	}
	
	public void databaseWriter() {
		
		int userid;
		Connection con = CreateConnection.con;
		
		
		try {
			
			con.setAutoCommit(false);
			
			// the mySQL insert statement
			String query = " insert into users (first_name, last_name, dob, gender, title,"
		    		+ " father_firstname, father_lastname, nationality, country, state, city, "
		    		+ "areacode, landmark, isAdmin) "
		    		+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
		   
		    // create the mySQL insert prepared statement
		     PreparedStatement preparedStmt = con.prepareStatement(query);
		      
		     preparedStmt.setString (1, fName.getText().trim());
		     preparedStmt.setString (2, lName.getText().trim());
		     preparedStmt.setDate   (3, Date.valueOf(dob.getJFormattedTextField().getText()));
		     preparedStmt.setString (4, (String) gender.getSelectedItem());
		     preparedStmt.setString (5, (String)title.getSelectedItem());
		     preparedStmt.setString (6, fNameFather.getText().trim());
		     preparedStmt.setString (7, lNameFather.getText().trim());
		     preparedStmt.setString (8, (String)nation.getSelectedItem());
		     preparedStmt.setString (9, country.getText().trim());
		     preparedStmt.setString (10, state.getText().trim());
		     preparedStmt.setString (11, city.getText().trim());
		     preparedStmt.setString (12, zipcode.getText().trim());
		     preparedStmt.setString (13, landmark.getText().trim());
		     preparedStmt.setBoolean (14, true);
		      
		     preparedStmt.execute();
		     
		      Statement stmt = con.createStatement();
		      
		      query = "select user_id from users ORDER by user_id DESC LIMIT 1";
		      
		      ResultSet rs = stmt.executeQuery(query);
		      
		      if(rs.next()) {
		    	  
		    	  userid = rs.getInt("user_id");
		    	  query = "insert into contactdetails (user_id,email,password,phone_number) "
				      		+ "values (?, ?, ?, ?)";
		     	  preparedStmt = con.prepareStatement(query);
				      
			      preparedStmt.setInt(1, userid);
			      preparedStmt.setString (2, email.getText().trim());
			      preparedStmt.setString (3, password.getText().trim());
			      preparedStmt.setString (4, number.getText().trim());
				      
			      preparedStmt.execute();
			      con.commit();
			      
			      databaseWritten = true;
			      
		      } else {
		    	  con.rollback();
		    	  JOptionPane.showMessageDialog(this, "Some Error Occurred !");
		    	  this.dispose();
		      }
			
		} catch (Exception e) {
			
			try {
				con.rollback();
			} catch (SQLException e1) {				
				e1.printStackTrace();
			}	
			
			e.printStackTrace();
			
			databaseWritten = false;	
		}
		
		
	}
	
	synchronized public void XMLWriter() {
		
		//XML Handling Components
		DocumentBuilderFactory factory;
		DocumentBuilder builder;
		Document doc;
		
		try {
			
			//Creating Document Builder to work with XML File
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();

			doc = builder.newDocument();
			
			Element root = doc.createElement("configuration");
			doc.appendChild(root);
			root.setAttribute("configured", "false");
			
			Element database = doc.createElement("database");
			root.appendChild(database);
			
			Element username = doc.createElement("username");			
			username.appendChild(doc.createTextNode(user.getText()));
			
			Element password = doc.createElement("password");
			password.appendChild(doc.createTextNode(passwd.getText()));
			
			Element portno = doc.createElement("port");
			portno.appendChild(doc.createTextNode(port.getText()));
			
			database.appendChild(username);
			database.appendChild(password);
			database.appendChild(portno);
			
			root.setAttribute("configured", "true");
			
			// output DOM XML to console 
            Transformer transformer = TransformerFactory.newInstance().newTransformer();        
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            DOMSource source = new DOMSource(doc);
            StreamResult configuration_file = new StreamResult(file);
            transformer.transform(source, configuration_file);           
            
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	private void intializeComponent() {	
		
		bg = new JLabel(new ImageIcon("res/configure/configure_bg.png"));
		bg.setBounds(0,0,800,500);
		
		user = new JTextField();
		passwd = new JTextField();
		port = new JTextField();
		
		finish =  new JButton("Finish");
		finish.setBounds(720,470,70,26);
		finish.addMouseListener(this);
		
		viewport = new JScrollPane();
		viewport.setBounds(0,0,800,469);
		viewport.setBorder(null);
		viewport.getViewport().setOpaque(false);
		viewport.setOpaque(false);
		
		database = databasePanel();
		
		viewport.setViewportView(database);
		
		bg.add(viewport);
		bg.add(finish);
		add(bg);		
	}
	
	public JPanel databasePanel() {
		
		JPanel pnl = new JPanel();
		pnl.setPreferredSize(getPreferredSize());
		pnl.setLayout(null);
		pnl.setOpaque(false);
		
		JLabel username = new JLabel("Username");
		username.setBounds(40,50,70,30);
		
		JLabel passwrd = new JLabel("Password");
		passwrd.setBounds(270,50,70,30);
		
		JLabel portno = new JLabel("Port Number");
		portno.setBounds(500,50,80,30);
		
		user.setBounds(110,45,150,40);
		passwd.setBounds(340,45,150,40);
		port.setBounds(580,45,150,40);
		
		title = new JComboBox<String>(titles);
		title.setBounds(30,147,120,40);
		
		fName = new JTextField();
		fName.setBounds(160,140,150,50);
		fName.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "First Name", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		
		lName = new JTextField();
		lName.setBounds(315,140,150,50);
		lName.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "Last Name", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		
		fNameFather = new JTextField();
		fNameFather.setBounds(470,140,150,50);
		fNameFather.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "Father's First Name", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		
		lNameFather = new JTextField();
		lNameFather.setBounds(625,140,150,50);
		lNameFather.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "Father's Last Name", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		
		// DatePicker
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
	    dob = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		dob.getComponent(0).setPreferredSize(new Dimension(120,30));
		dob.getComponent(1).setPreferredSize(new Dimension(23,30));
		dob.setOpaque(false);
		dob.setBounds(68,220,143,55);
		dob.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "DOB", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		
		gender = new JComboBox<String>(genders);
		gender.setBounds(240,220,140,55);
		gender.setOpaque(false);
		gender.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "Gender", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		
		
		nation = new JComboBox<String>(nations);
		nation.setBounds(410,220,140,55);
		nation.setOpaque(false);
		nation.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "Nation", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		
		countryCode = new JComboBox<String>(countryCodes);
		countryCode.setOpaque(false);
		countryCode.setBounds(580,220,140,55);
		countryCode.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "Country Code", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		
		number = new JTextField();
		number.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "Phone Number", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		number.setBounds(30,300,145,50);
		
		email = new JTextField();
		email.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "Email", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		email.setBounds(180,300,145,50);
		
		remail = new JTextField();
		remail.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "Confirm Email", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		remail.setBounds(330,300,145,50);
		
		password = new JTextField();
		password.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "Password", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		password.setBounds(480,300,145,50);
		
		repassword = new JTextField();
		repassword.setBounds(630,300,145,50);
		repassword.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "Confirm Password", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		
		
		country = new JTextField();
		country.setBounds(30,375,145,50);
		country.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "Country", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		
		state = new JTextField();
		state.setBounds(180,375,145,50);
		state.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "State", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		
		city = new JTextField();
		city.setBounds(330,375,145,50);
		city.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "City", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		
		zipcode = new JTextField();
		zipcode.setBounds(480,375,145,50);
		zipcode.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "Zipcode", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		
		landmark = new JTextField();
		landmark.setBounds(630,375,145,50);
		landmark.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "Landmark", TitledBorder.LEFT, TitledBorder.TOP,null, Color.BLACK));
		
		pnl.add(username);
		pnl.add(passwrd);
		pnl.add(portno);
		pnl.add(port);
		pnl.add(user);
		pnl.add(passwd);
		
		
		pnl.add(title);
		pnl.add(fName);
		pnl.add(lName);
		pnl.add(fNameFather);
		pnl.add(lNameFather);
		pnl.add(dob);
		pnl.add(gender);
		pnl.add(nation);
		pnl.add(countryCode);
		pnl.add(number);
		pnl.add(email);
		pnl.add(remail);
		pnl.add(password);
		pnl.add(repassword);
		pnl.add(country);
		pnl.add(state);
		pnl.add(city);
		pnl.add(zipcode);
		pnl.add(landmark);
			
		return pnl;
	}
	   
	
	private void configureJFrame() {
		
		FlatLightLaf.install();
		
		setTitle("Configuration");
		setSize(new Dimension(817,539));
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setVisible(true);
	}

	@Override
	public void run() {	
		
		intializeComponent();
		
		configureJFrame();
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == finish) {
			 
			XMLWriter();
			new DatabaseLoader();
			
			if(DatabaseLoader.tablesConfigured) {
				CreateConnection.createConnection();
				if(CreateConnection.con != null) {
					databaseWriter();
				}
			}
			
			if(databaseWritten) {
				configured = true;
			}
			
			if(configured) {
				JOptionPane.showMessageDialog(this, "Successfully Configured Application!");
			} else {
				JOptionPane.showMessageDialog(this, "Some error Occured !");
				System.exit(0);
			}
			
			this.dispose();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//Do Nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//Do nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//Do Nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//Do Nothing
	}
	
}
