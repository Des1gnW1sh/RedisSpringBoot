package com.scnjwh.intellectreport.modules.excelreport.Controller;

/**
 * Created by Administrator on 2017-10-30.
 */
public class Bdgsub {
    private String imp;
    private String bdgsub;
    private String pay;
    private String balance;
    private String paysource;
    private String paymentratio;

    public Bdgsub(String imp, String bdgsub, String pay, String balance, String paysource, String paymentratio) {
        this.imp = imp;
        this.bdgsub = bdgsub;
        this.pay = pay;
        this.balance = balance;
        this.paysource = paysource;
        this.paymentratio = paymentratio;
    }
    public Bdgsub(){

    }

    public String getImp() {
        return imp;
    }

    public void setImp(String imp) {
        this.imp = imp;
    }

    public String getBdgsub() {
        return bdgsub;
    }

    public void setBdgsub(String bdgsub) {
        this.bdgsub = bdgsub;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPaysource() {
        return paysource;
    }

    public void setPaysource(String paysource) {
        this.paysource = paysource;
    }

    public String getPaymentratio() {
        return paymentratio;
    }

    public void setPaymentratio(String paymentratio) {
        this.paymentratio = paymentratio;
    }
}
