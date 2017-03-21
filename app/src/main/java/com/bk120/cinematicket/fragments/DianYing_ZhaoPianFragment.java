package com.bk120.cinematicket.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.activitys.PlayFilmActivity;
import com.bk120.cinematicket.bean.FilmUrlBean;
import com.bk120.cinematicket.bean.SearchFilmBean;
import com.bk120.cinematicket.constants.DianYingConstant;
import com.bk120.cinematicket.utils.NetWorkUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * 搜索电影，电视剧，可以播放的页面
 */
public class DianYing_ZhaoPianFragment extends Fragment {
    //跟视图
    private View rootView;
    private TextView film_title,film_tag,film_acts,film_year,film_rating,film_area,film_dir,
    film_desc;
    private ImageView film_cover;
    private EditText input_et;
    private Button search_btn,play_btn;
    private ScrollView scrollView;
    //电影对象
    private SearchFilmBean mFilmBean;

    public DianYing_ZhaoPianFragment() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_dian_ying__zhao_pian, container, false);
        initView();
        initData();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    //数据与视图贴合
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initDataAndView(SearchFilmBean bean) {
        mFilmBean=bean;
        //显示
        scrollView.setVisibility(View.VISIBLE);
        //获取对象设置值
        film_title.setText(bean.getTitle());
        film_desc.setText("详情:"+bean.getDesc());
        film_dir.setText("导演:"+bean.getDir());
        film_rating.setText("评分:"+bean.getRating()+"分");
        film_year.setText("时间:"+bean.getYear()+"年");
        film_acts.setText("演员:"+bean.getActs());
        film_area.setText("地区:"+bean.getArea());
        film_tag.setText("题材:"+bean.getTag());
        //加载图片
        Picasso.with(getContext()).load(bean.getCover()).error(R.mipmap.load_error).into(film_cover);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toastMsg(String ms){
            Toast.makeText(getContext(), ms, Toast.LENGTH_SHORT).show();
    }

    //初始化网络获取数据
    private void initData() {
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=input_et.getText().toString().trim();
                if (TextUtils.isEmpty(msg)){
                    Toast.makeText(getContext(),"请输入你想搜索的影视节目！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!NetWorkUtil.isNetWorkAvaliable(getActivity())){
                    Toast.makeText(getContext(),"当前网络不可用！请检查网络连接",Toast.LENGTH_SHORT).show();
                    return;
                }
                //获取数据
                OkHttpClient client=new OkHttpClient.Builder().build();
                RequestBody body=new FormBody.Builder().add("key", DianYingConstant.SEARCH_FILM_KEY)
                        .add("q",msg).build();
                Request request=new Request.Builder().post(body).url(DianYingConstant.SEARCH_FILM_URL).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("错误获取",e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //获取的结果
                        String result=response.body().string();
                        SearchFilmBean bean = parseResult(result);
                        if (bean==null){
                            EventBus.getDefault().post(new String("未获取到影视！"));
                            return;
                        }
                        //将bean发送出去
                        EventBus.getDefault().post(bean);
                    }
                });

            }
        });
    }
    //解析结果，返回一个电影对象
    public SearchFilmBean parseResult(String res){
        SearchFilmBean bean=new SearchFilmBean();
        //JSON解析
        try {
            JSONObject rootObject=new JSONObject(res);
            if(!rootObject.getString("reason").equals("查询成功")){
                return null;
            }
            //获取对象的各个字段
            JSONObject result = rootObject.getJSONObject("result");
            String title = result.getString("title");
            String tag = result.getString("tag");
            String act = result.getString("act");
            String year = result.getString("year");
            String rating="0.0";
            if(result.getString("rating")!="null"){
                rating=result.getString("rating");
            }
            String area = result.getString("area");
            String dir = result.getString("dir");
            String desc = result.getString("desc");
            String cover = result.getString("cover");
            String vdo_status = result.getString("vdo_status");
            JSONObject playlinks = result.getJSONObject("playlinks");
            //获取优酷的播放路径
            String youku = playlinks.getString(DianYingConstant.ZHAOPIAN_FILM_PLAY_URL);
            bean.setTitle(title);bean.setTag(tag);bean.setActs(act);
            bean.setArea(area);bean.setCover(cover);bean.setDesc(desc);
            bean.setDir(dir);bean.setRating(rating);bean.setVdo_status(vdo_status);
            bean.setYear(year);bean.setYouKuFilmUrl(youku);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return bean;
    }

    //初始化View
    private void initView() {
        film_title= (TextView) rootView.findViewById(R.id.zhaopian_fragment_film_info_title);
        film_tag=(TextView) rootView.findViewById(R.id.zhaopian_fragment_film_info_tag);
        film_acts=(TextView) rootView.findViewById(R.id.zhaopian_fragment_film_info_acts);
        film_area=(TextView) rootView.findViewById(R.id.zhaopian_fragment_film_info_area);
        film_year=(TextView) rootView.findViewById(R.id.zhaopian_fragment_film_info_year);
        film_rating=(TextView) rootView.findViewById(R.id.zhaopian_fragment_film_info_rating);
        film_dir=(TextView) rootView.findViewById(R.id.zhaopian_fragment_film_info_dir);
        film_desc=(TextView) rootView.findViewById(R.id.zhaopian_fragment_film_info_desc);
        film_cover= (ImageView) rootView.findViewById(R.id.zhaopian_fragment_film_info_cover);
        input_et= (EditText) rootView.findViewById(R.id.zhaopian_fragment_search_et);
        search_btn= (Button) rootView.findViewById(R.id.zhaopian_fragment_search_btn);
        scrollView= (ScrollView) rootView.findViewById(R.id.zhaopian_fragment_film_info_sv);
        play_btn= (Button) rootView.findViewById(R.id.zhaopian_fragment_film_info_play_btn);
        //设置监听跳转
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mFilmBean.getVdo_status().equals("play")){
                    EventBus.getDefault().post(new String("视频由于某种原因不能播放！Sorry！"));
                    return;
                }
                //打开播放的Activity页面
                Intent i=new Intent(getContext(), PlayFilmActivity.class);
                //放置对象
                i.putExtra("filmUrl",mFilmBean.getYouKuFilmUrl());
                //i.putExtra("filmUrl","http:\\/\\/v.youku.com\\/v_show\\/id_XMTEyMTU5MDI4.html?tpa=dW5pb25faWQ9MTAyMjEzXzEwMDAwNl8wMV8wMQ");
                startActivity(i);
            }
        });
    }

}
