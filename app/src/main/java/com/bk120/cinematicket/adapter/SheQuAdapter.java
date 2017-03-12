package com.bk120.cinematicket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.bean.Comment;
import com.bk120.cinematicket.constants.MainConstant;
import com.bk120.cinematicket.db.CommentDao;

import java.util.List;

/**
 * Created by bk120 on 2017/3/12.
 */

public class SheQuAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<Comment> list;
    public SheQuAdapter(Context c,List<Comment> l){
        this.context=c;
        this.list=l;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.shequ_recycle_item,null);
        return new SheQuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SheQuViewHolder){
            final Comment comment = list.get(position);
            ((SheQuViewHolder) holder).icon.setImageResource(MainConstant.COMMENT_ICON[comment.getIcon()]);
            ((SheQuViewHolder) holder).name.setText(comment.getName());
            ((SheQuViewHolder) holder).time.setText(comment.getTime());
            String describe = comment.getDescribe();
            if (describe.length()>60){
                String s=describe.substring(0,60)+"...";
                ((SheQuViewHolder) holder).describe.setText(s);
            }else {
                ((SheQuViewHolder) holder).describe.setText(describe);
            }
            ((SheQuViewHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除一条数据
                    CommentDao dao=new CommentDao(context);
                    dao.delete(comment);
                    Toast.makeText(context,"删除成功！",Toast.LENGTH_SHORT).show();
                    //刷新Adapter
                    SheQuAdapter.this.notifyItemRemoved(position);
                    list.remove(position);
                    SheQuAdapter.this.notifyItemRangeChanged(position, SheQuAdapter.this.getItemCount());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SheQuViewHolder extends RecyclerView.ViewHolder{
        private ImageView icon, delete;
        private TextView name,time,describe;
        public SheQuViewHolder(View itemView) {
            super(itemView);
            icon= (ImageView) itemView.findViewById(R.id.shequ_recycleview_item_icon);
            delete= (ImageView) itemView.findViewById(R.id.shequ_recycleview_item_delete);
            name= (TextView) itemView.findViewById(R.id.shequ_recycleview_item_name);
            time= (TextView) itemView.findViewById(R.id.shequ_recycleview_item_time);
            describe= (TextView) itemView.findViewById(R.id.shequ_recycleview_item_describe);
        }
    }
}
