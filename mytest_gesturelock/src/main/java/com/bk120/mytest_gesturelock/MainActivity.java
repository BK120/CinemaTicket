package com.bk120.mytest_gesturelock;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bk120.mytest_gesturelock.gesture.GestureLockViewGroup;


public class MainActivity extends Activity {
    private GestureLockViewGroup glvc;
    private TextView tv;
    private int[] answer={1,4,7,8,9};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        glvc= (GestureLockViewGroup) this.findViewById(R.id.gs);
        tv= (TextView) this.findViewById(R.id.tv);
        glvc.setAnswer(answer);
        setListenner();
    }

    private void setListenner() {
        glvc.setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {
            @Override
            public void onBlockSelected(int cId) {
                Log.i("当前的值:",cId+"");
            }

            @Override
            public void onGestureEvent(boolean matched) {
                if (matched){
                    tv.setText("密码正确");
                }else {
                    tv.setText("密码错误");
                }
            }

            @Override
            public void onUnmatchedExceedBoundary() {
                glvc.setVisibility(View.GONE);
                glvc.setUnMatchExceedBoundary(3);
            }
        });
    }
}
