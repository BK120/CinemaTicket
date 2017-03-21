package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.adapter.CinemaSupFilmAdapter;
import com.bk120.cinematicket.bean.CinemaSupFilm;
import com.bk120.cinematicket.bean.CinemaSupFilmSign;
import com.bk120.cinematicket.bean.CitySign;
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

/**展示当前城市支持该电影买票的影院信息
 *能进入当前界面,表明当前城市id和影片Id是具有的
 */
public class CinemaSupFilmActivity extends Activity {
    //影片ID
    private String mMovie_id;
    private String movie_name;
    //城市ID
    private String mCity_id;
    private RecyclerView mRecyclerView;
    private String url;
    private List<CinemaSupFilm> mLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_sup_film);
        EventBus.getDefault().register(this);
        initView();
        initData();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    //处理关闭界面逻辑
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void close(StringSign ss){
        if ("CloseActivity".equals(ss.getSign())){
            this.finish();
        }
    }

    //初始化RecycleView
    private void initRecycleView() {
        //设置排列方式
        LinearLayoutManager manager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        //设置分割条
        mRecyclerView.addItemDecoration(new RecycleViewDividerL(this, LinearLayout.VERTICAL,10,
                R.color.colorBlue));
        //设置Adapter
        if (mLists==null){
            Toast.makeText(this,"影院信息获取失败",Toast.LENGTH_SHORT).show();
            return;
        }
        mRecyclerView.setAdapter(new CinemaSupFilmAdapter(mLists,this,mCity_id,mMovie_id,movie_name));
    }

    //初始化数据
    private void initData() {
        url= JuHeConstant.CINEMA_SUP_FILM_URL+"&cityid="+mCity_id+"&movieid="+mMovie_id;
        OkHttpClient client=new OkHttpClient.Builder().build();
        final Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                    Log.i("错误信息",e.getMessage());
                }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.i("结果",response.body().string());
                EventBus.getDefault().post(new CinemaSupFilmSign(response.body().string()));
                }
        });
    }
    //处理数据结果
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initCinemaSupFilmLists(CinemaSupFilmSign csfs){
        //获取list结构集
        mLists= JSONUtils.getCinemaSupFilmLists(csfs.getSign());
        initRecycleView();
    }


    //初始化数据
    private void initView() {
        Intent intent = this.getIntent();
        mMovie_id=intent.getStringExtra("movie_id");
        mCity_id= intent.getStringExtra("city_id");
        movie_name=intent.getStringExtra("movie_name");
        mRecyclerView= (RecyclerView) this.findViewById(R.id.cinemasupfilmactivity_recycleview);
    }
    //返回关闭当前页面
    public void back(View view){
        this.finish();
    }
}
