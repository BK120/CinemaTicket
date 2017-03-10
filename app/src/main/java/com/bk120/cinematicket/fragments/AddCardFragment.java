package com.bk120.cinematicket.fragments;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.adapter.CardAdapter;
import com.bk120.cinematicket.bean.Card;
import com.bk120.cinematicket.bean.StringSign;
import com.bk120.cinematicket.bean.User;
import com.bk120.cinematicket.constants.MainConstant;
import com.bk120.cinematicket.db.CardInfoDao;
import com.bk120.cinematicket.db.UserInfoDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 添加用户账号
 * 要求，统一账户类型的账号名只能有一个
 * 例如 微信中账户名为BK120的只能有一个  但是QQ中可以有账户名为BK120的
 */
public class AddCardFragment extends Fragment {
    //当前在线的那个人
    private User user;
    private UserInfoDao uDao;
    private CardInfoDao cDao;
    private RadioGroup bgColor_rg,type_rg;
    private Button addBtn;
    private EditText cardName_et,balance_et;
    //当前用户所有的账户
    private List<Card> lists;
    //默认账户类型和颜色
    private int type=0;
    private int bgColor=0;
    //根节点
    private View rootView;
    public AddCardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        rootView=inflater.inflate(R.layout.fragment_add_card, container, false);
        initView();
        setListener();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    //更新cardList集合，在每次删除账号或添加账号时
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSign(StringSign sign){
        if (sign.getSign().equals("UpdateCardList")){
            lists=cDao.selectAll(user.getUsername());
        }
    }

    //设置监听
    private void setListener() {
        type_rg.setOnCheckedChangeListener(new MyRadioGroupChickedListener());
        bgColor_rg.setOnCheckedChangeListener(new MyRadioGroupChickedListener());
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertCard();
            }
        });
    }
    //插入一条数据
    private void insertCard() {
        String cardName=cardName_et.getText().toString().trim();
        if (TextUtils.isEmpty(cardName)){
            Toast.makeText(getActivity(),"账户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        String b=balance_et.getText().toString().trim();
        if (TextUtils.isEmpty(b)){
            Toast.makeText(getActivity(),"金额不能为空，可以为0哟！",Toast.LENGTH_SHORT).show();
            return;
        }
        double balance=Double.valueOf(b);
        if (balance>MainConstant.BALANCEMAX){
            Toast.makeText(getContext(),"金额最多"+MainConstant.BALANCEMAX,Toast.LENGTH_SHORT).show();
            return;
        }
        //检验当前所选账户类型是否存在
        boolean isExist = checkOutCardName(cardName, type);
        if (isExist){
            Toast.makeText(getActivity(),"当前账户名已经存在，你可以选择修改账户名或则卡的类型！",Toast.LENGTH_SHORT).show();
            return;
        }
        if (lists.size()>=MainConstant.CARDMAX){
            Toast.makeText(getContext(),"账户数最多只能有"+MainConstant.CARDMAX+"个,添加失败！",Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new StringSign("AddCard Success"));
            return;
        }
        //插入数据
        Card card=new Card(user.getUsername(),type,balance);
        card.setBgColor(bgColor);
        card.setCardName(cardName);
        cDao.insert(card);
        Toast.makeText(getActivity(),"添加账户成功！",Toast.LENGTH_SHORT).show();
        //发出信号
        EventBus.getDefault().post(new StringSign("AddCard Success"));
        EventBus.getDefault().post(new StringSign("UpdateCardList"));

    }
    //校验当前用户类型的用户是否存在
    private boolean checkOutCardName(String cardName,int t) {
        //遍历集合,类型相等，账户名相等返回true
        for(Card c:lists){
            if (c.getType()==t){
                if (c.getCardName().equals(cardName)){
                    return true;
                }
            }
        }
        return false;
    }

    //初始化控件
    private void initView() {
        uDao=new UserInfoDao(getContext());
        cDao=new CardInfoDao(getContext());
        user=uDao.selectOnLine();
        lists=cDao.selectAll(user.getUsername());
        bgColor_rg= (RadioGroup) rootView.findViewById(R.id.drawer_updatecardinfo_bgcolor_rg);
        type_rg= (RadioGroup) rootView.findViewById(R.id.drawer_updatecardinfo_type_rg);
        cardName_et= (EditText) rootView.findViewById(R.id.drawer_updatecardinfo_cardname_et);
        balance_et= (EditText) rootView.findViewById(R.id.drawer_updatecardinfo_balance_et);
        addBtn= (Button) rootView.findViewById(R.id.drawer_updatecardinfo_add_btn);
    }
    class MyRadioGroupChickedListener implements RadioGroup.OnCheckedChangeListener{
        //账户类型
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if (group.getId()==R.id.drawer_updatecardinfo_type_rg){
                switch (checkedId){
                    case R.id.drawer_updatecardinfo_type_qq_rb:
                        type=0;
                        break;
                    case R.id.drawer_updatecardinfo_type_jianhang_rb:
                        type=1;
                        break;
                    case R.id.drawer_updatecardinfo_type_nonghang_rb:
                        type=2;
                        break;
                    case R.id.drawer_updatecardinfo_type_gonghang_rb:
                        type=3;
                        break;
                    case R.id.drawer_updatecardinfo_type_weixin_rb:
                        type=4;
                        break;
                    case R.id.drawer_updatecardinfo_type_zhifubao_rb:
                        type=5;
                        break;
                }
            }
            //背景颜色
            if (group.getId()==R.id.drawer_updatecardinfo_bgcolor_rg){
                switch (checkedId){
                    case R.id.drawer_updatecardinfo_bgcolor0_rb:
                        bgColor=0;
                        break;
                    case R.id.drawer_updatecardinfo_bgcolor1_rb:
                        bgColor=1;
                        break;
                    case R.id.drawer_updatecardinfo_bgcolor2_rb:
                        bgColor=2;
                        break;
                    case R.id.drawer_updatecardinfo_bgcolor3_rb:
                        bgColor=3;
                        break;
                    case R.id.drawer_updatecardinfo_bgcolor4_rb:
                        bgColor=4;
                        break;
                    case R.id.drawer_updatecardinfo_bgcolor5_rb:
                        bgColor=5;
                        break;
            }
            }
        }

    }

}
