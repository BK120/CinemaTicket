package com.bk120.cinematicket.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.activitys.MapViewActivity;
import com.bk120.cinematicket.bean.AddressBean;
import com.bk120.cinematicket.bean.Cinema;
import com.bk120.cinematicket.bean.CinemaSign;
import com.bk120.cinematicket.bean.SearchFilmBean;
import com.bk120.cinematicket.bean.StringSign;
import com.bk120.cinematicket.constants.DianYingConstant;
import com.bk120.cinematicket.constants.JuHeConstant;
import com.bk120.cinematicket.utils.BaiBuDingWeiUtils;
import com.bk120.cinematicket.utils.JSONUtils;
import com.bk120.cinematicket.utils.NetWorkUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 *
 * 112.545064;37.962551
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
    private String mcontent="定位失败...";
    //控件
    private TextView current_tv;
    private ImageView current_iv;
    private WebView mWebView;
    //
    private ProgressBar pb;
    private TextView loading_tv;
    //http://api.map.baidu.com/place/search?query=海底捞&location=31.204055632862,121.41117785465&radius=1000&region=上海&output=html&src=yourCompanyName;
    private String fileUrl="http://api.map.baidu.com/place/search?query=电影院&location=";
    private String radius="&radius=10000&region=";
    //网络是否连接
    public boolean isNetWork=true;
    public YingYuanFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        rootView=inflater.inflate(R.layout.fragment_ying_yuan, container, false);
        utils=new BaiBuDingWeiUtils(getContext());
        initView();
        initWebView();
        setListener();
        initData();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    //由于从Fragment传入Activity或则Context用于判断网络状态存在问题，故此从外部Mainactivity传入一个人
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getIsNetWorkOk(StringSign sign){
        if ("NetOn".equals(sign.getSign())){
            isNetWork=true;
        }else if ("NetOff".equals(sign.getSign())){
            isNetWork=false;
        }
        Log.i("获取网络状态",sign.getSign());
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
                    Toast.makeText(getContext(),"位置信息异常！",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
   /* @Override
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
                    initNetWork();
                }
            }
        },500);
    }*/
   //初始化数据
    public void initData(){
        utils.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                utils.stop();
                if (mcontent==null){
                    current_tv.setText("定位失败...");
                }else {
                    current_tv.setText(mcontent);
                    initNetWork();
                }
            }
        },500);
    }
    //判断网络可用否
    private void initNetWork() {
        //传入Activity或则Context对象有问题可能有问题
        Log.i("是否有网络",isNetWork+"");
        if (isNetWork){
            //显示其他控件
            fileUrl=fileUrl+mlontitude+","+mlatitude+radius+mcontent+output+"&src=BK票儿";
            mWebView.setVisibility(View.VISIBLE);
            mWebView.loadUrl(fileUrl);
            pb.setVisibility(View.GONE);
            loading_tv.setVisibility(View.GONE);
        }else {
            //隐藏其他控件
            mWebView.setVisibility(View.GONE);
        }
    }
    //初始化控件
    private void initView() {
        current_iv= (ImageView) rootView.findViewById(R.id.yingyuanfragment_currentaddress_iv);
        current_tv= (TextView) rootView.findViewById(R.id.yingyuanfragment_currentaddress_tv);
        mWebView= (WebView) rootView.findViewById(R.id.yingyuanfragment_webview);
        pb= (ProgressBar) rootView.findViewById(R.id.yingyuanfragment_pb);
        loading_tv= (TextView) rootView.findViewById(R.id.yingyuanfragment_loding_tv);
    }

    //初始化WebView
    private void initWebView() {
        //设置与JavaScript交互
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置页面缩放
        mWebView.getSettings().setSupportZoom(true);
        //显示缩放工具
        mWebView.getSettings().setBuiltInZoomControls(false);
        //NORMAL：正常显示，没有渲染变化。SINGLE_COLUMN：把所有内容放到WebView组件等宽的一列中。
        // NARROW_COLUMNS：可能的话，使所有列的宽度不超过屏幕宽度。即规范页面布局
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        mWebView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        //mWebView.getSettings().setAppCacheEnabled(true);//是否使用缓存
        mWebView.getSettings().setDomStorageEnabled(true);//DOM Storage
        /*//播放视频设置
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);*/
        //设置字体大小
        //mWebView.getSettings().setDefaultFontSize(18);
        //加载网页

        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
    }

}
