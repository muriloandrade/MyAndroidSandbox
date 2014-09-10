package com.example.murilo.myandroidsandbox.storage;

import android.provider.BaseColumns;

/**
 * Created by Murilo on 02/09/2014.
 */
public final class DbContract {

    // empty constructor to avoid accidental instantiation
    public DbContract() {
    }

    public static class UsersTable implements BaseColumns {

        public static final String TABLE_NAME = "usersTable";

        public static final String NAME = "name";
        public static final String PASSWORD = "password";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "( " +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        NAME + " TEXT, " +
                        PASSWORD + " TEXT " + ")";

        public static final String DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
