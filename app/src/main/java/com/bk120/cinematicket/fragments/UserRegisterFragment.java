package com.bk120.cinematicket.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.bean.Card;
import com.bk120.cinematicket.bean.RegisterSuccessSign;
import com.bk120.cinematicket.bean.StringSign;
import com.bk120.cinematicket.bean.User;
import com.bk120.cinematicket.constants.MainConstant;
import com.bk120.cinematicket.db.CardInfoDao;
import com.bk120.cinematicket.db.UserInfoDao;

import org.greenrobot.eventbus.EventBus;

/**
 * 注册界面
 */
public class UserRegisterFragment extends Fragment implements View.OnClickListener{
    private View rootView;
    private EditText username_et,password_et,confirmPWD_et;
    private Button register_btn;
    public UserRegisterFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_user_register, container, false);
        initView();
        initListener();
        return rootView;
    }


    //设置监听
    private void initListener() {
        register_btn.setOnClickListener(this);
    }
    //初始化控件
    private void initView() {
        password_et= (EditText) rootView.findViewById(R.id.registerfragment_password_et);
        username_et= (EditText) rootView.findViewById(R.id.registerfragment_username_et);
        confirmPWD_et= (EditText) rootView.findViewById(R.id.registerfragment_password_confirm_et);
        register_btn= (Button) rootView.findViewById(R.id.registerfragment_register_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerfragment_register_btn:
                //注册
                String username = username_et.getText().toString().trim();
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(getContext(),"用户名不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                String password = password_et.getText().toString().toString().trim();
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getContext(),"密码不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                String password_confirm = confirmPWD_et.getText().toString().toString().trim();
                if(TextUtils.isEmpty(password_confirm)){
                    Toast.makeText(getContext(),"请确认密码！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(password_confirm)){
                    Toast.makeText(getContext(),"两次密码不相同，请重新输入",Toast.LENGTH_SHORT).show();
                    confirmPWD_et.setText("");
                    return;
                }
                //去数据库查询用户名是否存在
                UserInfoDao dao=new UserInfoDao(getContext());
                User user = dao.select(username);
                if(user!=null){
                    Toast.makeText(getContext(),"用户名已经存在，请创建用户",Toast.LENGTH_SHORT).show();
                    username_et.setText("");
                    return;
                }
                //保存数据至数据库，并跳转至登录界面，携带用户名和密码过去
                User user1=new User(username,password);
                user1.setBalance(MainConstant.USERMONEY);
                user1.setIsRememberPWD(0);
                user1.setIsAutoLogin(0);
                user1.setMotto("Hello BK票儿！");
                user1.setStatus("0");
                dao.insert(user1);
                //同时初始化该用户的几张卡的信息
                initCard(username);
                //发送信号
                EventBus.getDefault().post(new StringSign("RegisterSuccess"));
                EventBus.getDefault().post(new RegisterSuccessSign(username,password));
                username_et.setText("");
                password_et.setText("");
                confirmPWD_et.setText("");
                break;
        }
    }
    //初始化用户的卡信息
    private void initCard(String username) {
        CardInfoDao dao=new CardInfoDao(getContext());
        for (int i=0;i<MainConstant.CARDMONEYS.length;i++){
            Card card=new Card(username,i,MainConstant.CARDMONEYS[i]);
            card.setBgColor(i);
            card.setCardName(username);
            dao.insert(card);
        }
    }
}
