package sample.Login_Signup;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.SceneChanger;
import sample.Server_Client.Session_Id;


public class Login {
        @FXML
        private JFXCheckBox passshow;

        @FXML
        private JFXTextField ID;

        @FXML
        private JFXTextField passtext;

        @FXML
        private JFXPasswordField password;

        @FXML
        private JFXButton submit;

        @FXML
        private Label rteach;

        @FXML
        private Label rstud;

        @FXML
        private Label inval;

        @FXML
        private ToggleGroup a;

        @FXML
        private JFXRadioButton stud;

        @FXML
        private JFXRadioButton teach;

        private SceneChanger changer = new SceneChanger();

        @FXML
        private void Login(ActionEvent event){
                String username = ID.getText();
                String pass;
                if(!passshow.isSelected())
                    pass =  password.getText();
                else
                    pass = passtext.getText();
                String type;
                if(teach.isSelected())
                        type="teacher";
                else
                        type="student";
                Main.user.sendString("Login");
                Main.user.sendString(username);
                Main.user.sendString(pass);
                Main.user.sendString(type);

                //Recieve if user exists or not
                boolean login= Main.user.recieveBoolean();
                if(login){
                        System.out.println("Login Succesful");
                        Session_Id.setID(username);
                        Session_Id.settype(type);
                        if(type.equals("student"))
                            changer.changeScene("Students/Sdash.fxml", event, "test");
                        else
                            changer.changeScene("Teachers/Tdash.fxml", event, "Teacher");
                } else
                {
                        inval.setOpacity(1);
                        System.out.println("Unsuccessful");
                }
        }



        @FXML
        private void SignupStd(MouseEvent click) {
                changer.changeScene("Login_Signup/SignupStudent.fxml", click, "Signup");
        }

        @FXML
        private void SignupTeach(MouseEvent click) {
                changer.changeScene("Login_Signup/SignupTeacher.fxml", click, "Signup");
        }
        @FXML
        private  void showPass(ActionEvent event){
                if(passshow.isSelected()){
                    String temp=password.getText();
                    passtext.setText(temp);
                    password.setDisable(true);
                    passtext.setDisable(false);
                    password.setOpacity(0);
                    passtext.setOpacity(1);
                }else{
                    String temp=passtext.getText();
                    password.setText(temp);
                    password.setDisable(false);
                    passtext.setDisable(true);
                    passtext.setOpacity(0);
                    password.setOpacity(1);
                }
        }
}