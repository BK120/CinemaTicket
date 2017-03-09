package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.bean.User;
import com.bk120.cinematicket.db.UserInfoDao;

/**
 * 修改用户信息Activity
 */
public class UpdateUserActivity extends Activity {
    //当前在线的用户
    private User user;
    private TextView username_tv,motto_tv,yue_tv;
    //当前的密码
    private String currentPassWord;
    private String oldPassWord;
    private UserInfoDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        //能进入当前界面，必定存在一个在线用户，即user!=null
        dao=new UserInfoDao(this);
        user=dao.selectOnLine();
        initView();
    }
    //初始化控件
    private void initView() {
        username_tv= (TextView) this.findViewById(R.id.updateuseractivity_username_tv);
        motto_tv= (TextView) this.findViewById(R.id.updateuseractivity_motto_tv);
        yue_tv= (TextView) this.findViewById(R.id.updateuseractivity_updateyue_tv);
        username_tv.setText(user.getUsername());
        motto_tv.setText(user.getMotto());
        yue_tv.setText(user.getBalance()+"元");
        currentPassWord=user.getPassword();
    }

    //修改用户名
    public void updateUserName(View view){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View childView=View.inflate(this,R.layout.userinfo_update_username_dialog,null);
        builder.setView(childView);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        final EditText usernameET= (EditText) childView.findViewById(R.id.userinfodialog_updateusername_et);
        Button cancleBtn= (Button) childView.findViewById(R.id.userinfodialog_updateusername_cancle_btn);
        Button confirmBtn= (Button) childView.findViewById(R.id.userinfodialog_updateusername_confirm_btn);
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameET.getText().toString().trim();
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(getApplicationContext(),"用户名不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                User select = dao.select(username);
                if (select!=null){
                    Toast.makeText(getApplicationContext(),"用户名已经存在！",Toast.LENGTH_SHORT).show();
                    return;
                }
                username_tv.setText(username);
                dialog.cancel();
            }
        });
        dialog.show();

    }
    //修改签名
    public void updateMotto(View view){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View childView=View.inflate(this,R.layout.userinfo_update_motto_dialog,null);
        builder.setView(childView);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        final EditText mottoEt= (EditText) childView.findViewById(R.id.userinfodialog_updatemotto_et);
        Button cancleBtn= (Button) childView.findViewById(R.id.userinfodialog_updatemotto_cancle_btn);
        Button confirmBtn= (Button) childView.findViewById(R.id.userinfodialog_updatemotto_confirm_btn);
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String motto = mottoEt.getText().toString().trim();
                if (TextUtils.isEmpty(motto)){
                    Toast.makeText(getApplicationContext(),"个性签名不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                motto_tv.setText(motto);
                dialog.cancel();
            }
        });
        dialog.show();
    }
    //提示余额信息不能在当前页面修改
    public void showErrol(View view){
        Toast.makeText(this,"当前界面不能充值,请去主页面充值余额！",Toast.LENGTH_SHORT).show();
        return;
    }
    //修改密码
    public void updatePassword(View view){
        //弹出Dialog修改密码
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View childView=View.inflate(this,R.layout.userinfo_update_passwod_dialog,null);
        builder.setView(childView);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        //查找控件
        final EditText oldPwd= (EditText) childView.findViewById(R.id.userinfodialog_updatepassword_oldpwd_et);
        final EditText newPwd= (EditText) childView.findViewById(R.id.userinfodialog_updatepassword_newpwd1_et);
        Button cancleBtn= (Button) childView.findViewById(R.id.userinfodialog_updatepassword_cancle_btn);
        Button confirmBtn= (Button) childView.findViewById(R.id.userinfodialog_updatepassword_confirm_btn);
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认修改密码
                String oldPassword = oldPwd.getText().toString().trim();
                if(TextUtils.isEmpty(oldPassword)){
                    Toast.makeText(getApplicationContext(),"旧密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                String newPassword = newPwd.getText().toString().trim();
                if(TextUtils.isEmpty(newPassword )){
                    Toast.makeText(getApplicationContext(),"新密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!oldPassword.equals(currentPassWord)){
                    Toast.makeText(getApplicationContext(),"旧密码输入错误！",Toast.LENGTH_SHORT).show();
                    oldPwd.setText("");
                    return;
                }
                if (oldPassword.equals(newPassword)){
                    Toast.makeText(getApplicationContext(),"两次密码不能相同",Toast.LENGTH_SHORT).show();
                    newPwd.setText("");
                    return;
                }
                //改变当前账户密码值
                currentPassWord=newPassword;
                dialog.cancel();
            }
        });
        dialog.show();
    }
    //确认修改
    public void confirmUpdate(View view){
        String username = username_tv.getText().toString().trim();
        user.setUsername(username);
        String motto = motto_tv.getText().toString().toString().trim();
        user.setMotto(motto);
        user.setPassword(currentPassWord);
        //保存至数据库
        dao.update(user);
        Toast.makeText(this,"修改成功！",Toast.LENGTH_SHORT).show();
        this.finish();
    }
    //退出登录
    public void outLogin(View view){
        user.setStatus("0");
        dao.update(user);
        this.finish();
    }
    //退出当前界面
    public void back(View view){
        this.finish();
    }
}
