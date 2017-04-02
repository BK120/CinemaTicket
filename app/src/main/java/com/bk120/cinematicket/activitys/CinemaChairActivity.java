package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.bean.StringSign;
import com.bk120.cinematicket.bean.Ticket;
import com.bk120.cinematicket.bean.User;
import com.bk120.cinematicket.constants.MainConstant;
import com.bk120.cinematicket.db.TicketInfoDao;
import com.bk120.cinematicket.db.UserInfoDao;
import com.bk120.cinematicket.gesture.GestureLockViewGroup;
import com.bk120.cinematicket.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

//影院座位的Activity
//236890;38;山西剧院(VR主题影院);太原市柳巷北路25号
public class CinemaChairActivity extends Activity {
    //要购买的电影Id
    private String movie_id;
    private String city_id;
    private String movie_name;
    //影院名
    private String cinema_name;
    private String cinema_address;
    //
    private User user;
    private UserInfoDao uDao;
    private TicketInfoDao tDao;
    //当前用户已经购买的票的数量
    private List<Ticket> tLists;
    //影院名
    @BindView(R.id.cinemachairactivity_cinema_name_tv)
    TextView cinema_name_tv;
    //票价
    @BindView(R.id.cinemachairactivity_chair_price)
    TextView chair_price_tv;
    //总价格
    @BindView(R.id.cinemachairactivity_all_price_tv)
    TextView all_price_tv;
    //买的总票数
    @BindView(R.id.cinemachairactivity_all_ticket_number)
    TextView ticket_number_tv;
    //当前选定的座位数
    private int chair_number=0;
    //当前的总价格
    private double all_price=0.00;
    //存储即将要买的座位号
    private List<Integer> chairList;
    //电影票单价为一个随机数
    private double price;
    //所有座位空间
    @BindView(R.id.bkcinema_chair1) ToggleButton tb1;@BindView(R.id.bkcinema_chair41) ToggleButton tb41;
    @BindView(R.id.bkcinema_chair2) ToggleButton tb2;@BindView(R.id.bkcinema_chair42) ToggleButton tb42;
    @BindView(R.id.bkcinema_chair3) ToggleButton tb3;@BindView(R.id.bkcinema_chair43) ToggleButton tb43;
    @BindView(R.id.bkcinema_chair4) ToggleButton tb4;@BindView(R.id.bkcinema_chair44) ToggleButton tb44;
    @BindView(R.id.bkcinema_chair5) ToggleButton tb5;@BindView(R.id.bkcinema_chair45) ToggleButton tb45;
    @BindView(R.id.bkcinema_chair6) ToggleButton tb6;@BindView(R.id.bkcinema_chair46) ToggleButton tb46;
    @BindView(R.id.bkcinema_chair7) ToggleButton tb7;@BindView(R.id.bkcinema_chair47) ToggleButton tb47;
    @BindView(R.id.bkcinema_chair8) ToggleButton tb8;@BindView(R.id.bkcinema_chair48) ToggleButton tb48;
    @BindView(R.id.bkcinema_chair9) ToggleButton tb9;@BindView(R.id.bkcinema_chair49) ToggleButton tb49;
    @BindView(R.id.bkcinema_chair10) ToggleButton tb10;@BindView(R.id.bkcinema_chair50) ToggleButton tb50;
    @BindView(R.id.bkcinema_chair11) ToggleButton tb11;@BindView(R.id.bkcinema_chair51) ToggleButton tb51;
    @BindView(R.id.bkcinema_chair12) ToggleButton tb12;@BindView(R.id.bkcinema_chair52) ToggleButton tb52;
    @BindView(R.id.bkcinema_chair13) ToggleButton tb13;@BindView(R.id.bkcinema_chair53) ToggleButton tb53;
    @BindView(R.id.bkcinema_chair14) ToggleButton tb14;@BindView(R.id.bkcinema_chair54) ToggleButton tb54;
    @BindView(R.id.bkcinema_chair15) ToggleButton tb15;@BindView(R.id.bkcinema_chair55) ToggleButton tb55;
    @BindView(R.id.bkcinema_chair16) ToggleButton tb16;@BindView(R.id.bkcinema_chair56) ToggleButton tb56;
    @BindView(R.id.bkcinema_chair17) ToggleButton tb17;@BindView(R.id.bkcinema_chair57) ToggleButton tb57;
    @BindView(R.id.bkcinema_chair18) ToggleButton tb18;@BindView(R.id.bkcinema_chair58) ToggleButton tb58;
    @BindView(R.id.bkcinema_chair19) ToggleButton tb19;@BindView(R.id.bkcinema_chair59) ToggleButton tb59;
    @BindView(R.id.bkcinema_chair20) ToggleButton tb20;@BindView(R.id.bkcinema_chair60) ToggleButton tb60;
    @BindView(R.id.bkcinema_chair21) ToggleButton tb21;@BindView(R.id.bkcinema_chair61) ToggleButton tb61;
    @BindView(R.id.bkcinema_chair22) ToggleButton tb22;@BindView(R.id.bkcinema_chair62) ToggleButton tb62;
    @BindView(R.id.bkcinema_chair23) ToggleButton tb23;@BindView(R.id.bkcinema_chair63) ToggleButton tb63;
    @BindView(R.id.bkcinema_chair24) ToggleButton tb24;@BindView(R.id.bkcinema_chair64) ToggleButton tb64;
    @BindView(R.id.bkcinema_chair25) ToggleButton tb25;@BindView(R.id.bkcinema_chair65) ToggleButton tb65;
    @BindView(R.id.bkcinema_chair26) ToggleButton tb26;@BindView(R.id.bkcinema_chair66) ToggleButton tb66;
    @BindView(R.id.bkcinema_chair27) ToggleButton tb27;@BindView(R.id.bkcinema_chair67) ToggleButton tb67;
    @BindView(R.id.bkcinema_chair28) ToggleButton tb28;@BindView(R.id.bkcinema_chair68) ToggleButton tb68;
    @BindView(R.id.bkcinema_chair29) ToggleButton tb29;@BindView(R.id.bkcinema_chair69) ToggleButton tb69;
    @BindView(R.id.bkcinema_chair30) ToggleButton tb30;@BindView(R.id.bkcinema_chair70) ToggleButton tb70;
    @BindView(R.id.bkcinema_chair31) ToggleButton tb31;@BindView(R.id.bkcinema_chair71) ToggleButton tb71;
    @BindView(R.id.bkcinema_chair32) ToggleButton tb32;@BindView(R.id.bkcinema_chair72) ToggleButton tb72;
    @BindView(R.id.bkcinema_chair33) ToggleButton tb33;@BindView(R.id.bkcinema_chair73) ToggleButton tb73;
    @BindView(R.id.bkcinema_chair34) ToggleButton tb34;@BindView(R.id.bkcinema_chair74) ToggleButton tb74;
    @BindView(R.id.bkcinema_chair35) ToggleButton tb35;@BindView(R.id.bkcinema_chair75) ToggleButton tb75;
    @BindView(R.id.bkcinema_chair36) ToggleButton tb36;@BindView(R.id.bkcinema_chair76) ToggleButton tb76;
    @BindView(R.id.bkcinema_chair37) ToggleButton tb37;@BindView(R.id.bkcinema_chair77) ToggleButton tb77;
    @BindView(R.id.bkcinema_chair38) ToggleButton tb38;@BindView(R.id.bkcinema_chair78) ToggleButton tb78;
    @BindView(R.id.bkcinema_chair39) ToggleButton tb39;@BindView(R.id.bkcinema_chair79) ToggleButton tb79;
    @BindView(R.id.bkcinema_chair40) ToggleButton tb40;@BindView(R.id.bkcinema_chair80) ToggleButton tb80;
    //所有的座位集合
    private ToggleButton[] tbs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_chair);
        ButterKnife.bind(this);
        initPrice();
        initData();
    }
    //初始化单价
    private void initPrice() {
        double db=MainConstant.TICKET_PRICE_MIN+Math.random()*
                (MainConstant.TICKET_PRICE_MAX-MainConstant.TICKET_PRICE_MIN);
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
        String filesize = df.format(db);//返回的是String类型的
        price=Double.valueOf(filesize);
    }

    private void initData() {
        Intent intent = this.getIntent();
        movie_id=intent.getStringExtra("movie_id");
        city_id=intent.getStringExtra("city_id");
        movie_name=intent.getStringExtra("movie_name");
        cinema_name=intent.getStringExtra("cinema_name");
        cinema_address= intent.getStringExtra("cinema_address");
        uDao=new UserInfoDao(this);
        user=uDao.selectOnLine();
        tDao=new TicketInfoDao(this);
        if (user==null){}else {
            //这个查询应该是所有用户的电影票信息，而不是一个人的
            tLists = tDao.selectAllUsers();
        }
        initTbs();
        //初始化座位颜色设置
        initChairSetting();
        chairList=new ArrayList<>();
       //设置数据
        cinema_name_tv.setText(cinema_name);
        chair_price_tv.setText(price+"元/张");
    }
    private void initTbs() {
        tbs=new ToggleButton[]{tb1,tb2,tb3,tb4,tb5,tb6,tb7,tb8,tb9,tb10,tb11,tb12,tb13,tb14,tb15,tb16,
                tb17,tb18,tb19,tb20,tb21,tb22,tb23,tb24,tb25,tb26,tb27,tb28,tb29,tb30,tb31,tb32,tb33,tb34,tb35,tb36,tb37,tb38,tb39,
                tb40,tb41,tb42,tb43,tb44,tb45,tb46,tb47,tb48,tb49,tb50,tb51,tb52,tb53,tb54,tb55,tb56,tb57,tb58,tb59,tb60,tb61,
                tb62,tb63,tb64,tb65,tb66,tb67,tb68,tb69,tb70,tb71,tb72,tb73,tb74,tb75,tb76,tb77,tb78,tb79,tb80};
    }

    //初始化座位颜色设置
    private void initChairSetting() {
        if (tLists==null){
            return;
        }
        for(Ticket t:tLists){
            int position=t.getChair_number();
            String id = t.getMoive_id();
            String name = t.getCinema_name();
            String address = t.getCinema_address();
            //对于已经座位备选了的进行颜色初始化,并设置不可触摸
            if (id.equals(movie_id)&&name.equals(cinema_name)&&address.equals(cinema_address)){
               //同一个电影同一个影院同一个位置
                tbs[position].setBackgroundColor(getResources().getColor(R.color.colorRed));
                tbs[position].setEnabled(false);
            }
        }
    }
    //推荐座位
    @OnClick(R.id.cinemachairactivity_tuijian_tv)
    public void tuiJianInit(){
        for(int i=0;i<MainConstant.CHAIR_TUIJIAN.length;i++){
            if(tLists==null){
                tbs[MainConstant.CHAIR_TUIJIAN[i]].setBackgroundResource(R.drawable.chair_tuijian_shape1);
            }else {
                for(Ticket t:tLists){
                    int position=t.getChair_number();
                    String id = t.getMoive_id();
                    String name = t.getCinema_name();
                    String address = t.getCinema_address();
                    //对于已经座位备选了的进行颜色初始化,并设置不可触摸
                    if (id.equals(movie_id)&&name.equals(cinema_name)&&address.equals(cinema_address)&&MainConstant.CHAIR_TUIJIAN[i]==t.getChair_number()){
                        //同一个电影同一个影院同一个位置
                        tbs[MainConstant.CHAIR_TUIJIAN[i]].setBackgroundResource(R.drawable.chair_tuijian_shape2);
                    }else {
                        tbs[MainConstant.CHAIR_TUIJIAN[i]].setBackgroundResource(R.drawable.chair_tuijian_shape1);
                    }
                }
            }
        }
    }

    //保存按钮
    @OnClick(R.id.cinemachairactivity_save)
    public void save(View view){
        if (user==null){
            //跳去登录注册界面
            Intent i=new Intent(this,LoginRegisterActivity.class);
            startActivity(i);
            //发出信号关闭其他界面
            EventBus.getDefault().post(new StringSign("CloseActivity"));
            this.finish();
        }else {
            //付款
            payMoney();
        }

    }
    //弹出一个Dialog进行付款
    private void payMoney() {
        if(user.getBalance()<all_price){
            Toast.makeText(this,"余额不足,请先充值！",Toast.LENGTH_SHORT).show();
            return;
        }
        if (chair_number==0){
            Toast.makeText(this,"请先选择座位！",Toast.LENGTH_SHORT).show();
            return;
        }
        View childView=View.inflate(this,R.layout.pay_money_dialog,null);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(childView);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        TextView all_tv= (TextView) childView.findViewById(R.id.pay_money_dialog_pay_tv);
        TextView balance_tv= (TextView) childView.findViewById(R.id.pay_money_dialog_balance_tv);
        final TextView tv_show= (TextView) childView.findViewById(R.id.pay_money_dialog_show_tv);
        final GestureLockViewGroup glvg= (GestureLockViewGroup) childView.findViewById(R.id.pay_money_dialog_glvs);
        all_tv.setText("应付:-"+all_price+"元");
        balance_tv.setText("余额:"+user.getBalance()+"元");
        glvg.setAnswer(MainConstant.GRSTURE_PAY);
        glvg.setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {
            public void onBlockSelected(int cId) {
            }
            public void onGestureEvent(boolean matched) {
                if (matched){
                    tv_show.setText("密码正确");
                    //插入多条购票信息
                    insertTicket();
                    updateUser();
                    dialog.dismiss();
                    Toast.makeText(CinemaChairActivity.this,"购票成功！",Toast.LENGTH_SHORT).show();
                    //发出信号关闭其他界面
                    EventBus.getDefault().post(new StringSign("CloseActivity"));
                    CinemaChairActivity.this.finish();
                }else {
                    tv_show.setText("密码错误");
                }
            }
            @Override
            public void onUnmatchedExceedBoundary() {
                Toast.makeText(CinemaChairActivity.this,"超过尝试次数!",Toast.LENGTH_SHORT).show();
                glvg.setUnMatchExceedBoundary(3);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    //处理用户
    private void updateUser() {
        //精度处理
        BigDecimal op=new BigDecimal(Double.toString(all_price));
        BigDecimal re=new BigDecimal(Double.toString(user.getBalance()));

        user.setBalance(re.subtract(op).doubleValue());
        uDao.update(user);
    }

    //插入多条购票信息
    private void insertTicket() {
        for (int i=0;i<chairList.size();i++){
            Ticket ticket=new Ticket(movie_id,movie_name,user.getUsername(),price);
            ticket.setCinema_name(cinema_name);
            ticket.setCinema_address(cinema_address);
            ticket.setChair_number(chairList.get(i));
            ticket.setTime(DateUtils.getCurrentDate());
            ticket.setChair_loc(numberToLoc(chairList.get(i)));
            tDao.insert(ticket);
        }
    }

    //由座位号转换成排位号  比如15  为2排8号
    public String  numberToLoc(int num){
        int pai=(int)(num/8)+1;
        int lie=(num)%8+1;
        return pai+"排"+lie+"号";
    }

    //返回
    public void back(View view){
        this.finish();
    }

    //增加票
    public void addTicket(){
        chair_number++;
        ticket_number_tv.setText(chair_number+"");
        getAllPrice();
        all_price_tv.setText(all_price+"元");
    }
    //减少票儿
    public void reduceTicket(){
        chair_number--;
        ticket_number_tv.setText(chair_number+"");
       getAllPrice();
        all_price_tv.setText(all_price+"元");
    }
    //运算精度处理
    public void getAllPrice(){
        BigDecimal bigDecimal=new BigDecimal(Integer.toString(chair_number));
        BigDecimal unitPrice=new BigDecimal(Double.toString(price));
        all_price=bigDecimal.multiply(unitPrice).doubleValue();
    }
    //从集合中移除一个座位号
    public void removeTicket(int number){
        for (int i=0;i<chairList.size();i++){
            if(number==chairList.get(i)){
                chairList.remove(i);
            }
        }

    }
    //绑定所有控件
    @OnCheckedChanged(R.id.bkcinema_chair1)
    public void onclick1(boolean isChecked){if (isChecked){
            chairList.add(0);
            tb1.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
            removeTicket(0);
            tb1.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair2)
    public void onclick2(boolean isChecked){if (isChecked){
        chairList.add(1);
        tb2.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(1);
        tb2.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair3)
    public void onclick3(boolean isChecked){if (isChecked){
        chairList.add(2);
        tb3.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(2);
        tb3.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair4)
    public void onclick4(boolean isChecked){if (isChecked){
        chairList.add(3);
        tb4.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(3);
        tb4.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair5)
    public void onclick5(boolean isChecked){if (isChecked){
        chairList.add(4);
        tb5.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(4);
        tb5.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair6)
    public void onclick6(boolean isChecked){if (isChecked){
        chairList.add(5);
        tb6.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(5);
        tb6.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair7)
    public void onclick7(boolean isChecked){if (isChecked){
        chairList.add(6);
        tb7.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(6);
        tb7.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair8)
    public void onclick8(boolean isChecked){if (isChecked){
        chairList.add(7);
        tb8.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(7);
        tb8.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair9)
    public void onclick9(boolean isChecked){if (isChecked){
        chairList.add(8);
        tb9.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(8);
        tb9.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair10)
    public void onclick10(boolean isChecked){if (isChecked){
        chairList.add(9);
        tb10.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(9);
        tb10.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair11)
    public void onclick11(boolean isChecked){if (isChecked){
        chairList.add(10);
        tb11.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(10);
        tb11.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair12)
    public void onclick12(boolean isChecked){if (isChecked){
        chairList.add(11);
        tb12.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(11);
        tb12.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair13)
    public void onclick13(boolean isChecked){if (isChecked){
        chairList.add(12);
        tb13.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(12);
        tb13.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair14)
    public void onclick14(boolean isChecked){if (isChecked){
        chairList.add(13);
        tb14.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(13);
        tb14.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair15)
    public void onclick15(boolean isChecked){if (isChecked){
        chairList.add(14);
        tb15.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(14);
        tb15.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair16)
    public void onclick16(boolean isChecked){if (isChecked){
        chairList.add(15);
        tb16.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(15);
        tb16.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair17)
    public void onclick17(boolean isChecked){if (isChecked){
        chairList.add(16);
        tb17.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(16);
        tb17.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair18)
    public void onclick18(boolean isChecked){if (isChecked){
        chairList.add(17);
        tb18.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(17);
        tb18.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair19)
    public void onclick19(boolean isChecked){if (isChecked){
        chairList.add(18);
        tb19.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(18);
        tb19.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair20)
    public void onclick20(boolean isChecked){if (isChecked){
        chairList.add(19);
        tb20.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(19);
        tb20.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair21)
    public void onclick21(boolean isChecked){if (isChecked){
        chairList.add(20);
        tb21.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(20);
        tb21.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair22)
    public void onclick22(boolean isChecked){if (isChecked){
        chairList.add(21);
        tb22.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(21);
        tb22.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair23)
    public void onclick23(boolean isChecked){if (isChecked){
        chairList.add(22);
        tb23.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(22);
        tb23.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair24)
    public void onclick24(boolean isChecked){if (isChecked){
        chairList.add(23);
        tb24.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(23);
        tb24.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair25)
    public void onclick25(boolean isChecked){if (isChecked){
        chairList.add(24);
        tb25.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(24);
        tb25.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair26)
    public void onclick26(boolean isChecked){if (isChecked){
        chairList.add(25);
        tb26.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(25);
        tb26.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair27)
    public void onclick27(boolean isChecked){if (isChecked){
        chairList.add(26);
        tb27.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(26);
        tb27.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair28)
    public void onclick28(boolean isChecked){if (isChecked){
        chairList.add(27);
        tb28.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(27);
        tb28.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair29)
    public void onclick29(boolean isChecked){if (isChecked){
        chairList.add(28);
        tb29.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(28);
        tb29.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair30)
    public void onclick30(boolean isChecked){if (isChecked){
        chairList.add(29);
        tb30.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(29);
        tb30.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair31)
    public void onclick31(boolean isChecked){if (isChecked){
        chairList.add(30);
        tb31.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(30);
        tb31.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair32)
    public void onclick32(boolean isChecked){if (isChecked){
        chairList.add(31);
        tb32.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(31);
        tb32.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair33)
    public void onclick33(boolean isChecked){if (isChecked){
        chairList.add(32);
        tb33.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(32);
        tb33.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair34)
    public void onclick34(boolean isChecked){if (isChecked){
        chairList.add(33);
        tb34.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(33);
        tb34.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair35)
    public void onclick35(boolean isChecked){if (isChecked){
        chairList.add(34);
        tb35.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(34);
        tb35.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair36)
    public void onclick36(boolean isChecked){if (isChecked){
        chairList.add(35);
        tb36.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(35);
        tb36.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair37)
    public void onclick37(boolean isChecked){if (isChecked){
        chairList.add(36);
        tb37.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(36);
        tb37.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair38)
    public void onclick38(boolean isChecked){if (isChecked){
        chairList.add(37);
        tb38.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(37);
        tb38.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair39)
    public void onclick39(boolean isChecked){if (isChecked){
        chairList.add(38);
        tb39.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(38);
        tb39.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair40)
    public void onclick40(boolean isChecked){if (isChecked){
        chairList.add(39);
        tb40.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(39);
        tb40.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair41)
    public void onclick41(boolean isChecked){if (isChecked){
        chairList.add(40);
        tb41.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(40);
        tb41.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair42)
    public void onclick42(boolean isChecked){if (isChecked){
        chairList.add(41);
        tb42.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(41);
        tb42.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair43)
    public void onclick43(boolean isChecked){if (isChecked){
        chairList.add(42);
        tb43.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(42);
        tb43.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair44)
    public void onclick44(boolean isChecked){if (isChecked){
        chairList.add(43);
        tb44.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(43);
        tb44.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair45)
    public void onclick45(boolean isChecked){if (isChecked){
        chairList.add(44);
        tb45.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(44);
        tb45.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair46)
    public void onclick46(boolean isChecked){if (isChecked){
        chairList.add(45);
        tb46.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(45);
        tb46.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair47)
    public void onclick47(boolean isChecked){if (isChecked){
        chairList.add(46);
        tb47.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(46);
        tb47.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair48)
    public void onclick48(boolean isChecked){if (isChecked){
        chairList.add(47);
        tb48.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(47);
        tb48.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair49)
    public void onclick49(boolean isChecked){if (isChecked){
        chairList.add(48);
        tb49.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(48);
        tb49.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair50)
    public void onclick50(boolean isChecked){if (isChecked){
        chairList.add(49);
        tb50.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(49);
        tb50.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair51)
    public void onclick51(boolean isChecked){if (isChecked){
        chairList.add(50);
        tb51.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(50);
        tb51.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair52)
    public void onclick52(boolean isChecked){if (isChecked){
        chairList.add(51);
        tb52.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(51);
        tb52.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair53)
    public void onclick53(boolean isChecked){if (isChecked){
        chairList.add(52);
        tb53.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(52);
        tb53.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair54)
    public void onclick54(boolean isChecked){if (isChecked){
        chairList.add(53);
        tb54.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(53);
        tb54.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair55)
    public void onclick55(boolean isChecked){if (isChecked){
        chairList.add(54);
        tb55.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(54);
        tb55.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair56)
    public void onclick56(boolean isChecked){if (isChecked){
        chairList.add(55);
        tb56.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(55);
        tb56.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair57)
    public void onclick57(boolean isChecked){if (isChecked){
        chairList.add(56);
        tb57.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(56);
        tb57.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair58)
    public void onclick58(boolean isChecked){if (isChecked){
        chairList.add(57);
        tb58.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(57);
        tb58.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair59)
    public void onclick59(boolean isChecked){if (isChecked){
        chairList.add(58);
        tb59.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(58);
        tb59.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair60)
    public void onclick60(boolean isChecked){if (isChecked){
        chairList.add(59);
        tb60.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(59);
        tb60.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair61)
    public void onclick61(boolean isChecked){if (isChecked){
        chairList.add(60);
        tb61.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(60);
        tb61.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair62)
    public void onclick62(boolean isChecked){if (isChecked){
        chairList.add(61);
        tb62.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(61);
        tb62.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair63)
    public void onclick63(boolean isChecked){if (isChecked){
        chairList.add(62);
        tb63.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(62);
        tb63.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair64)
    public void onclick64(boolean isChecked){if (isChecked){
        chairList.add(63);
        tb64.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(63);
        tb64.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair65)
    public void onclick65(boolean isChecked){if (isChecked){
        chairList.add(64);
        tb65.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(64);
        tb65.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair66)
    public void onclick66(boolean isChecked){if (isChecked){
        chairList.add(65);
        tb66.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(65);
        tb66.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair67)
    public void onclick67(boolean isChecked){if (isChecked){
        chairList.add(66);
        tb67.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(66);
        tb67.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair68)
    public void onclick68(boolean isChecked){if (isChecked){
        chairList.add(67);
        tb68.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(67);
        tb68.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair69)
    public void onclick69(boolean isChecked){if (isChecked){
        chairList.add(68);
        tb69.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(68);
        tb69.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair70)
    public void onclick70(boolean isChecked){if (isChecked){
        chairList.add(69);
        tb70.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(69);
        tb70.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair71)
    public void onclick71(boolean isChecked){if (isChecked){
        chairList.add(70);
        tb71.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(70);
        tb71.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair72)
    public void onclick72(boolean isChecked){if (isChecked){
        chairList.add(71);
        tb72.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(71);
        tb72.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}

    @OnCheckedChanged(R.id.bkcinema_chair73)
    public void onclick73(boolean isChecked){if (isChecked){
        chairList.add(72);
        tb73.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(72);
        tb73.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair74)
    public void onclick74(boolean isChecked){if (isChecked){
        chairList.add(73);
        tb74.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(73);
        tb74.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair75)
    public void onclick75(boolean isChecked){if (isChecked){
        chairList.add(74);
        tb75.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(74);
        tb75.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair76)
    public void onclick76(boolean isChecked){if (isChecked){
        chairList.add(75);
        tb76.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(75);
        tb76.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair77)
    public void onclick77(boolean isChecked){if (isChecked){
        chairList.add(76);
        tb77.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(76);
        tb77.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair78)
    public void onclick78(boolean isChecked){if (isChecked){
        chairList.add(77);
        tb78.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(77);
        tb78.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair79)
    public void onclick79(boolean isChecked){if (isChecked){
        chairList.add(78);
        tb79.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(78);
        tb79.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}
    @OnCheckedChanged(R.id.bkcinema_chair80)
    public void onclick80(boolean isChecked){if (isChecked){
        chairList.add(79);
        tb80.setBackgroundColor(getResources().getColor(R.color.colorRed));addTicket();}else {
        removeTicket(79);
        tb80.setBackgroundColor(getResources().getColor(R.color.colorWhite));reduceTicket();}}

}
