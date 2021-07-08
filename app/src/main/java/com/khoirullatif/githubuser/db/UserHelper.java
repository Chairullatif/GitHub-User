package com.khoirullatif.githubuser.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.provider.BaseColumns._ID;
import static com.khoirullatif.githubuser.db.DatabaseContract.TABLE_NAME;
import static com.khoirullatif.githubuser.db.DatabaseContract.UserColumns.COLUMN_NAME_USERNAME;

public class UserHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static UserHelper INSTANCE;

    private static SQLiteDatabase database;

    private UserHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static UserHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen()) {
            database.close();
        }

        Log.d("UserHelper ", "on close");
    }

    public Cursor queryAll() {
        Log.d("UserHelper onQueryAll", "query all");
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    public Cursor queryByUsername(String username) {
        Log.d("UserHelper onQueByName", username);
        String[] selectionArgs = { username };
        String sortOrder = COLUMN_NAME_USERNAME + " DESC";
        return database.query(
                DATABASE_TABLE,
                null,
                COLUMN_NAME_USERNAME + " LIKE ?",
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deletedByUsername(String username) {
        return database.delete(DATABASE_TABLE, COLUMN_NAME_USERNAME + " = ?", new String[]{username});
    }
}
