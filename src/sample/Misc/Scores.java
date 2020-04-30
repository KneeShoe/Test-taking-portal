package sample.Misc;

import java.io.Serializable;

public class Scores implements Serializable {
    public String usn;
    public int score;
    public Scores(String u,int s){
        usn=u;
        score=s;
    }

    public int getScore() {
        return score;
    }

    public String getUsn() {
        return usn;
    }
}
