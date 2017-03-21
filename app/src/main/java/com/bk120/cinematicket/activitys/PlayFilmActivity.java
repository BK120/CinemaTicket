package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.bean.FilmUrlBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.InvocationTargetException;

public class PlayFilmActivity extends Activity {
    private WebView mWebView;
    private String playUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_film);
        playUrl=getIntent().getStringExtra("filmUrl");
        initWebView();
    }
    //初始化WebView
    private void initWebView() {
        mWebView = (WebView)findViewById(R.id.playfilmactivity_webview);
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
           //解决方法：以"http","https"开头的url在本页用webview进行加载，其他链接进行跳转
           @Override
           public boolean shouldOverrideUrlLoading(WebView view, String url) {
               if(url.startsWith("http:") || url.startsWith("https:") ) {
                   view.loadUrl(url);
                   return false;
               }else{
                   Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                   startActivity(intent);
                   PlayFilmActivity.this.finish();
                   return true;
               }
           }
       });
        mWebView.loadUrl(playUrl);
    }

    //关闭当前页
    public void back(View view){
        this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //通过反射暂停播放，同时还可以继续播放，等
        try {
            mWebView.getClass().getMethod("onPause").invoke(mWebView,(Object[])null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
