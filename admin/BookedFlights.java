package admin;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import utilities.MixUtilities;

public class BookedFlights extends JPanel {
	
	private JTable flights;
	private static final long serialVersionUID = 5851609178588878479L;
	private JButton back;
	private JLabel bg;
	private Connection con;
	private ImageIcon bookedflights_bg;
	private JScrollPane table;
	
	//For storing list of flights
	private ArrayList<ArrayList<String>> data;
	
	BookedFlights(Connection con){
		
		this.con = con;
		
		imageLoading();
		
		getBookedFlights();
		
		intializeComonents();
		
		addComponents();
		
		setButtonActions();
		
		configurePanel();
		
	}
	
	private void imageLoading() {
		
		bookedflights_bg = new ImageIcon("res/admin/bookedflights/bookedflights_bg.png");
	}
	
	private void getBookedFlights() {
		
		String query = "select * from bookedflights";
		ResultSet rs;
		
		try {
			
			Statement st = con.createStatement();
			rs = st.executeQuery(query);
			
			data = new ArrayList<ArrayList<String>>();
			
			while(rs.next()) {
				
				ResultSet rs1 = con.createStatement().executeQuery("select * from users where user_id = " + rs.getInt(1));
				rs1.next();
				ResultSet rs2 = con.createStatement().executeQuery("select * from flights where serial_number = "+ rs.getInt(2));
				rs2.next();
				
				data.add(new ArrayList<String>());
				data.get(data.size()-1).add((rs1.getString(2)+" "+rs1.getString(3)));
				data.get(data.size()-1).add(rs2.getString(3));
				data.get(data.size()-1).add(String.valueOf(rs2.getString(4)));
				
			}
			
			String column[]={"Name","Origin","Destination"};
			String rows[][] = data.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
			
			flights = new JTable(rows,column);
			MixUtilities.TableDesinger(flights);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	private void intializeComonents() {
		
		bg = new JLabel(bookedflights_bg);
		bg.setBounds(0,0,800,600);
		
		back = new JButton(new ImageIcon("res/admin/back.png"));
		back.setBounds(10,8,40,25);		 
		
		table = new JScrollPane(flights);
		table.setBounds(20,70,760,450);
	}
	
	private void addComponents() {
		
		bg.add(back);
		bg.add(table);
		add(bg);
		
	}
	
	private void setButtonActions() {
		
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login.sp.setViewportView(Login.bg);
			}
		});
		
	}
	
	private void configurePanel() {
		
		setPreferredSize(new Dimension(800,600));
		setLayout(null);
		
	}
}
