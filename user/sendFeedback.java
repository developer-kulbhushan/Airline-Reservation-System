package user;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import utilities.CreateConnection;

public class sendFeedback extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = -4503035880494701074L;
	private JLabel bg;
	private JScrollPane sp;
	private JButton sendfeedback;
	JTextArea message;
	private int userid;
	private JButton refresh;
	
	public sendFeedback(int userid) {
		
		this.userid = userid;
		
		configureJPanel();
		
		intializeComponents();
		
	}
	public void configureJPanel() {
		
		setLayout(null);
		
	}
	
	public void intializeComponents() {
		
		bg = new JLabel(new ImageIcon("res/user.Login/sendfeedback_bg.png"));
		bg.setBounds(0,0,550,500);
		
		sp = new JScrollPane(Login.boarded_flights);
		sp.setBounds(10,60,530,300);
		
		sendfeedback = new JButton("Give Feedback");
		sendfeedback.setBounds(175,380,200,40);
		sendfeedback.addMouseListener(this);
		
		refresh = new JButton("Refresh");
		refresh.setBounds(200,430,150,40);
		refresh.addMouseListener(this);
		
		bg.add(sendfeedback);
		bg.add(refresh);
		bg.add(sp);
		
		add(bg);
		
	}
	
	public JPanel sendFeedbackPanel() {
		JPanel pnl = new JPanel();
		pnl.setPreferredSize(new Dimension(300,300));
		pnl.setLayout(null);
		
		JLabel bg = new JLabel(new ImageIcon("res/user.Login/message.png"));
		bg.setBounds(0,0,300,300);
		
		message = new JTextArea();
		message.setOpaque(false);
		message.setBounds(2,2,298,298);
		
		bg.add(message);
		pnl.add(bg);
		return pnl;
	}
	
	private void refresh() {
		new myFlights(userid).myFlightsSorter();
		sp.setViewportView(Login.boarded_flights);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getSource() == sendfeedback) {
			if(Login.boarded_flights.getSelectedRow() == -1) {
				return;
			}
			int result = JOptionPane.showConfirmDialog(this,
										  sendFeedbackPanel(),
										  "Message",
										  JOptionPane.OK_CANCEL_OPTION,
										  JOptionPane.PLAIN_MESSAGE);
			if(result == JOptionPane.OK_OPTION) {
				if(message.getText().length() > 200 || message.getText().length() < 20) {
					JOptionPane.showMessageDialog(this, "Letters range must be 20 - 200");
					return;
				} else {
					String query;
					query = "insert into feedbacks (userid,flight_id,message,isread) values(?,?,?,?)";
					try {
						PreparedStatement st = CreateConnection.con.prepareStatement(query);
						
						st.setInt(1, userid);
						st.setString(2, (String) Login.boarded_flights.getValueAt(Login.boarded_flights.getSelectedRow(), 0));
						st.setString(3, message.getText());
						st.setBoolean(4, false);
						st.executeUpdate();
						
						JOptionPane.showMessageDialog(this, "Feedback Submitted !");
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
			else {
				//Do Nothing
			}
		} else if (e.getSource() ==  refresh) {
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
