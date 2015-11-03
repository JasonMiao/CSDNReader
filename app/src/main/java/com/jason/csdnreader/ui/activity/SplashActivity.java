package com.jason.csdnreader.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jason.csdnreader.R;

/**
 * 应用初始化页面
 */
public class SplashActivity extends Activity {
    private static final int SHOW_TIME_MIN = 3000;// 最小显示时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // 初始化 加载资源
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                long startTime = System.currentTimeMillis();
                //TODO 加载资源
                long loadingTime = System.currentTimeMillis() - startTime;
                if (loadingTime < SHOW_TIME_MIN) {
                    try {
                        Thread.sleep(SHOW_TIME_MIN - loadingTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                goToNext();
            }
        }.execute();
    }

    private void goToNext() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }

    /**
     * Splash界面点击返回不让退出
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
