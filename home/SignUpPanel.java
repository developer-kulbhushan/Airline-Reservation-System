package home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utilities.CreateConnection;
import utilities.DateLabelFormatter;
import utilities.FormValidation;

final public class SignUpPanel extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private JPanel SignUpPanel;
	private JScrollPane scrollPane;
	final private Border matte;
	final private String titles[] = {"Please select..","Master","MISS","MR","MRS","MS"};
	final private String genders[] = {"Please select..","Male","Female","Others"};
	final private String nations[] = {"Please select..","India","United States"};
	final private String countryCodes[] = {" +91"};
	private JTextField fName,lName,fNameFather,lNameFather;
	private JTextField number,email,remail;
	private JTextField country,state,city,zipcode,landmark;
	private JTextField password,repassword;
	private JComboBox<String> title,gender,nation,countryCode;
	private JDatePickerImpl datePicker;
	private JCheckBox check;
	private JButton signup;
	private ImageIcon img1,img2;
	private final Font f;
	private int formcheck;
	private String url;
    private String username;
    private String passcode;
	private Connection con;
	private int user_id;
	
	public SignUpPanel() {
		
		con = CreateConnection.con;
		setTitle("SARK Airlines - SignUp");
		
		/* The actual size of Jframe where we can design is 800 * 600
		 * 34 is added in width and 57 is added in height
		 *  because of insets and scrollbar added to Jframe
		 */
		
		setSize(new Dimension(834,557));
		
		//This is used show the JFrame in the center of screen
		setLocationRelativeTo(null);
		
		// Setting border for TextFields
		matte = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE);
		
		// Setting font for Textfields 
		f = new Font("Serif",Font.PLAIN,18);
		
		
		// Image for the background
		img1 = new ImageIcon("res/SignUpPanel/signup_bg.jpg");
		
		//Image for the signup button
		img2 = new ImageIcon("res/SignUpPanel/signup.png");
		
		// SignUpDesignPanel() returns the designed JPanel
		SignUpPanel =  SignUpDesignPanel();
		
		//ScrollPaneDesign() returns the designed JScrollPane
		scrollPane = ScrollPaneDesign();
		
		//Add SignUpPanel as a view in scrollPane
		scrollPane.setViewportView(SignUpPanel);
		
		// Set Content Pane of this JFrame To JScrollPane
		setContentPane(scrollPane);
		
		//Set JFrame to non - resizable
		setResizable(false);
		
		//Make JFrame visible
		setVisible(true);
	}
	
	private JPanel SignUpDesignPanel() {
		
		JPanel SignUpPanel = new JPanel();
		SignUpPanel.setPreferredSize(new Dimension(800,1350));
		SignUpPanel.setLayout(null);
		
		JLabel bg = new JLabel(img1);
		bg.setBounds(0,0,800,1350);
		
		
		//Title DropDown Menu
		title = new JComboBox<String>(titles);
		title.setBounds(130,227,150,30);
		title.setOpaque(false);
		
		//Gender DropDown Menu
		gender = new JComboBox<String>(genders);
		gender.setBounds(150,277,150,30);
		gender.setOpaque(false);
		
		//First Name
		fName = new JTextField();
		fName.setBounds(170 ,330, 150, 20);
		
		// Last Name
		lName = new JTextField();
		lName.setBounds(515, 330, 150, 20);
		
		// DatePicker
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
	    datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(300,375,150,30);
		datePicker.getComponent(0).setPreferredSize(new Dimension(120,30));
		datePicker.getComponent(1).setPreferredSize(new Dimension(30,30));
		
		fNameFather = new JTextField();
		fNameFather.setBounds(230 ,430, 150, 20);
		
		lNameFather = new JTextField();
		lNameFather.setBounds(575 ,430, 150, 20);
		
		
		//NAtionality
		nation = new JComboBox<String>(nations);
		nation.setBounds(170,475,150,30);
		nation.setOpaque(false);
		
		
		//Country Code
		countryCode = new JComboBox<String>(countryCodes);
		countryCode.setBounds(210,665,60,30);
		countryCode.setOpaque(false);
		
		
		// Phone Number
		number = new JTextField();
		number.setBounds(300, 675, 150, 20);
		
		//Email
		email = new JTextField();
		email.setBounds(195, 720, 150, 20);
		
		//Re - Email
		remail = new JTextField();
		remail.setBounds(205, 770, 150, 20);
		
		//Password
		password = new JTextField();
		password.setBounds(160, 820, 150, 20);
		
		//Re-Enter Password
		repassword = new JTextField();
		repassword.setBounds(580, 820, 150, 20);
		
		//Country
		country = new JTextField();
		country.setBounds(145, 1020, 150, 20);
		
		//State
		state = new JTextField();
		state.setBounds(545, 1020, 150, 20);
		
		//City
		city = new JTextField();
		city.setBounds(230, 1070, 150, 20);
		
		//zipcode
		zipcode = new JTextField();
		zipcode.setBounds(565, 1070, 150, 20);
		
		//LandMark
		landmark = new JTextField();
		landmark.setBounds(205, 1120, 150, 20);
		
		//CheckBox
		check = new JCheckBox();
		check.setBounds(60,1230,20,20);
		
		
		//SignUp Button
		signup =  new JButton(img2);
		signup.setBounds(300,1270,200,54);
		signup.setContentAreaFilled(false); // For transparency of button
		signup.setBorder(null);
		
		
		// This is used to change state of SignUp button 
		// when mouse pointer is hovered on button
		signup.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
			   signup.setBounds(305,1275,180,49);
			   validation();
			   if(formcheck == 1) {
				  try {
					databaseEntry();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			   }
			}

			@Override
			public void mouseExited(MouseEvent e) {
				signup.setBounds(300,1270,200,54);
			}
			
		});
		
		configure(fName,lName,fNameFather,
				lNameFather,number,email,
				remail,country,state,city,
				zipcode,landmark,password,
				repassword);
		
		bg.add(title);
		bg.add(gender);
		bg.add(fName);
		bg.add(lName);
		bg.add(datePicker);
		bg.add(fNameFather);
		bg.add(lNameFather);
		bg.add(nation);
		bg.add(countryCode);
		bg.add(number);
		bg.add(email);
		bg.add(remail);
		bg.add(password);
		bg.add(repassword);
		bg.add(country);
		bg.add(state);
		bg.add(city);
		bg.add(zipcode);
		bg.add(landmark);
		bg.add(check);
		bg.add(signup);
		
		
		SignUpPanel.add(bg);
		
		return SignUpPanel;
	}
	
	
	// All the Jtextfields have some common attributes
	//This function is used to set those attributes
	private void configure(JTextField ...field) {
		
		for(var i : field) {
			
			i.setOpaque(false);
			i.setForeground(Color.WHITE);
			i.setBorder(matte);
			i.setFont(f);
			
		}	
	}
	
	
	private JScrollPane ScrollPaneDesign() {
		
		JScrollPane sp;
		
		sp = new JScrollPane();
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.getVerticalScrollBar().setUnitIncrement(10);
		sp.setWheelScrollingEnabled(true);
		
		return sp;
	}
	
	private void validation() {
		FormValidation test =  new FormValidation();
		
		test.intializeTextFields(fName,lName,fNameFather,lNameFather,number,
				                    email,remail,country,state,city,zipcode,
				                    password,repassword);
		test.initializeJComboBox(title,gender,nation);
		test.initializeDatePicker(datePicker);
		test.initializeCheckbox(check);
		
		formcheck = test.startValidation();
	}
	
	
	// This function enters the values into database
	private void databaseEntry() throws SQLException, FileNotFoundException {
		
		
/*	//Opening configuration file
		File file = new File("configuration/configure.xml");
			
		//XML Handling Components			
		DocumentBuilderFactory factory;
		DocumentBuilder builder;
		Document doc;   */
		
		try {
			
			/*			//Creating Document Builder to work with XML File
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			doc = builder.parse(file);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("database");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				 Node nNode = nList.item(temp);
		         
		         if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		        	 Element eElement = (Element) nNode;
		        	 String url = "jdbc:mysql://"+"localhost"+":"+eElement.getElementsByTagName("port").item(0).getTextContent()+"/sark";
		        	 String username = eElement.getElementsByTagName("username").item(0).getTextContent();
		        	 String password =  eElement.getElementsByTagName("password").item(0).getTextContent();
		        	 
		        	 System.out.println("connecting database server...");
		 	        
		        	 Class.forName("com.mysql.cj.jdbc.Driver");
		             con = DriverManager.getConnection(url,username,password);
		             
		             con.setAutoCommit(false);
		         }
			}   */
		
		con.setAutoCommit(false);

		    // the mysql insert statement
		    String query = " insert into users (first_name, last_name, dob, gender, title,"
		    		+ " father_firstname, father_lastname, nationality, country, state, city, "
		    		+ "areacode, landmark, isAdmin) "
		    		+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		   
		    
		 // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      
		      preparedStmt.setString (1, fName.getText().trim());
		      preparedStmt.setString (2, lName.getText().trim());
		      preparedStmt.setDate   (3, Date.valueOf(datePicker.getJFormattedTextField().getText()));
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
		      preparedStmt.setBoolean (14, false);
		      
		      preparedStmt.execute();
		      
		      Statement stmt = con.createStatement();
		      query = "select user_id from users ORDER by user_id DESC LIMIT 1";
		      
		      ResultSet rs = stmt.executeQuery(query);
		      
		      if(rs.next()) {
		    	  user_id = rs.getInt("user_id");
		      }
		      
		      query = "insert into contactdetails (user_id,email,password,phone_number) "
		      		+ "values (?, ?, ?, ?)";
		      preparedStmt = con.prepareStatement(query);
		      
		      preparedStmt.setInt(1, user_id);
		      preparedStmt.setString (2, email.getText().trim());
		      preparedStmt.setString (3, password.getText().trim());
		      preparedStmt.setString (4, number.getText().trim());
		      
		      preparedStmt.execute();
		      
		      con.commit();
		      
		      createUserFile();
			
			JOptionPane.showMessageDialog(this, "Registration Successful!");
			this.dispose();
			
		}catch(Exception e) {
			
			con.rollback();
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Registration Failed!");
			
		}
		
	}
	
	private void createUserFile() {
		
		try {
			File file = new File("data/users/"+user_id+".txt");
			if(file.exists()) {
				file.delete();
				file.createNewFile();
			}					
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		new SignUpPanel();
	}

}
