package sample.Server_Client;


import sample.Main;
import sample.Misc.Classes;
import sample.Misc.Qdetails;
import sample.Misc.Quiz;
import sample.Misc.Scores;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class clientHandler implements Runnable {

    private Socket client;

    private ObjectInputStream ObjectInput;
    private ObjectOutputStream ObjectOutput;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    private String name;
    private ConnectionClass Student = new ConnectionClass();
    private Connection connection = Student.getConnection();
    private PasswordUtils check = new PasswordUtils();
    private File f;


    public clientHandler(Socket client, String name, ObjectOutputStream ObjectOutput, ObjectInputStream ObjectInput, DataOutputStream dataOutput, DataInputStream dataInput) {
        this.name = name;
        this.client = client;
        this.ObjectOutput = ObjectOutput;
        this.ObjectInput = ObjectInput;
        this.dataInput = dataInput;
        this.dataOutput = dataOutput;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String recieved = dataInput.readUTF();
                if (recieved.equalsIgnoreCase("Exit")) {
                    System.out.println("Client Connection Terminating");
                    this.client.close();
                    break;
                }
                switch (recieved) {
                    case "Login":
                        dataOutput.writeBoolean(Login());
                        break;
                    case "SSignup":
                        dataOutput.writeBoolean(StudentSignUp());
                        break;
                    case "TSignup":
                        dataOutput.writeBoolean(TeacherSignUp());
                        break;
                    case "createClass":
                        dataOutput.writeBoolean(createClass());
                        break;
                    case "getClasses":
                        ObjectOutput.writeObject(getClasses());
                        break;
                    case "getClassID":
                        dataOutput.writeInt(getClassID());
                        break;
                    case "createQuiz":
                        dataOutput.writeBoolean(createQuiz());
                        break;
                    case "createTemplate":
                        dataOutput.writeInt(createTemplate());
                        break;
                    case "getQnames":
                        ObjectOutput.writeObject(getQnames());
                        break;
                    case "QuizEntry":
                        QuizEntry();
                        break;
                    case "getClassInfo":
                        ObjectOutput.writeObject(getClassInfo());
                        break;
                    case "getQuizInfo":
                        ObjectOutput.writeObject(getQuizInfo());
                        break;
                    case "getDisplayQn":
                        dataOutput.writeInt(getDisplayQn());
                        break;
                    case "enterScore":
                        dataOutput.writeBoolean(enterScore());
                        break;
                    case "checkTest":
                        dataOutput.writeBoolean(checkTest());
                        break;
                    case "getScores":
                        ObjectOutput.writeObject(getScores());
                        break;
                    case "deleteClass":
                        dataOutput.writeBoolean(deleteClass());
                        break;
                    case "deleteQuiz":
                        deleteQuiz();
                        break;
                    default:
                        System.out.println("YOU HAVE REACHED DEFAULT CASE");
                }
            } catch (Exception e) {
                System.out.println(e);
                break;
            }

        }
        try {
            ObjectOutput.close();
            ObjectInput.close();
            dataOutput.close();
            dataInput.close();
            client.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private boolean Login() {
        try{                                                               //Check if user password match and if user exists
            String username=dataInput.readUTF().toLowerCase();
            String password=dataInput.readUTF();
            String type=dataInput.readUTF();
            String table;
            String col;
            if(type.equals("teacher")){
                table="`teacherReg`";
                col="`Fid`";
            }else{
                table="`studentReg`";
                col="`USN`";
            }
            String sql = "SELECT `Password`, `Salt` FROM "+ table +" WHERE "+ col +"=\"" + username + "\";";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String Spassword = "";
            String Salt = "A2B2";

            while (rs.next()) {
                Spassword = rs.getString("Password");
                Salt = rs.getString("Salt");
            }
            if (check.verifyUserPassword(password, Spassword, Salt)) {
                return true;
            } else return false;
        }catch(IOException | SQLException | InvalidKeySpecException | IllegalArgumentException e){
            System.out.println(e);
            return false;
        }
    }




    private boolean StudentSignUp() {
        try {                                                                               //Input student details into database
            String name = this.dataInput.readUTF().toLowerCase();
            String email = this.dataInput.readUTF().toLowerCase();
            String usn = this.dataInput.readUTF().toLowerCase();
            String sem = this.dataInput.readUTF().toLowerCase();
            String sec = this.dataInput.readUTF().toLowerCase();
            String pass = this.dataInput.readUTF();
            String salt = this.dataInput.readUTF();
            String sql = "INSERT INTO `studentReg`(`Name`, `USN`, `Password`, `Salt`, `Sem`, `Email`, `Section`) VALUES('" + name + "','" + usn + "','" + pass + "','" +
                    salt + "','" + sem + "','" + email + "','" + sec + "');";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            return true;
        } catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return false;
    }





    private boolean TeacherSignUp() {
        try {                                                                            //Input Teacher details into database
            String name = this.dataInput.readUTF().toLowerCase();
            String email = this.dataInput.readUTF().toLowerCase();
            String id = this.dataInput.readUTF().toLowerCase();
            String pass = this.dataInput.readUTF();
            String salt = this.dataInput.readUTF();
            String dept =this.dataInput.readUTF().toLowerCase();
            String sql = "INSERT INTO `teacherReg`(`Name`, `Email`, `Fid`,`Password`, `Salt`,`Dept`) VALUES('" + name + "','" + email + "','" + id + "','" + pass + "', '" +
                    salt + "','"+ dept+ "');";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            return true;
        } catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return false;
    }





    private boolean createClass(){
        try{                                                                            //Insert Class details into database
            String sub= this.dataInput.readUTF().toLowerCase();
            String sem= this.dataInput.readUTF().toLowerCase();
            String sec= this.dataInput.readUTF().toLowerCase();
            String fid= this.dataInput.readUTF().toLowerCase();
            String sql= "INSERT INTO `class`(`fid`,`semester`,`section`,`subject`) VALUES ('" + fid +"','"+
                    sem+"','"+sec+"','"+sub+"');";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            return true;
        }catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return false;
    }





    private ArrayList <Classes> getClasses(){
        try{                                                                            //Retrieve classes for the teacher dashboard
            ArrayList<Classes> classes = new ArrayList<>();
            String Fid = this.dataInput.readUTF().toLowerCase();
            String sql = "SELECT semester,`section`,subject FROM class where fid='" + Fid + "';";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next())
            {
                String sem=rs.getString("semester");
                String sec=rs.getString("section");
                String sub=rs.getString("subject");
                Classes class1= new Classes(sub,sec,sem);
                classes.add(class1);
            }
            return classes;
        }catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return null;
    }





    private  int getClassID(){
        try{                                                                        //Retrieve class id
            String sem= dataInput.readUTF().toLowerCase();
            String sec= dataInput.readUTF().toLowerCase();
            String sub= dataInput.readUTF().toLowerCase();
            String sql="Select classid from class where semester="+sem+" and section='"+sec+"' and subject='"+sub+"';";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int classId= rs.getInt("classid");
            return classId;
        }catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return 0;

    }




    private boolean createQuiz(){
        try{                                                                         //Input Quiz details into database
            int Tqn= dataInput.readInt();
            int Dqn= dataInput.readInt();
            int Tl= dataInput.readInt();
            int Classid=dataInput.readInt();
            String name=dataInput.readUTF();
            String sql="Insert into `quiz`(`Qname`,`Classid`, `TotalQn`, `DispQn`, `TimeLimit`) values('"+name+"','"+Classid+"','"+Tqn+
                        "','"+Dqn+"','"+Tl+"');";
            Statement statement= connection.createStatement();
            statement.execute(sql);
            return true;
        }catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return false;
    }




    public int createTemplate(){
        try{                                                                        //Retrieves Total questions in a test
            String name= dataInput.readUTF().toLowerCase();
            String sql="Select TotalQn from quiz where Qname='"+name+"';";
            Statement statement= connection.createStatement();
            ResultSet rs= statement.executeQuery(sql);
            rs.next();
            return rs.getInt("TotalQn");
        }catch(SQLException | IOException e){
            System.out.println(e);
        }
        return 0;
    }





    public ArrayList<String> getQnames(){
        try{                                                                    //Retrieves Quiz names
            int classid=dataInput.readInt();
            System.out.println(classid);
            String sql="Select Qname from quiz where Classid='"+classid+"';";
            Statement statement = connection.createStatement();
            ResultSet rs= statement.executeQuery(sql);
            ArrayList<String> a=new ArrayList<String>();
            while(rs.next()){
                a.add(rs.getString("Qname"));
            }
            System.out.println(a);
            return a;
        }catch(IOException | SQLException  e){
            System.out.println(e);
        }
        return null;
    }




    public void QuizEntry(){
        try{                                                                         //Input Question details into database
            Qdetails q= (Qdetails) ObjectInput.readObject();
            String qn=q.qn;
            String opa=q.opa;
            String opb=q.opb;
            String opc=q.opc;
            String opd=q.opd;
            String corr=q.corr;
            int classid=dataInput.readInt();
            String qname=dataInput.readUTF();
            String sql="Insert into questions(Qname,Classid,qn,a,b,c,d,correct) values('"+qname+"','"+
                        classid+"','"+qn+"','"+opa+"','"+opb+"','"+opc+"','"+opd+"','"+corr+"');";
            Statement statement= connection.createStatement();
            statement.execute(sql);
        }catch(IOException | SQLException  | ClassNotFoundException e){
            System.out.println(e);
        }
        return;
    }





    public ArrayList<Classes> getClassInfo(){
        try{                                                                    //Retrieve semester and section of student
            String id=dataInput.readUTF().toLowerCase();
            String sql="Select Sem,`Section` from studentreg where USN='"+id+"';";
            Statement statement= connection.createStatement();
            ResultSet rs= statement.executeQuery(sql);
            rs.next();
            String sem=rs.getString("Sem");
            String sec=rs.getString("Section").toLowerCase();
            sql="SELECT c.subject,t.Name from class c,teacherreg t where c.section='"+sec+"' and " +
                    "c.fid=t.Fid and c.semester='"+sem+"';";
            rs= statement.executeQuery(sql);
            ArrayList<Classes> a=new ArrayList<Classes>();
            while(rs.next())
            {
                String sub=rs.getString("subject");
                String name=rs.getString("Name");
                Classes class1=new Classes(sem,sec,sub,name);
                a.add(class1);
            }
            return a;
        }catch(IOException | SQLException  e){
            System.out.println(e);
        }
        return null;
    }





    public ArrayList<Quiz> getQuizInfo(){
        try{                                                                    //Retrieve Question details
            String qname=dataInput.readUTF().toLowerCase();
            String sql="Select `qn`,`a`,`b`,`c`,`d`,correct from questions where qname='"+qname+"';";
            Statement statement=connection.createStatement();
            ResultSet rs= statement.executeQuery(sql);
            ArrayList<Quiz> ab=new ArrayList<Quiz>();
            while (rs.next()){
                String qn=rs.getString("qn");
                String a=rs.getString("a");
                String b=rs.getString("b");
                String c=rs.getString("c");
                String d=rs.getString("d");
                String cor=rs.getString("correct");
                Quiz q=new Quiz(qn,a,b,c,d,cor);
                ab.add(q);
            }
            return ab;
        }catch(IOException | SQLException  e){
            System.out.println(e);
        }
        return null;
    }





    public int getDisplayQn(){
        try {                                                                  //Retrieve number of questions to display
            String qname=dataInput.readUTF().toLowerCase();
            String sql="Select DispQn from quiz where Qname='"+qname+"';";
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            rs.next();
            int dqn=rs.getInt("DispQn");
            return dqn;
        }catch(IOException | SQLException  e){
            System.out.println(e);
        }
        return 0;
    }




    public boolean enterScore(){
        try{                                                                    //Insert score
            int s=dataInput.readInt();
            String Qname=dataInput.readUTF().toLowerCase();
            String usn=dataInput.readUTF().toLowerCase();
            String sql="Insert into leaderboard values('"+usn+"','"+s+"','"+Qname+"');";
            Statement statement= connection.createStatement();
            statement.execute(sql);
            return true;
        }catch(IOException | SQLException  e){
            System.out.println(e);
        }
        return false;
    }





    public boolean checkTest(){
        try{                                                                    //Check if the test has been taken or not
            String Qname=dataInput.readUTF().toLowerCase();
            String usn=dataInput.readUTF().toLowerCase();
            String sql="Select count(*) from leaderboard where Qname='"+Qname+"' and usn='"+usn+"';";
            Statement statement= connection.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            rs.next();
            if(rs.getInt("count(*)")>0)
                return true;
        }catch (IOException | SQLException e){
            System.out.println(e);
        }
        return  false;
    }




    public ArrayList<Scores> getScores(){
        try{                                                                    //Retrieve leaderboard information
            String Qname=dataInput.readUTF().toLowerCase();
            String sql="SELECT usn,score from leaderboard where Qname='"+Qname+"' order by score desc;";
            Statement statement=connection.createStatement();
            ResultSet rs= statement.executeQuery(sql);
            ArrayList<Scores> a=new ArrayList<Scores>();
            while(rs.next())
            {
                String usn=rs.getString("usn");
                int score=rs.getInt("score");
                Scores score1=new Scores(usn,score);
                a.add(score1);
            }
            return a;
        }catch (IOException | SQLException e){
            System.out.println(e);
        }
        return  null;
    }


    public boolean deleteClass(){
        try{
            int classid=dataInput.readInt();
            String sql="DELETE  from questions where Classid='"+classid+"';";
            Statement statement= connection.createStatement();
            statement.execute(sql);
            sql="DELETE  from quiz where ClassId='"+classid+"';";
            statement.execute(sql);
            sql="Delete from class where classid='"+classid+"';";
            statement.execute(sql);

            return true;
        }catch (IOException | SQLException e){
            System.out.println(e);
        }
        return  false;
    }

    public boolean deleteQuiz(){
        try{
            String qname=dataInput.readUTF().toLowerCase();
            String sql="Delete from quiz where Qname='"+qname+"';";
            Statement statement= connection.createStatement();
            statement.execute(sql);
            return true;
        }catch (IOException | SQLException e){
            System.out.println(e);
        }
        return  false;
    }
}
