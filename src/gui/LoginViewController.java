package gui;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;
import model.Connectionn;
import model.PasswordGenerator;

/**
 * Controller Class for the Login View
 * @author Juan José Valencia
 *
 */
public class LoginViewController {

	@FXML
	private Label advertenciaText;

	@FXML
	private Button loginBtn;

	@FXML
	private PasswordField passText;

	@FXML
	private TextField userText;

	/**
	 * This method initialize the login view
	 */
	@FXML
	private void initialize() {
		advertenciaText.setVisible(false);
	}
	
	/**
	 * This method logs in the user in the platform
	 * @param event event triggered when pressing the login button in the application
	 */
	@FXML
	private void tryLogin(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {

		String user = userText.getText();
		String pass = passText.getText();

		try {
			System.out.println(PasswordGenerator.generateStrongPasswordHash("12345"));
		} catch (NoSuchAlgorithmException e) {			
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		String consulta = "SELECT username, userpassword, userprivilege FROM users WHERE username = '"+user+"'";

		try {

			Connection con= Connectionn.getConnection();

			PreparedStatement ps = con.prepareStatement(consulta); 
			ResultSet rs =ps.executeQuery();

			System.out.println(rs);

			//Check if the user exists in the database
			if(rs.next()) {

				String userBD = rs.getString("username"); 
				String passBD = rs.getString("userpassword"); 
				if(passBD.equals("")) { 
					rs.close(); ps.close();
					con.close(); 
					throw new NullPointerException(); 
				} 
				String privi = rs.getString("userprivilege");
				System.out.println(privi);
				if (PasswordGenerator.validatePassword(pass, passBD)) { 
					rs.close();
					ps.close(); 
					con.close(); 
					if (privi.equals("t")) {
						nextToStageAdmin();
					}else {

						nextToStageUser(userBD);
					}

				}else {
					advertenciaText.setText("Wrong Password"); advertenciaText.setVisible(true);
					rs.close(); ps.close(); con.close(); }

			}else {
				advertenciaText.setVisible(true);
			}
		} catch (NullPointerException n) {
			System.out.println("here");
			alert();
		}
	}
	
	/**
	 * This method creates and Alert that is triggered if the admin has eliminated an user's passwords
	 */
	private void alert() {
		Alert info = new Alert(AlertType.INFORMATION);
		info.setTitle("Information");
		info.setHeaderText(null);
		info.initStyle(StageStyle.UTILITY);
		info.setContentText("La contraseña fue eliminada por el administrador");
		info.show();
	}
	
	/**
	 * This method launches the admin view in case an admin user successfully logs in
	 * @throws IOException 
	 */
	private void nextToStageAdmin() {
		try {
			FXMLLoader loader= new FXMLLoader(getClass().getResource("admiDashBoard.fxml"));
			Parent root=loader.load();
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method launches the user view in case a normal user successfully logs in
	 * @param user UserName of the user that just logs in the system
	 * @throws IOException
	 */
	private void nextToStageUser(String user) {
		try {
			FXMLLoader loader= new FXMLLoader(getClass().getResource("usersDashBoard.fxml"));
			Parent root=loader.load();
			UserViewController scc= (UserViewController) loader.getController();         
			scc.recibirUser(user);
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}