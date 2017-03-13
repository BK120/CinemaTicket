package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.bk120.cinematicket.utils.DateUtils;
import com.bk120.cinematicket.utils.KeyBoardUtils;

import android.os.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private User user;
    private UserInfoDao uDao;
    private CommentDao cDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        ButterKnife.bind(this);
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    }

    //返回
    public void back(View view){
        this.finish();
    }
    //提交
    public void send(View view){
        //保存一条评论头像由给出的头像库中
        int icon=(int)(Math.random()* MainConstant.COMMENT_ICON_LIBRARY.length);
        String username = user.getUsername();
        String describe=content_et.getText().toString().trim();
        String time= DateUtils.getCurrentDate();
        Comment c=new Comment(username,icon,describe,time);
        cDao.insert(c);
        Toast.makeText(this,"评论成功！",Toast.LENGTH_SHORT).show();
        this.finish();
    }

   private TextWatcher myTextWatcher=new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence s, int start, int count, int after) {
       }

       @Override
       public void onTextChanged(CharSequence s, int start, int before, int count) {
           if (start==0){
               sendBtn.setBackgroundColor(getResources().getColor(R.color.colorGray));
               content_et.setHint("分享影评...");
               sendBtn.setClickable(false);
           }else {
               sendBtn.setBackgroundColor(getResources().getColor(R.color.card_bg_yellow));
               sendBtn.setClickable(true);
           }
           if (TextUtils.isEmpty(s.toString().trim())){
               content_length_tv.setVisibility(View.INVISIBLE);
           }else {
               content_length_tv.setVisibility(View.VISIBLE);
               content_length_tv.setText(start+"");
           }

       }
       @Override
       public void afterTextChanged(Editable s) {
       }
   };
}
