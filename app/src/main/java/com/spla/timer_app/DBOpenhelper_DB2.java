package com.spla.timer_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Wastl on 31.05.2015.
 */
public class DBOpenhelper_DB2 extends SQLiteOpenHelper {
    public static final String TABLE_WORKED = "worked";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_STARTED = "startetd";
    public static final String COLUMN_FINISHED = "finished";
    public static final String COLUMN_CHRONO_BASE = "chrono_base";
    public static final String COLUMN_SERVICE = "service";
    public static final String COLUMN_K_NAME = "k_name";
    public static final String COLUMN_K_NUMBER = "k_number";
    public static final String COLUMN_W_DATE = "w_date";
    public static final String COLUMN_ID_DATE = "id_date";

    private static final String DATABASE_NAME = "worked.db";
    private static final int DATABASE_VERSION = 1;

    private static final String db_creation = "create table if not exists "
            + TABLE_WORKED + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_STARTED + " text not null, "
            + COLUMN_FINISHED + " text not null, "
            + COLUMN_CHRONO_BASE + " UNSIGNED BIG INT, "
            + COLUMN_SERVICE + " text not null, "
            + COLUMN_K_NAME + " text not null, "
            + COLUMN_K_NUMBER + " text not null,"
            + COLUMN_W_DATE + " text not null,"
            + COLUMN_ID_DATE + " text not null);";


    public DBOpenhelper_DB2(Context context) {
        super(context, TABLE_WORKED, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(db_creation);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBOpenHelper_DB1.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKED);
        onCreate(db);
    }
}
