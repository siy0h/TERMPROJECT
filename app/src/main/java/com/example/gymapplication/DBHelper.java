package com.example.gymapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";
    public static final int DATABASE_VERSION = 10;

    public DBHelper(Context context) {
        super(context, "Login.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase gymDB) {
        gymDB.execSQL("create Table instructor(username Text primary key, password TEXT)");
        gymDB.execSQL("create Table member(username Text primary key, password TEXT)");
        gymDB.execSQL("create Table admin(username Text primary key, password TEXT)");
        String adminAccount = "INSERT INTO admin(username, password) VALUES ('admin', 'admin123'); ";
        gymDB.execSQL(adminAccount);


    }

    @Override
    public void onUpgrade(SQLiteDatabase gymDB, int i, int i1) {
        gymDB.execSQL("drop Table if exists instructor");
        gymDB.execSQL("drop Table if exists member");
        gymDB.execSQL("drop Table if exists admin");
        onCreate(gymDB);

    }

    public boolean insertData(String username, String password, String table) {
        SQLiteDatabase gymDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);

        long result = gymDB.insert(table, null, contentValues);
        if (result == -1)
            return false;
        return true;

    }

    public Boolean checkUsername(String username, String table) {
        SQLiteDatabase gymDB = this.getWritableDatabase();
        Cursor cursor = null;
        if (table.equals("instructor"))
            cursor = gymDB.rawQuery("Select * from instructor where username = ?", new String[]{username});
        else if (table.equals("member"))
            cursor = gymDB.rawQuery("Select * from member where username = ?", new String[]{username});
        else if (table.equals("admin"))
            cursor = gymDB.rawQuery("Select * from admin where username = ?", new String[]{username});

        if (cursor.getCount() > 0)
            return true;
        return false;
    }

    public Boolean checkUsernamePassword(String username, String password, String table) {
        SQLiteDatabase gymDB = this.getWritableDatabase();
        Cursor cursor = null;
        if (table.equals("instructor"))
            cursor = gymDB.rawQuery("Select * from instructor where username = ? and password = ?", new String[]{username, password});
        else if (table.equals("member"))
            cursor = gymDB.rawQuery("Select * from member where username = ? and password = ?", new String[]{username, password});
        else if (table.equals("admin"))
            cursor = gymDB.rawQuery("Select * from admin where username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        return false;
    }
}