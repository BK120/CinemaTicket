package com.bk120.mytest_baidumap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import android.os.Handler;

import static com.baidu.location.h.j.m;

//apk key
//hiDKWB5w3ZNDOCiTRXKOIHtOs3LtsVsk
public class MainActivity extends Activity {
    private BaiBuDingWeiUtils utils;
    private TextView tv;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0x110){
                String ms= (String) msg.obj;
                tv.append(ms);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= (TextView) this.findViewById(R.id.result_tv);
        utils=new BaiBuDingWeiUtils(this);
        utils.setListener(new BaiBuDingWeiUtils.BaiDuDingWeiListener() {
            @Override
            public void getTime(String time) {
                Message m=Message.obtain();
                m.what=0x110;
                m.obj=time;
                handler.sendMessage(m);
            }

            @Override
            public void getLatitude(String latitude) {
                Message m=Message.obtain();
                m.what=0x110;
                m.obj=latitude;
                handler.sendMessage(m);
            }

            @Override
            public void getLontitude(String lontitude) {
                Message m=Message.obtain();
                m.what=0x110;
                m.obj=lontitude;
                handler.sendMessage(m);
            }

            @Override
            public void getRadius(String radius) {
                Message m=Message.obtain();
                m.what=0x110;
                m.obj=radius;
                handler.sendMessage(m);
            }

            @Override
            public void getAddress(String address) {
                Message m=Message.obtain();
                m.what=0x110;
                m.obj=address;
                handler.sendMessage(m);
            }

            @Override
            public void getLocationDes(String des) {
                Message m=Message.obtain();
                m.what=0x110;
                m.obj=des;
                handler.sendMessage(m);
            }

            @Override
            public void error(String s) {
                Message m=Message.obtain();
                m.what=0x110;
                m.obj=s;
                handler.sendMessage(m);
            }
        });
    }


    //开启
    public void start(View view){
        //先清空
        tv.setText("");
        utils.start();
    }
    //关闭
    public void stop(View view){
        utils.stop();
    }
}
