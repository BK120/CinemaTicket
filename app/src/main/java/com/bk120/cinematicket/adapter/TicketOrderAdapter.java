package com.bk120.cinematicket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.bean.Ticket;
import com.bk120.cinematicket.bean.User;
import com.bk120.cinematicket.db.TicketInfoDao;
import com.bk120.cinematicket.db.UserInfoDao;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by bk120 on 2017/3/21.
 */

public class TicketOrderAdapter extends RecyclerView.Adapter{
    private List<Ticket> list;
    private Context context;
    public TicketOrderAdapter(List<Ticket> l,Context c){
        this.list=l;
        this.context=c;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view=View.inflate(context,R.layout.ticket_order_recycleview_item,null);
        return new MyTicketOrderAdapter(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof MyTicketOrderAdapter){
            final Ticket ticket = list.get(i);
            ((MyTicketOrderAdapter) viewHolder).moviename_tv.setText(ticket.getMovie_name());
            ((MyTicketOrderAdapter) viewHolder).time_tv.setText(ticket.getTime());
            ((MyTicketOrderAdapter) viewHolder).chairloc_tv.setText(ticket.getChair_loc());
            ((MyTicketOrderAdapter) viewHolder).cinemaaddress_tv.setText(ticket.getCinema_address());
            ((MyTicketOrderAdapter) viewHolder).cinemaname_tv.setText(ticket.getCinema_name());
            ((MyTicketOrderAdapter) viewHolder).price_tv.setText(ticket.getPrice()+"元/张");
            ((MyTicketOrderAdapter) viewHolder).delete_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tuiDingTicket(ticket,i);
                }
            });
        }
    }
    //退订一张票
    private void tuiDingTicket(Ticket ticket,int i) {
        //从电影票的表中删除
        TicketInfoDao tDao=new TicketInfoDao(context);
        tDao.delete(ticket);
        //修改当前用户余额
        UserInfoDao uDao=new UserInfoDao(context);
        User user = uDao.selectOnLine();
        //精度处理
        BigDecimal op=new BigDecimal(Double.toString(user.getBalance()));
        BigDecimal re=new BigDecimal(Double.toString(ticket.getPrice()));
        user.setBalance(op.add(re).doubleValue());
        uDao.update(user);
        //刷新控件
        TicketOrderAdapter.this.notifyItemRemoved(i);
        list.remove(i);
        TicketOrderAdapter.this.notifyItemRangeChanged(i, TicketOrderAdapter.this.getItemCount());
        Toast.makeText(context,"退订成功！退款将尽快到你账户",Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyTicketOrderAdapter extends RecyclerView.ViewHolder{
        public TextView moviename_tv,price_tv,cinemaname_tv,cinemaaddress_tv,chairloc_tv,time_tv,delete_tv;
        public MyTicketOrderAdapter(View itemView) {
            super(itemView);
            moviename_tv= (TextView) itemView.findViewById(R.id.ticket_order_recycleview_moviename_tv);
            price_tv= (TextView) itemView.findViewById(R.id.ticket_order_recycleview_price_tv);
            cinemaname_tv= (TextView) itemView.findViewById(R.id.ticket_order_recycleview_cinema_name_tv);
            cinemaaddress_tv= (TextView) itemView.findViewById(R.id.ticket_order_recycleview_cinema_address_tv);
            chairloc_tv= (TextView) itemView.findViewById(R.id.ticket_order_recycleview_chair_loc_tv);
            time_tv= (TextView) itemView.findViewById(R.id.ticket_order_recycleview_cinema_time);
            delete_tv= (TextView) itemView.findViewById(R.id.ticket_order_recycleview_delete_tv);
        }
    }
}
