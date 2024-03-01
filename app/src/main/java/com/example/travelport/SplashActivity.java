package com.example.travelport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 使用 Handler 延迟启动登录界面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startLoginActivity();
            }
        }, SPLASH_DELAY);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, MainActivity.class); // 请替换为你的登录界面的类名
        startActivity(intent);
        finish(); // 关闭当前启动界面，避免用户返回到启动界面
    }
}