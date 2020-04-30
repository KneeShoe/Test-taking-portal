package sample.Misc;

import java.io.Serializable;

public class Classes  implements Serializable {
    public String subject;
    public String section;
    public String semester;
    public String fname;
    public Classes(String sub,String sec, String sem)
    {
        subject=sub;
        section=sec;
        semester=sem;
    }
    public Classes(String sem,String sec,String sub,String name){
        fname=name;
        subject=sub;
        semester=sem;
        section=sec;
    }

    public String getFname() {
        return fname;
    }
    public String getsub(){
        return subject;
    }
    public String getsec() {
        return section;
    }
    public String getsem(){
        return semester;
    }
}
