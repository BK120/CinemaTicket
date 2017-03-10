package com.bk120.cinematicket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.bean.Card;
import com.bk120.cinematicket.bean.ViewSign;
import com.bk120.cinematicket.constants.MainConstant;
import com.bk120.cinematicket.utils.SharePreferencesUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by bk120 on 2017/3/10.
 * 充值账户余额的Adapter
 */

public class AddBalanceAdapter extends RecyclerView.Adapter{
    private List<Card> list;
    private Context context;
    public AddBalanceAdapter(List<Card> l, Context c){
        this.list=l;
        this.context=c;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.add_balance_recycleview_item,null);
        return new MyAddBalanceHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyAddBalanceHolder){
            Card card = list.get(position);
            ((MyAddBalanceHolder) holder).type_iv.setImageResource(MainConstant.CARDICONS[card.getType()]);
            ((MyAddBalanceHolder) holder).cardname_tv.setText(card.getCardName());
            ((MyAddBalanceHolder) holder).balance_tv.setText(card.getBalance()+"元");
            int payCard = SharePreferencesUtils.getInt(context, "PayCard", 0);
            if (position==payCard){
                ((MyAddBalanceHolder) holder).chicked_iv.setVisibility(View.VISIBLE);
            }else {
                ((MyAddBalanceHolder) holder).chicked_iv.setVisibility(View.GONE);
            }
            holder.itemView.setBackgroundColor(context.getResources().getColor(MainConstant.CARD_BG[card.getBgColor()]));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharePreferencesUtils.putInt(context,"PayCard",position);
                    EventBus.getDefault().post(new ViewSign(holder.itemView,position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyAddBalanceHolder extends RecyclerView.ViewHolder {
        private ImageView type_iv,chicked_iv;
        private TextView cardname_tv,balance_tv;
        public MyAddBalanceHolder(View itemView) {
            super(itemView);
            chicked_iv= (ImageView) itemView.findViewById(R.id.addbalanceactivty_chicked_iv);
            type_iv= (ImageView) itemView.findViewById(R.id.addbalancerecycleview_item_iv);
            cardname_tv= (TextView) itemView.findViewById(R.id.addbalancerecycleview_item_cardname_tv);
            balance_tv= (TextView) itemView.findViewById(R.id.addbalancerecycleview_item_money_tv);
        }
    }
}
