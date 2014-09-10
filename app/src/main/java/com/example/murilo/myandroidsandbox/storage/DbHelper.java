package com.example.murilo.myandroidsandbox.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Murilo on 02/09/2014.
 */
public class DbHelper extends SQLiteOpenHelper {

    public final static String DB_NAME = "mDatabase";
    public final static int DB_VERSION = 2;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DbContract.UsersTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DbContract.UsersTable.DELETE_TABLE);
        onCreate(db);
    }
}
