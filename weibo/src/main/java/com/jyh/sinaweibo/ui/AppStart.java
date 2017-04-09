package com.jyh.sinaweibo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jyh.sinaweibo.R;
import com.jyh.sinaweibo.app.AppManager;
import com.jyh.sinaweibo.util.AccessTokenKeeper;


/*
* 引导页
* */
public class AppStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 防止第三方跳转时出现双实例
        Activity aty = AppManager.getActivity(MainActivity.class);
        if (aty != null && !aty.isFinishing()) {
            finish();
        }

        setContentView(R.layout.activity_app_start);

        findViewById(R.id.app_start_view).postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectTo();
            }
        }, 800);


    }


    /**
     * 跳转到...
     */
    private void redirectTo() {


        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    //睡眠2秒
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //判断令牌是否失效,如果失效就启动ActivitySinaLogin，重新受权认证
                //否则直接进入主页
                if (AccessTokenKeeper.readAccessToken(AppStart.this)
                        .isSessionValid()) {
                    startActivity(new Intent(AppStart.this,
                            MainActivity.class));

                } else {
                    startActivity(new Intent(AppStart.this,
                            ActivitySinaLogin.class));
                }
                finish();
            }
        }).start();

    }
}
