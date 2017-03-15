package com.bk120.cinematicket.utils;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by bk120 on 2017/3/13.
 * 百度定位工具类
 */

public class BaiBuDingWeiUtils {
    private Context context;
    private LocationClient locationClient;
    private BDLocationListener bdLocationListener;
    private BaiDuDingWeiListener listener;
    //构造器---手动调用
    public BaiBuDingWeiUtils(Context context){
        this.context=context;
        init();
    }
    public void setListener(BaiDuDingWeiListener l){
        this.listener=l;
    }
    //初始化操作
    private void init() {
        locationClient=new LocationClient(context);
        bdLocationListener=new MyLocationListener();
        locationClient.registerLocationListener(bdLocationListener);
        initLocation();
    }
    /**
     * 设置定位参数包括：定位模式（高精度定位模式、
     * 低功耗定位模式和仅用设备定位模式），返回坐标类型，是否打开GPS，
     * 是否返回地址信息、位置语义化信息、POI信息等等。
     */
    private void initLocation() {
        LocationClientOption option=new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span=5000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        locationClient.setLocOption(option);
    }

    //开始定位---手动调用
    public void start(){
        locationClient.start();
    }
    //定位结束--手动调用
    public void stop(){
        locationClient.stop();
    }

    class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //获取定位结果
            listener.getTime(bdLocation.getTime());
            listener.getLatitude(bdLocation.getLatitude()+"");
            listener.getLontitude(bdLocation.getLongitude()+"");
            listener.getRadius(bdLocation.getRadius()+"");
          if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                //网络定位结果
              listener.getAddress(bdLocation.getAddrStr());
            } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
                listener.error("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
              listener.error("网络不同导致定位失败，请检查网络是否通畅");
            } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
              listener.error("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            listener.getLocationDes(bdLocation.getLocationDescribe());    //位置语义化信息
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }
    //回调接口
    public interface BaiDuDingWeiListener {
        //定位时间
        void getTime(String time);
        //经度
        void getLatitude(String latitude);
        //纬度
        void getLontitude(String lontitude);
        //定位精度
        void getRadius(String radius);
        //地址
        void getAddress(String address);
        //定位描述
        void getLocationDes(String des);
        //定位失败
        void error(String s);
    }

}
