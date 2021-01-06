package user;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import payment.Payment;
import utilities.CreateConnection;
import utilities.DateLabelFormatter;
import utilities.MixUtilities;
import utilities.RouteFinder;

public class bookFlights extends JPanel {
	
	JScrollPane sp;
	private int userid;
	
	//Components of flightSearch
	private JButton search,book;
	private String cities[] = {"New Delhi","Mumbai","Bangalore","Chennai",
	           "Hyderabad","Goa","Kolkata","Patna","Jaipur","Lucknow"};
	private JComboBox<String> from,to;
	private JDatePickerImpl datePicker,datePicker2;
	private JRadioButton oneway,twoway;
	private JRadioButton business,economy;
	private JScrollPane flightList ;
	private JSpinner seats;
	private JTable table;
	ButtonGroup travelClass;
	JScrollPane sp2;
	
	public bookFlights(int userid) {
		
		this.userid = userid;
		
		intializeComponents();
		
		configureJPanel();
		
	}
	
	public void intializeComponents() {
		
		JLabel bg = new JLabel(new ImageIcon("res/user.Login/white.png"));
		bg.setLocation(0, 0);
		
		JPanel flightSearch =  flightSearchPanel();
		flightSearch.setBounds(0,0,532,350);
		bg.add(flightSearch);
		
		sp = new JScrollPane(bg);
		sp.setBounds(0,0,550,500);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		add(sp);

		flightList  = new JScrollPane();
		flightList.setBounds(36,350,460,90);
		bg.add(flightList);
		
		book = new JButton("Book");
		book.setBounds(216,450,100,30);
		bg.add(book);
		
		book.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				if(table.getSelectedRow() == -1) {
					//Do Nothing
				}
				else {
					int result = JOptionPane.showConfirmDialog(null,
												"Continue To Booking Flight ?",
												"Logout",
												JOptionPane.YES_NO_OPTION);
					
					if(result == JOptionPane.YES_OPTION) {
						Payment pm = new Payment();
						enterRecord();
					}
					else {
						//Do Nothing
					}
				}
				
		}
		});
		
		search.addMouseListener(new MouseAdapter() {
	    	
		    public void mouseClicked(MouseEvent e) {
		    	
		    	RouteFinder obj = new RouteFinder(CreateConnection.con);
		    	
		    	//Checking if same origin and destination is selected
				if(from.getSelectedItem().equals(to.getSelectedItem())) {
					JOptionPane.showMessageDialog(null, "Origin and destination must be different!");
					flightList.setViewportView(null); //For Removing previous details of table
					return;
				}
				
				if(datePicker.getJFormattedTextField().getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Date Must Be Selected !");
					flightList.setViewportView(null); //For Removing previous details of table
					return;
				}
		    	
				table = obj.findRoots((String)from.getSelectedItem(),
									(String)to.getSelectedItem(),
									MixUtilities.getSelectedButtonText(travelClass),
									(int) seats.getValue());
				
				MixUtilities.TableDesinger(table);
				
				flightList.setViewportView(table);
				
				if(table.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "No Flights available for selected cities!");
				}
		    }
		    
		    });
		
	}
	
	private JPanel flightSearchPanel() {
		
		JPanel pnl = new JPanel();
		pnl.setPreferredSize(new Dimension(532,350));
		pnl.setLayout(null);

		JLabel bg = new JLabel(new ImageIcon("res/user.Login/bookflight_bg.png"));
		bg.setBounds(0,0,532,350);
		pnl.add(bg);
		
		// DatePicker
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.getComponent(0).setPreferredSize(new Dimension(90,25));
		datePicker.getComponent(1).setPreferredSize(new Dimension(30,25));
		
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model, p);
		datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		datePicker2.getComponent(0).setPreferredSize(new Dimension(90,25));
		datePicker2.getComponent(1).setPreferredSize(new Dimension(30,25));
		
		
		datePicker.setBounds(98,152,120,25);
		bg.add(datePicker);
		
		datePicker2.setBounds(283,152,120,25);
		bg.add(datePicker2);
		
		from = new JComboBox<String>(cities);
		to = new JComboBox<>(cities);
		
		from.setBounds(98,93,120,25);
		bg.add(from);
		
		to.setBounds(283,93,120,25);
		bg.add(to);
		
		oneway = new JRadioButton();
		oneway.setBounds(100,190,20,20);
		oneway.setSelected(true);
		bg.add(oneway);
		
		twoway = new JRadioButton();
		twoway.setBounds(190,190,20,20);
		bg.add(twoway);
		
		ButtonGroup type = new ButtonGroup();
		type.add(oneway);
		type.add(twoway);
		
		
		business = new JRadioButton("Business");
		business.setBounds(100,225,20,20);
		business.setSelected(true);
		bg.add(business);
		
		economy = new JRadioButton("Economy");
		economy.setBounds(190,225,20,20);
		bg.add(economy);
		
		travelClass = new ButtonGroup();
		travelClass.add(business);
		travelClass.add(economy);
		
		SpinnerModel value =  
	             new SpinnerNumberModel(1, //initial value  
	                0, //minimum value  
	                15, //maximum value  
	                1); //step  
	    seats = new JSpinner(value);
	    
	    seats.setBounds(140,255,50,30);
	    bg.add(seats);
	    
	    search =  new JButton(new ImageIcon("res/user.Login/search.png"));
	    search.setBounds(213,284,85,39);
	    search.setContentAreaFilled(false);
	    search.setBorder(null);
	    
	    bg.add(search);
	    
		return pnl;	
	}
	
	private void enterRecord() {
		
		File file = new File("data/users/"+userid+".txt");
		
		try {
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
			
			bw.write(table.getValueAt(table.getSelectedRow(), 0) + "@"
					+ datePicker.getJFormattedTextField().getText()+ "@"
					+ table.getValueAt(table.getSelectedRow(), 3) + "@"
					+ table.getValueAt(table.getSelectedRow(), 4) + "@"
					+ table.getValueAt(table.getSelectedRow(), 1) + "@"
					+ table.getValueAt(table.getSelectedRow(), 2) + "@"
					+ table.getValueAt(table.getSelectedRow(), 6) +"\n");
			
			bw.flush();
			bw.close();
			
			String query = "select serial_number from flights where origin like"
					+ " '"+table.getValueAt(table.getSelectedRow(), 1)+"' AND flight_id like '"
					+table.getValueAt(table.getSelectedRow(), 0)+"'";
			Statement st = CreateConnection.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			rs.next();
			int sno = rs.getInt(1);
			
			query = "insert into bookedflights values (?,?)";
			PreparedStatement stmt = CreateConnection.con.prepareStatement(query);
			
			stmt.setInt(1, userid);
			stmt.setInt(2, sno);
			
			stmt.executeUpdate();
			
		} catch (IOException e) {
			
			JOptionPane.showMessageDialog(this, "Unable To Book Flight!");
			e.printStackTrace();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void configureJPanel() {
		
		setPreferredSize(new Dimension(550,500));
		setLayout(null);
		
	}
	
}
