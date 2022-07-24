package com.example.quiz;

import android.widget.ImageView;
import android.widget.TextView;

public class Modal {
    String t1, t2, t3, t4, t5;

    public Modal(String t1, String t2, String t3, String t4, String t5) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
    }

    public String getT1() {
        return t1;
    }

    public String getT2() {
        return t2;
    }

    public String getT3() {
        return t3;
    }

    public String getT4() {
        return t4;
    }

    public String getT5() {
        return t5;
    }
}
