package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import model.Connectionn;

public class AdminViewController {

	public static final String PATH = "img/admin.png";

	@FXML
	private Button buttonDelete;

	@FXML
	private Button buttonSetPassword;

	@FXML
	private ComboBox<String> comboBoxUsers;

	@FXML
	private ImageView imageAdmi;

	private Connection con;

	private PreparedStatement ps;

	/**
	 * This method initialize the user view
	 */
	@FXML
	private void initialize() {
		connect();   	
	}

	/**
	 * This method allows admins to delete users from the system 
	 * @param event event triggered when pressing the delete button
	 */
	@FXML
	private void deleteUser(ActionEvent event) {
		try {
			if(comboBoxUsers.getValue().equals("")) {
				throw new NullPointerException();
			}
			String delete = "DELETE FROM users WHERE username = '" + comboBoxUsers.getValue() + "'";
			Statement st =con.createStatement();
			st.execute(delete);
			updateCombo();
		} catch (NullPointerException npe){
			alert();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method allows admins to set a user password to a blank-empty password 
	 * @param event event triggered when pressing the set password button
	 */
	@FXML
	void setUserPassword(ActionEvent event) {
		try {
			if(comboBoxUsers.getValue().equals("")) {
				throw new NullPointerException();
			}
			String setPass = "Update users set userpassword = " + "''" + " where username= '" + comboBoxUsers.getValue() + "'";
			Statement st =con.createStatement();
			st.execute(setPass);
		} catch (NullPointerException npe){
			alert();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method creates and Alert that is triggered if the admin user tries to perform a task without previously selecting a user
	 */
	private void alert() {
		Alert info = new Alert(AlertType.INFORMATION);
		info.setTitle("Information");
		info.setHeaderText(null);
		info.initStyle(StageStyle.UTILITY);
		info.setContentText("No ha escogido un usuario");
		info.show();
	}

	/**
     * This method connects the application to the data base to get the current users that are admins
     */
	private void connect() {
		String consulta = "SELECT username FROM users WHERE userprivilege = 'false'";
		comboBoxUsers.getItems().clear();
		try {
			con= Connectionn.getConnection();
			ps = con.prepareStatement(consulta);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String user = rs.getString("username");
				comboBoxUsers.getItems().add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method updates the user list when a user is deleted from the system
	 * 
	 * @throws SQLException
	 */
	private void updateCombo() {
		String consulta = "SELECT username FROM users WHERE privilege = 'true'";
		comboBoxUsers.getItems().clear();
		try {
			ps = con.prepareStatement(consulta);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String user = rs.getString("username");
				comboBoxUsers.getItems().add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}