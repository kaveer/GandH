package com.kavsoftware.kaveer.gandh.DB.Table;

import android.provider.BaseColumns;

/**
 * Created by kaveer on 1/7/2018.
 */

public class Table {

    public  static abstract class User implements BaseColumns {
        public static final String tableName = "UserTable";

        public static String Email = "UserEmail";
        public static String Password = "UserPassword";
    }

}
