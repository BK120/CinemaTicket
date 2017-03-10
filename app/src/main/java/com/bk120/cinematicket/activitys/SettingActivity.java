package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.bk120.cinematicket.R;
//对用户的设置中心
public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
    //关闭当前Activity
    public void back(View view){
        this.finish();
    }
}
