package gui;

import model.PasswordGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author Juan José Valencia
 * Application Main Class
 */
public class Main extends Application{
	
	public static Stage stage;
	
	/**
	 * Main Method.
	 * Prints ciphered passwords of some users.
	 * @param args
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		System.out.println(PasswordGenerator.generateStrongPasswordHash("123456"));
        System.out.println(PasswordGenerator.generateStrongPasswordHash("654321"));
        System.out.println(PasswordGenerator.generateStrongPasswordHash("123123"));
        System.out.println(PasswordGenerator.generateStrongPasswordHash("456456"));
		
		launch(args);
	}

	/**
	 * Method that starts the login window
	 * @param stage
	 */
	@Override
	public void start(Stage stage) throws Exception {
    	Main.stage= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("LoginViewController.fxml"));

        Scene scene = new Scene(root);
        Main.stage.setTitle("Login Application");
        Main.stage.setScene(scene);
        Main.stage.show();		
	}
}