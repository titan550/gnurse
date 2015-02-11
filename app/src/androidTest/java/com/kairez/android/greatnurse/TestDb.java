package com.kairez.android.greatnurse;


import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.kairez.android.greatnurse.data.UserDbHelper;

public class TestDb extends AndroidTestCase
{
    public void testCreateDb() throws Throwable
    {
        mContext.deleteDatabase(UserDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new UserDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }
}
