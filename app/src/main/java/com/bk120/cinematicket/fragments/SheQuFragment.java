package com.bk120.cinematicket.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.activitys.AddCommentActivity;
import com.bk120.cinematicket.activitys.LoginRegisterActivity;
import com.bk120.cinematicket.adapter.SheQuAdapter;
import com.bk120.cinematicket.bean.Comment;
import com.bk120.cinematicket.bean.User;
import com.bk120.cinematicket.db.CommentDao;
import com.bk120.cinematicket.db.UserInfoDao;
import com.bk120.cinematicket.utils.DateUtils;
import com.bk120.cinematicket.views.RecycleViewDividerL;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SheQuFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private ImageButton btn;
    //当前在线的用户
    private User user;
    private UserInfoDao uDao;
    public SheQuFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_she_qu, container, false);
        initView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        initRecycleview();
    }
    //初始化RecycleView
    private void initRecycleview() {
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new RecycleViewDividerL(getContext(), LinearLayout.VERTICAL,10,R.color.colorBlue));
        CommentDao cDao=new CommentDao(getContext());
        List<Comment> list = cDao.selelctAll();
        recyclerView.setAdapter(new SheQuAdapter(getContext(),list));

    }

    //初始化数据
    private void initData() {
        uDao=new UserInfoDao(getContext());
        user=uDao.selectOnLine();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user==null){
                    //登录注册界面
                    Intent i=new Intent(getContext(), LoginRegisterActivity.class);
                    startActivity(i);
                }else {
                    //添加评论
                    Intent i=new Intent(getContext(), AddCommentActivity.class);
                    startActivity(i);
                }
            }
        });
    }
    //初始化控件
    private void initView() {
        uDao=new UserInfoDao(getContext());
        recyclerView= (RecyclerView) rootView.findViewById(R.id.shequfragment_recycleview);
        btn= (ImageButton) rootView.findViewById(R.id.shequfragment_add_btn);
    }

}
