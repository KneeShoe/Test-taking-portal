package sample.Server_Client;

public class Session_Id {

    //Stores the session info of the current user
    private  static String ID;
    private  static String type;
    private  static int classId;
    private static String name;
    private static int tqn;
    private static String sec;
    private static String sem;
    private static String qname;

    public void setqname(String n){
        qname=n;
    }

    public String getqname(){
        return qname;
    }

    public static String getSec() {
        return sec;
    }

    public static String getSem() {
        return sem;
    }

    public static void setSem(String sem) {
        Session_Id.sem = sem;
    }

    public static void setSec(String sec) {
        Session_Id.sec = sec;
    }

    public static int getTqn(){
        return tqn;
    }
    public static void setTqn(int tot){
        tqn=tot;
    }
    public static String getQname(){
        return name;
    }
    public static void setQname(String n){
        name=n;
    }
    public static int getClassId(){
        return classId;
    }
    public static void setClassId(int id){
        classId=id;
    }
    public static String getID() {
        return ID;
    }
    public static String gettype() {
        return type;
    }
    public static void setID(String ID){
        Session_Id.ID=ID;
    }
    public static void settype(String type){
        Session_Id.type=type;
    }
}