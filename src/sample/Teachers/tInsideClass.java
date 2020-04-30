package sample.Teachers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import sample.Main;
import sample.Misc.Classes;
import sample.SceneChanger;
import sample.Server_Client.Session_Id;

import javax.security.auth.Subject;
import java.util.ArrayList;

public class tInsideClass {
    @FXML
    private JFXListView<?> quizList;

    @FXML
    private JFXButton cQuiz;

    @FXML
    private JFXTextField tQn;

    @FXML
    private JFXTextField dQn;

    @FXML
    private JFXTextField Qname;

    @FXML
    private JFXButton back;

    SceneChanger changer=new SceneChanger();

    public void initialize(){
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
                        changer.changeScene("Teachers/leaderboard.fxml",event,test);
                    }
                });
                a.add(vbox);
            }
            quizList.setItems(FXCollections.observableArrayList(a));
        }
    }

    @FXML
    void createQuiz(ActionEvent event) {
        int TQn = Integer.parseInt(tQn.getText());
        int DQn = Integer.parseInt(dQn.getText());
        int Tl = 1;
        if(TQn>=DQn) {
            String name = Qname.getText();
            Main.user.sendString("createQuiz");
            Main.user.sendInt(TQn);
            Main.user.sendInt(DQn);
            Main.user.sendInt(Tl);
            Main.user.sendInt(Session_Id.getClassId());
            Main.user.sendString(name);
            if (Main.user.recieveBoolean()) {
                System.out.println("Creation Succesful");
                Session_Id.setQname(name);
                System.out.println(name);
                changer.changeScene("Teachers/createQuiz.fxml", event, "Quiz");
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Failure");
            alert.setContentText("The questions to display is more than the total questions");
            alert.showAndWait();
        }
    }

    @FXML
    void back(ActionEvent event) {
            changer.changeScene("Teachers/Tdash.fxml",event,"Dashboard");
    }

}
