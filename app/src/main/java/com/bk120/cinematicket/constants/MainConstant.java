package com.bk120.cinematicket.constants;

import com.bk120.cinematicket.R;

/**
 * Created by bk120 on 2017/3/7.
 * 主要常量池
 */

public class MainConstant {
    //当前所在的主Fragment位置标记
    public static String CURRENT_MAIN_FRAGMENT="current_main_fragment";
    //存放所有Card的图标
    public static int[] CARDICONS={R.mipmap.card_jianhang,R.mipmap.card_nonghang,
            R.mipmap.card_gonghang,R.mipmap.card_weixin,R.mipmap.card_qq,R.mipmap.card_zhifubao};
    //存放该账户Card上的的余额，注册时就赋值
    //顺序，建行，农行，工行，微信，QQ，支付宝
    public static double[] CARDMONEYS={1800,20,101,35.56,28.11,300};
    //存放该账户的余额,注册时就赋值
    public static double USERMONEY=6.66;
    //账户背景颜色数组
    public static int[] CARD_BG={R.color.card_bg_blue,R.color.card_bg_brown,R.color.card_bg_green,
    R.color.card_bg_red,R.color.card_bg_yale,R.color.card_bg_yellow};
    //账户类型
    public static String[] CARD_TYPE={"QQ","建行","农行","工行","微信","支付宝"};
    //设定个人账户最多个数
    public static int CARDMAX=10;
    //每次添加的账户钱的最大值
    public static double BALANCEMAX=200;
    //充值账户余额界面的Gallery里面的电影海报图
    public static int[] GALLERIMAGES={R.mipmap.gallery0,R.mipmap.gallery1,R.mipmap.gallery2,R.mipmap.gallery3,
    R.mipmap.gallery4};
    //未设置，默认的手势密码
    public static int[] GESTURE_NUMBER={1,4,7,8,9};
    //手势密码存储在SharePrefernce中
    public static String GESTURE_LOCK="gesture_lock";
    //是否开启手势密码
    public static String IS_OPEN_GESTURE="is_open_gesture";
    //是否开启隐藏金额
    public static String IS_HIDE_MONEY="is_hide_money";
    //本项目GitHub地址
    public static String PROJECT_GITHUB_URL="https://github.com/BK120/CinemaTicket.git";


}
