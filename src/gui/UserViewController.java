package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import model.Connectionn;
import model.PasswordGenerator;

public class UserViewController {
	
	@FXML
    private Button buttonSave;

    @FXML
    private ImageView imagePanel;

    @FXML
    private Label labelError;

    @FXML
    private TextField textFechaAccesp;

    @FXML
    private PasswordField textNewPassword;
    
    private Connection con;
    
    private PreparedStatement ps;
    
    private String user;
        
	/**
	 * This method initialize the user view
	 */
    @FXML
    private void initialize() {    	
    	labelError.setVisible(false);
    	textFechaAccesp.setText(new Date().toString());
    	connect();    	
    }
    
    /**
     * This method changes a user password
     * @param event event triggered when pressing the save button in the application
     */
    @FXML
    private void saveNewPassword(ActionEvent event) {
    	String consulta = "SELECT userpassword FROM users WHERE username = '"+user+"'";
    	
    	try {
    		ps = con.prepareStatement(consulta);
        	ResultSet rs = ps.executeQuery();
    		if (rs.next()) {
    			
    			String passBD = rs.getString("userpassword");
    			if(PasswordGenerator.validatePassword(textNewPassword.getText(), passBD) || textNewPassword.getText().equals("")) {
    				alert1();
    				textNewPassword.clear();
    			} else {
    				String passCifrada = PasswordGenerator.generateStrongPasswordHash(textNewPassword.getText());
    				String setPass = "Update users set userpassword = " + "'"+ passCifrada +"'" + " where username= '" + user + "'";
    	    		Statement st =con.createStatement();
    	    		alert2();
    	            st.execute(setPass);
    	            
        		}
    		}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * This method connects the application to the data base to get the current users that arent admins
     */
    private void connect() {
    	String consulta = "SELECT username FROM users WHERE userprivilege = 'false'";
    	try {
			con= Connectionn.getConnection();
			ps = con.prepareStatement(consulta);
			ResultSet rs = ps.executeQuery();			
			System.out.println(rs);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
    }
    
    /**
	 * This method creates and Alert that is triggered if the user tries to change their password using the same old password
	 */
    private void alert1() {
    	Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle("Information");
        info.setHeaderText(null);
        info.initStyle(StageStyle.UTILITY);
        info.setContentText("You cannot use your old password");
        info.show();
    }
    
    /**
	 * This method creates and Alert that is triggered when the password is succesfully changed
	 */
    private void alert2() {
    	Alert info = new Alert(AlertType.CONFIRMATION);
        info.setTitle("Information");
        info.setHeaderText(null);
        info.initStyle(StageStyle.UTILITY);
        info.setContentText("Your password was succesfully changed");
        info.show();
    }
    
    /**
     * This method receives the current user that is login in the system
     * @param user the user log in the current session
     */
    public void recibirUser(String user) {
    	System.out.println(user);
    	this.user = user;
    }
}