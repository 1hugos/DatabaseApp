package com.example.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.database.Cursor;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button insertBtn;
    public Button deleteBtn;
    public Button selectBtn;
    public Button searchBtn;
    public EditText nameEditTxt;
    public EditText lastNameEditTxt;
    public EditText ssnEditTxt;
    public EditText ageEditTxt;
    public Spinner genderSpinner;
    public EditText deleteEditTxt;
    public EditText searchEditTxt;

    DataManager dm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertBtn = (Button)findViewById(R.id.insertBtn);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);
        selectBtn = (Button)findViewById(R.id.selectBtn);
        searchBtn = (Button)findViewById(R.id.searchBtn);
        nameEditTxt = (EditText)findViewById(R.id.nameEditTxt);
        lastNameEditTxt = (EditText)findViewById(R.id.lastnameEditTxt);
        ssnEditTxt= (EditText)findViewById(R.id.ssnEditTxt);
        ageEditTxt= (EditText)findViewById(R.id.ageEditTxt);
        genderSpinner=(Spinner) findViewById(R.id.genderSpinner);
        deleteEditTxt = (EditText)findViewById(R.id.deleteEditTxt);
        searchEditTxt = (EditText)findViewById(R.id.searchEditTxt);

        dm = new DataManager(this);

        selectBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        insertBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
    }

    public void showData(Cursor c){
        while(c.moveToNext()){
            Log.i(c.getString(1),c.getString(2));
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.insertBtn:{
                dm.insert(nameEditTxt.getText().toString(), lastNameEditTxt.getText().toString());
                break;
            }
            case R.id.selectBtn:{
                showData(dm.selectAll());
                break;
            }
            case R.id.searchBtn:{
                showData(dm.searchName(searchEditTxt.getText().toString()));
                break;
            }
            case R.id.deleteBtn:{
                dm.delete(deleteEditTxt.getText().toString());
                break;
            }
        }
    }
}