package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.adapter.ChatAdapter;
import com.bk120.cinematicket.bean.BuyFilmSign;
import com.bk120.cinematicket.bean.Chat;
import com.bk120.cinematicket.bean.ChatSign;
import com.bk120.cinematicket.bean.User;
import com.bk120.cinematicket.constants.JuHeConstant;
import com.bk120.cinematicket.constants.MainConstant;
import com.bk120.cinematicket.db.UserInfoDao;
import com.bk120.cinematicket.turing.AesTest;
import com.bk120.cinematicket.utils.DateUtils;
import com.bk120.cinematicket.utils.JSONUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChatRoomActivity extends Activity {
    @BindView(R.id.chatroomactivity_recycleview)
    RecyclerView recyclerView;
    @BindView(R.id.chatroomactivity_text_tv)
    TextView text_tv;
    //聊天数据
    private List<Chat> lists;
    private User user;
    private UserInfoDao uDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initRecycleview();
        initLists();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initLists() {
        lists=new ArrayList<>();
        Chat chat1=new Chat(0,MainConstant.ROBOT_NAME,"您好呀！",DateUtils.getCurrentTime());
        lists.add(chat1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        srtAdapter();
    }

    private void srtAdapter() {
        recyclerView.setAdapter(new ChatAdapter(lists,this));
    }

    //初始化RecycleView
    private void initRecycleview() {
        uDao=new UserInfoDao(this);
        user=uDao.selectOnLine();
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }

    //发送消息
    public void sendMessage(View view){
        final String trim = text_tv.getText().toString().trim();
        if (TextUtils.isEmpty(trim)){
            Toast.makeText(this,"输入为空！",Toast.LENGTH_SHORT).show();
            return;
        }
        //插入用户一句话
        lists.add(new Chat(1,user.getUsername(),trim,DateUtils.getCurrentTime()));
        //访问网络
       /* OkHttpClient client=new OkHttpClient.Builder().build();
        final Request request=new Request.Builder().url(MainConstant.CHAT_URL+trim+MainConstant.CHAT_KEY).build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                Log.i("错误信息",e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.i("结果",response.body().string());
                EventBus.getDefault().post(new ChatSign(response.body().string()));
            }
        });*/
       new Thread(new Runnable() {
           @Override
           public void run() {
            String s = AesTest.testAes(trim);
               EventBus.getDefault().post(new ChatSign(s));
           }
       }).start();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getChatInfo(ChatSign sign){
        Chat chat=JSONUtils.getChat(sign.getSign());
        if (chat==null){
            String text="这个问题太难！我表示不知道耶！";
             Chat chat1=new Chat(0, MainConstant.ROBOT_NAME,text,DateUtils.getCurrentTime());
             lists.add(chat1);
        }else {
            lists.add(chat);
        }
        //清空话板
        text_tv.setText("");
        recyclerView.setAdapter(new ChatAdapter(lists,this));
        recyclerView.scrollToPosition(lists.size()-1);
    }

    //退出当前页
    public void back(View view){
        this.finish();
    }
}
