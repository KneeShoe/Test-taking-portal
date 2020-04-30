package sample.Login_Signup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Main;
import sample.SceneChanger;
import sample.Server_Client.PasswordUtils;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class SignupStudent {

    @FXML
    private JFXButton back;

    @FXML
    private JFXTextField sem;

    @FXML
    private JFXTextField usn;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField sec;

    @FXML
    private Button submit;

    @FXML
    private JFXPasswordField cpass;

    @FXML
    private JFXTextField email;

    SceneChanger changer=new SceneChanger();

    @FXML
    void SSignUp(ActionEvent event) throws SQLException, IOException, InvalidKeySpecException {

        //Action of the Button Signup to Register the User
        System.out.println("out");
        if ((pass.getText()).equals(cpass.getText())){// IF passwod and confirm password fields match
            System.out.println("sjdajd");
            PasswordUtils pas= new PasswordUtils();
            //Gets the salt required for hashing
            String salt= pas.getSalt(30);
            String securePass= pas.generateSecurePassword(pass.getText(), salt);// Hashes the password using the salt generated
            Main.user.sendString("SSignup");
            Main.user.sendString(name.getText());
            Main.user.sendString(email.getText());
            Main.user.sendString(usn.getText());
            Main.user.sendString(sem.getText());
            Main.user.sendString(sec.getText());
            Main.user.sendString(securePass);
            Main.user.sendString(salt);
            Boolean SignUp=Main.user.recieveBoolean();
            if(SignUp){
                //creates dialog box to show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Success");
                alert.setContentText("You have been registered");
                alert.showAndWait();
                //Goes back to login page
                Parent home_parent= FXMLLoader.load(getClass().getResource("Login.fxml"));
                Scene Home= new Scene(home_parent);

                Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
                window.setScene(Home);
                window.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Failure");
                alert.setHeaderText("Failure");
                alert.setContentText("Registration Failed");
                alert.showAndWait();
            }

        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password Mismatch");
            alert.setHeaderText("Error:");
            alert.setContentText("Passwords Do not match");

            alert.showAndWait();
        }

    }

    @FXML
    void back(ActionEvent event) {
        changer.changeScene("Login_Signup/Login.fxml",event,"Login");
    }
}
