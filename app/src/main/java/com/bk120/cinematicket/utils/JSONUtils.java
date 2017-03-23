package com.bk120.cinematicket.utils;

import android.util.Log;

import com.bk120.cinematicket.bean.BuyFilm;
import com.bk120.cinematicket.bean.Chat;
import com.bk120.cinematicket.bean.Cinema;
import com.bk120.cinematicket.bean.CinemaSign;
import com.bk120.cinematicket.bean.CinemaSupFilm;
import com.bk120.cinematicket.bean.City;
import com.bk120.cinematicket.bean.Movie;
import com.bk120.cinematicket.constants.MainConstant;

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

    //解析获取所有城市信息
    public static List<City> getAllCityInfo(String sign) {
        List<City> list=null;
        try {
            JSONObject rootObject=new JSONObject(sign);
            if (!"success".equals(rootObject.getString("reason"))){
                //获取失败
                return null;
            }
            list=new ArrayList<>();
            JSONArray result = rootObject.getJSONArray("result");
            for(int i=0;i<result.length();i++){
                //获取单个城市
                JSONObject obj= (JSONObject) result.get(i);
                String id = obj.getString("id");
                String city_name = obj.getString("city_name");
                String count = obj.getString("count");
                City city=new City(id,city_name,count);
                list.add(city);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    //获取解析所有热映电影的信息
    public static List<Movie> getAllPlayingMovies(String sign) {
        List<Movie> list=null;
        try {
            JSONObject rootObject=new JSONObject(sign);
            if (!"success".equals(rootObject.getString("reason"))){
                //获取失败
                return null;
            }
            list=new ArrayList<>();
            JSONArray result = rootObject.getJSONArray("result");
            for(int i=0;i<result.length();i++){
                JSONObject obj= (JSONObject) result.get(i);
                String id = obj.getString("movieId");
                String name = obj.getString("movieName");
                String pic_url = obj.getString("pic_url");
                Movie movie=new Movie(id,name,pic_url);
                list.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
    //处理解析当前城市当前电影支持的影院信息
    public static List<CinemaSupFilm> getCinemaSupFilmLists(String sign) {
        List<CinemaSupFilm> list=null;
        try {
            JSONObject rootObject=new JSONObject(sign);
            if (!"success".equals(rootObject.getString("reason"))){
                //获取失败
                return null;
            }
            list=new ArrayList<>();
            JSONArray result = rootObject.getJSONArray("result");
            for(int i=0;i<result.length();i++){
                JSONObject obj= (JSONObject) result.get(i);
                String cinemaId = obj.getString("cinemaId");
                String cinemaName = obj.getString("cinemaName");
                String address = obj.getString("address");
                String latitude = obj.getString("latitude");
                String longitude = obj.getString("longitude");
                CinemaSupFilm csf=new CinemaSupFilm(cinemaId,cinemaName,address,latitude,longitude);
                list.add(csf);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    //解析即将购买的电影详情
    public static BuyFilm getBuyFilms(String sign) {
        BuyFilm buyFilm=null;
        try {
            JSONObject rootObj=new JSONObject(sign);
            //返回失败
            if (!"成功的返回".equals(rootObj.getString("reason"))){
                return null;
            }
            JSONObject result = rootObj.getJSONObject("result");
            String movieid = result.getString("movieid");
            String rating = result.getString("rating");
            String genres = result.getString("genres");
            String language = result.getString("language");
            String title = result.getString("title");
            String poster = result.getString("poster");
            String directors = result.getString("directors");
            String actors = result.getString("actors");
            String plot_simple = result.getString("plot_simple");
            String country = result.getString("country");
            buyFilm=new BuyFilm(movieid,title,genres,language);
            buyFilm.setActors(actors);
            buyFilm.setCountry(country);
            buyFilm.setDirectors(directors);
            buyFilm.setPlot_simple(plot_simple);
            buyFilm.setPoster(poster);
            buyFilm.setRating(rating);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return buyFilm;
    }
    //解析一个聊天信息
    public static Chat getChat(String sign) {
        Chat chat=null;
        try {
            JSONObject rootObj=new JSONObject(sign);
            //返回失败
            if (!"100000".equals(rootObj.getString("code"))){
                return null;
            }
            String text = rootObj.getString("text");
            chat=new Chat(0, MainConstant.ROBOT_NAME,text,DateUtils.getCurrentTime());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return chat;
    }
}
