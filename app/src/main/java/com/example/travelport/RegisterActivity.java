package com.example.travelport;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelport.sql.DatabaseHelper;
import com.example.travelport.utill.DBUtil;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnRegister, btnBackToLogin;
    private TextView tvRegisterSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.et_register_username);
        etPassword = findViewById(R.id.et_register_password);
        btnRegister = findViewById(R.id.btn_register);
        btnBackToLogin = findViewById(R.id.btn_back_to_login);
        tvRegisterSuccess = findViewById(R.id.tv_register_success);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入的用户名和密码
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // 输入验证
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    showToast("请输入用户名和密码");
                } else {
                    if (registerUser(username, password)) {
                        showRegistrationSuccess();
                    } else {
                        showToast("此用户以存在，请重新输入");
                    }
                }
            }
        });

        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 返回登录界面
                finish();
            }
        });
    }

    private boolean registerUser(String username, String password) {
        // 在实际应用中，这里应该获取用户输入的其他信息，例如邮箱等
        String email = "";

        // 获取数据库实例
        SQLiteDatabase db = DBUtil.getmInstance(RegisterActivity.this).getWritableDatabase();

        // 将用户信息插入数据库
        ContentValues values = new ContentValues();
        values.put("Username", username);
        values.put("Password", password);
        values.put("Email", email);

        // 插入用户数据
        long result = db.insert("User", null, values);


        // 根据插入结果显示相应的消息
        if (result != -1) {
            showRegistrationSuccess();
            // 延迟两秒后返回登录界面
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();  // 关闭当前注册界面
                }
            }, 2000);
            return true;  // 注册成功
        } else {
            // 判断是否是因为用户名已存在导致插入失败
            if (isUsernameExists(username)) {
                showToast("该用户名已存在");
            } else {
                showToast("注册失败，请重试");
                hideRegistrationSuccess();  // 隐藏注册成功提示
            }
            return false;  // 注册失败
        }
    }

    // 隐藏注册成功提示
    private void hideRegistrationSuccess() {
        tvRegisterSuccess.setVisibility(View.INVISIBLE);
    }

    // 判断用户名是否已存在
    private boolean isUsernameExists(String username) {
        SQLiteDatabase db = DBUtil.getmInstance(RegisterActivity.this).getReadableDatabase();
        String query = "SELECT * FROM User WHERE Username=?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // 显示 Toast 消息
    private void showToast(String message) {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    private void showRegistrationSuccess() {
        // 注册成功后显示提示信息
        tvRegisterSuccess.setVisibility(View.VISIBLE);
    }
}
