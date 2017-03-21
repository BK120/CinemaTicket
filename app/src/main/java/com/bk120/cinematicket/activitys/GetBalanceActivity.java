package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.adapter.AddBalanceAdapter;
import com.bk120.cinematicket.bean.Card;
import com.bk120.cinematicket.bean.User;
import com.bk120.cinematicket.bean.ViewSign;
import com.bk120.cinematicket.constants.MainConstant;
import com.bk120.cinematicket.db.CardInfoDao;
import com.bk120.cinematicket.db.UserInfoDao;
import com.bk120.cinematicket.utils.SharePreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.List;

public class GetBalanceActivity extends Activity {
    private RecyclerView recyclerView;
    private EditText balance_et;
    private User user;
    private UserInfoDao uDao;
    private CardInfoDao cDao;
    private List<Card> list;
    private AddBalanceAdapter adapter;
    //付款账户
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_balance);
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
        recyclerView= (RecyclerView) this.findViewById(R.id.getbalanceactivty_recycleview);
        balance_et= (EditText) this.findViewById(R.id.getbalanceactivty_money_et);
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
            Toast.makeText(this,"提现不能为空!",Toast.LENGTH_SHORT).show();
            return;
        }
        double b=Double.valueOf(s);
        Card card = list.get(position);
        if(user.getBalance()<b){
            Toast.makeText(this,"你想的太多了吧,钱不够！",Toast.LENGTH_SHORT).show();
            return;
        }
        if(b> MainConstant.BALANCEMAX){
            Toast.makeText(this,"一次最多只能提取"+MainConstant.BALANCEMAX+"元！",Toast.LENGTH_SHORT).show();
            return;
        }
        BigDecimal opter=new BigDecimal(Double.toString(b));
        BigDecimal res=new BigDecimal(Double.toString(user.getBalance()));
        BigDecimal yuan=new BigDecimal(Double.toString(card.getBalance()));
        card.setBalance(yuan.add(opter).doubleValue());
        //更新卡
        cDao.update(card);
        //更新用户
        user.setBalance(res.subtract(opter).doubleValue());
        uDao.update(user);
        Toast.makeText(this,"提现成功！",Toast.LENGTH_SHORT).show();
        this.finish();
    }
}
