package com.bk120.cinematicket.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bk120.cinematicket.MainActivity;
import com.bk120.cinematicket.R;
import com.bk120.cinematicket.constants.MainConstant;

import android.os.Handler;

/**
 * 导航页
 */
public class FlashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        //
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(FlashActivity.this, MainActivity.class);
                startActivity(i);
                FlashActivity.this.finish();
            }
        }, MainConstant.FLASH_TIME);
    }
}
