package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DatabaseLoader {
	
	BufferedReader reader;
	Connection con;
	public static boolean tablesConfigured = false;
	
	public DatabaseLoader(){
		
		//Opening configuration file
		File file = new File("configuration/configure.xml");
		
		//XML Handling Components			
		DocumentBuilderFactory factory;
		DocumentBuilder builder;
		Document doc;
		
		try{
			
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
		        	 String url = "jdbc:mysql://"+"localhost"+":"+eElement.getElementsByTagName("port").item(0).getTextContent()+"/";
		        	 String username = eElement.getElementsByTagName("username").item(0).getTextContent();
		        	 String password =  eElement.getElementsByTagName("password").item(0).getTextContent();		 	        
		        	 Class.forName("com.mysql.cj.jdbc.Driver");
		             con = DriverManager.getConnection(url,username,password);
		         }
			}
			
            if(!con.isClosed()) {
            	System.out.println("Connection successfull !");
            }
            
            Statement stmt  = con.createStatement();
            
            reader = new BufferedReader(new FileReader("res/mySQL/database.sql"));
    		String line = null;
    		
    		System.out.println("Initializing Database...");
    		
    		// execute query line by line from database queries file
    		while ((line = reader.readLine()) != null) {  			
    			stmt.execute(line);
    		}
            
            System.out.println("Database Initialized !");
            
            tablesConfigured = true;
            
			} catch (Exception e){
			
             e.printStackTrace();
             
             } finally {
         		// close file reader
         		if (reader != null) {
         			try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
         		}
         	}
		}

	public static void main(String[] args) throws IOException, SQLException {
		
		new DatabaseLoader();
		
	}

}
