package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.bean.BuyFilm;
import com.bk120.cinematicket.bean.BuyFilmSign;
import com.bk120.cinematicket.bean.CinemaSupFilmSign;
import com.bk120.cinematicket.bean.StringSign;
import com.bk120.cinematicket.constants.JuHeConstant;
import com.bk120.cinematicket.utils.JSONUtils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//展示要购买的电影详细信息
public class BuyFilmActivity extends Activity {
    //要购买的电影Id
    private String movie_id;
    private String city_id;
    private String movie_name;
    //影院名
    private String cinema_name;
    private String cinema_address;
    //影院地址
    @BindView(R.id.buyfilmactivity_title_tv)
    TextView title_tv;
    @BindView(R.id.buyfilmactivity_actors_tv)
    TextView actors_tv;
    @BindView(R.id.buyfilmactivity_country_tv)
    TextView country_tv;
    @BindView(R.id.buyfilmactivity_directors_tv)
    TextView directors_tv;
    @BindView(R.id.buyfilmactivity_genres_tv)
    TextView genres_tv;
    @BindView(R.id.buyfilmactivity_language_tv)
    TextView language_tv;
    @BindView(R.id.buyfilmactivity_plot_simple_tv)
    TextView plotsimple_tv;
    @BindView(R.id.buyfilmactivity_rating_tv)
    TextView rating_tv;
    @BindView(R.id.buyfilmactivity_poster_iv)
    ImageView poster_iv;
    @BindView(R.id.buyfilmactivity_sv)
    ScrollView scrollView;
    //即将要购买的电影信息
    private BuyFilm buyFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_film);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //初始化从前一个界面传过来的数据
    private void initData() {
        Intent intent = this.getIntent();
        movie_id=intent.getStringExtra("movie_id");
        city_id=intent.getStringExtra("city_id");
        movie_name=intent.getStringExtra("movie_name");
        cinema_name=intent.getStringExtra("cinema_name");
        cinema_address= intent.getStringExtra("cinema_address");
        //连接网络获取数据
        OkHttpClient client=new OkHttpClient.Builder().build();
        final Request request=new Request.Builder().url(JuHeConstant.BUY_FILM_URL+movie_id).build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                Log.i("错误信息",e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.i("结果",response.body().string());
                EventBus.getDefault().post(new BuyFilmSign(response.body().string()));
            }
        });
    }
    //处理数据结果
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initBuyFilm(BuyFilmSign bfs){
        //获取list结构集
        buyFilm= JSONUtils.getBuyFilms(bfs.getSign());
        //界面与数据贴合
        setData();
    }
    //处理关闭界面逻辑
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void close(StringSign ss){
        if ("CloseActivity".equals(ss.getSign())){
            this.finish();
        }
    }

    private void setData() {
        if (buyFilm==null){
            Toast.makeText(this,"电影数据获取失败",Toast.LENGTH_SHORT).show();
            return;
        }
        scrollView.setVisibility(View.VISIBLE);
        //设置数据
        title_tv.setText(buyFilm.getTitle());
        actors_tv.setText("演员:"+buyFilm.getActors());
        plotsimple_tv.setText("详情:"+buyFilm.getPlot_simple());
        rating_tv.setText("评分:"+buyFilm.getRating());
        directors_tv.setText("导演:"+buyFilm.getDirectors());
        country_tv.setText("城市:"+buyFilm.getCountry());
        genres_tv.setText("题材:"+buyFilm.getGenres());
        language_tv.setText("语言:"+buyFilm.getLanguage());
        Picasso.with(this).load(buyFilm.getPoster()).error(R.mipmap.load_error).into(poster_iv);
    }

    //返回
    public void back(View view){
        this.finish();
    }
    //购买当前的电影票
    public void buy(View view){
        //代开选座位
        Intent intent=new Intent(this,CinemaChairActivity.class);
        //传递数据
        intent.putExtra("movie_id",movie_id);
        intent.putExtra("city_id",city_id);
        intent.putExtra("movie_name",movie_name);
       intent.putExtra("cinema_name",cinema_name);
       intent.putExtra("cinema_address",cinema_address);
        startActivity(intent);
        overridePendingTransition(R.anim.acrivity3_push_in,0);
    }
}
