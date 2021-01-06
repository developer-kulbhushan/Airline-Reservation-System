package home;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.formdev.flatlaf.FlatLightLaf;

import Configuration.Configure;
import utilities.CreateConnection;

public class Run extends JFrame{

	private static final long serialVersionUID = 2737243441587538335L;
	static Thread configure;
	static Thread main;
	static Thread running,homepage,configuration;
	private ImageIcon loader;
	private JLabel bg;
	private Icon plane_icon;
	private JLabel plane;
	
	Run(){
		
		//Loading Images needed in frame
		loadImages();
		//Initializing components of frame
		intializeComponents();
		//Configuring JFrame
		configure();	
	}
	
	private void loadImages() {
		
		loader = new ImageIcon("res/run/loader.png");
		
	}
	
	private void intializeComponents() {	
		
		bg = new JLabel(loader);
		bg.setBounds(0,0,692,500);
		
		plane_icon = new ImageIcon("res/run/loader2.gif");
		plane = new JLabel(plane_icon);
		plane.setBounds(2,2,688,496);
		
		bg.add(plane);	
		add(bg);
	}
	
	private void configure() {
		
		setTitle("SARK");
		setSize(new Dimension(692,500));
		setUndecorated(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}	

	public static void main(String[] args) {
		
		//Setting Look & Feel to FlightLightLaf
		FlatLightLaf.install();
		
		//Creating object to show loader
		Run obj = new Run();
		
		System.out.println("checking configuration...");
		
		Configure.isConfigured();
		
		if(Configure.configured) {
			
			homepage = new Thread(new Homepage());
			System.out.println("configuration done !");
			System.out.println("starting application...");
			homepage.start();
			
		} else {
			
			configuration = new Thread(new Configure());
			JOptionPane.showMessageDialog(null, "Application must be configured first !");
			System.out.println("entering into configuration...");
			configuration.start();
			
		}
		
		//Closing Loader
		obj.dispose();
		
		while(!Configure.configured) {
			System.out.print("");
			continue;
		}
		
		if(homepage != null) {
			//Do Nothing
		} else {
			homepage = new Thread(new Homepage());	
			homepage.start();
		}
	}

}
