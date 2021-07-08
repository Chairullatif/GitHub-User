package com.khoirullatif.consumerapp.db;

import android.net.Uri;
import android.provider.BaseColumns;

public final class DatabaseContract {

    public static final String TABLE_NAME = "favorite_user";

    public static final String AUTHORITY = "com.khoirullatif.githubuser";
    private static final String SCHEME = "content";

    public static final class UserColumns implements BaseColumns {

        public static String COLUMN_NAME_USERNAME = "username";
        public static String COLUMN_NAME_REALNAME = "realname";
        public static String COLUMN_NAME_AVATAR_URL = "avatar_url";
        public static String COLUMN_NAME_COMPANY = "company";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();

    }
}
