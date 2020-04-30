package sample.Teachers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import sample.Main;
import sample.Misc.Scores;
import sample.SceneChanger;
import sample.Server_Client.Session_Id;

import java.util.ArrayList;


public class leaderboard {

    @FXML
    private JFXListView<?> leaderlist;

    @FXML
    private JFXButton back;

    SceneChanger changer=new SceneChanger();

    public void initialize(){
        Main.user.sendString("getScores");
        Main.user.sendString(Session_Id.getQname());
        ArrayList<Scores> scores=(ArrayList<Scores>)Main.user.recieveObject();
        ArrayList a=new ArrayList();
        HBox hBox=new HBox(50);
        Label lbl=new Label("Student USN");
        lbl.setFont(new Font(30));
        Label lbl1=new Label("Scores");
        lbl1.setFont(new Font(30));
        hBox.getChildren().addAll(lbl,lbl1);
        a.add(hBox);
        if(scores.size()!=0){
            for(Scores test:scores){
                HBox hbox1=new HBox(50);
                Label lbl2=new Label(test.getUsn());
                lbl2.setFont(new Font(20));
                Label lbl3=new Label(test.getScore()+"");
                lbl3.setFont(new Font(20));
                hbox1.getChildren().addAll(lbl2,lbl3);
                a.add(hbox1);
            }
            leaderlist.setItems(FXCollections.observableArrayList(a));
        }

    }
    @FXML
    void back(ActionEvent event) {
        if(Session_Id.gettype().equals("teacher"))
            changer.changeScene("Teachers/tInsideClass.fxml",event,"");
        else
            changer.changeScene("Students/sInsideClass.fxml",event,"");
    }
}
