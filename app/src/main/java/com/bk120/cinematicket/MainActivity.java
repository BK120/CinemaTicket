package com.bk120.cinematicket;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.bk120.cinematicket.bean.StringSign;
import com.bk120.cinematicket.bean.User;
import com.bk120.cinematicket.constants.MainConstant;
import com.bk120.cinematicket.db.UserInfoDao;
import com.bk120.cinematicket.fragments.DianYingFragment;
import com.bk120.cinematicket.fragments.SheQuFragment;
import com.bk120.cinematicket.fragments.WodeFragment;
import com.bk120.cinematicket.fragments.YingYuanFragment;
import com.bk120.cinematicket.utils.NetWorkUtil;
import com.bk120.cinematicket.utils.SharePreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    //获取下面的控件
    @BindView(R.id.mainactivity_dianying)
    TextView dianying;
    @BindView(R.id.mainactivity_yingyuan)
    TextView yingyuan;
    @BindView(R.id.mainactivity_shequ)
    TextView shequ;
    @BindView(R.id.mainactivity_wode)
    TextView wode;
    //部分Fragment
    private DianYingFragment dianyingFm;
    YingYuanFragment yingyuanFm;SheQuFragment shequFm;WodeFragment wodeFm;
    //Fragment管理器
    private FragmentManager fManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定注解
        ButterKnife.bind(this);
        fManager=this.getSupportFragmentManager();
        initListener();
        initFragments();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        initShow();
        //设置缓存界面数
    }
    //判断网络状态
    public void initNetWork(){
        boolean netWorkAvaliable = NetWorkUtil.isNetWorkAvaliable(this);
        if (netWorkAvaliable){
            EventBus.getDefault().post(new StringSign("NetOn"));
        }else {
            EventBus.getDefault().post(new StringSign("NetOff"));
        }
    }
    //开始显示
    private void initShow() {
        //根据上一次记录，选择显示Fragment
        int anInt = SharePreferencesUtils.getInt(this, MainConstant.CURRENT_MAIN_FRAGMENT, 0);
        switch (anInt){
            case 0:
                showDianYingFragment();
                break;
            case 1:
                showDianYingYuanFragment();
                initNetWork();
                break;
            case 2:
                showSheQuFragment();
                break;
            case 3:
                showWoDeFragment();
                break;
        }

    }

    //初始化所有的Fragment片段
    private void initFragments() {
        dianyingFm=new DianYingFragment();
        yingyuanFm=new YingYuanFragment();
        shequFm=new SheQuFragment();
        wodeFm=new WodeFragment();
        fManager.beginTransaction().add(R.id.mainactivity_framelayout,dianyingFm).commit();
        fManager.beginTransaction().add(R.id.mainactivity_framelayout,yingyuanFm).commit();
        fManager.beginTransaction().add(R.id.mainactivity_framelayout,shequFm).commit();
        fManager.beginTransaction().add(R.id.mainactivity_framelayout,wodeFm).commit();
    }

    //注册监听
    private void initListener() {
        dianying.setOnClickListener(this);
        yingyuan.setOnClickListener(this);
        shequ.setOnClickListener(this);
        wode.setOnClickListener(this);
    }

    //点击切换Fragment
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mainactivity_dianying:
                showDianYingFragment();
                SharePreferencesUtils.putInt(this, MainConstant.CURRENT_MAIN_FRAGMENT,0);
                 break;
            case R.id.mainactivity_yingyuan:
                showDianYingYuanFragment();
                initNetWork();
                SharePreferencesUtils.putInt(this, MainConstant.CURRENT_MAIN_FRAGMENT,1);
                break;
            case R.id.mainactivity_shequ:
                showSheQuFragment();
                SharePreferencesUtils.putInt(this, MainConstant.CURRENT_MAIN_FRAGMENT,2);
                break;
            case R.id.mainactivity_wode:
                showWoDeFragment();
                SharePreferencesUtils.putInt(this, MainConstant.CURRENT_MAIN_FRAGMENT,3);
                break;
        }
    }
    //隐藏所有Fragment
    public void hideAllFragment(){
        fManager.beginTransaction().hide(wodeFm).commit();
        fManager.beginTransaction().hide(dianyingFm).commit();
        fManager.beginTransaction().hide(yingyuanFm).commit();
        fManager.beginTransaction().hide(shequFm).commit();
    }
    //展示电影Fragment
    private void showDianYingFragment(){
        //设置默认状态
        initStatus();
        //改变第一个的颜色
        dianying.setTextColor(Color.parseColor("#11CD6E"));
        Drawable dianying2_drwable=getResources().getDrawable(R.mipmap.dianying2);
         //不调用这句 将不显示drawable
        dianying2_drwable.setBounds(0,0,dianying2_drwable.getMinimumWidth(), dianying2_drwable.getMinimumHeight());
        dianying.setCompoundDrawables(null,dianying2_drwable,null,null);
        //切换Fragment
        hideAllFragment();
        fManager.beginTransaction().show(dianyingFm).commit();
        //fManager.beginTransaction().replace(R.id.mainactivity_framelayout,dianyingFm).commit();

    }
    //展示电影院Fragment
    private void showDianYingYuanFragment(){
        initStatus();
        yingyuan.setTextColor(Color.parseColor("#11CD6E"));
        Drawable yingyuan2_drwable=getResources().getDrawable(R.mipmap.dianyingyuan2);
        //不调用这句 将不显示drawable
        yingyuan2_drwable.setBounds(0,0,yingyuan2_drwable.getMinimumWidth(), yingyuan2_drwable.getMinimumHeight());
        yingyuan.setCompoundDrawables(null,yingyuan2_drwable,null,null);
        //切换Fragment
        hideAllFragment();
        fManager.beginTransaction().show(yingyuanFm).commit();
        //fManager.beginTransaction().replace(R.id.mainactivity_framelayout,yingyuanFm).commit();
    }
    //展示社区Fragment
    private void showSheQuFragment(){
        initStatus();
        shequ.setTextColor(Color.parseColor("#11CD6E"));
        Drawable shequ2_drwable=getResources().getDrawable(R.mipmap.shequ2);
        //不调用这句 将不显示drawable
        shequ2_drwable.setBounds(0,0,shequ2_drwable.getMinimumWidth(), shequ2_drwable.getMinimumHeight());
        shequ.setCompoundDrawables(null,shequ2_drwable,null,null);
        //切换Fragment
        hideAllFragment();
        fManager.beginTransaction().show(shequFm).commit();
       // fManager.beginTransaction().replace(R.id.mainactivity_framelayout,shequFm).commit();
    }
    //显示我的Fragment
    private void showWoDeFragment(){
        initStatus();
        wode.setTextColor(Color.parseColor("#11CD6E"));
        Drawable wode2_drwable=getResources().getDrawable(R.mipmap.wode2);
        //不调用这句 将不显示drawable
        wode2_drwable.setBounds(0,0,wode2_drwable.getMinimumWidth(), wode2_drwable.getMinimumHeight());
        wode.setCompoundDrawables(null,wode2_drwable,null,null);
        //切换Fragment
        hideAllFragment();
        fManager.beginTransaction().show(wodeFm).commit();
        //fManager.beginTransaction().replace(R.id.mainactivity_framelayout,wodeFm).commit();
    }



    //下面的默认的颜色
    public void initStatus(){
        //设置默认字体颜色
        dianying.setTextColor(Color.parseColor("#323232"));
        yingyuan.setTextColor(Color.parseColor("#323232"));
        shequ.setTextColor(Color.parseColor("#323232"));
        wode.setTextColor(Color.parseColor("#323232"));
        //设置默认图片
        Drawable dianying1_drwable=getResources().getDrawable(R.mipmap.dianying1);
        dianying1_drwable.setBounds(0,0,dianying1_drwable.getMinimumWidth(), dianying1_drwable.getMinimumHeight());
        Drawable yingyuan1_drwable=getResources().getDrawable(R.mipmap.dianyingyuan1);
        yingyuan1_drwable.setBounds(0,0,yingyuan1_drwable.getMinimumWidth(), yingyuan1_drwable.getMinimumHeight());
        Drawable shequ1_drwable=getResources().getDrawable(R.mipmap.shequ1);
        shequ1_drwable.setBounds(0,0,shequ1_drwable.getMinimumWidth(), shequ1_drwable.getMinimumHeight());
        Drawable wode1_drwable=getResources().getDrawable(R.mipmap.wode1);
        wode1_drwable.setBounds(0,0,wode1_drwable.getMinimumWidth(), wode1_drwable.getMinimumHeight());
        dianying.setCompoundDrawables(null,dianying1_drwable,null,null);
        yingyuan.setCompoundDrawables(null,yingyuan1_drwable,null,null);
        shequ.setCompoundDrawables(null,shequ1_drwable,null,null);
        wode.setCompoundDrawables(null,wode1_drwable,null,null);

    }

}
