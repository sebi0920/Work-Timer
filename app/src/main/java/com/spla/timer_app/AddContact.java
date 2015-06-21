package com.spla.timer_app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class AddContact extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addButton(View view){
        ArrayList k_number_array = getIntent().getStringArrayListExtra(MainActivity.EXTRA_MESSAGE5);

        EditText text_name = (EditText)findViewById(R.id.editText6);
        EditText text_k_number = (EditText)findViewById(R.id.editText7);
        EditText text_p_number = (EditText)findViewById(R.id.editText9);
        EditText text_postal = (EditText)findViewById(R.id.editText8);


        String name = text_name.getText().toString();
        String k_number = text_k_number.getText().toString();
        String p_number = text_p_number.getText().toString();
        String postal = text_postal.getText().toString();

        ContentValues values = new ContentValues();

        values.put(DBOpenHelper_DB1.COLUMN_K_NAME, name);
        values.put(DBOpenHelper_DB1.COLUMN_K_NUMBER, k_number);
        values.put(DBOpenHelper_DB1.COLUMN_T_NUMBER, p_number);
        values.put(DBOpenHelper_DB1.COLUMN_ADDRESS, postal);

        if(!k_number_array.contains(k_number)) {

            DBOpenHelper_DB1 dbOpenHelperDB1 = new DBOpenHelper_DB1(this);
            SQLiteDatabase database = dbOpenHelperDB1.getWritableDatabase();

            long id = database.insert(DBOpenHelper_DB1.TABLE_CONTACTS, null, values);

            Toast.makeText(this, "New Contact has been Added", Toast.LENGTH_SHORT).show();

            database.close();

            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        }else{
            Toast.makeText(this, "K_Number already in use!", Toast.LENGTH_SHORT).show();
        }
    }
}
