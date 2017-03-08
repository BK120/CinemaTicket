package com.bk120.mytest_cehuacaidan;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;

//侧滑菜单的使用
public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    public static final String TAG=MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout= (DrawerLayout) this.findViewById(R.id.drawer_layout);
        //设置侧滑监听
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                Log.i(TAG,drawerView.getTag()+";"+slideOffset);
                // 判断是否左菜单
                if (drawerView.getTag().equals("left")) {
                    // 得到contentView
                    View content = mDrawerLayout.getChildAt(0);
                    int offset = (int) (drawerView.getWidth() * slideOffset);
                    content.setTranslationX(offset);
                    content.setScaleX(1 - slideOffset * 0.1f);
                    content.setScaleY(1 - slideOffset * 0.1f);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }
}
