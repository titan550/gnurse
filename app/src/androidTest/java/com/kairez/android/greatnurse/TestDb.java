package com.kairez.android.greatnurse;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.kairez.android.greatnurse.data.UserContract.UserEntry;
import com.kairez.android.greatnurse.data.UserDbHelper;

import java.util.Map;
import java.util.Set;

public class TestDb extends AndroidTestCase
{
    static public String TEST_EMAIL = "test@kairez.com";
    static public String TEST_NAME = "Don Joe";
    static public String TEST_API_KEY = "d184cee502d61d277f2f254936122f91";
    static public String TEST_API_EXPIRY = "";

    public void testCreateDb() throws Throwable
    {
        mContext.deleteDatabase(UserDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new UserDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertReadDb()
    {
        UserDbHelper dbHelper = new UserDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testUserValues = createTestUserValues();

        Long userRowId = db.insert(UserEntry.TABLE_NAME, null, testUserValues);
        assertTrue(userRowId != -1);

        Cursor userCursor = db.query(UserEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        validateCursorValues(userCursor, testUserValues);
        userCursor.close();
    }

    private ContentValues createTestUserValues()
    {
        ContentValues testValues = new ContentValues();

        testValues.put(UserEntry.COLUMN_EMAIL, TEST_EMAIL);
        testValues.put(UserEntry.COLUMN_NAME, TEST_NAME);
        testValues.put(UserEntry.COLUMN_API_KEY, TEST_API_KEY);
        testValues.put(UserEntry.COLUMN_API_EXPIRY, TEST_API_EXPIRY);

        return testValues;
    }

    private void validateCursorValues(Cursor valueCursor, ContentValues expectedValues)
    {
        assertTrue(valueCursor.moveToFirst()); //lock on the first row
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet)
        {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse(idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals(expectedValue, valueCursor.getString(idx));
        }
    }
}
