package admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Feedback extends JPanel {

	private static final long serialVersionUID = -3352306658325084367L;
	private Connection con;
	private JButton back;
	private ImageIcon feedback_bg;
	private JLabel bg;
	private JLabel feedbacks;	
	private JScrollPane sp;
	
	Feedback(Connection con){
		this.con = con;
		
		imageLoading();
		
		getFeedbacks();
		
		intializeComonents();
		
		addComponents();
		
		setButtonActions();
		
		configurePanel();
		
	}
	
	private void imageLoading() {
		
		feedback_bg = new ImageIcon("res/admin/feedback/feedback_bg.png");
	}
	
	private void getFeedbacks() {
		
		feedbacks = new JLabel(new ImageIcon());
		feedbacks.setLayout(null);
		feedbacks.setOpaque(false);
		
		ResultSet rs;
		int size;
		try {
			Statement st = con.createStatement();
			
			rs = st.executeQuery("select count(*) from feedbacks");
			rs.next();
			size = rs.getInt(1);
			
			feedbacks.setPreferredSize(new Dimension(740,110*size+10));
			
			int x = 20;
			int y = 10;
			
			rs = st.executeQuery("select * from feedbacks");
			
			
			while(rs.next()) {
				
				JPanel pnl =  feedbacks(rs.getInt(2),rs.getInt(1));
				pnl.setBounds(x,y,720,100);
				feedbacks.add(pnl);
				y = y + 110;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void intializeComonents() {
		
		bg = new JLabel(feedback_bg);
		bg.setBounds(0,0,800,600);
		
		back = new JButton(new ImageIcon("res/admin/back.png"));
		back.setBounds(10,10,40,25);
		
		sp = new JScrollPane(feedbacks);
		sp.setBounds(20,70,760,450);
	}
	
	private void addComponents() {
		
		bg.add(back);
		bg.add(sp);
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
	
	private JPanel feedbacks(int userid , int feedbackid) {
		
		JPanel pnl = new JPanel();
		pnl.setPreferredSize(new Dimension(720,100));
		pnl.setLayout(null);
		JLabel name;
		JLabel flight;
		JLabel message;

		JLabel bg = new JLabel(new ImageIcon("res/admin/feedback/message.png"));
		bg.setBounds(0,0,720,100);
		
		try {
			Statement st = con.createStatement();
			
			//Getting name from feedbacks table
			ResultSet rs = st.executeQuery("select first_name from users where user_id = "+userid);
			
			if(rs.next()) {
				name = new JLabel(rs.getString(1));
				name.setBounds(70,0,100,30);
				name.setForeground(Color.white);
				bg.add(name);
			}
			
			//Getting Message from feedbacks table
			rs = st.executeQuery("select message from feedbacks where feedbackid = "+feedbackid);
			
			if(rs.next()) {
				message = new JLabel(rs.getString(1));
				message.setBounds(10,15,700,50);
				bg.add(message);
			}
			
			//Getting Flight ID from feedbacks table
			rs = st.executeQuery("select flight_id from feedbacks where feedbackid = "+feedbackid);
			
			if(rs.next()) {
				flight = new JLabel(rs.getString(1));
				flight.setBounds(300,0,100,30);
				flight.setForeground(Color.WHITE);
				bg.add(flight);
			}			
			
			pnl.add(bg);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pnl;
	}
	
	private void configurePanel() {
		
		setPreferredSize(new Dimension(800,600));
		setLayout(null);
		
	}

}
