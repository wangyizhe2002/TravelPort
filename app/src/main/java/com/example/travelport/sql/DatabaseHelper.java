package com.example.travelport.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "travel_app.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建用户表
        String createUserTableQuery = "CREATE TABLE User ("
                + "UserID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Username TEXT UNIQUE, "
                + "Password TEXT, "
                + "Email TEXT)";
        db.execSQL(createUserTableQuery);
        String insertUserQuery = "INSERT INTO User (Username, Password, Email) VALUES ('example_user', 'password123', 'user@example.com')";
        db.execSQL(insertUserQuery);

        // 创建目的地表
        String createDestinationTableQuery = "CREATE TABLE Destination ("
                + "DestinationID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "DestinationName TEXT, "
                + "Location TEXT, "
                + "Description TEXT, "
                + "ImageURL TEXT)";
        db.execSQL(createDestinationTableQuery);

        // 创建行程表
        String createItineraryTableQuery = "CREATE TABLE Itinerary ("
                + "ItineraryID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "UserID INTEGER, "
                + "DestinationID INTEGER, "
                + "StartDate DATE, "
                + "EndDate DATE, "
                + "FOREIGN KEY (UserID) REFERENCES User(UserID), "
                + "FOREIGN KEY (DestinationID) REFERENCES Destination(DestinationID))";
        db.execSQL(createItineraryTableQuery);

        // 创建活动表
        String createActivityTableQuery = "CREATE TABLE Activity ("
                + "ActivityID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "DestinationID INTEGER, "
                + "ActivityName TEXT, "
                + "ActivityTime DATETIME, "
                + "Cost DECIMAL(10, 2), "
                + "FOREIGN KEY (DestinationID) REFERENCES Destination(DestinationID))";
        db.execSQL(createActivityTableQuery);

        // 创建费用表
        String createExpenseTableQuery = "CREATE TABLE Expense ("
                + "ExpenseID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "ItineraryID INTEGER, "
                + "Category TEXT, "
                + "Amount DECIMAL(10, 2), "
                + "Date DATE, "
                + "Note TEXT, "
                + "FOREIGN KEY (ItineraryID) REFERENCES Itinerary(ItineraryID))";
        db.execSQL(createExpenseTableQuery);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 在数据库版本更新时执行的操作
        // 这里可以添加对表结构的修改或数据迁移逻辑
    }
}