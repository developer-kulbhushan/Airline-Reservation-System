package home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import user.Login;
import utilities.CreateConnection;
import utilities.PlaceholderTextField;

final public class LoginPanel extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField email;
	private JPasswordField password;
	private JButton login,signup,forgot;
	private Font f;
	private JLabel bg;
	private int x,y;
	private JPanel titlebar;
	private ImageIcon background;
	private Connection con;
	private int userid;
	private Border matte;
	
	public LoginPanel() {
		
		f = new Font("Serif",Font.PLAIN,15);
		new ImageIcon("src/close.png");
		background = new ImageIcon("res/LoginPanel/loginpanel_bg.png");
		
		matte = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.white);	
		this.con = CreateConnection.con;
		
		//For intializing all components of JFrame
		intializeComponents();
		
		//Adding functions to buttons(login..etc)
		addFunctionstoButtons();
		
		//Adding components to JFrame
		addComponents();
		
		//Configuring the JFrame
		configureFrame();
		
	}
	
	
	private void intializeComponents() {
		
		// Email TextField
		email = new JTextField();
		email.setOpaque(false);
		email.setBounds(20, 90, 260, 45);
		email.setFont(f);
		email.setForeground(Color.white);
		email.setBorder(BorderFactory.createTitledBorder(matte, "Email",0,0,null,Color.white));
				
		// Password TextField
		password = new JPasswordField();
		password.setBounds(20, 150, 260, 45);
		password.setFont(f);
		password.setEchoChar('*');
		password.setOpaque(false);
		password.setForeground(Color.white);
		password.setBorder(BorderFactory.createTitledBorder(matte, "Password",0,0,null,Color.white));
				
		// Login Button
		login = new JButton(new ImageIcon("res/LoginPanel/login.png"));
		login.setBounds(25, 220, 250, 40);
		login.setForeground(Color.WHITE);
		login.setContentAreaFilled(false);
		
		// Forgot Password button
		forgot = new JButton("Forgot password ?");
		forgot.setForeground(Color.WHITE);
		forgot.setFont(new Font("Serif",Font.CENTER_BASELINE,15));
		forgot.setBounds(75, 270, 150, 25);
		forgot.setContentAreaFilled(false);
		forgot.setBorder(null);
				
		// Sign UP button
		signup = new JButton(new ImageIcon("res/LoginPanel/createaccount.png"));
		signup.setForeground(Color.WHITE);
		signup.setBounds(25, 310, 250, 40);
		signup.setContentAreaFilled(false);
		
		bg = new JLabel(background);
		bg.setBounds(0, 0, 300, 380);
		
		titlebar = titlebar();
		titlebar.setBounds(0, 0, 300, 30);
	}
	
	//Adding functionality to buttons
	private void addFunctionstoButtons() {
		
		login.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				//Vaildation() returns 1 
				//if fields are filled correctly
				int result = validation();
				
				try {
					if(result == 1) {
						//Verification() returns 1 
						//if email and password matches
						result = verification();
						if(result == 1) {
							new Login(con,userid);
							close();
						} else if (result == 2) {
							new admin.Login(con);
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.exit(DO_NOTHING_ON_CLOSE);
				}
			}
		});
		
		
		signup.addActionListener(new ActionListener(){  
			 public void actionPerformed(ActionEvent e){
				 new SignUpPanel();
				 close();
				 
			 }
		});
		
	}
	
	
	private void addComponents() {
		
		//Adding all components to JLabel
		
		bg.add(email);
		bg.add(password);
		bg.add(login);
		bg.add(forgot);
		bg.add(signup);
		bg.add(titlebar);
		
		//Adding JLabel to Frame
		add(bg);
		
	}
	
	
	private void configureFrame() {
		
		setSize(new Dimension(300,380));
		setName("LoginPanel");
		setLayout(null);
		setBackground(new Color(255,255,255));
		setLocationRelativeTo(null);
		setUndecorated(true);
		setVisible(true);
		
	}
	
	//Function for verifying user entered details
	private int verification() throws SQLException {
		
		Statement stmt =  con.createStatement();
		String query = "select * from contactdetails where email = '" + email.getText()+"'";
		ResultSet rs = stmt.executeQuery(query);
		
		if(rs.next()) {
			userid = rs.getInt("user_id");
		}
		else {
			JOptionPane.showMessageDialog(null, "Invalid E-mail");
			return 0;
		}
		
		if(rs.getString("password").equals(String.valueOf(password.getPassword()))) {
			
			query = "select isAdmin from users where user_id like '"+userid+"'";
			
			rs= stmt.executeQuery(query);
			rs.next();
			
			if(rs.getBoolean(1)) {
				System.out.println("Admin Login");
				return 2;
			} else {
				System.out.println("User Login");
				return 1;
			}
		}
		else {
			System.out.println("Password Not Matched !");
			return 0;
		}
	}
	
	
	private int validation() {
		String error = "";
		
		if(email.getText().isEmpty()) {
			error += "e-Mail can't be empty !\n";
		}
		if(String.valueOf(password.getPassword()).isEmpty()) {
			error += "Password can't be empty !\n";
		}
		
		if(error.isEmpty()) {
			return 1;
		}
		else {
			JOptionPane.showMessageDialog(null,error);
			return 0;
		}
		
	}
	
	//For desining titlebar
	private JPanel titlebar() {
		JPanel pnl =  new JPanel();
		pnl.setLayout(null);
		
		pnl.setPreferredSize(new Dimension(300,30));
		pnl.setOpaque(false);
		
		// For enabling repositioning Frame by
		// Dragging it from title bar
		pnl.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}
		});
		
		pnl.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				int xx = e.getXOnScreen();
				int yy = e.getYOnScreen();
				setLocation(xx- x,yy- y);
			}
		});
		
		//Button for closing window
		JButton close = new JButton();
		close.setContentAreaFilled(false);
		close.setBorder(null);
		close.setBounds(265, -1, 40, 40);
		close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				close();
			}
		});
		
		pnl.add(close);
		
		return pnl;
	}
	
	
	
	private void close() {
		dispose();
	}
	
}
