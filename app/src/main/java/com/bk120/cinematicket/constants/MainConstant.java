package com.bk120.cinematicket.constants;

import com.bk120.cinematicket.R;

/**
 * Created by bk120 on 2017/3/7.
 * 主要常量池
 */

public class MainConstant {
    //是否需要在Application中初始化资源
    public static String IS_INIT_RES="is_init_res";
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
    //评论社区的初始化用户名,预先放置对象
    public static String[] COMMENT_NAME={"初三四班小月亮","二月红花","No-Faker","ButterKey",
            "自习室前排","蚂蚁国王","铅笔头","BK120","Android小白"};
    //评论社区用户头像
    public static int[] COMMENT_ICON={R.mipmap.comment_icon1,R.mipmap.comment_icon2,R.mipmap.comment_icon3,
    R.mipmap.comment_icon4,R.mipmap.comment_icon5,R.mipmap.comment_icon6,R.mipmap.comment_icon7,
    R.mipmap.comment_icon8,R.mipmap.comment_icon9,R.mipmap.i0,R.mipmap.i1,R.mipmap.i2,R.mipmap.i3,R.mipmap.i4,
            R.mipmap.i5,R.mipmap.i6,R.mipmap.i7,R.mipmap.i8,R.mipmap.i9,R.mipmap.i10,R.mipmap.i11,R.mipmap.i12,
            R.mipmap.i13,R.mipmap.i14,R.mipmap.i15,R.mipmap.i16,R.mipmap.i17,R.mipmap.i18,R.mipmap.i19,R.mipmap.i20,
            R.mipmap.i22,R.mipmap.i21};
    //评论描述
    public static String[] COMMENT_DESCRIBE={"好全面，很真实的画面和平静的介绍，偷偷拍下陌生人的表情，看到镜头的人们总是带着迷茫和好奇，在他人眼中的自己，很想让我的父母也看看这个片子，对我来说完全陌生的时代，这些镜头里的人们现在依然在某个地方生活着吧。 生活生产学校娱乐景点文化等多个方面。part1是北京，part2有河南、江苏，part3是上海，最后很长的一个杂技表演。",
    "安东尼奥尼的三段中国映像。在文革期间受邀访华拍摄，却受到地点和人物的诸多限制。不愧是大师，依然展现出了卓越的洞察力，导致被禁31年。要别人来拍自己国家建设的多好，，完了发现人家视角比较客观还批判别人拍得不好，这种事也就某党干得出来。",
    "珍贵且真实的影像记录,让我不禁有所感想感思！","纪录片本身并不高明，不过能看到那个年代的中国就很满足了。至于偏见嘛，总不能只拍好的，不拍差的，那不就是政治宣传片了嘛！那个时代离我远去的太快了。",
            "不得不说，这部电影治好了我多年的拖延症和懒癌，情节紧凑到一气呵成，整部电影的观感就像过山车一样。可以看得出来剧本是经过了精心编排的，主线情节上穿插的笑点，让整部电影看起来酣畅淋漓",
            "张震讲故事之合租屋》：最令我们恐惧的是人的内心文/王麟孤身女子，陌生的租客，隐蔽在城市深处的合租屋，明亮的阳光铺撒进安宁的房间，斜靠在房屋一角的鲜红小提琴，隐含着一段令人崩溃的超自然的诡异。",
            "好像是学校统一看的吧...看到馨子的百度百科的时候顺便想起来了一刚= =！","那个时候孩子常上网是被抨击的，很庆幸自己生在网络时代且没有被抨击",
            "倪妮长了张我学生时代最讨厌的碧池脸，不想看。再看出品公司，特么的居然是光线，烂片地位还能撼动吗？",
            "张一白老师创造了中国广告界的一座里程碑。他让众多明星联袂拍摄了一个多种品牌混搭跨界的广告长片，还让这个广告登上了大银幕，并且还需要人们买票观看，颠覆了广告一向是免费的low逼形象，为广告这个边缘创意产业赢得了尊严。"
    };
    public static String[] COMMENT_TIME={"2014年02年23日","2011月05年20日","2012年02月15日","2016年10月18日","2016年05月05日","2016年11月23日","2017年02月12日","2017年01月22日","2013年06月08日"};
}
