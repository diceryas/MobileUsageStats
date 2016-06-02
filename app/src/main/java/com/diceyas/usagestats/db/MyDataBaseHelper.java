package com.diceyas.usagestats.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by youansheng on 2016/5/28.
 */
public class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context mContext;
    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String aa = "create table useTime (year integer, month integer, day integer, packageName text, time integer)";
        db.execSQL(aa);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
