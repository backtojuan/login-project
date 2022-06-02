package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Felipe Sanchez
 * Java class in charge of connecting to the database.
 */

public class Connectionn {
	
	/**
	 * This method establishes and return a connection to a local postgres
	 * database
	 * @throws SQLException 
	 * @return An sql.Connection object
	 */
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


