package com.ciaocollect.yogyatourism;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "loginDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_LOGIN = "loginTable";
    public static final String LOGIN_ID = "loginID";
    public static final String LOGIN_USERNAME = "loginUser";
    public static final String LOGIN_PASSWORD = "loginPass";

    public static final String SQL_TABLE_LOGIN = "CREATE TABLE " + TABLE_LOGIN
            + " ( "
            + LOGIN_ID + " INTEGER PRIMARY KEY, "
            + LOGIN_USERNAME + " TEXT,"
            + LOGIN_PASSWORD + " TEXT"
            + " ) ";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TABLE_LOGIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
    }

    public void addLogin(Login login){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LOGIN_USERNAME, login.username);
        values.put(LOGIN_PASSWORD, login.password);
        long todo_id = db.insert(TABLE_LOGIN, null, values);
    }

    public Login Authenticate (Login login){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LOGIN,
                                 new String[]{
                                         LOGIN_ID,
                                         LOGIN_USERNAME,
                                         LOGIN_PASSWORD},
                                 LOGIN_USERNAME + "=?",
                                 new String[]{login.username},
                                 null,
                                 null,
                                 null);
        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0){
            Login user = new Login(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            if(login.password.equalsIgnoreCase(user.password)){
                return user;
            }
        }
        return null;
    }

    public boolean isUsernameExists(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LOGIN, new String[]{
                LOGIN_ID,
                LOGIN_USERNAME,
                LOGIN_PASSWORD
        }, LOGIN_USERNAME + "=?",
                                 new String[]{username},
                                 null,
                                 null,
                                 null);

        if(cursor != null && cursor.moveToFirst() && cursor.getCount() > 0){
            return true;
        }
        return false;
    }
}
