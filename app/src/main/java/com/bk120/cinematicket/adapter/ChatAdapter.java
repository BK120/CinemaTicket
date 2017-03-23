package com.bk120.cinematicket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.bean.Chat;

import java.util.List;

/**
 * Created by bk120 on 2017/3/22.
 */

public class ChatAdapter extends RecyclerView.Adapter{
    public List<Chat> list;
    private Context context;
    public ChatAdapter(List<Chat> l,Context c){
        this.list=l;
        this.context=c;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view0=View.inflate(context,R.layout.chat_room_recycleview_item0,null);
        View view1=View.inflate(context,R.layout.chat_room_recycleview_item1,null);
        if(i==0){
            return new MyChatHolder0(view0);
        }else {
            return new MyChatHolder1(view1);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Chat chat = list.get(i);
        if(viewHolder instanceof MyChatHolder0){
            //机器人
            ((MyChatHolder0) viewHolder).robot_time_tv.setText(chat.getTime());
            ((MyChatHolder0) viewHolder).robot_mes_tv.setText(chat.getMessage());
            ((MyChatHolder0) viewHolder).robot_name_tv.setText(chat.getName());
        }
        if(viewHolder instanceof MyChatHolder1){
            //用户
            ((MyChatHolder1) viewHolder).user_mes_tv.setText(chat.getMessage());
            ((MyChatHolder1) viewHolder).user_name_tv.setText(chat.getName());
            ((MyChatHolder1) viewHolder).user_time_tv.setText(chat.getTime());
        }

    }

    @Override
    public int getItemViewType(int position) {
        Chat chat = list.get(position);
        return chat.getSign();
    }

    @Override
    public int getItemCount() {
        return list.size();

    }
    class MyChatHolder0 extends RecyclerView.ViewHolder{
        public RelativeLayout robot_rl;
        public TextView robot_mes_tv,robot_time_tv,robot_name_tv;
        public MyChatHolder0(View itemView) {
            super(itemView);
            robot_rl= (RelativeLayout) itemView.findViewById(R.id.chatrecycleview_item_robot_rl);
            robot_mes_tv= (TextView) itemView.findViewById(R.id.chatrecycleview_item_robot_mes_tv);
            robot_time_tv= (TextView) itemView.findViewById(R.id.chatrecycleview_item_robot_time_tv);
            robot_name_tv= (TextView) itemView.findViewById(R.id.chatrecycleview_item_robot_name_tv);
        }
    }
    class MyChatHolder1 extends RecyclerView.ViewHolder{
        public RelativeLayout user_rl;
        public TextView user_mes_tv,user_time_tv,user_name_tv;
        public MyChatHolder1(View itemView) {
            super(itemView);
            user_rl= (RelativeLayout) itemView.findViewById(R.id.chatrecycleview_item_user_rl);
            user_mes_tv= (TextView) itemView.findViewById(R.id.chatrecycleview_item_user_mes_tv);
            user_time_tv= (TextView) itemView.findViewById(R.id.chatrecycleview_item_user_time_tv);
            user_name_tv= (TextView) itemView.findViewById(R.id.chatrecycleview_item_user_name_tv);
        }
    }
}
