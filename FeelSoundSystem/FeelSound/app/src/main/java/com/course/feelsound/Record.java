package com.course.feelsound;

public class Record {
    int s_num;
    String s_name;
    String time;

    public Record(int s_num, String s_name, String time){
        this.s_num = s_num;
        this.s_name = s_name;
        this.time = time;
    }

    public Record(String s_name, String time){
        this.s_num = s_num;
        this.s_name = s_name;
        this.time = time;
    }

    public int getS_num() {return s_num; }

    public String getTime() {
        return time;
    }

    public String getS_name() {
        return s_name;
    }
}
