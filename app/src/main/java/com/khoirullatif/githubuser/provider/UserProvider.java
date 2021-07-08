package com.khoirullatif.githubuser.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.khoirullatif.githubuser.db.UserHelper;

import java.util.Objects;

import static com.khoirullatif.githubuser.db.DatabaseContract.*;
import static com.khoirullatif.githubuser.db.DatabaseContract.UserColumns.CONTENT_URI;

public class UserProvider extends ContentProvider {

    private static final int USER = 1;
    private static final int USER_NAME = 2;
    private UserHelper userHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER);
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/*", USER_NAME);
    }

    @Override
    public boolean onCreate() {
        userHelper = UserHelper.getInstance(getContext());
        userHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        Log.d("UserPov onQuery", Objects.requireNonNull(uri.getLastPathSegment()));
        switch (sUriMatcher.match(uri)) {
            case USER:
                cursor = userHelper.queryAll();
                break;
            case USER_NAME:
                cursor = userHelper.queryByUsername(uri.getLastPathSegment());
                Log.d("UserPov onQuery", "masuk ke USER_NAME case");
                break;
            default:
                cursor = null;
                Log.d("UserPov onQuery", "masuk ke default");
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long added;
        if (sUriMatcher.match(uri) == USER) {
            added = userHelper.insert(values);
        } else {
            added = 0;
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI, null);

        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case USER_NAME:
                deleted = userHelper.deletedByUsername(uri.getLastPathSegment());
                Log.d("UserPov onDelete", String.valueOf(deleted));
                break;
            default:
                deleted = 0;
                Log.d("UserPov onDelete", String.valueOf(deleted));
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return deleted;
    }
}

