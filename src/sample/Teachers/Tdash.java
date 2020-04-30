package sample.Teachers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import sample.Main;
import sample.Misc.Classes;
import sample.Server_Client.Session_Id;
import sample.SceneChanger;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Tdash {

    @FXML
    private JFXButton back;

    @FXML
    private JFXListView<?> classlist;

    @FXML
    private JFXTextField Semester;

    @FXML
    private JFXTextField Section;

    @FXML
    private JFXTextField Subject;

    @FXML
    private JFXButton createbtn;

    private SceneChanger changer = new SceneChanger();


    public void initialize(){
        Main.user.sendString("getClasses");
        Main.user.sendString(Session_Id.getID());
        ArrayList<Classes> classes = (ArrayList<Classes>)Main.user.recieveObject();
        ArrayList a=new ArrayList();
        if(classes.size() != 0)
        {
            for(Classes test : classes) {
                VBox vbox = new VBox();
                vbox.setPadding(new Insets(10, 0, 10, 10));
                HBox hbox=new HBox(650);
                JFXButton btn=new JFXButton("x");
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Main.user.sendString("getClassID");
                        Main.user.sendString(test.getsem());
                        Main.user.sendString(test.getsec());
                        Main.user.sendString(test.getsub());
                        int classid=Main.user.recieveInt();
                        Main.user.sendString("deleteClass");
                        Main.user.sendInt(classid);
                        if(Main.user.recieveBoolean())
                            changer.changeScene("Teachers/Tdash.fxml",event,"Teacher Dashboard");
                    }
                });
                btn.setFont(new Font(15));
                Label lbl1 = new Label(test.getsem());
                lbl1.setFont(new Font(20));
                Label lbl2 = new Label(test.getsec());
                lbl2.setFont(new Font(15));
                Label lbl3 = new Label(test.getsub());
                lbl3.setFont(new Font(15));
                hbox.getChildren().addAll(lbl1,btn);
                vbox.getChildren().addAll(hbox, lbl2, lbl3);
                vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Main.user.sendString("getClassID");
                        Main.user.sendString(test.getsem());
                        Main.user.sendString(test.getsec());
                        Main.user.sendString(test.getsub());
                        int classid=Main.user.recieveInt();
                        Session_Id.setClassId(classid);
                        changer.changeScene("Teachers/tInsideClass.fxml",event,test.getsub());
                    }
                });
                a.add(vbox);
            }
        }
        classlist.setItems(FXCollections.observableArrayList(a));
    }

    public void createClass(ActionEvent event){
        String Sub = Subject.getText();
        String Sem = Semester.getText();
        String Sec = Section.getText();
        String Fid = Session_Id.getID();
        Main.user.sendString("createClass");
        Main.user.sendString(Sub);
        Main.user.sendString(Sem);
        Main.user.sendString(Sec);
        Main.user.sendString(Fid);
        if(Main.user.recieveBoolean()) {
            System.out.println("Creation Succesful");
            changer.changeScene("Teachers/Tdash.fxml",event,"Teacher Dashboard");
        }
    }

    @FXML
    void back(ActionEvent event) {
        changer.changeScene("Login_Signup/Login.fxml",event,"Login");
    }

}