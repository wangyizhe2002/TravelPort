package com.example.travelport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils; // Import TextUtils
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.travelport.utill.DBUtil;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //加载数据库
        SQLiteOpenHelper helper= DBUtil.getmInstance(this);
        SQLiteDatabase readableDatabase = helper.getReadableDatabase();

        etUsername = findViewById(R.id.et_number);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        checkBox = findViewById(R.id.checkBox4);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入的用户名和密码
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // 检查是否勾选了同意隐私政策
                if (!checkBox.isChecked()) {
                    showToast("请确认已阅读并同意《隐私政策》");
                    return;
                }

                // 检查用户名和密码是否为空
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    showToast("请输入需要登录的账号和密码");
                    return;
                }

                // 校验用户名和密码
                if (validateUser(username, password)) {
                    showToast("登录成功");
                    Intent intent = new Intent(MainActivity.this, IndexActivity.class);
                    startActivity(intent);
                    finish(); // 关闭当前登录界面，避免用户按返回键返回到登录界面
                } else {
                    showToast("用户名或密码错误，请重新输入或点击注册按钮");
                }
            }
        });

        TextView tvRegister = findViewById(R.id.textView7);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击注册账号后跳转到注册界面
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateUser(String username, String password) {
        SQLiteDatabase db = DBUtil.getmInstance(MainActivity.this).getReadableDatabase();
        String query = "SELECT * FROM User WHERE Username=? AND Password=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
