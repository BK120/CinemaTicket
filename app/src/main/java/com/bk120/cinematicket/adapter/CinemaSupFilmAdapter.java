package com.bk120.cinematicket.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.activitys.BuyFilmActivity;
import com.bk120.cinematicket.activitys.MapViewActivity;
import com.bk120.cinematicket.bean.CinemaSupFilm;

import java.util.List;

/**
 * Created by bk120 on 2017/3/18.
 * 关于当前城市  当前电影的所支持影院的信息
 */

public class CinemaSupFilmAdapter extends RecyclerView.Adapter{
    private List<CinemaSupFilm> list;
    private Context context;
    //城市id
    private String city_id;
    private String movie_name;
    //电影ID、
    private String movie_id;
    //路径
    private String one="http://api.map.baidu.com/marker?" ;
    private String location="location=";
    private String title="&title=";
    private String content="&content=";
    private String output="&output=html";
    public CinemaSupFilmAdapter(List<CinemaSupFilm> l,Context c,String ci,String mi,String mN){
        this.city_id=ci;
        this.movie_id=mi;
        this.list=l;
        this.context=c;
        this.movie_name=mN;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view=View.inflate(context,R.layout.cinemasupfilm_recycleview_item,null);
        return new MyCinemaSupFilmHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof  MyCinemaSupFilmHolder){
            final CinemaSupFilm cinemaSupFilm = list.get(i);
            ((MyCinemaSupFilmHolder) viewHolder).name.setText(cinemaSupFilm.getCinemaName());
            ((MyCinemaSupFilmHolder) viewHolder).address.setText(cinemaSupFilm.getAddress());
            //打开购票界面
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context, BuyFilmActivity.class);
                    i.putExtra("movie_id",movie_id);
                    i.putExtra("movie_name",movie_name);
                    i.putExtra("city_id",city_id);
                    i.putExtra("cinema_name",cinemaSupFilm.getCinemaName());
                    i.putExtra("cinema_address",cinemaSupFilm.getAddress());
                    context.startActivity(i);
                }
            });
            //导航至影院位置
            ((MyCinemaSupFilmHolder) viewHolder).address_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context, MapViewActivity.class);
                    String currentUrl=one+location+cinemaSupFilm.getLatitde()+","+cinemaSupFilm.getLongtitude()+title+cinemaSupFilm.getCinemaName()+content+cinemaSupFilm.getCinemaName()+output;
                    i.putExtra("addressUrl",currentUrl);
                    context.startActivity(i);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyCinemaSupFilmHolder extends RecyclerView.ViewHolder{
        private TextView name,address;
        private ImageView address_iv;
        public MyCinemaSupFilmHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.cinemasupfilmactivity_recycleview_item_name_tv);
            address= (TextView) itemView.findViewById(R.id.cinemasupfilmactivity_recycleview_item_address_tv);
            address_iv= (ImageView) itemView.findViewById(R.id.cinemasupfilmactivity_recycleview_item_address_iv);
        }
    }
}
