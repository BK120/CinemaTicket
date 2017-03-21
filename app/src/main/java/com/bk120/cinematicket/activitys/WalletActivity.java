package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.adapter.CardAdapter;
import com.bk120.cinematicket.bean.Card;
import com.bk120.cinematicket.bean.StringSign;
import com.bk120.cinematicket.bean.User;
import com.bk120.cinematicket.db.CardInfoDao;
import com.bk120.cinematicket.db.UserInfoDao;
import com.bk120.cinematicket.fragments.AddCardFragment;
import com.bk120.cinematicket.views.RecycleViewDividerL;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 钱包界面，显示当前信用卡种类如，建行，农行，工行，支付宝，微信，QQ等
 */
public class WalletActivity extends FragmentActivity {
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    //当前在线的唯一一个用户
    private User user;
    private UserInfoDao uDao;
    private CardInfoDao cDao;
    private List<Card> lists;
    private CardAdapter adpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        EventBus.getDefault().register(this);
        uDao=new UserInfoDao(this);
        cDao=new CardInfoDao(this);
        user=uDao.selectOnLine();
        initView();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSign(StringSign sign){
        if (sign.getSign().equals("AddCard Success")){
           drawerLayout.closeDrawer(Gravity.RIGHT);
            initRecycleView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRecycleView();
    }
    //初始化RecycleView
    private void initRecycleView() {
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        //RecycleViewDividerL dividerL=new RecycleViewDividerL(this,LinearLayout.VERTICAL,10, Color.parseColor("#00000000"));
        //recyclerView.addItemDecoration(dividerL);
        lists=cDao.selectAll(user.getUsername());
        adpter=new CardAdapter(lists,this);
        recyclerView.setAdapter(adpter);
    }

    private void setListener() {
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }
            public void onDrawerOpened(View drawerView) {
            }
            public void onDrawerClosed(View drawerView) {
            }
            public void onDrawerStateChanged(int newState) {
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.walletactivity_framelayout,
                new AddCardFragment()).commit();
    }

    //添加账户弹出侧边栏
    public void addCard(View view){
        drawerLayout.openDrawer(Gravity.RIGHT);
    }

    private void initView() {
        drawerLayout= (DrawerLayout) this.findViewById(R.id.walletactivity_drawerlayout);
        recyclerView= (RecyclerView) this.findViewById(R.id.walletactivity_main_recycleview);
    }

    //退出当前页
    public void back(View view){
        this.finish();
    }
}
