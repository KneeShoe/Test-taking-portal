package sample.Students;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import sample.Main;
import sample.Misc.Classes;
import sample.SceneChanger;
import sample.Server_Client.Session_Id;

import java.util.ArrayList;

public class Sdash {

    SceneChanger changer=new SceneChanger();

    @FXML
    private JFXButton back;

    @FXML
    private JFXListView<?> classList;

    public void initialize(){
        Main.user.sendString("getClassInfo");
        Main.user.sendString(Session_Id.getID());
        ArrayList<Classes> a=(ArrayList) Main.user.recieveObject();
        ArrayList b=new ArrayList();
        if(a.size()!=0)
        {
            for(Classes test:a){
                VBox vbox=new VBox();
                vbox.setPadding(new Insets(10, 10, 10, 10));
                Label lbl= new Label(test.getsub());
                lbl.setFont(new Font(25));
                Label lbl1=new Label(test.getFname());
                lbl1.setFont(new Font(15));
                vbox.getChildren().addAll(lbl,lbl1);
                vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Main.user.sendString("getClassID");
                        Main.user.sendString(test.getsem());
                        Main.user.sendString(test.getsec());
                        Main.user.sendString(test.getsub());
                        int classid=Main.user.recieveInt();
                        Session_Id.setClassId(classid);
                        changer.changeScene("Students/sInsideClass.fxml",event,test.getsub());
                    }
                });
                b.add(vbox);
            }
            classList.setItems(FXCollections.observableArrayList(b));
        }
    }



    @FXML
    void back(ActionEvent event) {
        changer.changeScene("Login_Signup/Login.fxml",event,"Login");
    }
}
