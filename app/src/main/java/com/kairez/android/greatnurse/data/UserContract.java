package com.kairez.android.greatnurse.data;


import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class UserContract
{
    public static final String CONTENT_AUTHORITY = "com.kairez.android.greatnurse";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_USER = "user";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_USER;


    public static final class UserEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String TABLE_NAME = "user";

        //email address of user is located in this column
        public static final String COLUMN_EMAIL = "email";

        //user's name
        public static final String COLUMN_NAME = "name";

        //api authorization key of the user is stored here
        public static final String COLUMN_API_KEY = "api_key";

        //expiry time of api key -- not implemented in v1
        public static final String COLUMN_API_EXPIRY = "api_expiry";


        public static Uri buildLocationUri(long id)
        {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
