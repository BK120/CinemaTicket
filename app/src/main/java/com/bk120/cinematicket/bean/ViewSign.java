package com.bk120.cinematicket.bean;

import android.view.View;

/**
 * Created by bk120 on 2017/3/10.
 * 信号量
 * 携带一个int值和一个View值
 */

public class ViewSign {
    private View view;
    private int position;
    public ViewSign(View view,int i){
        this.view=view;
        this.position=i;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
