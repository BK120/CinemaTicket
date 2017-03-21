package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.adapter.TicketOrderAdapter;
import com.bk120.cinematicket.bean.Ticket;
import com.bk120.cinematicket.bean.User;
import com.bk120.cinematicket.db.TicketInfoDao;
import com.bk120.cinematicket.db.UserInfoDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//电影票订单
public class TicketOrderActivity extends Activity {
    private User user;
    private UserInfoDao uDao;
    private List<Ticket> tLists;
    private TicketInfoDao tDao;
    @BindView(R.id.ticketorderactivity_recycleview)
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_order);
        ButterKnife.bind(this);
        initData();
    }
    //初始化数据
    private void initData() {
        uDao=new UserInfoDao(this);
        user=uDao.selectOnLine();
        tDao=new TicketInfoDao(this);
        tLists=tDao.selectAll(user.getUsername());
        if (tLists==null){
            Toast.makeText(this,"你还没有订单呢！",Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.INVISIBLE);
            return;
        }
        initRecycleView();
    }
    //初始化RecycleView
    private void initRecycleView() {
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new TicketOrderAdapter(tLists,this));
    }
}
