package com.bk120.cinematicket.fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.activitys.BalanceActivity;
import com.bk120.cinematicket.activitys.ChatRoomActivity;
import com.bk120.cinematicket.activitys.GestureLockActivity;
import com.bk120.cinematicket.activitys.LoginRegisterActivity;
import com.bk120.cinematicket.activitys.SettingActivity;
import com.bk120.cinematicket.activitys.TicketOrderActivity;
import com.bk120.cinematicket.activitys.UpdateUserActivity;
import com.bk120.cinematicket.activitys.WalletActivity;
import com.bk120.cinematicket.bean.StringSign;
import com.bk120.cinematicket.bean.User;
import com.bk120.cinematicket.constants.MainConstant;
import com.bk120.cinematicket.db.UserInfoDao;
import com.bk120.cinematicket.utils.SharePreferencesUtils;
import com.bk120.cinematicket.zxing.encoding.EncodingHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class WodeFragment extends Fragment implements View.OnClickListener{
    private View rootView;
    //用户头像
    private ImageView user_icon_iv,erweima_iv;
    //用户登录状态
    private TextView user_status_tv,yue_tv,xiangkan_tv,kanguo_tv,yingping_tv,redian_tv, weixiaofei_tv,
    daifukaun_tv,daipingjia_tv,daituikuan_tv;
    private RelativeLayout user_info_rl,wodedingdan_rl,wodeqianbao_rl,yue_rl,youhuijuan_rl,shangcheng_rl,
    wodexiaoxi_rl,wodeshouchang_rl,huiyuanzhongxin_rl,wodeshenghuo_rl,shezhi_rl,chatroom_rl;
    private User user;
    private UserInfoDao dao;
    public WodeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_wode, container, false);
        dao=new UserInfoDao(getContext());
        initView();
        initListener();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        user=dao.selectOnLine();
        initShow(user);
    }

    //开始显示
    private void initShow(User user) {
        //获取一个在线的人
        if (user==null){
            user_status_tv.setText("立即登录");
            user_icon_iv.setImageResource(R.mipmap.touxiang_outline);
            erweima_iv.setImageResource(R.mipmap.arrow_right);
            yue_tv.setText("0.00元");
        }else {
            user_status_tv.setText(user.getUsername());
            //是否隐藏
            boolean aBoolean = SharePreferencesUtils.getBoolean(getContext(), MainConstant.IS_HIDE_MONEY, false);
            if (aBoolean){
                yue_tv.setText("**.**元");
            }else {
                yue_tv.setText(user.getBalance()+"元");
            }
            erweima_iv.setImageResource(R.mipmap.erweima);
            user_icon_iv.setImageResource(R.mipmap.touxiang_online);
        }
    }
    //设置监听
    private void initListener() {
        xiangkan_tv.setOnClickListener(this);
        kanguo_tv.setOnClickListener(this);
        yingping_tv.setOnClickListener(this);
        redian_tv.setOnClickListener(this);
        daituikuan_tv.setOnClickListener(this);
        daipingjia_tv.setOnClickListener(this);
        daifukaun_tv.setOnClickListener(this);
        weixiaofei_tv.setOnClickListener(this);
        user_info_rl.setOnClickListener(this);
        wodedingdan_rl.setOnClickListener(this);
        wodeqianbao_rl.setOnClickListener(this);
        yue_rl.setOnClickListener(this);
        youhuijuan_rl.setOnClickListener(this);
        shangcheng_rl.setOnClickListener(this);
        wodexiaoxi_rl.setOnClickListener(this);
        huiyuanzhongxin_rl.setOnClickListener(this);
        wodeshouchang_rl.setOnClickListener(this);
        wodeshenghuo_rl.setOnClickListener(this);
        shezhi_rl.setOnClickListener(this);
        chatroom_rl.setOnClickListener(this);
        erweima_iv.setOnClickListener(this);
    }

    //初始化控件
    private void initView() {
        user_icon_iv= (ImageView) rootView.findViewById(R.id.wodefragment_icon);
        user_status_tv= (TextView) rootView.findViewById(R.id.wodefragment_status_tv);
        yue_tv= (TextView) rootView.findViewById(R.id.wodefragment_yue_rl_tv);
        //同时需要设置监听
        xiangkan_tv= (TextView) rootView.findViewById(R.id.wodefragment_xiangkan);
        kanguo_tv= (TextView) rootView.findViewById(R.id.wodefragment_kanguo);
        yingping_tv= (TextView) rootView.findViewById(R.id.wodefragment_yinping);
        redian_tv= (TextView) rootView.findViewById(R.id.wodefragment_redian);
        weixiaofei_tv= (TextView) rootView.findViewById(R.id.wodefragment_weixiaofei);
        daifukaun_tv= (TextView) rootView.findViewById(R.id.wodefragment_daifukuan);
        daipingjia_tv= (TextView) rootView.findViewById(R.id.wodefragment_daipinjia);
        daituikuan_tv= (TextView) rootView.findViewById(R.id.wodefragment_daituikuan);
        user_info_rl= (RelativeLayout) rootView.findViewById(R.id.wodefragment_user_info_rl);
        wodedingdan_rl= (RelativeLayout) rootView.findViewById(R.id.wodefragment_all_dingdan_rl);
        wodeqianbao_rl= (RelativeLayout) rootView.findViewById(R.id.wodefragment_wodeqianbao_rl);
        yue_rl= (RelativeLayout) rootView.findViewById(R.id.wodefragment_yue_rl);
        youhuijuan_rl= (RelativeLayout) rootView.findViewById(R.id.wodefragment_youhuijuan_rl);
        shangcheng_rl= (RelativeLayout) rootView.findViewById(R.id.wodefragment_shangcheng_rl);
        wodexiaoxi_rl= (RelativeLayout) rootView.findViewById(R.id.wodefragment_wodexiaoxi_rl);
        wodeshouchang_rl= (RelativeLayout) rootView.findViewById(R.id.wodefragment_wodeshouchang_rl);
        huiyuanzhongxin_rl= (RelativeLayout) rootView.findViewById(R.id.wodefragment_huiyuanzhongxing_rl);
        wodeshenghuo_rl= (RelativeLayout) rootView.findViewById(R.id.wodefragment_wodeshenghuo_rl);
        shezhi_rl= (RelativeLayout) rootView.findViewById(R.id.wodefragment_shezhi_rl);
        chatroom_rl= (RelativeLayout) rootView.findViewById(R.id.wodefragment_chatroom_rl);
        erweima_iv= (ImageView) rootView.findViewById(R.id.wodefragment_erweima);
    }
    //监听点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wodefragment_user_info_rl:
                if (user==null){
                    showLoginAndRegister();
                }else {
                    //修改用户信息部分
                    Intent i=new Intent(getContext(), UpdateUserActivity.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.acrivity4_push_in,0);
                }
                break;
            case R.id.wodefragment_wodeqianbao_rl:
                //进入钱包部分
                if (user==null){
                    showLoginAndRegister();
                }else {
                    //进入查看钱包页面
                    Intent i=new Intent(getContext(), WalletActivity.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.acrivity2_push_in,0);
                }
                break;
            case R.id.wodefragment_yue_rl:
                //进入余额设置部分功能  隐藏余额
                if (user==null){
                    showLoginAndRegister();
                }else {
                    //进入查看钱包页面
                    boolean aBoolean = SharePreferencesUtils.getBoolean(getContext(), MainConstant.IS_OPEN_GESTURE, false);
                    if (aBoolean){
                        Intent i = new Intent(getContext(), GestureLockActivity.class);
                        startActivity(i);
                    }else {
                        Intent i = new Intent(getContext(), BalanceActivity.class);
                        startActivity(i);
                    }
                    getActivity().overridePendingTransition(R.anim.acrivity3_push_in,0);
                }
                break;
            case R.id.wodefragment_all_dingdan_rl:
                //进入订单界面
                if (user==null){
                    showLoginAndRegister();
                }else {
                    Intent i=new Intent(getContext(), TicketOrderActivity.class);
                    startActivity(i);
                }
                break;
            case R.id.wodefragment_erweima:
                //进入订单界面
                if (user==null){
                    showLoginAndRegister();
                }else {
                   shwoErWeiMaDialogInfo();
                }
                break;
            case R.id.wodefragment_chatroom_rl:
                //进入钱包部分
                if (user==null){
                    showLoginAndRegister();
                }else {
                    //进入查看钱包页面
                    Intent i=new Intent(getContext(), ChatRoomActivity.class);
                    startActivity(i);
                }
                break;
            case R.id.wodefragment_daifukuan:
                //待付款
                Toast.makeText(getContext(),"功能未开启!敬请期待!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wodefragment_daipinjia:
                //待评价
                Toast.makeText(getContext(),"功能未开启!敬请期待!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wodefragment_daituikuan:
                //待退款
                Toast.makeText(getContext(),"功能未开启!敬请期待!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wodefragment_weixiaofei:
                //未消费
                Toast.makeText(getContext(),"功能未开启!敬请期待!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wodefragment_xiangkan:
                //想看
                Toast.makeText(getContext(),"功能未开启!敬请期待!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wodefragment_kanguo:
                //看过
                Toast.makeText(getContext(),"功能未开启!敬请期待!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wodefragment_yinping:
                //影评
                Toast.makeText(getContext(),"功能未开启!敬请期待!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wodefragment_redian:
                //热点
                Toast.makeText(getContext(),"功能未开启!敬请期待!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wodefragment_youhuijuan_rl:
                //优惠券
                Toast.makeText(getContext(),"功能未开启!敬请期待!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wodefragment_shangcheng_rl:
                //商城
                Toast.makeText(getContext(),"功能未开启!敬请期待!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wodefragment_wodexiaoxi_rl:
                //我的消息
                Toast.makeText(getContext(),"功能未开启!敬请期待!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wodefragment_wodeshouchang_rl:
                //我的收藏
                Toast.makeText(getContext(),"功能未开启!敬请期待!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wodefragment_huiyuanzhongxing_rl:
                //会员中心
                Toast.makeText(getContext(),"功能未开启!敬请期待!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wodefragment_wodeshenghuo_rl:
                //我的生活
                Toast.makeText(getContext(),"功能未开启!敬请期待!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wodefragment_shezhi_rl:
                //设置
                if (user==null){
                    showLoginAndRegister();
                }else {
                    //进入查看钱包页面
                    Intent i=new Intent(getContext(), SettingActivity.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.acrivity1_push_in,0);
                }
                break;
            default:
                Toast.makeText(getContext(),"功能未开启!敬请期待!",Toast.LENGTH_SHORT).show();
        }
    }
    //显示一个二维码Diaolog
    private void shwoErWeiMaDialogInfo() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view=View.inflate(getContext(),R.layout.erweima_dialog,null);
        ImageView iv= (ImageView) view.findViewById(R.id.erweima_dialog_iv);
        builder.setView(view);
        builder.create().show();
        //二维码信息
        String content=user.getId()+"\n"+user.getUsername()+"\n"+user.getMotto();
        try {
            //生成二维码图片,第一个为地址，第二参数个为正方形图片边长
            Bitmap bitmap = EncodingHandler.createQRCode(content, 800);
            if (bitmap==null){
                Toast.makeText(getContext(),"生成二维码失败!",Toast.LENGTH_SHORT).show();
            }else {
                iv.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoginAndRegister(){
        //登录注册部分
        Intent i=new Intent(getContext(), LoginRegisterActivity.class);
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.acrivity1_push_in,0);
    }
}
