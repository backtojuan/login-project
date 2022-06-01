package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectionn {

    public static Connection getConnection() {
    	
    	String url = "jdbc:postgresql://localhost:5432/sec";
    	String user = "postgres";
	    String password = "12345";
		try {
			
			Connection con = DriverManager.getConnection(url, user, password);
		    return con;
		    
		} catch (SQLException ex) {	
			ex.printStackTrace();
			return null;
		}
	}
    	
 }


