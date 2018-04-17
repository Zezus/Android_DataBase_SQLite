package com.example.database_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by СадвакасовР on 17.04.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String PEOPLE_TABLE = "people";
    public static final String ID_COLUMN = "_id";
    public static final String FULL_NAME_COLUMN = "full_name";
    public static final String AGE_COLUMN = "age";
    public static final String PHONE_NUMBER_COLUMN = "phone_number";
    public static final String EMAIL_COLUMN = "email";

    public static final String CREATE_DATABASE_COMMAND = "CREATE TABLE " + PEOPLE_TABLE +
            " (" + ID_COLUMN + " INTEGER PRIMARY KEY, " +
            FULL_NAME_COLUMN + " TEXT NOT NULL, " +
            AGE_COLUMN + " INTEGER NOT NULL, " +
            PHONE_NUMBER_COLUMN + " TEXT NOT NULL, " +
            EMAIL_COLUMN + " TEXT NOT NULL);";

    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, "ourName.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DATABASE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //if(DATABASE_VERSION==2) удалить старую бд, сделать новую
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
