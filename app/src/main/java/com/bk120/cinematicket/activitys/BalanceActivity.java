package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.bean.Card;
import com.bk120.cinematicket.bean.User;
import com.bk120.cinematicket.constants.MainConstant;
import com.bk120.cinematicket.db.CardInfoDao;
import com.bk120.cinematicket.db.UserInfoDao;

import java.util.List;

public class BalanceActivity extends Activity {
    private ViewFlipper viewFlipper;
    private TextView balance_tv;
    private UserInfoDao uDao;
    //当前在线用户
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        initView();
    }
    //初始化控件
    private void initView() {
        viewFlipper= (ViewFlipper) this.findViewById(R.id.balanceactivity_viewflipper);
        for(int i=0;i<MainConstant.GALLERIMAGES.length;i++){
            ImageView view=new ImageView(this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
            view.setImageResource(MainConstant.GALLERIMAGES[i]);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(view,i);
        }
        viewFlipper.setFlipInterval(2500);
        viewFlipper.setAutoStart(true);
        balance_tv= (TextView) this.findViewById(R.id.balanceactivity_balance_tv);
        uDao=new UserInfoDao(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
    //初始化数据
    private void initData() {
        user=uDao.selectOnLine();
        balance_tv.setText(user.getBalance()+"元");
    }

    //充值
    public void addMoney(View view){
        //打开一个Dialog样式的Activity
        Intent i=new Intent(this,AddBalanceActivity.class);
        startActivity(i);
    }
    //提现
    public void getMoney(View view){
        Intent i=new Intent(this,GetBalanceActivity.class);
        startActivity(i);
    }
    //退出
    public void back(View view){
        this.finish();
    }
}
