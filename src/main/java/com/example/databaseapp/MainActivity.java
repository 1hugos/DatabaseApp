package com.example.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.util.Log;
import android.database.Cursor;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button insertBtn;
    public Button deleteBtn;
    public Button selectBtn;
    public Button searchBtn;
    public EditText nameEditTxt;
    public EditText lastNameEditTxt;
    public EditText peselEditTxt;
    public EditText ageEditTxt;
    public EditText deleteEditTxt;
    public EditText searchEditTxt;
    public static Spinner categorySpinner;
    public Spinner genderSpinner;
    public TableLayout dataTable;
    public static CheckBox oneRecordCheckBox;
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
        peselEditTxt= (EditText)findViewById(R.id.peselEditTxt);
        ageEditTxt= (EditText)findViewById(R.id.ageEditTxt);
        deleteEditTxt = (EditText)findViewById(R.id.deleteEditTxt);
        searchEditTxt = (EditText)findViewById(R.id.searchEditTxt);
        dataTable = (TableLayout)findViewById(R.id.dataTable);
        categorySpinner = (Spinner)findViewById(R.id.categorySpinner);
        genderSpinner = (Spinner)findViewById(R.id.genderSpinner);
        oneRecordCheckBox = (CheckBox) findViewById(R.id.oneRecordCheckBox);

        dm = new DataManager(this);

        selectBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        insertBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);

        // Tworzenie adaptera dla spinnera z czterema opcjami
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{"All results", "Male", "Female", "Adult"});
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // Tworzenie adaptera dla spinnera z dwoma opcjami
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{"Male", "Female"});
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
    }

    public void showData(Cursor cursor){
        if (cursor.moveToFirst()) {
            // Iteruj przez wszystkie wiersze kursora
            do {
                // Pobierz wartości kolumn dla bieżącego wiersza
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DataManager.TABLE_ROW_NAME));
                @SuppressLint("Range") String lastname = cursor.getString(cursor.getColumnIndex(DataManager.TABLE_ROW_LASTNAME));
                @SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex(DataManager.TABLE_ROW_AGE));
                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(DataManager.TABLE_ROW_GENDER));
                @SuppressLint("Range") String pesel = cursor.getString(cursor.getColumnIndex(DataManager.TABLE_ROW_PESEL));

                System.out.println("Name: " + name);
                System.out.println("Last Name: " + lastname);
                System.out.println("Age: " + age);
                System.out.println("Gender: " + gender);
                System.out.println("Pesel: " + pesel);

            } while (cursor.moveToNext());
        }

        if (cursor.moveToFirst()) {
            // Tworzenie wiersza nagłówka
            TableRow headerRow = new TableRow(this);
            headerRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // Dodawanie nagłówków kolumn
            String[] columnNames = cursor.getColumnNames();
            for (int i = 0; i < columnNames.length; i++) {
                TextView headerTextView = new TextView(this);
                headerTextView.setText(columnNames[i]);

                TableRow.LayoutParams layoutParams;
                if (i == 0) {
                    // Ustawienie layout_weight dla komórki ID na 2
                    layoutParams = new TableRow.LayoutParams(
                            0,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            0.3f);
                }else if( i == 1) {
                    // Ustawienie layout_weight dla pozostałych komórek na 1
                    layoutParams = new TableRow.LayoutParams(
                            0,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            0.8f);
                }else if( i == 3) {
                    // Ustawienie layout_weight dla pozostałych komórek na 1
                    layoutParams = new TableRow.LayoutParams(
                            0,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            0.4f);
                } else if( i == 4) {
                    // Ustawienie layout_weight dla pozostałych komórek na 1
                    layoutParams = new TableRow.LayoutParams(
                            0,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            0.7f);
                }else {
                    // Ustawienie layout_weight dla pozostałych komórek na 1
                    layoutParams = new TableRow.LayoutParams(
                            0,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            1f);
                }
                headerTextView.setLayoutParams(layoutParams);

                headerRow.addView(headerTextView);
            }
            // Dodawanie wiersza nagłówka do tabeli
            dataTable.addView(headerRow);

            // Tworzenie wierszy z danymi rekordów
            do {
                TableRow dataRow = new TableRow(this);
                dataRow.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                // Dodawanie komórek z danymi dla poszczególnych kolumn
                for (int i = 0; i < columnNames.length; i++) {
                    TextView dataTextView = new TextView(this);
                    @SuppressLint("Range") String value = cursor.getString(cursor.getColumnIndex(columnNames[i]));
                    dataTextView.setText(value);

                    TableRow.LayoutParams layoutParams;
                    if (i == 0) {
                        // Ustawienie layout_weight dla komórki ID na 2
                        layoutParams = new TableRow.LayoutParams(
                                0,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                0.3f);
                    } else if( i == 1) {
                        // Ustawienie layout_weight dla pozostałych komórek na 1
                        layoutParams = new TableRow.LayoutParams(
                                0,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                0.8f);
                    } else if( i == 3) {
                        // Ustawienie layout_weight dla pozostałych komórek na 1
                        layoutParams = new TableRow.LayoutParams(
                                0,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                0.4f);
                    } else if( i == 4) {
                        // Ustawienie layout_weight dla pozostałych komórek na 1
                        layoutParams = new TableRow.LayoutParams(
                                0,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                0.7f);
                    } else {
                        // Ustawienie layout_weight dla pozostałych komórek na 1
                        layoutParams = new TableRow.LayoutParams(
                                0,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                1f);
                    }
                    dataTextView.setLayoutParams(layoutParams);

                    dataRow.addView(dataTextView);
                }
                // Dodawanie wiersza z danymi do tabeli
                dataTable.addView(dataRow);
            } while (cursor.moveToNext());
        }
        // Zamknij kursor po zakończeniu jego użytkowania
        cursor.close();
    }

    public void fillRandomData(){
        Random rand = new Random();

        String [] randomNamesArr = { "Adam", "Michał", "Jakub", "Kacper", "Piotr", "Mateusz", "Szymon", "Dawid", "Tomasz", "Jan",
                "Robert", "Marcin", "Krzysztof", "Andrzej", "Rafał", "Łukasz", "Patryk", "Grzegorz", "Bartosz", "Damian",
                "Anna", "Katarzyna", "Marta", "Agnieszka", "Monika", "Joanna", "Magdalena", "Natalia", "Julia", "Karolina" };

        String [] randomLastNamesArr = { "Nowak", "Kowalski", "Wiśniewski", "Wójcik", "Kowalczyk", "Kamiński", "Lewandowski", "Zieliński", "Szymański", "Woźniak",
                "Dąbrowski", "Kozłowski", "Jankowski", "Mazur", "Kwiatkowski", "Krawczyk", "Piotrowski", "Grabowski", "Nowakowski", "Pawłowski",
                "Adamczyk", "Dudek", "Wieczorek", "Jabłoński", "Król", "Majewski", "Olszewski", "Jaworski", "Wróbel", "Malinowski" };

        int randomAge = rand.nextInt(100);

        BigInteger randomPesel = peselGenerator();

        int index = rand.nextInt(20);

        if (nameEditTxt.getText().toString().isEmpty()) {
            nameEditTxt.setText(randomNamesArr[index]);
        }
        if (lastNameEditTxt.getText().toString().isEmpty()) {
            lastNameEditTxt.setText(randomLastNamesArr[index]);
        }
        if (ageEditTxt.getText().toString().isEmpty()) {
            ageEditTxt.setText(String.valueOf(randomAge));
        }
        if (peselEditTxt.getText().toString().isEmpty()) {
            peselEditTxt.setText(String.valueOf(randomPesel));
        }

        dm.insert(nameEditTxt.getText().toString(), lastNameEditTxt.getText().toString(), ageEditTxt.getText().toString(), genderSpinner.getSelectedItem().toString(), peselEditTxt.getText().toString());
    }

    public boolean checkBlanks (){
        boolean result = false;

        if (nameEditTxt.getText().toString().isEmpty()) {
            result = true;
        } else if (lastNameEditTxt.getText().toString().isEmpty()) {
            result = true;
        } else if (peselEditTxt.getText().toString().isEmpty()) {
            result = true;
        } else if (ageEditTxt.getText().toString().isEmpty()) {
            result = true;
        }

        return result;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.insertBtn:{
                if(checkBlanks()){
                    fillRandomData();
                } else{
                    dm.insert(nameEditTxt.getText().toString(), lastNameEditTxt.getText().toString(), ageEditTxt.getText().toString(), genderSpinner.getSelectedItem().toString(), peselEditTxt.getText().toString());
                }
                break;
            }
            case R.id.selectBtn:{
                dataTable.removeAllViews();
                showData(dm.selectAll());
                break;
            }
            case R.id.searchBtn:{
                dataTable.removeAllViews();
                showData(dm.searchName(searchEditTxt.getText().toString()));
                break;
            }
            case R.id.deleteBtn:{
                dataTable.removeAllViews();
                dm.delete(deleteEditTxt.getText().toString());
                showData(dm.selectAll());
                break;
            }
        }
    }

    public static BigInteger peselGenerator() {
        Random random = new Random();

        // Pierwsza cyfra (rok urodzenia)
        BigInteger pesel = BigInteger.valueOf(random.nextInt(9) + 1)
                .multiply(BigInteger.TEN.pow(10));

        // Druga i trzecia cyfra (miesiąc urodzenia)
        int month = random.nextInt(12) + 1;
        pesel = pesel.add(BigInteger.valueOf(month).multiply(BigInteger.TEN.pow(8)));

        // Czwarta i piąta cyfra (dzień urodzenia)
        int day = random.nextInt(31) + 1;
        pesel = pesel.add(BigInteger.valueOf(day).multiply(BigInteger.TEN.pow(6)));

        // Cyfry od szóstej do dziesiątej (losowe cyfry)
        for (int i = 0; i < 5; i++) {
            pesel = pesel.add(BigInteger.valueOf(random.nextInt(10)).multiply(BigInteger.TEN.pow(4 - i)));
        }

        // Ostatnia cyfra (cyfra kontrolna)
        int checksum = calculatePeselChecksum(pesel);
        pesel = pesel.add(BigInteger.valueOf(checksum));

        return pesel;
    }

    private static int calculatePeselChecksum(BigInteger pesel) {
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int checksum = 0;

        String peselString = pesel.toString();
        for (int i = 0; i < 10; i++) {
            int digit = Character.getNumericValue(peselString.charAt(i));
            checksum += digit * weights[i];
        }

        int lastDigit = checksum % 10;
        if (lastDigit != 0) {
            lastDigit = 10 - lastDigit;
        }
        return lastDigit;
    }
}