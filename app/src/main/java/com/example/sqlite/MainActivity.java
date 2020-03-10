package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText name;
    private EditText mobileNumber;
    private EditText email;

    private dbHelper dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name_et);
        email = findViewById(R.id.email_et);
        mobileNumber = findViewById(R.id.mobileNumber_et);

        dbhelper = new dbHelper(this);
    }

    public void write(View v) {
        ContentValues values = getValues();
        if(values == null)
            Toast.makeText(this,"value should not be empty!", Toast.LENGTH_SHORT).show();

        SQLiteDatabase db = dbhelper.getWritableDatabase();
        long id = db.insert(dbhelper.tableName, null, values);
        if(id == -1)
            Toast.makeText(this,"write failed!", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "write successfully!", Toast.LENGTH_SHORT).show();
            read(v);
        }
        db.close();
    }

    public void update(View v) {
        ContentValues values = getValues();
        if(values == null)
            Toast.makeText(this,"value should not be empty!", Toast.LENGTH_SHORT).show();

        SQLiteDatabase db = dbhelper.getWritableDatabase();
        int id = db.update(dbhelper.tableName, values, "mobileNumber=?",
                new String[] {mobileNumber.getText().toString()});
        if(id == -1)
            Toast.makeText(this,"update failed!", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "update successfully!", Toast.LENGTH_SHORT).show();
            read(v);
        }
        db.close();
    }

    public void remove(View v) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        int id = db.delete(dbhelper.tableName, null, null);
        Toast.makeText(this, id + " items removed!",
                Toast.LENGTH_LONG).show();
        db.close();
        read(v);
    }

    public void read(View v) {
        TextView show = findViewById(R.id.showInfo);
        show.setText("");

        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query(dbhelper.tableName, null, null,
                null, null, null, null, null);
        if(cursor.getCount() == 0)
            Toast.makeText(this,"no items!", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this,"read " + cursor.getCount() + " items!", Toast.LENGTH_SHORT).show();
            cursor.moveToFirst();
            do {
                show.append("Name: " + cursor.getString(0) + "\n");
                show.append("Email: " + cursor.getString(1) + "\n");
                show.append("Mobile Number: " + cursor.getString(2) + "\n\n");
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    //get information from edit text as ContentValues
    private ContentValues getValues() {
        String name_str = name.getText().toString();
        String email_str = email.getText().toString();
        String mobileNumber_str = mobileNumber.getText().toString();
        if(name_str.length() == 0 || mobileNumber_str.length() == 0 || email_str.length() == 0)
            return null;

        ContentValues values = new ContentValues();
        values.put("name", name_str);
        values.put("email", email_str);
        values.put("mobileNumber", mobileNumber_str);

        return values;
    }

}
