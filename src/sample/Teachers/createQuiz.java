package sample.Teachers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import sample.Main;
import sample.Misc.Qdetails;
import sample.SceneChanger;
import sample.Server_Client.Session_Id;
import java.util.ArrayList;

public class createQuiz {

    @FXML
    private JFXButton back;

    @FXML
    private AnchorPane anchor;

    @FXML
    private JFXListView<?> qnList;

    @FXML
    private JFXButton cbtn;

    SceneChanger changer=new SceneChanger();

    ArrayList<JFXTextField> qn=new ArrayList();
    ArrayList<JFXTextField> opa=new ArrayList();
    ArrayList<JFXTextField> opb=new ArrayList();
    ArrayList<JFXTextField> opc=new ArrayList();
    ArrayList<JFXTextField> opd=new ArrayList();
    ArrayList<JFXComboBox> cor=new ArrayList();

    public void initialize(){
        Main.user.sendString("createTemplate");
        Main.user.sendString(Session_Id.getQname());
        int tqn=Main.user.recieveInt();
        Session_Id.setTqn(tqn);
        ArrayList a=new ArrayList();
        for(int i=0;i<tqn;i++){
            VBox vbox= new VBox();
            vbox.setPadding(new Insets(10, 10, 10, 10));
            vbox.setSpacing(10);
            HBox hBox=new HBox();
            hBox.setSpacing(20);
            Label lbl=new Label((i+1)+")");
            lbl.setFont(new Font(15));
            JFXTextField question = new JFXTextField();
            question.setPromptText("Question");
            JFXTextField optiona = new JFXTextField();
            optiona.setPromptText("OptionA");
            JFXTextField optionb = new JFXTextField();
            optionb.setPromptText("OptionB");
            JFXTextField optionc = new JFXTextField();
            optionc.setPromptText("OptionC");
            JFXTextField optiond = new JFXTextField();
            optiond.setPromptText("OptionD");
            ObservableList<String> options=FXCollections.observableArrayList("A","B","C","D");
            final JFXComboBox correct = new JFXComboBox(options);
            qn.add(question);
            opa.add(optiona);
            opb.add(optionb);
            opc.add(optionc);
            opd.add(optiond);
            cor.add(correct);
            hBox.getChildren().addAll(optiona,optionb,optionc,optiond);
            vbox.getChildren().addAll(lbl,question,hBox,correct);
            a.add(vbox);
        }
        qnList.setItems(FXCollections.observableArrayList(a));
    }

    @FXML
    void back(ActionEvent event) {
        Main.user.sendString("deleteQuiz");
        Main.user.sendString(Session_Id.getQname());
        changer.changeScene("Teachers/tInsideClass.fxml",event,"");
    }

    @FXML
    void getQuizElements (ActionEvent event){
        try {
            int tqn = Session_Id.getTqn();
            for (int i = 0; i < tqn; i++) {
                Qdetails q = new Qdetails();
                Main.user.sendString("QuizEntry");
                q.qn = qn.get(i).getText();
                q.opa = opa.get(i).getText();
                q.opb = opb.get(i).getText();
                q.opc = opc.get(i).getText();
                q.opd = opd.get(i).getText();
                switch (cor.get(i).getValue().toString()) {
                    case "A":
                        q.corr = opa.get(i).getText();
                        break;
                    case "B":
                        q.corr = opb.get(i).getText();
                        break;
                    case "C":
                        q.corr = opc.get(i).getText();
                        break;
                    case "D":
                        q.corr = opd.get(i).getText();
                        break;
                    default:
                        System.out.println("Default case initiated");
                }
                Main.user.sendObject(q);
                Main.user.sendInt(Session_Id.getClassId());
                Main.user.sendString(Session_Id.getQname());
            }
            changer.changeScene("Teachers/tInsideClass.fxml", event, "");
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty Fields");
            alert.setHeaderText("Error:");
            alert.setContentText("Please fill in all the fields!");
            alert.showAndWait();
        }
}



}