package com.example.travelport.utill;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 链接数据库
 */

public class DBUtil extends SQLiteOpenHelper {


    private static  SQLiteOpenHelper mInstance;
    public static synchronized SQLiteOpenHelper getmInstance(Context context){
        if(mInstance==null){
            mInstance=new DBUtil(context,"travel.db",null,1);
        }
        return mInstance;
    }


    public DBUtil(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBUtil(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBUtil(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 创建用户表
        String createUserTableQuery = "CREATE TABLE User ("
                + "UserID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Username TEXT UNIQUE, "
                + "Password TEXT, "
                + "Email TEXT)";
        sqLiteDatabase.execSQL(createUserTableQuery);


        // 创建目的地表
        String createDestinationTableQuery = "CREATE TABLE Destination ("
                + "DestinationID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "DestinationName TEXT, "
                + "Location TEXT, "
                + "Description TEXT, "
                + "ImageURL TEXT)";
        sqLiteDatabase.execSQL(createDestinationTableQuery);

        // 创建行程表
        String createItineraryTableQuery = "CREATE TABLE Itinerary ("
                + "ItineraryID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "UserID INTEGER, "
                + "DestinationID INTEGER, "
                + "StartDate DATE, "
                + "EndDate DATE, "
                + "FOREIGN KEY (UserID) REFERENCES User(UserID), "
                + "FOREIGN KEY (DestinationID) REFERENCES Destination(DestinationID))";
        sqLiteDatabase.execSQL(createItineraryTableQuery);

        // 创建活动表
        String createActivityTableQuery = "CREATE TABLE Activity ("
                + "ActivityID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "DestinationID INTEGER, "
                + "ActivityName TEXT, "
                + "ActivityTime DATETIME, "
                + "Cost DECIMAL(10, 2), "
                + "FOREIGN KEY (DestinationID) REFERENCES Destination(DestinationID))";
        sqLiteDatabase.execSQL(createActivityTableQuery);

        // 创建费用表
        String createExpenseTableQuery = "CREATE TABLE Expense ("
                + "ExpenseID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "ItineraryID INTEGER, "
                + "Category TEXT, "
                + "Amount DECIMAL(10, 2), "
                + "Date DATE, "
                + "Note TEXT, "
                + "FOREIGN KEY (ItineraryID) REFERENCES Itinerary(ItineraryID))";
        sqLiteDatabase.execSQL(createExpenseTableQuery);
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

