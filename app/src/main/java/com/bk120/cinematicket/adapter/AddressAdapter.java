package com.bk120.cinematicket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.utils.PinYinUtils;

import java.util.List;

/**
 * Created by bk120 on 2017/3/4.
 *
*/

public class AddressAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<String> mLists;
    private AddressClickListener addressClickListener;
    public AddressAdapter(Context c, List<String> l){
        this.mContext=c;
        this.mLists=l;
    }
    //分类返回ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1=View.inflate(mContext, R.layout.addressitem,null);
        AddressHolder1 holder1=new AddressHolder1(view1);
        return holder1;
    }
    //设置监听
    public void setOnAddressItemClickListener(AddressClickListener a){
        this.addressClickListener=a;
    }
    //绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        AddressHolder1 addressHolder1= (AddressHolder1) holder;
        addressHolder1.addressTv.setText(mLists.get(position));
        String now=getShouZhiMu(position);
        addressHolder1.titleTv.setText(now.toUpperCase());
        addressHolder1.addressLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }
    //获取当前Item的首字母
    public String getShouZhiMu(int position){
        char firstHanZhi = mLists.get(position).charAt(0);
        char[] charPinYinChar = PinYinUtils.getCharPinYinChar(firstHanZhi);
        return charPinYinChar[0]+"";
    }
    //地址的ViewHOlde
    class AddressHolder1 extends RecyclerView.ViewHolder{
        TextView addressTv,titleTv;
        LinearLayout addressLl;
        public AddressHolder1(View itemView) {
            super(itemView);
            addressTv= (TextView) itemView.findViewById(R.id.addressactivity_recycleview_address_item_tv);
            titleTv= (TextView) itemView.findViewById(R.id.addressactivity_recycleview_address_item_title_tv);
            addressLl= (LinearLayout) itemView.findViewById(R.id.addressactivity_recycleview_address_item_ll);
        }
    }
    public interface AddressClickListener{
        void onItemClick(int postion);
    }
}
