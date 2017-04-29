package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.bean.Comment;
import com.bk120.cinematicket.bean.User;
import com.bk120.cinematicket.constants.MainConstant;
import com.bk120.cinematicket.db.CommentDao;
import com.bk120.cinematicket.db.UserInfoDao;
import com.bk120.cinematicket.utils.BaiBuDingWeiUtils;
import com.bk120.cinematicket.utils.DateUtils;
import com.bk120.cinematicket.utils.KeyBoardUtils;

import android.os.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;

public class AddCommentActivity extends Activity {
    @BindView(R.id.addcommentactivity_username_tv)
    TextView username_tv;
    @BindView(R.id.addcommentactivity_content_et)
    EditText content_et;
    @BindView(R.id.addcommentactivity_rl)
    RelativeLayout rl;
    @BindView(R.id.addcommentactivity_content_number_tv)
    TextView content_length_tv;
    @BindView(R.id.addcommentactivity_send_btn)
    Button sendBtn;

    @BindView(R.id.addcommentactivity_address)
    TextView address_tv;
    private User user;
    private UserInfoDao uDao;
    private CommentDao cDao;
    private BaiBuDingWeiUtils mBUtils;
    private String addressInfo="定位中...";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        ButterKnife.bind(this);
        mBUtils=new BaiBuDingWeiUtils(this);
        initListener();
        mBUtils.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBUtils.stop();
                if (addressInfo!=null) {
                    if (addressInfo.length() > 15) {
                        String n = addressInfo.substring(0, 15) + "...";
                        address_tv.setText(n);
                    } else {
                        address_tv.setText(addressInfo);
                    }
                }else {
                    addressInfo="定位失败...";
                    address_tv.setText(addressInfo);
                }
            }
        },3000);
        initData();
    }
    //初始化数据
    private void initData() {
        uDao=new UserInfoDao(this);
        user=uDao.selectOnLine();
        username_tv.setText(user.getUsername());
        cDao=new CommentDao(this);
    }

    //初始化监听
    private void initListener() {
        content_et.addTextChangedListener(myTextWatcher);
        mBUtils.setListener(new BaiBuDingWeiUtils.BaiDuDingWeiListener() {
            @Override
            public void getTime(String time) {
            }
            @Override
            public void getLatitude(String latitude) {
            }
            @Override
            public void getLontitude(String lontitude) {
            }
            @Override
            public void getRadius(String radius) {
            }
            @Override
            public void getAddress(String address) {
            }
            @Override
            public void getLocationDes(String des) {
                addressInfo=des;
            }
            @Override
            public void error(String s) {
                addressInfo="定位失败...";
            }
        });
    }

    //返回
    public void back(View view){
        this.finish();
    }
    //提交
    public void send(View view){
        //保存一条评论头像由给出的头像库中
        int icon=(int)(Math.random()* MainConstant.COMMENT_ICON.length);
        String username = user.getUsername();
        String describe=content_et.getText().toString().trim();
        String time= DateUtils.getCurrentDate();
        Comment c=new Comment(username,icon,describe,time);
        cDao.insert(c);
        Toast.makeText(this,"评论成功！",Toast.LENGTH_SHORT).show();
        this.finish();
    }

    /**
     * 监视输入文字改变而对其他控件的改变
     */
   private TextWatcher myTextWatcher=new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence s, int start, int count, int after) {
       }

       @Override
       public void onTextChanged(CharSequence s, int start, int before, int count) {
           if (s==null||s.length()==0){
               content_length_tv.setVisibility(View.INVISIBLE);
               content_et.setHint("分享影评...");
               content_length_tv.setText("");
               sendBtn.setBackgroundColor(getResources().getColor(R.color.colorGray));
               sendBtn.setClickable(false);
           }
           if(s.length()!=0){
               content_length_tv.setVisibility(View.VISIBLE);
               content_length_tv.setText(s.length()+"");
               sendBtn.setBackgroundColor(getResources().getColor(R.color.card_bg_yellow));
               sendBtn.setClickable(true);
           }
       }
       @Override
       public void afterTextChanged(Editable s) {
       }
   };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
