package user;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import utilities.MixUtilities;

public class myFlights extends JPanel {
	
	
	private JLabel bg;
	private int userid;
	private JScrollPane sp1,sp2;
	private JButton refresh;
	
	public myFlights(int userid) {
		
		this.userid = userid;
		
		myFlightsSorter();
		
		configureJPanel();
		
		intializeComponents();
		
	}
	
	public void configureJPanel() {
		
		setLayout(null);
		setOpaque(false);
	}
	
	public void intializeComponents() {
		
		bg = new JLabel(new ImageIcon("res/user.Login/myFlights_bg.png"));
		bg.setBounds(0,0,550,500);
		
		sp1 = new JScrollPane(Login.upcoming_flights);
		sp1.setBounds(10,40,530,180);
		
		sp2 = new JScrollPane(Login.boarded_flights);
		sp2.setBounds(10,320,530,160);
		
		refresh = new JButton("Refresh");
		refresh.setBounds(225,235,100,30);
		
		refresh.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				myFlightsSorter();
				sp1.setViewportView(Login.upcoming_flights);
				sp2.setViewportView(Login.boarded_flights);
			}
		});
		
		bg.add(sp1);
		bg.add(sp2);
		bg.add(refresh);
		
		add(bg);
		
	}
	
	//Getting myFlights list(Upcoming and Boarded) 
		 void myFlightsSorter() {	

			String line;
			String[] values;
			
			ArrayList<ArrayList<String>> upcoming_rows = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> boarded_rows = new ArrayList<ArrayList<String>>();
			ArrayList<String> columns;
			
			columns = new ArrayList<String>();
			
			columns.add("Flight ID");
			columns.add("Date");
			columns.add("Origin");
			columns.add("Destination");
			columns.add("Departure");
			columns.add("Arrival");
			columns.add("Cost");
			
			try {
				File file = new File("data/users/"+userid+".txt");
				
				if(file.exists()) {
					//Do Noting
				} else {
					file.createNewFile();
				}
				
				BufferedReader reader = new BufferedReader(new FileReader(file));
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				
				//System Current Date and Time
				Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dtf.format(now));
				
				while((line = reader.readLine())!=null) {
					
					values = line.split("@");
					
					String flightid = values[0];
					String date = values[1];
					String departure = values[2];
					String arrival = values[3];
					String origin = values[4];
					String destination = values[5];
					String cost = values[6];
					
					//Flight arrival Time
					Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date+" "+arrival);
					
					if(date2.after(date1)) {
						
						upcoming_rows.add(new ArrayList<String>());
						upcoming_rows.get(upcoming_rows.size()-1).add(flightid);
						upcoming_rows.get(upcoming_rows.size()-1).add(date);
						upcoming_rows.get(upcoming_rows.size()-1).add(origin);
						upcoming_rows.get(upcoming_rows.size()-1).add(destination);
						upcoming_rows.get(upcoming_rows.size()-1).add(departure);
						upcoming_rows.get(upcoming_rows.size()-1).add(arrival);
						upcoming_rows.get(upcoming_rows.size()-1).add(cost);
						
					}
					else {
						boarded_rows.add(new ArrayList<String>());
						boarded_rows.get(boarded_rows.size()-1).add(flightid);
						boarded_rows.get(boarded_rows.size()-1).add(date);
						boarded_rows.get(boarded_rows.size()-1).add(origin);
						boarded_rows.get(boarded_rows.size()-1).add(destination);
						boarded_rows.get(boarded_rows.size()-1).add(departure);
						boarded_rows.get(boarded_rows.size()-1).add(arrival);
						boarded_rows.get(boarded_rows.size()-1).add(cost);
					} 
				}
				
				Object[][] rows = upcoming_rows.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
				Object[] column = columns.toArray();
				
				Login.upcoming_flights = new JTable(rows,column);
				
				MixUtilities.TableDesinger(Login.upcoming_flights);
				rows = boarded_rows.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
				
				Login.boarded_flights = new JTable(rows,column);
				MixUtilities.TableDesinger(Login.boarded_flights);
				
				reader.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
}
