package com.bk120.cinematicket.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.activitys.AddressActivity;
import com.bk120.cinematicket.activitys.ScanActivity;
import com.bk120.cinematicket.adapter.DianYingPageAdapter;
import com.bk120.cinematicket.bean.AddressBean;
import com.bk120.cinematicket.utils.BaiBuDingWeiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;


/**
 * A simple {@link Fragment} subclass.
 * 电影页面fragment,用EventBus进行通信传递
 */
public class DianYingFragment extends Fragment {
    ViewPager mViewPager;
    TextView reyingTv;
    TextView zhaopianTv;
    //地址空间
    Button addressBtn;
    //扫描图片
    ImageView scan_iv;
    private View rootView;
    //当前地址默认上海
    private String currentAddress="定位...";
    private BaiBuDingWeiUtils utils;
    public DianYingFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        utils=new BaiBuDingWeiUtils(getContext());
        rootView=inflater.inflate(R.layout.fragment_dian_ying, container, false);
        reyingTv= (TextView) rootView.findViewById(R.id.mainactivity_dianying_fragment_toolbar_reying);
        zhaopianTv= (TextView) rootView.findViewById(R.id.mainactivity_dianying_fragment_toolbar_zhaopian);
        addressBtn= (Button) rootView.findViewById(R.id.mainactivity_dianying_fragment_toolbar_address);
        mViewPager= (ViewPager) rootView.findViewById(R.id.mainactivity_dianying_fragment_viewpager);
        scan_iv= (ImageView) rootView.findViewById(R.id.mainactivity_dianying_fragment_toolbar_scan);
        initViewPager();
        initToolBarItem();
        setListener();
        return rootView;
    }

    private void setListener() {
        utils.setListener(new BaiBuDingWeiUtils.BaiDuDingWeiListener() {
            @Override
            public void getTime(String time) {
            }
            @Override
            public void getLatitude(String latitude) {
            }
            @Override
            public void getLontitude(String lontitude) {
            }
            @Override
            public void getRadius(String radius) {
            }
            @Override
            public void getAddress(String address) {
                //截取地址  山西省太原市   省与市之间的数字
                int first=address.indexOf("省");
                int end = address.indexOf("市");
                String newOne = address.substring(first+1, end);
                currentAddress=newOne;
            }
            @Override
            public void getLocationDes(String des) {
            }
            @Override
            public void error(String s) {
                currentAddress="定位...";
            }
        });
        utils.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                utils.stop();
                EventBus.getDefault().post(new AddressBean(currentAddress));
            }
        },500);
        //进入扫描界面
        scan_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),ScanActivity.class);
                startActivity(i);
            }
        });
    }

    //初始化toolbar上面的拖动条,注册监听
    private void initToolBarItem() {
        reyingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReYingFragment();
            }
        });
        zhaopianTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showZhaoPianFragment();
            }
        });
        //展示地址页面，回调是获取一个地址，或则联网是获取一个地址，地址长度大于2时省略后面的部分  比如
        //乌鲁木齐  为 乌鲁..    显示如此
        addressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), AddressActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.acrivity1_push_in,0);
            }
        });

    }

    //初始化ViewPager
    private void initViewPager() {
        List<Fragment> list=new ArrayList<>();
        list.add(new DianYing_ReYingFragment());
        list.add(new DianYing_ZhaoPianFragment());
        //此处不能使用getFragmentManager，否则会有其中的Fragment不起作用
        FragmentManager manager =this.getChildFragmentManager();
        mViewPager.setAdapter(new DianYingPageAdapter(manager,list));
        //状态改变
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                //改变文字颜色和背景
                switch (position){
                    case 0:
                       showReYingFragment();
                        break;
                    case 1:
                        showZhaoPianFragment();
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    //展示热映Fragment
    public void  showReYingFragment(){
        reyingTv.setTextColor(Color.parseColor("#ff0000"));
        reyingTv.setBackgroundResource(R.drawable.dianying_fragment_toolbar_drag_shape);
        zhaopianTv.setTextColor(Color.parseColor("#ffffff"));
        zhaopianTv.setBackgroundResource(R.drawable.dianying_fragment_toolbar_drag_shape_nor);
        mViewPager.setCurrentItem(0);
    }
    //展示找片Fragment
    public void showZhaoPianFragment(){
        zhaopianTv.setTextColor(Color.parseColor("#ff0000"));
        zhaopianTv.setBackgroundResource(R.drawable.dianying_fragment_toolbar_drag_shape);
        reyingTv.setTextColor(Color.parseColor("#ffffff"));
        reyingTv.setBackgroundResource(R.drawable.dianying_fragment_toolbar_drag_shape_nor);
        mViewPager.setCurrentItem(1);
    }
    //获取设置选择的地址
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setAddress(AddressBean bean){
        String address=bean.getAddress();
        currentAddress=address;
        if (address.length()>2){
            String newAddress=address.substring(0,2)+"..";
            addressBtn.setText(newAddress);
            return;
        }
        addressBtn.setText(bean.getAddress());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销
        EventBus.getDefault().unregister(this);
    }
}
