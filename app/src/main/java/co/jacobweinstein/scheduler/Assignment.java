package co.jacobweinstein.scheduler;

import java.io.Serializable;

public class Assignment implements Serializable {
    public String title;
    private int month;
    private int dayOfMonth;
    private int year;
    private String className;
    private String desc;

    public Assignment(){
        title = "";
        month = 0;
        dayOfMonth = 0;
        year = 0;
        className = "";
        desc = "";
    }
    public Assignment (String t, String d, String c, String dc){
        title = t;
        month = Integer.parseInt(d.substring(0,d.indexOf("/")));
        d = d.substring(d.indexOf("/") + 1);
        dayOfMonth = Integer.parseInt(d.substring(0,d.indexOf("/")));
        d = d.substring(d.indexOf("/") + 1);
        year = Integer.parseInt(d);
        className = c;
        desc = dc;
    }
    public String getDate(){
        return month + "/" + dayOfMonth + "/" + year;
    }
    public int getMonth(){
        return month;
    }
    public int getDayOfMonth(){
        return dayOfMonth;
    }
    public int getYear(){
        return year;
    }
    public String getTitle(){
        return title;
    }
    public String getClassName(){
        return className;
    }
    public String getDesc(){
        return desc;
    }
    public void setMonth(int x){
        month = x;
    }
    public void setDayOfMonth(int x){
        dayOfMonth = x;
    }
    public void setYear(int x){
        year = x;
    }
    public void setTitle(String x){
        title = x;
    }
    public void setClassName(String x){
        className = x;
    }
    public void setDesc(String x){
        desc = x;
    }
    public String toString(){
        //return "Title: " + title + " Date: " + month + "/" + dayOfMonth + "/" + year + " Class: " + className + " Desc: " + desc;
        return title + " due on " + getDate() + " for " + className;
    }
}
