package admin;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import utilities.MixUtilities;


public class Flights extends JPanel implements MouseListener{

	private static final long serialVersionUID = 2312733264553903041L;
	
	private JTable flights;
	private ImageIcon background;
	private JLabel bg;
	private JButton back,edit,add,remove,refresh;
	private JScrollPane table;
	private Connection con;
	
	//For storing list of flights
	private ArrayList<ArrayList<String>> data;

	public Flights(Connection con) {
		
		this.con = con;
		
		data = new ArrayList<ArrayList<String>>();
		
		imageLoading();
		
		getflightschedules();
		
		intializeComonents();
		
		addComponents();
		
		configurePanel();
		
	}
	
	private void imageLoading() {
		
		background = new ImageIcon("res/admin/flights/flights_bg.png");
		
	}
	
	private void getflightschedules() {
		
		String query = "select * from flights";
		ResultSet rs;
		
		try {
			
			Statement st = con.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				data.add(new ArrayList<String>());
				data.get(data.size()-1).add(String.valueOf(rs.getInt(1)));
				data.get(data.size()-1).add(String.valueOf(rs.getString(2)));
				data.get(data.size()-1).add(String.valueOf(rs.getString(3)));
				data.get(data.size()-1).add(String.valueOf(rs.getString(4)));
				data.get(data.size()-1).add(String.valueOf(rs.getString(5)));
				data.get(data.size()-1).add(String.valueOf(rs.getString(6)));
				data.get(data.size()-1).add(String.valueOf(rs.getString(7)));
				data.get(data.size()-1).add(String.valueOf(rs.getString(8)));
				data.get(data.size()-1).add(String.valueOf(rs.getInt(9)));
				data.get(data.size()-1).add(String.valueOf(rs.getInt(10)));
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	private void intializeComonents() {
		
		bg = new JLabel(background);
		bg.setBounds(0,0,800,600);
		
		back = new JButton(new ImageIcon("res/admin/back.png"));
		back.setBounds(10,8,40,30);
		
		fillTable();
		
		table = new JScrollPane(flights);
		table.setBounds(10,60,780,430);
		
		edit = new JButton("Edit Selected");
		edit.setBounds(50,505,150,40);
		
		add = new JButton("Add New Record");
		add.setBounds(250,505,150,40);
		
		remove = new JButton("Remove Selected");
		remove.setBounds(450,505,150,40);
		
		refresh = new JButton("Refresh");
		refresh.setBounds(650, 505, 150, 40);
		
		back.addMouseListener(this);
		edit.addMouseListener(this);
		add.addMouseListener(this);
		remove.addMouseListener(this);
		refresh.addMouseListener(this);
		
	}
	
	private void fillTable() {
		String column[]={"Sr.No.","Flight_ID","Origin","Destination","Departure","Arrival","Aircraft","Time","Economic Cost","Business Cost"};
		String rows[][] = data.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		
		flights = new JTable(rows,column);
		MixUtilities.TableDesinger(flights);
	}
	
	private void addComponents(){
		
		bg.add(back);
		bg.add(table);
		bg.add(edit);
		bg.add(add);
		bg.add(remove);
		bg.add(refresh);
		add(bg);
		
	}
	
	private void edit() {
		
		//Checking If NO row is selected in Table
		if(flights.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog( this, "No Row is selected !","Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//Serial Number of selected Row
		String s_no = (String) flights.getValueAt(flights.getSelectedRow(), 0);
		ResultSet rs;
		Time d,a ;		
		
		//Panel For Taking Input For Updating a row
		DataComponents inputs = new DataComponents();
		
		//Fetching All Details OF selected Row
		try {
			Statement st = con.createStatement();
			
			rs = st.executeQuery("select * from flights where serial_number "
					+ "like '"+s_no+"'");
			if(!rs.next()) {
				JOptionPane.showMessageDialog(this, "No Flights Found For this Serial Number !");
				return;
			}
			
			
			inputs.flight_id.setText(rs.getString(2));
			inputs.origin.setText(rs.getString(3));
			inputs.destination.setText(rs.getString(4));
			inputs.departure.setText(rs.getString(5));
			inputs.arrival.setText(rs.getString(6));
			inputs.aircraft.setText(rs.getString(7));
			inputs.time.setText(rs.getString(8));
			inputs.economy_cost.setText(rs.getString(9));
			inputs.business_cost.setText(rs.getString(10));
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		//Confirm Dialog For Inputing Update Values
		int result = JOptionPane.showConfirmDialog(this, inputs,
								"Update Row", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
			
			String query =  "update flights set flight_id = ?, origin = ?,"
					     + "destination = ?,departure = ?,arrival = ?,"
					     + " aircraft = ?,time = ?,economy_cost = ?,"
						 + "business_cost = ? where serial_number like '"+s_no+"'";
			
			
			try {
				
				//Casting String to java.sql.Time
				d = java.sql.Time.valueOf(inputs.departure.getText());
				a = java.sql.Time.valueOf(inputs.arrival.getText());
				
				PreparedStatement st = con.prepareStatement(query);
				
				st.setString(1, inputs.flight_id.getText());
				st.setString(2, inputs.origin.getText());
				st.setString(3, inputs.destination.getText());
				st.setTime(4, d);
				st.setTime(5, a);
				st.setString(6, inputs.aircraft.getText());
				st.setString(7, inputs.time.getText());
				st.setInt(8, Integer.parseInt(inputs.economy_cost.getText()));
				st.setInt(9, Integer.parseInt(inputs.business_cost.getText()));
				
				result = JOptionPane.showConfirmDialog(this, "Are You Sure To Update Changes ?",
					""	,JOptionPane.OK_CANCEL_OPTION);
				
				//Updating Database if user confirms updation
				if(result == JOptionPane.OK_OPTION) {
					st.executeUpdate();
				}
				else {
					//Do Nothing
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}			
			
		}
		
	}
	
	public void add() {
		
		Time d,a;
		
		DataComponents inputs = new DataComponents();
		
		//For Taking Input
		int result = JOptionPane.showConfirmDialog(this, inputs,
				"ADD ROW", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
			
			try {
				
				String query = "insert into flights "
						+ "(flight_id, origin, destination, departure,"
						+ "arrival, aircraft, time, economy_cost, business_cost) "
						+ "values(?,?,?,?,?,?,?,?,?)";
				
				//Casting String to java.sql.Time
				d = java.sql.Time.valueOf(inputs.departure.getText());
				a = java.sql.Time.valueOf(inputs.arrival.getText());
				
				PreparedStatement st = con.prepareStatement(query);
				
				st.setString(1, inputs.flight_id.getText());
				st.setString(2, inputs.origin.getText());
				st.setString(3, inputs.destination.getText());
				st.setTime(4, d);
				st.setTime(5, a);
				st.setString(6, inputs.aircraft.getText());
				st.setString(7, inputs.time.getText());
				st.setInt(8, Integer.parseInt(inputs.economy_cost.getText()));
				st.setInt(9, Integer.parseInt(inputs.business_cost.getText()));
				
				result = JOptionPane.showConfirmDialog(this, "Are You Sure To Insert row ?",
						""	,JOptionPane.OK_CANCEL_OPTION);
					
					//inserting row if user confirms insertion
					if(result == JOptionPane.OK_OPTION) {
						st.executeUpdate();
					}
					else {
						//Do Nothing
					}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void remove() {
		
		//Checking If NO row is selected in Table
		if(flights.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog( this, "No Row is selected !",
									"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//Serial Number of selected Row
		String s_no = (String) flights.getValueAt(flights.getSelectedRow(), 0);
		
		//MySQL Query for deleting a row
		String query = "delete from flights where "
				+ "serial_number like '"+s_no+"'";
		
		try {
			
			Statement st = con.createStatement();
			
			int result = JOptionPane.showConfirmDialog(this, "Are You Sure To Delete row ?",
					""	,JOptionPane.OK_CANCEL_OPTION);
				
				//Deleting row if user confirms deletion
				if(result == JOptionPane.OK_OPTION) {
					st.executeUpdate(query);
				}
				else {
					//Do Nothing
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	//Function For refreshing Database 
	private void refresh() {
		
		//Clearing existing data
		data.clear();
		
		//Getting Current Flight schedules
		getflightschedules();
		
		//Filling table with flight schedules
		fillTable();
		
		//Setting Viewport to New Updated Table
		table.setViewportView(flights);
		
	}
	
	private void configurePanel() {
		
		setPreferredSize(new Dimension(800,558));
		setLayout(null);
		
	}
	
	//Inner Class for providing components for 
	//Adding or updating Flights List
    private class DataComponents extends JPanel{
		
		JTextField flight_id, origin, destination;
		JTextField  departure, arrival, aircraft;
		JTextField  time, economy_cost , business_cost;
		
		public DataComponents() {
			
			flight_id = new JTextField();
			origin = new JTextField();
			destination = new JTextField();
			departure = new JTextField();
			arrival = new JTextField();
			aircraft = new JTextField();
			time = new JTextField();
			economy_cost = new JTextField();
			business_cost = new JTextField();
			
			configure();
		}
		
		//Inner Class DataComponents configuration
		private void configure() {
			
			add(new JLabel("Flight ID "));
			add(flight_id);
			add(new JLabel("Origin "));
			add(origin);
			add(new JLabel("Destination "));
			add(destination);
			add(new JLabel("Departure "));
			departure.setToolTipText("HH:MM:SS");
			add(departure);
			add(new JLabel("Arrival "));
			arrival.setToolTipText("HH:MM:SS");
			add(arrival);
			add(new JLabel("Aircraft "));
			add(aircraft);
			add(new JLabel("Time "));
			add(time);
			add(new JLabel("Economy Cost "));
			add(economy_cost);
			add(new JLabel("Business Cost "));
			add(business_cost);
			
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == back) {
			Login.sp.setViewportView(Login.bg);
		}
		else if(e.getSource() == edit) {
			edit();
			refresh();
		}
		else if(e.getSource() == add) {
			add();
			refresh();
		}
		else if(e.getSource() ==  remove) {
			remove();
			refresh();
		}
		else if(e.getSource() == refresh) {
			refresh();
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
		//Do nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//Do nothing
	}
	
}
