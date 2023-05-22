package com.example.databaseapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.Cursor;

public class DataManager {
    private SQLiteDatabase db;
    public static final String TABLE_ROW_ID = "_id";
    public static final String TABLE_ROW_NAME = "name";
    public static final String TABLE_ROW_LASTNAME = "lastname";
    public static final String DB_NAME = "address_book_db";
    public static final int DB_VERSRION = 1;
    private static final String TABLE_N_AND_L = "names_and_lastnames";



    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper{
        public CustomSQLiteOpenHelper(Context context){
            super(context, DB_NAME, null, DB_VERSRION);
        }

        public void onCreate(SQLiteDatabase db){
            String newTableQueryString = "create table "
                    + TABLE_N_AND_L + " ("
                    + TABLE_ROW_ID
                    + " integer primary key autoincrement not null,"
                    + TABLE_ROW_NAME
                    + " text not null,"
                    + TABLE_ROW_LASTNAME
                    + " text not null);";

            db.execSQL(newTableQueryString);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        }
    }


    public DataManager(Context context){
        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert (String name, String lastName){
        String query = "INSERT INTO "
                + TABLE_N_AND_L
                + " ("
                + TABLE_ROW_NAME
                + ", "
                + TABLE_ROW_LASTNAME
                + ") "
                + "VALUES ("
                + "'"
                + name
                + "'"
                + ", "
                + "'"
                + lastName
                + "');";

        Log.i("insert() = ", query);
        db.execSQL(query);

    }

    public void delete(String name){
        String query = "DELETE FROM "
                + TABLE_N_AND_L
                + " WHEERE "
                + TABLE_ROW_NAME
                + " = '"
                + name
                + "';";

        Log.i("delete() = ", query);
    }

    public Cursor selectAll(){
        Cursor c = db.rawQuery("SELECT * from " + TABLE_N_AND_L, null);
        return c;
    }

    public Cursor searchName(String name){
        String query = "SELECT "
                + TABLE_ROW_ID
                + ", "
                + TABLE_ROW_NAME
                + ", "
                + TABLE_ROW_LASTNAME
                + " FROM"
                + TABLE_N_AND_L
                + " WHERE "
                + TABLE_ROW_NAME
                + " = '"
                + name
                + "';";

        Log.i("searchName() =", query);

        Cursor c = db.rawQuery(query, null);
        return c;
    }
}
