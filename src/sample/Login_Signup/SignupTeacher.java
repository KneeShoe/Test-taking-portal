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
import sample.Server_Client.ConnectionClass;
import sample.Server_Client.PasswordUtils;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;

public class SignupTeacher {

    @FXML
    private JFXButton back;

    @FXML
    private JFXTextField dept;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField fid;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField regkey;

    @FXML
    private JFXPasswordField cpass;

    @FXML
    private Button submit;

    SceneChanger changer=new SceneChanger();

    @FXML
    public void TsignUp(ActionEvent event) throws SQLException, IOException, InvalidKeySpecException {
        if(regkey.getText().equals("111213")){
            //Signs Up new Teachers
            if ((password.getText()).equals(cpass.getText())){
                //If both the Confirm Pasasword And Password Match
                //Creates a Object of Password Util and then generates Encrypted Password
                PasswordUtils pass= new PasswordUtils();
                String salt= pass.getSalt(30);
                String securePass= pass.generateSecurePassword(password.getText(), salt);
                //Puts the Enter Information into Database
                Main.user.sendString("TSignup");
                Main.user.sendString(name.getText());
                Main.user.sendString(email.getText());
                Main.user.sendString(fid.getText());
                Main.user.sendString(securePass);
                Main.user.sendString(salt);
                Main.user.sendString(dept.getText());
                Boolean SignUp=Main.user.recieveBoolean();
                if(SignUp){
                    //Displays Alert for Sucess of Registeration
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Success");
                    alert.setContentText("You have been registered");
                    alert.showAndWait();
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
                //Shows Alert of Password Mismatch
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Password Mismatch");
                alert.setHeaderText("Error:");
                alert.setContentText("Passwords Do not match");
                alert.showAndWait();
            }
        }
        else{
            //Shows Alert of Password Mismatch
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration Key Mismatch");
            alert.setHeaderText("Error:");
            alert.setContentText("Registration Key is not correct!");
            alert.showAndWait();
        }
    }

    @FXML
    void back(ActionEvent eventa) {
        changer.changeScene("Login_Signup/Login.fxml",eventa,"Login");
    }
}
