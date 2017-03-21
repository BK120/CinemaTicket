package com.bk120.cinematicket.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.bean.Card;
import com.bk120.cinematicket.bean.StringSign;
import com.bk120.cinematicket.constants.MainConstant;
import com.bk120.cinematicket.db.CardInfoDao;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */

public class CardAdapter extends RecyclerView.Adapter{
    private List<Card> list;
    private Context context;
    private CardInfoDao cDao;
    public CardAdapter(List<Card> l,Context c){
        this.list=l;
        this.context=c;
        cDao=new CardInfoDao(c);
    }
    //创建ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.card_recycleview_item,null);
        return new MyViewHolder(view);
    }
    //绑定资源
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            Card card=list.get(position);
            ((MyViewHolder) holder).type_iv.setImageResource(MainConstant.CARDICONS[card.getType()]);
            ((MyViewHolder) holder).type_tv.setText(MainConstant.CARD_TYPE[card.getType()]);
            ((MyViewHolder) holder).cardName_tv.setText(card.getCardName());
            ((MyViewHolder) holder).balance_tv.setText(card.getBalance()+"元");
            //设置背景颜色
            ((MyViewHolder) holder).item_rl.setBackgroundResource(MainConstant.CARD_BG[card.getBgColor()]);
            holder.itemView.setOnClickListener(new MyRecycleViewItemClickListenetr(position));
        }
    }
    //创建ViewHolder缓冲器
    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView type_tv,cardName_tv,balance_tv;
        private ImageView type_iv;
        private RelativeLayout item_rl;
        public MyViewHolder(View itemView) {
            super(itemView);
            type_tv= (TextView) itemView.findViewById(R.id.card_recycleview_item_type_tv);
            cardName_tv= (TextView) itemView.findViewById(R.id.card_recycleview_item_cardname_tv);
            balance_tv= (TextView) itemView.findViewById(R.id.card_recycleview_item_balance_tv);
            type_iv= (ImageView) itemView.findViewById(R.id.card_recycleview_item_type_iv);
            item_rl= (RelativeLayout) itemView.findViewById(R.id.card_recycleview_item_rl);
        }
    }
    //item点击监听
    class MyRecycleViewItemClickListenetr implements View.OnClickListener{
        private int position;
        public MyRecycleViewItemClickListenetr(int p){
            this.position=p;
        }
        @Override
        public void onClick(View v) {
            //弹出一个PopuWindow，用于修改金额、删除账户(前六个账户不能删除)
            showPopupWindow(v,position);
        }
    }
    //弹出一个PopupWindow
    private void showPopupWindow(View view, final int position) {
        View childView=View.inflate(context,R.layout.card_item_updatecardinfo_pop,null);
        Button addMoneyBtn= (Button) childView.findViewById(R.id.card_recycleview_item_updatecard_popwin_topup);
        Button deleteCardBtn= (Button) childView.findViewById(R.id.card_recycleview_item_updatecard_popwin_deletercard);
        if (position<6){
            deleteCardBtn.setVisibility(View.GONE);
        }else {
            deleteCardBtn.setVisibility(View.VISIBLE);
        }
        final PopupWindow window=new PopupWindow(childView,
                RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT,true);
        //设置点击popup以外可以关闭,必须要设置背景颜色
        window.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.card_update_item_shape));
        //弹出动画
        window.setAnimationStyle(R.style.popup_window_anim);
        //显示位置
        window.showAsDropDown(view,view.getWidth()-150,-(2*view.getHeight()));
        //充值账户
        addMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMoney(position);
                window.dismiss();
            }
        });
        //删除当前账户
        deleteCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cDao.delete(list.get(position));
                Toast.makeText(context,"删除成功!",Toast.LENGTH_SHORT).show();
                //发出信号
                EventBus.getDefault().post(new StringSign("AddCard Success"));
                EventBus.getDefault().post(new StringSign("UpdateCardList"));
                list.remove(position);
                CardAdapter.this.notifyItemRangeChanged(position, CardAdapter.this.getItemCount());
                window.dismiss();
            }
        });
    }
    //充值账户，弹出一个Dialog进行充值
    private void addMoney(final int position) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View childView=View.inflate(context,R.layout.card_item_addmoney_dialog,null);
        builder.setView(childView);
        final EditText balance_et= (EditText) childView.findViewById(R.id.card_addmoney_et);
        Button cancle_btn= (Button) childView.findViewById(R.id.addmoneydialog_cancle_btn);
        Button confirm_btn= (Button) childView.findViewById(R.id.addmoneydialog_confirm_btn);
        final AlertDialog dialog = builder.create();
        dialog.show();
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //充值
                String m=balance_et.getText().toString().trim();
                if (TextUtils.isEmpty(m)){
                    Toast.makeText(context,"充值不能为空,可以为0也没意义！",Toast.LENGTH_SHORT).show();
                    return;
                }
                double b=Double.valueOf(m);
                if (b>MainConstant.BALANCEMAX){
                    Toast.makeText(context,"一次最多充值"+MainConstant.BALANCEMAX,Toast.LENGTH_SHORT).show();
                    return;
                }
                Card card = list.get(position);
                BigDecimal op=new BigDecimal(Double.toString(b));
                BigDecimal re=new BigDecimal(Double.toString(card.getBalance()));
                card.setBalance(op.add(re).doubleValue());
                //更新数据
                cDao.update(card);
                dialog.dismiss();
                //发出信号
                EventBus.getDefault().post(new StringSign("AddCard Success"));
            }
        });
    }

}
