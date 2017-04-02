package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bk120.cinematicket.zxing.activity.CaptureActivity;
import com.bk120.cinematicket.R;

import java.util.regex.Pattern;

/**
 * 扫描二维码界面
 */
public class ScanActivity extends Activity {
    private TextView tv;
    //扫描结果
    private String res;
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        tv= (TextView) this.findViewById(R.id.scanactivty_tv);
        mWebView = (WebView) findViewById(R.id.scanactivity_webview);
        initWebView();
        Intent i=new Intent(this, CaptureActivity.class);
        //打开一个已经提供给我们的Activity
        startActivityForResult(i,1);
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
        mWebView.setWebViewClient(new WebViewClient() {
            //解决方法：以"http","https"开头的url在本页用webview进行加载，其他链接进行跳转
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                    return false;
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    ScanActivity.this.finish();
                    return true;
                }
            }
        });

    }

    //判断结果是否为一个网站
    private boolean resultIsWeb(String s) {
        //如果为www开头自动转换成http://www.
        if(s.startsWith("www.")){
            res="http://"+s;
            return true;
        }
       return s.startsWith("http");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null){
            this.finish();
            return;
        }
        if (requestCode==1){
            res=data.getExtras().getString("result");
            boolean isWeb = resultIsWeb(res);
            Log.i("是否：",isWeb+"");
            if (isWeb){
                tv.setVisibility(View.INVISIBLE);
                mWebView.loadUrl(res);
            }else{
                mWebView.setVisibility(View.INVISIBLE);
                tv.setText(res);
            }
        }
    }

}
