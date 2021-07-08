package com.khoirullatif.githubuser.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbfavoriteuser";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITE_USER = String.format("CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_NAME,
            DatabaseContract.UserColumns._ID,
            DatabaseContract.UserColumns.COLUMN_NAME_USERNAME,
            DatabaseContract.UserColumns.COLUMN_NAME_REALNAME,
            DatabaseContract.UserColumns.COLUMN_NAME_AVATAR_URL,
            DatabaseContract.UserColumns.COLUMN_NAME_COMPANY,
            DatabaseContract.UserColumns.COLUMN_NAME_LOCATION,
            DatabaseContract.UserColumns.COLUMN_NAME_REPOSITORY,
            DatabaseContract.UserColumns.COLUMN_NAME_FOLLOWING,
            DatabaseContract.UserColumns.COLUMN_NAME_FOLLOWER
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(db);
    }
}
