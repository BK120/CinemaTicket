package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.cinematicket.R;
import com.bk120.cinematicket.constants.MainConstant;
import com.bk120.cinematicket.gesture.GestureLockViewGroup;

//手势密码界面
public class GestureLockActivity extends Activity {
    private GestureLockViewGroup gv;
    private TextView show_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_lock);
        show_tv= (TextView) this.findViewById(R.id.gestureactivity_tv);
        gv= (GestureLockViewGroup) this.findViewById(R.id.gestureactivity_gv);
        gv.setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {
            @Override
            public void onBlockSelected(int cId) {
            }
            @Override
            public void onGestureEvent(boolean matched) {
                if (matched){
                    show_tv.setText("密码正确");
                    Intent i=new Intent(GestureLockActivity.this,BalanceActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.acrivity3_push_in,0);
                    GestureLockActivity.this.finish();
                }else {
                    show_tv.setText("密码错误");
                }
            }
            @Override
            public void onUnmatchedExceedBoundary() {
                Toast.makeText(getApplicationContext(),"超过测试次数！",Toast.LENGTH_LONG).show();
                gv.setUnMatchExceedBoundary(3);
                GestureLockActivity.this.finish();
            }
        });
    }
    //退出
    public void back(View view){
        this.finish();
    }
}
