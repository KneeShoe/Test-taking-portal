package sample.Students;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import sample.Main;
import sample.SceneChanger;
import sample.Server_Client.Session_Id;

import java.util.ArrayList;

public class sInsideClass {
    @FXML
    private JFXButton back;

    @FXML
    private JFXListView<?> quizList;

    SceneChanger changer=new SceneChanger();

    public void initialize() {
        Main.user.sendString("getQnames");
        Main.user.sendInt(Session_Id.getClassId());
        ArrayList<String> names=(ArrayList<String>) Main.user.recieveObject();
        ArrayList a=new ArrayList();
        if(names.size()>0)
        {
            for(String test:names) {
                VBox vbox=new VBox();
                vbox.setPadding(new Insets(10, 10, 10, 10));
                Label lbl = new Label(test);
                lbl.setFont(new Font(20));
                vbox.getChildren().addAll(lbl);
                vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Session_Id.setQname(test);
                        Main.user.sendString("checkTest");
                        Main.user.sendString(test);
                        Main.user.sendString(Session_Id.getID());
                        if(Main.user.recieveBoolean())
                            changer.changeScene("Teachers/leaderboard.fxml",event,test);
                        else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Warning");
                            alert.setHeaderText("Warning");
                            alert.setContentText("You can only take a test once!!");
                            alert.showAndWait();
                            changer.changeScene("Students/takeQuiz.fxml", event, test);
                        }
                    }
                });
                a.add(vbox);
            }
            quizList.setItems(FXCollections.observableArrayList(a));
        }
    }


    @FXML
    void back(ActionEvent event) {
        changer.changeScene("Login_Signup/Login.fxml",event,"Login");
    }
}
