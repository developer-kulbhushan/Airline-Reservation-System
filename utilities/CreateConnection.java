package utilities;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CreateConnection {
	
	public static Connection con;

	// This function create connection to mySQL server
	public static void createConnection() {
		
		//Opening configuration file
		File file = new File("configuration/configure.xml");
		
		//XML Handling Components
		DocumentBuilderFactory factory;
		DocumentBuilder builder;
		Document doc;
		
		try {
			
			//Creating Document Builder to work with XML File
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			doc = builder.parse(file);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("database");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				 Node nNode = nList.item(temp);
		         
		         if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		        	 Element eElement = (Element) nNode;
		        	 String url = "jdbc:mysql://"+"localhost"+":"+eElement.getElementsByTagName("port").item(0).getTextContent()+"/sark";
		        	 String username = eElement.getElementsByTagName("username").item(0).getTextContent();
		        	 String password =  eElement.getElementsByTagName("password").item(0).getTextContent();
		        	 
		        	 System.out.println("connecting database server...");
		 	        
		        	 Class.forName("com.mysql.cj.jdbc.Driver");
		             con = DriverManager.getConnection(url,username,password);
		         }
			}
            
			System.out.println("database server connected !");
            
            } catch (com.mysql.cj.jdbc.exceptions.CommunicationsException e) {
            	System.out.println("Couldn't connect to database!\n"
            			+ "Please ensure following things :-\n"
            			+ "1. MySQL service is running.\n"
            			+ "2. URL and Port number for mySQL server are configured correctly.");
            	System.exit(JFrame.DO_NOTHING_ON_CLOSE);
			
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
    		System.exit(JFrame.DO_NOTHING_ON_CLOSE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//Function for closing MySQL Server Connection
	public static void close() {
		try {
			con.close();
			if(con.isClosed()) {
				System.out.println("database server disconnected !");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
