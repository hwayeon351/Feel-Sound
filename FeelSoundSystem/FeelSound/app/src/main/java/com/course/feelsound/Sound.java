package com.course.feelsound;

public class Sound {
    int s_id;
    int type;
    int vib_p;
    int vib_s;
    int state;

    public Sound(int s_id, int type, int vib_p, int vib_s, int state){
        this.s_id = s_id;
        this.type = type;
        this.vib_p = vib_p;
        this.vib_s = vib_s;
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public int getS_id(){
        return s_id;
    }

    public int getVib_p() {
        return vib_p;
    }

    public int getVib_s() {
        return vib_s;
    }

    public int getState() {
        return state;
    }
}
