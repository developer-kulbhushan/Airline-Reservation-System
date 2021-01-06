package user;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import utilities.MixUtilities;

final public class Login extends JFrame{
	
	private static final long serialVersionUID = 1L;
	//JButton for left Panel
	private JButton myprofile,myflights,bookflight;
	private JButton sendfeedback,viewflights,logout;
	
	//Icons for left Panel
	private ImageIcon img,img1,img2,img3,img4,img5,img6;
	private ImageIcon img7,img8,img9,img10,img11,img12;
	
	private JPanel leftbar,rightbar;
	private CardLayout c1;
	private int userid;
	private Connection con;
	
	ButtonGroup travelClass;
	JScrollPane sp2;
	
	//For storing list of flights
	private ArrayList<ArrayList<String>> data;
	
	//Components for myFights Panel
	public static JTable upcoming_flights,boarded_flights;
	
	public Login(Connection con , int userid) {
		
		this.userid = userid;
		this.con = con;
		data = new ArrayList<ArrayList<String>>();
		
		img = new ImageIcon("res/user.Login/login_bg.png");		
		
		JLabel bg = new JLabel(img);
		bg.setBounds(0, 0, 800, 500);
		
		c1 = new CardLayout();
		
	    leftbar = leftPane();
		leftbar.setBounds(0, 0, 250, 500);
		
		rightbar = rightPane();
		rightbar.setBounds(250, 0, 550, 500);
		
		bg.add(leftbar);
		bg.add(rightbar);
		
		c1.show(rightbar, "myprofile");
		
		add(bg);
		
		leftPaneActions();
		
		rightPaneActions();
		
		configureJFrame();
	}
	
	// Left Pane of the login Panel
	public JPanel leftPane() {
		JPanel leftbar = new JPanel();
		leftbar.setOpaque(false);
		leftbar.setLayout(null);
		
		img1 = new ImageIcon("res/user.Login/myprofile.png");
		img2 = new ImageIcon("res/user.Login/myprofile_entered.png");
		img3 = new ImageIcon("res/user.Login/myflights.png");
		img4 = new ImageIcon("res/user.Login/myflights_entered.png");
		img5 = new ImageIcon("res/user.Login/bookflight.png");
		img6 = new ImageIcon("res/user.Login/bookflight_entered.png");
		img7 = new ImageIcon("res/user.Login/viewallflight.png");
		img8 = new ImageIcon("res/user.Login/viewallflight_entered.png");
		img9 = new ImageIcon("res/user.Login/sendfeedback.png");
		img10 = new ImageIcon("res/user.Login/sendfeedback_entered.png");
		img11 = new ImageIcon("res/user.Login/logout.png");
		img12 = new ImageIcon("res/user.Login/logout_entered.png");
		
		myprofile = new JButton(img1);
		myprofile.setContentAreaFilled(false);
		myprofile.setBounds(0, 150, 250, 35);
		myprofile.setBorder(null);
		
		myflights = new JButton(img3);
		myflights.setContentAreaFilled(false);
		myflights.setBounds(0, 185, 250, 35);
		myflights.setBorder(null);
		
		bookflight = new JButton(img5);
		bookflight.setContentAreaFilled(false);
		bookflight.setBounds(0, 220, 250, 35);
		bookflight.setBorder(null);
		
		viewflights = new JButton(img7);
		viewflights.setContentAreaFilled(false);
		viewflights.setBounds(0, 255, 250, 35);
		viewflights.setBorder(null);
		
		sendfeedback = new JButton(img9);
		sendfeedback.setContentAreaFilled(false);
		sendfeedback.setBounds(0, 290, 250, 35);
		sendfeedback.setBorder(null);
		
		logout = new JButton(img11);
		logout.setContentAreaFilled(false);
		logout.setBounds(0,325,250,35);
		logout.setBorder(null);
		
		leftbar.add(myprofile);
		leftbar.add(myflights);
		leftbar.add(bookflight);
		leftbar.add(viewflights);
		leftbar.add(sendfeedback);
		leftbar.add(logout);
		
		return leftbar;
	}
	
	
	// Panel For Flight Check
	public JPanel viewallFlights() {
		
		JTable flights;
		JPanel pnl = new JPanel();
		pnl.setLayout(null);
		
		getflightschedules();
		
		JLabel bg = new JLabel(new ImageIcon("res/user.Login/flights_bg.png"));
		
		bg.setBounds(0,0,550,500);
		
		String column[]={"Sr.No.","Flight_ID","Origin","Destination",
							"Departure","Arrival","Aircraft","Time",
							"Economic Cost","Business Cost"};
		String rows[][] = data.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		
		flights = new JTable(rows,column);
		MixUtilities.TableDesinger(flights);
		
		JScrollPane table = new JScrollPane(flights);
		table.setBounds(10,50,530,430);
		
		bg.add(table);
		pnl.add(bg);
		
		pnl.setOpaque(false);
		return pnl;
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
		
	
	
	// Right Pane of the Login Panel
	public JPanel rightPane() {
		
		JPanel rightbar = new JPanel();
		rightbar.setOpaque(false);
		rightbar.setLayout(c1);
		
		JPanel profile = new userProfile(userid);
		JPanel myflights =  new myFlights(userid);
		JPanel bookflight = new bookFlights(userid);
		JPanel viewallflights = viewallFlights();
		JPanel sendfeedback = new sendFeedback(userid);
				
		rightbar.add(profile,"myprofile");
		rightbar.add(myflights,"myflights");
		rightbar.add(bookflight,"bookflight");
		rightbar.add(viewallflights,"viewallflights");
		rightbar.add(sendfeedback,"sendfeedback");
		
		return rightbar;
	}
	
	// All the actions done by Left Pane buttons will be implemented here
	public void leftPaneActions() {
		
        myprofile.addMouseListener(new MouseAdapter() {
			
        	@Override
			public void mouseEntered(MouseEvent e) {
				myprofile.setIcon(img2);
			}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
        		myprofile.setIcon(img1);
        	}
        	
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		myprofile.setIcon(img2);
        		c1.show(rightbar, "myprofile");
        	}
			
		});
        
        myflights.addMouseListener(new MouseAdapter() {
			
        	@Override
			public void mouseEntered(MouseEvent e) {
				myflights.setIcon(img4);
			}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
        		myflights.setIcon(img3);
        	}
        	
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		myflights.setIcon(img4);
        		c1.show(rightbar, "myflights");
        	}
			
		});
        
        bookflight.addMouseListener(new MouseAdapter() {
			
        	@Override
			public void mouseEntered(MouseEvent e) {
        		bookflight.setIcon(img6);
			}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
        		bookflight.setIcon(img5);
        	}
			
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		bookflight.setIcon(img6);
        		c1.show(rightbar, "bookflight");
        	}
		});
        
        viewflights.addMouseListener(new MouseAdapter() {
			
        	@Override
			public void mouseEntered(MouseEvent e) {
        		viewflights.setIcon(img8);
			}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
        		viewflights.setIcon(img7);
        	}
        	
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		viewflights.setIcon(img8);
        		c1.show(rightbar, "viewallflights");
        	}
			
		});
        
        
        sendfeedback.addMouseListener(new MouseAdapter() {
			
        	@Override
			public void mouseEntered(MouseEvent e) {
        		sendfeedback.setIcon(img10);
			}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
        		sendfeedback.setIcon(img9);
        	}
        	
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		sendfeedback.setIcon(img10);
        		c1.show(rightbar, "sendfeedback");
        	}
			
		});
        
        logout.addMouseListener(new MouseAdapter() {
			
        	@Override
			public void mouseEntered(MouseEvent e) {
        		logout.setIcon(img12);
			}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
        		logout.setIcon(img11);
        	}
        	
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		int result = JOptionPane.showConfirmDialog(null, "Are you sure ?","Logout",JOptionPane.YES_NO_OPTION);
        		if(result == JOptionPane.YES_OPTION) {
        			System.out.println("Logout !");
        			close();
        		}
        		else {
        			System.out.println("Logout cancelled !");
        		}
        	}
			
		});
		
		}
	
	private void close() {
		dispose();
	}
	
	public void rightPaneActions() {
		// All the actions done by Left Pane buttons will be implemented here
		
		}
	
	private void configureJFrame() {
		
		setName("My Account");
		setSize(817,539);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
}
