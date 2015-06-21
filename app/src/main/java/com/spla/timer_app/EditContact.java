package com.spla.timer_app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class EditContact extends ActionBarActivity {
    String k_name;
    String k_number;
    String p_number;
    String postal;
    String primary_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText k_name_text = (EditText)findViewById(R.id.edit_kname);
        EditText k_number_text = (EditText)findViewById(R.id.edit_knumber);
        EditText postal_text = (EditText)findViewById(R.id.edit_postal);
        EditText phone_text = (EditText)findViewById(R.id.edit_phone);


        DBOpenHelper_DB1 helperDb1 = new DBOpenHelper_DB1(this);
        SQLiteDatabase database = helperDb1.getReadableDatabase();

        Cursor cursor = database.query(DBOpenHelper_DB1.TABLE_CONTACTS, new String[]{DBOpenHelper_DB1.COLUMN_K_NAME, DBOpenHelper_DB1.COLUMN_K_NUMBER, DBOpenHelper_DB1.COLUMN_T_NUMBER, DBOpenHelper_DB1.COLUMN_ADDRESS, DBOpenHelper_DB1.COLUMN_ID}, DBOpenHelper_DB1.COLUMN_K_NUMBER + " = ?", new String[]{getIntent().getStringExtra(Contact_ListFragment.EXTRA_MESSAGE_3)}, null, null, null);
        cursor.moveToFirst();
            k_name = cursor.getString(0);
            k_number = cursor.getString(1);
            p_number = cursor.getString(2);
            postal = cursor.getString(3);
            primary_key = cursor.getString(4);

        database.close();

        k_name_text.setText(k_name);
        k_number_text.setText(k_number);
        postal_text.setText(postal);
        phone_text.setText(p_number);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_contact, menu);
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

    public void editButton(View view){

        EditText k_name_text = (EditText)findViewById(R.id.edit_kname);
        EditText k_number_text = (EditText)findViewById(R.id.edit_knumber);
        EditText postal_text = (EditText)findViewById(R.id.edit_postal);
        EditText phone_text = (EditText)findViewById(R.id.edit_phone);

        String k_name_new = k_name_text.getText().toString();
        String k_number_new = k_number_text.getText().toString();
        String postal_new = postal_text.getText().toString();
        String p_number_new = phone_text.getText().toString();

        if(k_name_new.equals(k_name) && k_number_new.equals(k_number) && postal_new.equals(postal) && p_number_new.equals(p_number)){
            Toast.makeText(getBaseContext(), "Nothing has been updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        }else{
            DBOpenHelper_DB1 helperDb1 = new DBOpenHelper_DB1(this);
            SQLiteDatabase database = helperDb1.getReadableDatabase();

            ContentValues values = new ContentValues();
            values.put(DBOpenHelper_DB1.COLUMN_K_NAME, k_name_new);
            values.put(DBOpenHelper_DB1.COLUMN_K_NUMBER, k_number_new);
            values.put(DBOpenHelper_DB1.COLUMN_T_NUMBER, p_number_new);
            values.put(DBOpenHelper_DB1.COLUMN_ADDRESS, postal_new);

            long id = database.update(DBOpenHelper_DB1.TABLE_CONTACTS, values, "_id = ?", new String[]{primary_key});

            database.close();

            Toast.makeText(getBaseContext(), "Contact Informations have been updated!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        }

    }
}
