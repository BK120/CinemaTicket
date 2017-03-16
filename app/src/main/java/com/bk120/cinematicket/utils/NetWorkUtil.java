package com.bk120.cinematicket.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by renxuon 2016/11/28.
 * 获取当前网络状态信息
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.INTERNET" />
 */

public class NetWorkUtil {
    /**
     * 获取当前网络是否可用
     * @param mActivity
     * @return
     */
    public static boolean isNetWorkAvaliable(Activity mActivity){
        ConnectivityManager mcManager= (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(mcManager==null){
            return false;
        }
        NetworkInfo[] infos = mcManager.getAllNetworkInfo();
        if(infos!=null&&infos.length>0){
            for(int i=0;i<infos.length;i++){
                if(infos[i].getState()==NetworkInfo.State.CONNECTED){
                    //已经连接
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetWorkAvaliable(Context context){
        ConnectivityManager mcManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(mcManager==null){
            return false;
        }
        NetworkInfo[] infos = mcManager.getAllNetworkInfo();
        if(infos!=null&&infos.length>0){
            for(int i=0;i<infos.length;i++){
                if(infos[i].getState()==NetworkInfo.State.CONNECTED){
                    //已经连接
                    return true;
                }
            }
        }
        return false;
    }
    public static String getIpAddress(Context mContext){
        NetworkInfo info = ((ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
            Toast.makeText(mContext,"当前无网络！请先打开网络设置。",Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    /**
     * 将得到的int类型的IP转换为String类型
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

}
