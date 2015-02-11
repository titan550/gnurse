package com.kairez.android.greatnurse.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kairez.android.greatnurse.data.UserContract.UserEntry;

public class UserDbHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    public UserDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create database with the described features in this method
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME + " ( " +
                UserEntry._ID + " INTEGER PRIMARY KEY, " +
                UserEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                UserEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                UserEntry.COLUMN_API_KEY + " TEXT NOT NULL, " +
                "UNIQUE (" + UserEntry.COLUMN_EMAIL + ") ON CONFLICT REPLACE" +
                " );";

        db.execSQL(SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME); //drop the old table in case db version is upgraded to new version
        onCreate(db);
    }
}
