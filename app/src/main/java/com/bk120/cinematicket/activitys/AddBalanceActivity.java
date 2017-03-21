package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.adapter.AddBalanceAdapter;
import com.bk120.cinematicket.bean.Card;
import com.bk120.cinematicket.bean.User;
import com.bk120.cinematicket.bean.ViewSign;
import com.bk120.cinematicket.db.CardInfoDao;
import com.bk120.cinematicket.db.UserInfoDao;
import com.bk120.cinematicket.utils.SharePreferencesUtils;
import com.bk120.cinematicket.views.RecycleViewDividerL;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
//充值dialog样式的Activity
public class AddBalanceActivity extends Activity {
    private RecyclerView recyclerView;
    private EditText balance_et;
    private User user;
    private UserInfoDao uDao;
    private CardInfoDao cDao;
    private List<Card> list;
    private AddBalanceAdapter adapter;
    //付款账户
    private int position=0;
    //默认由第0个账户付款
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);
        EventBus.getDefault().register(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    //接收到信号
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSign(ViewSign sign){
        initData();
        position=sign.getPosition();
        recyclerView.scrollToPosition(position);
    }

    //初始化控件
    private void initView() {
        recyclerView= (RecyclerView) this.findViewById(R.id.addbalanceactivty_recycleview);
        balance_et= (EditText) this.findViewById(R.id.addbalanceactivty_money_et);
        uDao=new UserInfoDao(this);
        cDao=new CardInfoDao(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        user=uDao.selectOnLine();
        list=cDao.selectAll(user.getUsername());
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        //recyclerView.addItemDecoration(new RecycleViewDividerL(this, LinearLayout.VERTICAL));
        adapter=new AddBalanceAdapter(list,this);
        recyclerView.setAdapter(adapter);
        position= SharePreferencesUtils.getInt(this,"PayCard",0);
    }

    //关闭取消
    public void cancle(View view){
        this.finish();
    }
    //确定充值
    public void confirm(View view){
        String s = balance_et.getText().toString().trim();
        if (TextUtils.isEmpty(s)){
            Toast.makeText(this,"充值不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }
        //此处可能出现精度丢失
        double b=Double.valueOf(s);
        Card card = list.get(position);
        if(card.getBalance()<b){
            Toast.makeText(this,"卡内余额不足！",Toast.LENGTH_SHORT).show();
            return;
        }
        BigDecimal big1=new BigDecimal(Double.toString(b));
        BigDecimal big2=new BigDecimal(Double.toString(card.getBalance()));
        BigDecimal subtract = big2.subtract(big1);
        card.setBalance(subtract.doubleValue());
        //更新卡
        cDao.update(card);
        //更新用户
        BigDecimal big3=new BigDecimal(Double.toString(user.getBalance()));
        BigDecimal add = big3.add(big1);
        user.setBalance(add.doubleValue());
        uDao.update(user);
        Toast.makeText(this,"充值成功！",Toast.LENGTH_SHORT).show();
        this.finish();
    }
}
