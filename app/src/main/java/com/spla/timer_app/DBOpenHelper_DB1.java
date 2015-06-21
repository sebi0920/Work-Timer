package com.spla.timer_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Wastl on 22.05.2015.
 */
public class DBOpenHelper_DB1 extends SQLiteOpenHelper {

    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_K_NAME = "k_name";
    public static final String COLUMN_K_NUMBER = "k_number";
    public static final String COLUMN_T_NUMBER = "t_number";
    public static final String COLUMN_ADDRESS = "address";

    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_CONTACTS + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_K_NAME + " text not null, "
            + COLUMN_K_NUMBER + " text not null, "
            + COLUMN_T_NUMBER + " text not null, "
            + COLUMN_ADDRESS + " text not null);";


    public DBOpenHelper_DB1(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBOpenHelper_DB1.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

}
