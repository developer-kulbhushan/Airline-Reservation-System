package admin;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import utilities.MixUtilities;

public class Users extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 8825827581660417262L;
	private Connection con;
	private ImageIcon users_bg;
	private JLabel bg;
	private JButton back,refresh,remove;
	private JScrollPane table;
	private JTable usersList;
	
	public Users(Connection con) {
		
		this.con = con;
		
		imageLoading();
		
		getusersinformation();
		
		intializeComonents();
		
		addComponents();
		
		setButtonActions();
		
		configurePanel();
	}
	
	private void imageLoading() {
		
		users_bg = new ImageIcon("res/admin/users/users_bg.png");
		
	}
	
	//Getting information from database
	private void getusersinformation() {
		
		ResultSet rs;
		String query = "select * from users";
		
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		
		try {
			
			Statement st = con.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				list.add(new ArrayList<String>());
				int size = list.size() - 1;
				
				//User ID
				list.get(size).add(String.valueOf(rs.getInt(1)));
				//Name = Title + Firstname + lastname
				list.get(size).add(rs.getString(6)+" "+rs.getString(2)+" "+rs.getString(3));
				//Father name = Father Firstname + Father Lastname
				list.get(size).add(rs.getString(7) + " " +rs.getString(8));
				//Date
				list.get(size).add(String.valueOf(rs.getDate(4)));
				//Address
				list.get(size).add(rs.getString(14)+", "+rs.getString(12)+", "+
				rs.getString(11)+", "+rs.getString(10)+" "+ rs.getString(13));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String columns[] = {"User ID","Name","Father Name","DOB","Address"};		
		String rows[][] = list.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		
		usersList = new JTable(rows,columns);
		MixUtilities.TableDesinger(usersList);
		
	}
	
	private void intializeComonents() {
		
		bg = new JLabel(users_bg);
		bg.setBounds(0,0,800,600);
		
		back = new JButton(new ImageIcon("res/admin/back.png"));
		back.setBounds(10,10,40,25);
		
		table = new JScrollPane(usersList);
		table.setBounds(20,70,760,400);
		
		remove = new JButton("Remove");
		remove.setBounds(275,480,100,40);
		
		refresh = new JButton("Refresh");
		refresh.setBounds(425,480,100,40);
		
		remove.addMouseListener(this);
		refresh.addMouseListener(this);
	}
	
	private void addComponents() {
		
		bg.add(back);
		bg.add(table);
		bg.add(remove);
		bg.add(refresh);
		add(bg);
		
	}
	
	//Removing row
	private void remove() {
		
		//Checking if no row is selected
		if(usersList.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "No row is selected !");
			return;
		}
		
		//getting userID of selected row
		String serial_number = (String) usersList.getValueAt(usersList.getSelectedRow(), 0);
		
		//Converting String userID to Integer userID
		int userid = Integer.parseInt(serial_number);
		
		String query4 = "delete from users where user_id = "+userid;
		String query2 = "delete from contactdetails where user_id = "+userid;
		String query1 = "delete from feedbacks where userid = "+userid;
		String query3 = "delete from bookedflights where user_id = "+userid;
		
		try {
			Statement st = con.createStatement();
			
			//Deleting row from feedbacks
			st.executeUpdate(query1);
			//Deleting row from contactDetails
			st.executeUpdate(query2);
			//Deleting row from bookedflights
			st.executeUpdate(query3);
			//Deleting row from Users
			st.executeUpdate(query4);
			
			JOptionPane.showMessageDialog(this, "Row Deleted Successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//Refreshing Tables
	private void refresh() {
		
		getusersinformation();
		table.setViewportView(usersList);
		
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

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getSource() ==  remove) {
			remove();
			refresh();
		} else if (e.getSource() == refresh) {
			refresh();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//Do Nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//Do Nothing
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
