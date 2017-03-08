package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.bean.AddressBean;
import com.bk120.cinematicket.constants.DianYingConstant;
import com.bk120.cinematicket.utils.PinYinUtils;
import com.bk120.cinematicket.adapter.AddressAdapter;
import com.bk120.cinematicket.views.RecycleViewDividerL;

import org.greenrobot.eventbus.EventBus;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//选择地址页面,并通过eventBus传递事件
public class AddressActivity extends Activity {
    private Button alpButton ,searchBtn;
    //定义字母表的排序规则
    private String alphabet="#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private RelativeLayout sectionToastLayout;
    private TextView sectionToastText,searchEt;
    private RecyclerView mRecyclerView;
    //汉字拼音比较器
    public static final Comparator CHINA_COMPARE= Collator.getInstance(java.util.Locale.CHINA);
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        alpButton= (Button) this.findViewById(R.id.alphabetButton);
        sectionToastLayout= (RelativeLayout) this.findViewById(R.id.section_toast_layout);
        sectionToastText= (TextView) this.findViewById(R.id.section_toast_text);
        mRecyclerView= (RecyclerView) this.findViewById(R.id.addressactivity_recycleview);
        searchBtn= (Button) this.findViewById(R.id.addressactivity_search_btn);
        searchEt= (TextView) this.findViewById(R.id.addressactivity_search_et);
        //设置监听
        setAlphabetListener();
        initLists();
        initRecycleView();
        initSearchBtn();
    }
    //初始化搜索按钮
    private void initSearchBtn() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = searchEt.getText().toString().trim();
                if (TextUtils.isEmpty(address)){
                    Toast.makeText(AddressActivity.this,"输入城市不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                int position = getPositionFromListWithAddress(address);
                //让其多移动几个，是当前Item位于中上部分
                recycleviewScrollToFitedLocation(position);

            }
        });


    }

    //初始化List,并排序
    private void initLists() {
        list=new ArrayList<>();
        for(int i = 0; i< DianYingConstant.ADDRESS_ALL_CITY.length; i++){
            list.add(DianYingConstant.ADDRESS_ALL_CITY[i]);
        }
        Collections.sort(list,CHINA_COMPARE);
    }

    //初始化RecycleView
    private void initRecycleView() {
        LinearLayoutManager manager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new RecycleViewDividerL(this, LinearLayout.VERTICAL));
        AddressAdapter addressAdapter=new AddressAdapter(this,list);
        mRecyclerView.setAdapter(addressAdapter);
        addressAdapter.setOnAddressItemClickListener(new AddressAdapter.AddressClickListener() {
            @Override
            public void onItemClick(int postion) {
                //将地址传递出去，并关闭
                EventBus.getDefault().post(new AddressBean(list.get(postion)));
                AddressActivity.this.finish();
            }
        });
    }


    //滑动按钮设置监听,根据触摸位置结合字母表高度，计算当前触摸的字母，
    //按上时展示弹出式分组，松开即隐藏
    private void setAlphabetListener() {
        alpButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int alphabetHeight = alpButton.getHeight();
                float y = event.getY();
                int sectionPosition= (int) ((y/alphabetHeight)/(1f/27f));
                if(sectionPosition<0){
                    sectionPosition=0;
                }else if(sectionPosition>26){
                    sectionPosition=26;
                }
                //当前字母
                String sectionLetter=String.valueOf(alphabet.charAt(sectionPosition));
                int position = getPostionFromListWithLetter(sectionLetter.toLowerCase());
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        sectionToastLayout.setVisibility(View.VISIBLE);
                        sectionToastText.setText(sectionLetter);
                        recycleviewScrollToFitedLocation(position);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        sectionToastText.setText(sectionLetter);
                        recycleviewScrollToFitedLocation(position);
                        break;
                    default:
                        sectionToastLayout.setVisibility(View.GONE);
                }
                return true;
            }
        });
    }
    //滑动到中上的部分,方便查看
    public void recycleviewScrollToFitedLocation(int oldPosition){
        if(oldPosition<(list.size()-6)&&oldPosition>5){
            mRecyclerView.scrollToPosition(oldPosition+5);
            return;
        }
        mRecyclerView.scrollToPosition(oldPosition);
    }

    /**
     * 通过传入一个字母，获取list中的元素的汉字的首字母
     * @param letter 传入字母
     * @return 返回的位置position
     */
    public int getPostionFromListWithLetter(String letter){
        int position=0;
        for(int i=0;i<list.size();i++){
            String s = list.get(i);
            String shouZhiMu = getShouZhiMu(s);
            if (letter.equals(shouZhiMu)){
                return i;
            }
        }
        return position;
    }
    //获取当前Item的首字母
    public String getShouZhiMu(String s){
        char firstHanZhi = s.charAt(0);
        char[] charPinYinChar = PinYinUtils.getCharPinYinChar(firstHanZhi);
        return charPinYinChar[0]+"";
    }
    //传入地址，获取集合中的位置
    public int getPositionFromListWithAddress(String address){
        int position=0;
        for(int i=0;i<list.size();i++){
            String s = list.get(i);
            if (address.equals(s)){
                return i;
            }
        }
        return position;
    }
    //结束当前界面
    public void back(View view){
        this.finish();
    }
}
