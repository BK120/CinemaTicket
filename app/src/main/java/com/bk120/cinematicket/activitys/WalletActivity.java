package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.fragments.AddCardFragment;

/**
 * 钱包界面，显示当前信用卡种类如，建行，农行，工行，支付宝，微信，QQ等
 */
public class WalletActivity extends FragmentActivity {
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        initView();
        setListener();
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
