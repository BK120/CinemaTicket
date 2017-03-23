package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.constants.MainConstant;
import com.bk120.cinematicket.utils.SharePreferencesUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

//对用户的设置中心
public class SettingActivity extends Activity {
    @BindView(R.id.settingactivity_hidemoney_togglebutton) ToggleButton money_tb;
    @BindView(R.id.settingactivity_opengesturelock_togglebutton) ToggleButton gesture_tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setListener();
        initView();
    }
    private void initView() {
        boolean aBoolean = SharePreferencesUtils.getBoolean(this, MainConstant.IS_HIDE_MONEY, false);
        boolean bBoolean = SharePreferencesUtils.getBoolean(this, MainConstant.IS_OPEN_GESTURE, false);
        money_tb.setChecked(aBoolean);
        gesture_tb.setChecked(bBoolean);
    }

    private void setListener() {
        money_tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharePreferencesUtils.putBoolean(getApplicationContext(), MainConstant.IS_HIDE_MONEY,isChecked);
            }
        });
        gesture_tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharePreferencesUtils.putBoolean(getApplicationContext(), MainConstant.IS_OPEN_GESTURE,isChecked);
            }
        });

    }

    //关闭当前Activity
    public void back(View view){
        this.finish();
    }

    //关于我们
    public void myInfo(View view){
        //通过AlertDialog显示，读取文本。
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View v=View.inflate(this,R.layout.aboutme_item,null);
        TextView tv= (TextView) v.findViewById(R.id.aboutme_item_tc);
        StringBuilder file = getTextFromFile();
        tv.setText(file);
        builder.setView(v);
        builder.create().show();
    }

    //获取信息，读取文件
    public StringBuilder getTextFromFile(){
        StringBuilder sb=new StringBuilder();
        try {
            InputStream is = getAssets().open("aboutme", MODE_PRIVATE);
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line=br.readLine())!=null) {
                sb.append(line+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb;
    }
}
