package com.bk120.cinematicket.utils;

import com.bk120.cinematicket.bean.Cinema;
import com.bk120.cinematicket.bean.CinemaSign;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bk120 on 2017/3/16.
 * 对返回的Json数据解析
 */

public class JSONUtils {
    //处理获取电影院的JSON解析
    public static List<Cinema> getCinemaLists(CinemaSign sign) {
        //解析
        List<Cinema> list=null;
        try {
            JSONObject rootObject=new JSONObject(sign.getSign());
            if ("success".equals(rootObject.getString("reason"))){
                list=new ArrayList<>();
                JSONArray result = rootObject.getJSONArray("result");
                if (result.length()==0){
                    return null;
                }
                //初始化每个对象
                for(int i=0;i<result.length();i++){
                    JSONObject obj = (JSONObject) result.get(i);
                    String name=obj.getString("cinemaName");
                    String address=obj.getString("address");
                    String telephone = obj.getString("telephone");
                    String latitude = obj.getString("latitude");
                    String longitude = obj.getString("longitude");
                    String trafficRoutes = obj.getString("trafficRoutes");
                    String distance = obj.getString("distance");
                    Cinema cinema=new Cinema(name,address);
                    cinema.setTelephone(telephone);
                    cinema.setTrafficRoutes(trafficRoutes);
                    cinema.setDistance(distance);
                    cinema.setLatitude(latitude);
                    cinema.setLongtitudel(longitude);
                    list.add(cinema);
                }

            }else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
