package com.bk120.cinematicket.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.activitys.MapViewActivity;
import com.bk120.cinematicket.bean.AddressBean;
import com.bk120.cinematicket.utils.BaiBuDingWeiUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class YingYuanFragment extends Fragment {
    private View rootView;

    //当前地址信息
    private String currentUrl=null;
    private String one="http://api.map.baidu.com/marker?" ;
    private String location="location=";
    private String title="&title=我的位置&content=";
    private String output="&output=html";
    private BaiBuDingWeiUtils utils;
    private String mlatitude="0.00";
    private String mlontitude="0.00";
    private String mcontent="bk120";
    //控件
    private TextView current_tv;
    private ImageView current_iv;
    public YingYuanFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_ying_yuan, container, false);
        utils=new BaiBuDingWeiUtils(getContext());
        initView();
        setListener();
        return rootView;
    }
    //设置监听
    private void setListener() {
        //获取数据
        utils.setListener(new BaiBuDingWeiUtils.BaiDuDingWeiListener() {
            @Override
            public void getTime(String time) {
            }
            @Override
            public void getLatitude(String latitude) {
                mlatitude=latitude;
            }
            @Override
            public void getLontitude(String lontitude) {
                mlontitude=lontitude;
            }
            @Override
            public void getRadius(String radius) {

            }
            @Override
            public void getAddress(String address) {

            }
            @Override
            public void getLocationDes(String des) {
                mcontent=des;
            }
            @Override
            public void error(String s) {
                currentUrl=null;
            }
        });
        //点击图标
        current_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), MapViewActivity.class);
                if (mcontent!=null){
                    //能获取到结果  构造地址
                    currentUrl=one+location+mlatitude+","+mlontitude+title+mcontent+output;
                    i.putExtra("addressUrl",currentUrl);
                    startActivity(i);
                }else {
                    Toast.makeText(getContext(),"请先检查网络！",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        utils.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                utils.stop();
                if (mcontent==null){
                    current_tv.setText("定位失败...");
                }else {
                    current_tv.setText(mcontent);
                }
            }
        },1500);
    }

    private void initView() {
        current_iv= (ImageView) rootView.findViewById(R.id.yingyuanfragment_currentaddress_iv);
        current_tv= (TextView) rootView.findViewById(R.id.yingyuanfragment_currentaddress_tv);
    }

}
