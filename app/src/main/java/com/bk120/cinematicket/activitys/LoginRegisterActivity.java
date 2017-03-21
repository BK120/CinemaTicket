package com.bk120.cinematicket.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.adapter.LoginRegisterPagerAdapter;
import com.bk120.cinematicket.bean.StringSign;
import com.bk120.cinematicket.fragments.UserLoginFragment;
import com.bk120.cinematicket.fragments.UserRegisterFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登录注册Activity
 */
public class LoginRegisterActivity extends FragmentActivity implements View.OnClickListener{
    @BindView(R.id.loginregisteractivity_viewpager) ViewPager mViewPager;
    @BindView(R.id.loginregisteractivity_login_tv) TextView login_tv;
    @BindView(R.id.loginregisteractivity_register_tv) TextView register_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initViewPager();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    //处理注册成功信号量，改变当前Fragment状态
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSign(StringSign sign){
        if (sign.getSign().equals("RegisterSuccess")){
            showLoginFragment();
        }
    }

    //初始化ViewPager
    private void initViewPager() {
        List<Fragment> lists=new ArrayList<>();
        lists.add(new UserLoginFragment());
        lists.add(new UserRegisterFragment());
        mViewPager.setAdapter(new LoginRegisterPagerAdapter(getSupportFragmentManager(),lists));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        showLoginFragment();
                        break;
                    case 1:
                        showRegisterFragment();
                        break;
                }
            }
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //设置监听
    private void initListener() {
        login_tv.setOnClickListener(this);
        register_tv.setOnClickListener(this);
    }

    //退出当前页面
    public void back(View view){
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginregisteractivity_login_tv:
                showLoginFragment();
                //登录界面
                break;
            case R.id.loginregisteractivity_register_tv:
                //注册界面
                showRegisterFragment();
                break;
        }
    }
    //显示注册Fragment
    private void showRegisterFragment() {
        mViewPager.setCurrentItem(1);
        login_tv.setBackgroundResource(R.color.colorWhite);
        login_tv.setTextColor(getResources().getColor(R.color.colorGray));
        register_tv.setBackgroundResource(R.color.card_bg_green);
        register_tv.setTextColor(getResources().getColor(R.color.colorPurple));
    }

    //显示登录Fragment
    private void showLoginFragment() {
        mViewPager.setCurrentItem(0);
        login_tv.setBackgroundResource(R.color.card_bg_green);
        login_tv.setTextColor(getResources().getColor(R.color.colorPurple));
        register_tv.setBackgroundResource(R.color.colorWhite);
        register_tv.setTextColor(getResources().getColor(R.color.colorGray));
    }

}
