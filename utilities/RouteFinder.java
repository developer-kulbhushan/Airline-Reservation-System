package utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JTable;

public class RouteFinder {
	
	private Connection con;
	private String destination,origin,Class;
	private ArrayList<ArrayList<String>> rows;
	private ArrayList<String> columns;
	private Statement st;
	private int seats;
	private String[] routeCities;
	
	//String for storing routes
	private String route;
	
	//Set for storing flight_IDs
	private HashSet<String> set;
	
	//HashMap for storing origin-destination pair in a particular route
	private HashMap<String,String> map;
	
	public RouteFinder(Connection con) {
		this.con =  con;
	}
	
	public void initComponents(String origin,String destination,String Class, int seats) {

		this.origin = origin;
		this.destination = destination;
		this.Class = Class;
		this.seats = seats;
		
		set = new HashSet<>();
		map = new HashMap<>();
		columns = new ArrayList<String>();
		rows = new ArrayList<ArrayList<String>>();
		
		route = origin;
		
		columns.add("Flight ID");
		columns.add("Source");
		columns.add("Destination");
		columns.add("Departure");
		columns.add("Arrival");
		columns.add("Stops");
		columns.add("Cost");
		
	}
	
	public JTable findRoots(String origin,String destination,String Class, int seats) {
		
		initComponents(origin,destination,Class,seats);
		
		int cost ;
		String srno;
		
		try {
			
			st = con.createStatement();
			
			//finding all flights_ID Which have selected origin
			ResultSet rs = st.executeQuery("select flight_id from flights"
					+ " where origin like '"+ origin+"'");
			
			//Making list of all Flights_ID returned By above query
			while(rs.next()) {
				set.add(rs.getString(1));
			}
			
			//Looping through each Flight_id for finding route
			//Between selected origin and destination
			for(String i : set) {
				
				//Getting all flights of current flight_id (i)
				rs =  st.executeQuery("select origin,destination from flights"
						+ " where flight_id like '" + i +"'");
				
				//Creating HashMap of key(origin) - value(Destination) pair
				while(rs.next()) {
					map.put(rs.getString("origin"), rs.getString("destination"));
				}
				
				//Checking if selected destination is available in HashMap Destinations
				if(!map.containsValue(destination)) {
					continue;
				}
				
				//Finding path between origin and destination
				while(true) {
					if(!map.containsKey(origin)) {
						route = this.origin;
						break;
					}
					if(map.get(origin).equals(destination)) {
						route += ":";
						route += destination;
						break;
					}
					else {
						route += ":";
						route += map.get(origin);
						origin = map.get(origin);
					}
				}
				
				//Checking if no path is found
				if(route.equals(this.origin)) {
					
					origin = this.origin;
					map.clear();
					
					continue;
				}
				else {
					String arrival,departure;
							
					routeCities = route.split(":");
					
					cost = costCalculation(routeCities,i);
					
					rs = st.executeQuery("select departure from flights "
							+ "where flight_id like '"+i+"'"
									+ "AND origin like '"+this.origin+"'");
					
					rs.next();
					departure = rs.getString(1);
					
					rs = st.executeQuery("select arrival from flights "
							+ "where flight_id like '"+i+"'"
							+ "AND destination like '"+this.destination+"'");
					
					rs.next();
					arrival = rs.getString(1);
					
					rows.add(new ArrayList<String>());
					rows.get(rows.size()-1).add(i);
					rows.get(rows.size()-1).add(this.origin);
					rows.get(rows.size()-1).add(this.destination);
					rows.get(rows.size()-1).add(departure);
					rows.get(rows.size()-1).add(arrival);
					rows.get(rows.size()-1).add(String.valueOf(routeCities.length-2));
					rows.get(rows.size()-1).add(String.valueOf(cost));
					
				}
				
				origin = this.origin;
				route = this.origin;
				map.clear();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Object[][] rows = this.rows.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		Object[] columns =this.columns.toArray();
		
		JTable table = new JTable(rows,columns);
		
		return table;
	}
	
	int costCalculation(String[] route,String flightid) {
		
		int cost = 0;
		ResultSet rs;
		for(int i = 0 ; i < route.length - 1; i++) {
			String origin = route[i];
			String destination = route[i+1];
			
			try {
				
				if(Class == "Business") {
					rs =  st.executeQuery("select business_cost from flights "
										+ "where origin like '"+origin+"' "
										+ "AND destination like '"+destination+"' "
											+ "and flight_id like '"+flightid+"'");
				}
				else {
					rs =  st.executeQuery("select economy_cost from flights "
										+ "where origin like '"+origin+"' "
										+ "AND destination like '"+destination+"' "
										+ "and flight_id like '"+flightid+"'");
				}
				rs.next();
				cost += rs.getInt(1);
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		cost *= seats;
		
		return cost;
	}

}
