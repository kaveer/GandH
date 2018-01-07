package com.kavsoftware.kaveer.gandh.DB.DbHandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kavsoftware.kaveer.gandh.DB.Table.Table;

/**
 * Created by kaveer on 1/7/2018.
 */

public class DBHandler extends SQLiteOpenHelper {
    public static final String databaseName = "GandHDb";
    public static final int databaseVersion = 1;

    public DBHandler(Context context){
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUserTable;

        createUserTable =
                "CREATE TABLE " + Table.User.tableName +
                        " ("
                        + Table.User.Email + "  TEXT,"
                        + Table.User.Password + "  TEXT"
                        + " )";

        sqLiteDatabase.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table.User.tableName);
    }

    // get
    //delete
}
