package com.example.travelport.sql;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 创建数据库
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.close();
    }
}
