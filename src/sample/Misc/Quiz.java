package sample.Misc;

import java.io.Serializable;

public class Quiz implements Serializable {
    public String qn;
    public String a;
    public String b;
    public String c;
    public String d;
    public String cor;
    public Quiz(String q,String opa,String opb,String opc,String opd,String corr){
        qn=q;
        a=opa;
        b=opb;
        c=opc;
        d=opd;
        cor=corr;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    public String getCor() {
        return cor;
    }

    public String getD() {
        return d;
    }

    public String getQn() {
        return qn;
    }
}
