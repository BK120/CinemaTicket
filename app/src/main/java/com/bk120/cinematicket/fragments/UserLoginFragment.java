package com.bk120.cinematicket.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.bean.RegisterSuccessSign;
import com.bk120.cinematicket.bean.StringSign;
import com.bk120.cinematicket.bean.User;
import com.bk120.cinematicket.db.UserInfoDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 用户登录界面
 */
public class UserLoginFragment extends Fragment implements View.OnClickListener{
    private View rootView;
    private EditText username_et,password_et;
    private Button login_btn;
    private CheckBox isRememberPwdCB,isAutoLoginCB;
    private int isRememberPWD=0;
    private int isAutoLogin=0;
    public UserLoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        rootView=inflater.inflate(R.layout.fragment_user_login, container, false);
        initView();
        initListener();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //注册监听
    private void initListener() {
        login_btn.setOnClickListener(this);
        isAutoLoginCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isAutoLogin=1;
                }else {
                    isAutoLogin=0;
                }
            }
        });
        isRememberPwdCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isRememberPWD=1;
                }else {
                    isRememberPWD=0;
                }
            }
        });
    }
    //初始化控件
    private void initView() {
        username_et= (EditText) rootView.findViewById(R.id.loginfragment_username_et);
        password_et= (EditText) rootView.findViewById(R.id.loginfragment_password_et);
        login_btn= (Button) rootView.findViewById(R.id.loginfragment_login_btn);
        isRememberPwdCB= (CheckBox) rootView.findViewById(R.id.loginfragment_jizhumima_cb);
        isAutoLoginCB= (CheckBox) rootView.findViewById(R.id.loginfragment_zhidongdenglu_cb);
    }

    //接受信号处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSign(RegisterSuccessSign sign){
        username_et.setText(sign.getUserName());
        password_et.setText(sign.getPassWord());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginfragment_login_btn:
                //登录
                String username = username_et.getText().toString().trim();
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(getContext(),"用户名不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                String password = password_et.getText().toString().trim();
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getContext(),"密码不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                //根据用户名查询密码是否正确
                UserInfoDao dao =new UserInfoDao(getContext());
                User user = dao.select(username);
                if (user==null){
                    Toast.makeText(getContext(),"当前用户不存在！请重新输入或则注册！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!user.getPassword().equals(password)){
                    Toast.makeText(getContext(),"密码错误！请重新输入！",Toast.LENGTH_SHORT).show();
                    return;
                }
                user.setIsAutoLogin(isAutoLogin);
                user.setIsRememberPWD(isRememberPWD);
                user.setStatus("1");
                dao.update(user);
                //登录成功
                getActivity().finish();
                break;
        }
    }
}
