package com.kavsoftware.kaveer.gandh.DB.DbHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kavsoftware.kaveer.gandh.DB.Table.Table;
import com.kavsoftware.kaveer.gandh.Model.AccountViewModel;

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
                        + Table.User.Password + "  TEXT,"
                        + Table.User.LoginType + "  INT"
                        + " )";

        sqLiteDatabase.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table.User.tableName);
    }

    public AccountViewModel GetUserDetails() {
        AccountViewModel result = null;

        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        query  = "SELECT "
                + Table.User.Email + ", "
                + Table.User.Password + ", "
                + Table.User.LoginType + " "
                + " FROM " + Table.User.tableName;

        Cursor cursor = db.rawQuery(query , null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            result = new AccountViewModel();

            result.setEmail(cursor.getString(0));
            result.setPassword(cursor.getString(1));
          //  result.setLoginType(Integer.parseInt(cursor.getString(2)));

        }
        db.close();

        return result;
    }

    public void SaveUser(AccountViewModel item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Table.User.Email, item.getEmail());
        values.put(Table.User.Password, item.getPassword());
      //  values.put(Table.User.LoginType, item.getLoginType());

        db.insert(Table.User.tableName, null , values);
        db.close();
    }

    public void DeleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table.User.tableName, null, null);
        db.close();
    }


}
