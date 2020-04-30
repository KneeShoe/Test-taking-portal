package sample.Students;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import sample.Main;
import sample.Misc.Qdetails;
import sample.Misc.Quiz;
import sample.SceneChanger;
import sample.Server_Client.Session_Id;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class takeQuiz {
    @FXML
    private JFXButton back;

    @FXML
    private JFXListView<?> qnList;

    ArrayList<Quiz> quizzes;

    ArrayList<String> correct=new ArrayList<String>();

    SceneChanger changer=new SceneChanger();

    ArrayList<JFXRadioButton> opa=new ArrayList<JFXRadioButton>();
    ArrayList<JFXRadioButton> opb=new ArrayList<JFXRadioButton>();
    ArrayList<JFXRadioButton> opc=new ArrayList<JFXRadioButton>();
    ArrayList<JFXRadioButton> opd=new ArrayList<JFXRadioButton>();

    public void initialize(){
        Main.user.sendString("createTemplate");
        Main.user.sendString(Session_Id.getQname());
        int tqn=Main.user.recieveInt();
        Main.user.sendString("getQuizInfo");
        Main.user.sendString(Session_Id.getQname());
        quizzes=(ArrayList<Quiz>) Main.user.recieveObject();
        Main.user.sendString("getDisplayQn");
        Main.user.sendString(Session_Id.getQname());
        int dqn=Main.user.recieveInt();
        Random r = new Random();
        for(int i=quizzes.size()-1;i>0;i--){
                int j=r.nextInt(i);
                Quiz temp=quizzes.get(i);
                quizzes.set(i,quizzes.get(j));
                quizzes.set(j,temp);
            }
            for(int i=0;i<tqn-dqn;i++){
                quizzes.remove(i);
        }
        ArrayList ab=new ArrayList();
        int i=1;
        if(quizzes.size()!=0){
            for(Quiz test:quizzes){
                correct.add(test.getCor());
                VBox vbox=new VBox();
                vbox.setPadding(new Insets(10, 10, 10, 10));
                HBox hbox=new HBox();
                hbox.setPadding(new Insets(10, 10, 10, 10));
                Label lbl=new Label(i+")");
                i++;
                lbl.setFont(new Font(25));
                Label lbl1=new Label(test.getQn());
                lbl1.setFont(new Font(25));
                ToggleGroup tg=new ToggleGroup();
                JFXRadioButton a=new JFXRadioButton(test.getA());
                a.setFont(new Font(20));
                JFXRadioButton b=new JFXRadioButton(test.getB());
                JFXRadioButton c=new JFXRadioButton(test.getC());
                JFXRadioButton d=new JFXRadioButton(test.getD());
                a.setToggleGroup(tg);
                b.setToggleGroup(tg);
                c.setToggleGroup(tg);
                d.setToggleGroup(tg);
                opa.add(a);
                opb.add(b);
                opc.add(c);
                opd.add(d);
                hbox.getChildren().addAll(lbl,lbl1);
                vbox.getChildren().addAll(hbox,a,b,c,d);
                ab.add(vbox);
            }
            qnList.setItems(FXCollections.observableArrayList(ab));
        }
    }

    @FXML
    void submit(ActionEvent event) {
            int score=0;
            for(int i=0;i<opa.size();i++) {
                if (opa.get(i).isSelected() && opa.get(i).getText().equals(correct.get(i)))
                    score++;
                if (opb.get(i).isSelected() && opb.get(i).getText().equals(correct.get(i)))
                    score++;
                if (opc.get(i).isSelected() && opc.get(i).getText().equals(correct.get(i)))
                    score++;
                if (opd.get(i).isSelected() && opd.get(i).getText().equals(correct.get(i)))
                    score++;
            }

        Main.user.sendString("enterScore");
        Main.user.sendInt(score);
        Main.user.sendString(Session_Id.getQname());
        Main.user.sendString(Session_Id.getID());
        if(Main.user.recieveBoolean())
        {
            System.out.println(score);
            changer.changeScene("Students/sInsideClass.fxml",event,"");
        }
    }
}
