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
    public static final String TABLE_ROW_PESEL = "pesel";
    public static final String TABLE_ROW_AGE = "age";
    public static final String TABLE_ROW_GENDER = "gender";
    public static final String DB_NAME = "address_book_db";
    public static final int DB_VERSION = 3;
    private static final String TABLE_N_AND_L = "names_and_lastnames";



    private static class CustomSQLiteOpenHelper extends SQLiteOpenHelper{
        public CustomSQLiteOpenHelper(Context context){
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db){
            String newTableQueryString = "CREATE TABLE "
                    + TABLE_N_AND_L + " ("
                    + TABLE_ROW_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + TABLE_ROW_NAME
                    + " TEXT NOT NULL, "
                    + TABLE_ROW_LASTNAME
                    + " TEXT NOT NULL, "
                    + TABLE_ROW_AGE
                    + " TEXT NOT NULL, "
                    + TABLE_ROW_GENDER
                    + " TEXT NOT NULL, "
                    + TABLE_ROW_PESEL
                    + " TEXT NOT NULL);";

            db.execSQL(newTableQueryString);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_N_AND_L);
            onCreate(db);
        }
    }

    public DataManager(Context context){
        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert (String name, String lastName, String age, String gender, String ssn){
        String query = "INSERT INTO "
                + TABLE_N_AND_L
                + " ("
                + TABLE_ROW_NAME
                + ", "
                + TABLE_ROW_LASTNAME
                + ", "
                + TABLE_ROW_AGE
                + ", "
                + TABLE_ROW_GENDER
                + ", "
                + TABLE_ROW_PESEL
                + ") "
                + "VALUES ("
                + "'"
                + name
                + "', "
                + "'"
                + lastName
                + "', "
                + "'"
                + age
                + "', "
                + "'"
                + gender
                + "', "
                + "'"
                + ssn
                + "');";

        Log.i("insert() = ", query);
        db.execSQL(query);
    }

    public void delete(String name){
        String query = "DELETE FROM "
                + TABLE_N_AND_L
                + " WHERE "
                + TABLE_ROW_NAME
                + " = '"
                + name
                + "';";

        Log.i("delete() = ", query);
        db.execSQL(query);
    }

    public Cursor selectAll(){
        String spinnerCategory = MainActivity.categorySpinner.getSelectedItem().toString();

        String query = "";

        if(spinnerCategory.equals("Male")){
            query = "SELECT * FROM "
                    + TABLE_N_AND_L
                    + " WHERE "
                    + TABLE_ROW_GENDER
                    + " = '"
                    + "Male"
                    + "';";
        } else if(spinnerCategory.equals("Female")){
            query = "SELECT * FROM "
                    + TABLE_N_AND_L
                    + " WHERE "
                    + TABLE_ROW_GENDER
                    + " = '"
                    + "Female"
                    + "';";
        } else if(spinnerCategory.equals("Adult")){
            query = "SELECT * FROM "
                    + TABLE_N_AND_L
                    + " WHERE "
                    + TABLE_ROW_AGE
                    + " >= 18;";
        } else if(spinnerCategory.equals("All results")){
            query = "SELECT * from " + TABLE_N_AND_L;
        }

        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public Cursor searchName(String name){
        String query = "SELECT * FROM "
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
