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
import com.bk120.cinematicket.activitys.CinemaSupFilmActivity;
import com.bk120.cinematicket.bean.Movie;
import com.bk120.cinematicket.utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by bk120 on 2017/3/17.
 * 显示当前城市所有热映的电影
 */

public class ReYingAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<Movie> list;
    private String city_id;
    public ReYingAdapter(List<Movie> l,Context c,String i){
        this.list=l;
        this.context=c;
        this.city_id=i;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view=View.inflate(context,R.layout.reying_recycleview_item,null);
        return new MyItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof MyItemHolder){
            final Movie movie = list.get(i);
            ((MyItemHolder) viewHolder).name.setText(movie.getMovie_name());
            ((MyItemHolder) viewHolder).time.setText(DateUtils.getCurrentDate());
            Picasso.with(context).load(movie.getPic_url()).error(R.mipmap.user).into(((MyItemHolder) viewHolder).icon);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context, CinemaSupFilmActivity.class);
                    //将电影ID和城市ID传送至显示界面
                    i.putExtra("movie_id",movie.getMovie_id());
                    i.putExtra("movie_name",movie.getMovie_name());
                    i.putExtra("city_id",city_id);
                    context.startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyItemHolder extends RecyclerView.ViewHolder{
        private TextView name,time;
        private ImageView icon;
        public MyItemHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.reying_recycleview_item_name);
            time= (TextView) itemView.findViewById(R.id.reying_recycleview_item_time);
            icon= (ImageView) itemView.findViewById(R.id.reying_recycleview_item_icon);
        }
    }
}
