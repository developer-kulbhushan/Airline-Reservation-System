package user;

import java.awt.Dialog;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import utilities.CreateConnection;

public class userProfile extends JPanel {
	
	//JLabels for MyProfile section
	private JLabel name,gender,dob,fathername;
	private JLabel nationality,country,state,city;
	private JLabel zipcode,landmark,email,number;
	
	private JLabel bg;
	private JScrollPane sp; 
	private int userid;
	private Connection con;
	
	private JButton edit;
	
	public userProfile(int userid) {
		
		con = CreateConnection.con;
		this.userid = userid;
		
		getUserInformation();
		
		intializeComponents();
		
		configureJPanel();
	}
	
	private void getUserInformation() {
		
		Statement stmt;		
		String query;
		ResultSet rs;
		
		try {
			
			stmt = con.createStatement();
			query = "select * from users where user_id = "+userid;
		    rs = stmt.executeQuery(query);
		    
		    while(rs.next()) {
		    	
		    	name = new JLabel(rs.getString("title")+" "+
		    			rs.getString("first_name")+" "+
		    			rs.getString("last_name"));
		    	
		    	dob = new JLabel(rs.getDate("DOB").toString());
		    	gender = new JLabel(rs.getString("gender"));
		    	
		    	fathername = new JLabel("Mr "+rs.getString("father_firstname")+
		    			rs.getString("father_lastname"));
		    	
		    	nationality = new JLabel(rs.getString("nationality"));
		    	country = new JLabel(rs.getString("country"));
		    	state = new JLabel(rs.getString("state"));
		    	city = new JLabel(rs.getString("city"));
		    	zipcode = new JLabel(rs.getString("areacode"));
		    	landmark = new JLabel(rs.getString("landmark"));
		    }
		    
		    query = "select * from contactDetails where user_id = "+userid;
		    rs = stmt.executeQuery(query);
		    
		    while(rs.next()) {
		    	
		    	email = new JLabel(rs.getString("email"));
		    	number = new JLabel(rs.getString("phone_number"));
		    }
		    
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void configureJPanel() {
		
		setPreferredSize(new Dimension(550,500));
		setLayout(null);
	}
	
	public void intializeComponents() {

		JLabel bg = new JLabel(new ImageIcon("res/user.Login/myprofile_bg.png"));
		
		name.setBounds(250, 65, 200, 30);
		fathername.setBounds(250, 95 , 200 ,30);	
		gender.setBounds(250, 125, 200, 30);		
		dob.setBounds(250, 155, 200, 30);		
		nationality.setBounds(250,185,200,30);		
		email.setBounds(250,318,300,30);		
		number.setBounds(250,285,300,30);		
		country.setBounds(250,428,200,30);	
		state.setBounds(250,455,200,30);		
		city.setBounds(250,485,200,30);		
		zipcode.setBounds(250,515,200,30);		
		landmark.setBounds(250,545,300,30);
		
		edit = new JButton("Edit");
		edit.setBounds(216,600,100,40);
		
		bg.add(name);
		bg.add(fathername);
		bg.add(gender);
		bg.add(dob);
		bg.add(nationality);
		bg.add(email);
		bg.add(number);
		bg.add(country);
		bg.add(state);
		bg.add(city);
		bg.add(zipcode);
		bg.add(landmark);
		bg.add(edit);
		
		sp = new JScrollPane(bg);
		sp.setBounds(0,0,550,500);
		
		add(sp);
		
	}
	
}
