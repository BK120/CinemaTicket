package com.bk120.cinematicket.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.adapter.ReYingAdapter;
import com.bk120.cinematicket.bean.AddressBean;
import com.bk120.cinematicket.bean.City;
import com.bk120.cinematicket.bean.CitySign;
import com.bk120.cinematicket.bean.Movie;
import com.bk120.cinematicket.bean.MovieSign;
import com.bk120.cinematicket.bean.StringSign;
import com.bk120.cinematicket.constants.JuHeConstant;
import com.bk120.cinematicket.utils.JSONUtils;
import com.bk120.cinematicket.views.RecycleViewDividerL;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * //太原  id  38
 */
public class DianYing_ReYingFragment extends Fragment {
    private View rootView;
    private ProgressBar pb;
    private TextView loding_tv;
    private RecyclerView recyclerView;
    //当前地址值
    private String currentAddress;
    //所有可以查询的城市信息
    private List<City> cityLists;
    //城市ID
    private String city_id="-1";
    //当前城市正在热映的所有电影
    private List<Movie> movieLists;

    public DianYing_ReYingFragment() {
    }
    //获取设置选择的地址
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setAddress(AddressBean bean){
        String address=bean.getAddress();
        if ("定位...".equals(address)){
            Toast.makeText(getContext(),"位置信息获取失败！",Toast.LENGTH_SHORT).show();
            return;
        }
        pb.setVisibility(View.GONE);
        loding_tv.setVisibility(View.GONE);
        currentAddress=address;
        if (cityLists==null){
            Toast.makeText(getContext(),"数据获取异常！",Toast.LENGTH_SHORT).show();
            return;
        }
        //检查城市id
        city_id=getCityId(cityLists,currentAddress);
        if ("-1".equals(city_id)){
            Toast.makeText(getContext(),"当前城市不支持查询！",Toast.LENGTH_SHORT).show();
            return;
        }else {
            initAllMovieLists();
        }

    }

    private void initAllMovieLists() {
        OkHttpClient client=new OkHttpClient.Builder().build();
        Request request=new Request.Builder().url(JuHeConstant.CURRENT_PLAYING_FILM_URL+city_id).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("错误信息",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventBus.getDefault().post(new MovieSign(response.body().string()));
            }
        });
    }

    //查询ID
    private String getCityId(List<City> cityLists, String currentAddress) {
        for (City c:cityLists){
            if (currentAddress.equals(c.getName())){
                return c.getId();
            }
        }
        //未查到结果返回-1
        return "-1";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        rootView=  inflater.inflate(R.layout.fragment_dian_ying__re_ying, container, false);
        //获取API所支持的所有城市
        initAllCityLists();
        initView();
        initRecycleView();
        return rootView;
    }
    //初始化RecycleView
    private void initRecycleView() {
        //设置排列方式
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        //设置分割条
        recyclerView.addItemDecoration(new RecycleViewDividerL(getContext(), LinearLayout.VERTICAL,10,
                R.color.colorBlue));

    }

    //初始化可以插叙的所有城市信息
    private void initAllCityLists() {
        OkHttpClient client=new OkHttpClient.Builder().build();
        Request request=new Request.Builder().url(JuHeConstant.CITY_INFO_URL).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("错误信息",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventBus.getDefault().post(new CitySign(response.body().string()));
            }
        });
    }
    //获取所有城市
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void parseAllCityLists(CitySign sign){
        //解析JSON，获取所有的城市list集合
        cityLists= JSONUtils.getAllCityInfo(sign.getSign());
    }
    //获取所有当前正在热映的电影
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void parseAllMoviesLists(MovieSign sign){
        //解析JSON，获取所有热映电影信息
        movieLists= JSONUtils.getAllPlayingMovies(sign.getSign());
        if (movieLists==null){
            Toast.makeText(getContext(),"热映电影信息获取失败!",Toast.LENGTH_SHORT).show();
            return;
        }
        //设置Adapter
        recyclerView.setAdapter(new ReYingAdapter(movieLists,getContext()));
    }

    //初始化控件
    private void initView() {
        recyclerView= (RecyclerView) rootView.findViewById(R.id.reyingfragment_recycleview);
        pb= (ProgressBar) rootView.findViewById(R.id.reyingfragment_pb);
        loding_tv= (TextView) rootView.findViewById(R.id.reyingfragment_loading_tv);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
