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

import java.util.ArrayList;


public class Task_before_start extends ActionBarActivity {
    String k_name;
    String k_number;
    String postal;
    String t_number;
    String col_id;

    public static final String EXTRA_MESSAGE_4 = "com.spla.timer_app.MESSAGE4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_before_start);

        String name_cust = getIntent().getStringExtra(Contact_ListFragment.EXTRA_MESSAGE_3);
        //String[] smt = {name_cust};

        EditText k_name_text = (EditText)findViewById(R.id.editText);
        EditText postal_text = (EditText)findViewById(R.id.editText2);
        EditText k_number_text = (EditText)findViewById(R.id.editText3);
        EditText t_number_text = (EditText)findViewById(R.id.editText4);



        DBOpenHelper_DB1 openHelperDb1 = new DBOpenHelper_DB1(this);
        SQLiteDatabase database = openHelperDb1.getWritableDatabase();

        Cursor cursor = database.query(DBOpenHelper_DB1.TABLE_CONTACTS, new String[]{DBOpenHelper_DB1.COLUMN_K_NAME, DBOpenHelper_DB1.COLUMN_K_NUMBER, DBOpenHelper_DB1.COLUMN_ADDRESS, DBOpenHelper_DB1.COLUMN_T_NUMBER, DBOpenHelper_DB1.COLUMN_ID}, null, null , null , null, null, null);

        if(cursor.moveToFirst()) {
            do {
                if (name_cust.equals(cursor.getString(1))) {
                    k_name = cursor.getString(0);
                    k_number = name_cust;
                    postal = cursor.getString(2);
                    t_number = cursor.getString(3);
                    col_id = cursor.getString(4);
                }
            } while (cursor.moveToNext());

            //Toast.makeText(this, k_name + k_number + postal + t_number, Toast.LENGTH_SHORT).show();
        }

        k_name_text.setText(k_name);
        postal_text.setText(postal);
        k_number_text.setText(k_number);
        t_number_text.setText(t_number);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browse_contacts, menu);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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

    public void knopf(View view){
        //kunden-info
        EditText k_name_text = (EditText)findViewById(R.id.editText);
        EditText postal_text = (EditText)findViewById(R.id.editText2);
        EditText k_number_text = (EditText)findViewById(R.id.editText3);
        EditText t_number_text = (EditText)findViewById(R.id.editText4);

        //gather info from customer-fields
        String k_name_new = k_name_text.getText().toString();
        String k_number_new = k_number_text.getText().toString();
        String postal_new = postal_text.getText().toString();
        String t_number_new = t_number_text.getText().toString();

        //Task-Info
        EditText service_text = (EditText)findViewById(R.id.editText5);

        String service = service_text.getText().toString();

        ArrayList<String> k_info = new ArrayList<String>();

        if(k_name_new.equals(this.k_name) && k_number_new.equals(this.k_number)&& postal_new.equals(this.postal) && t_number_new.equals(this.t_number)){
            //Toast.makeText(this, "Works!", Toast.LENGTH_SHORT).show();


                k_info.add(k_name);
                k_info.add(k_number);
                k_info.add(service);

            Intent intent = new Intent(this, Task_start_activity.class);
            intent.putExtra(EXTRA_MESSAGE_4, k_info);

            startActivity(intent);
        }else{
            DBOpenHelper_DB1 helperDb1 = new DBOpenHelper_DB1(this);
            SQLiteDatabase database = helperDb1.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DBOpenHelper_DB1.COLUMN_K_NAME, k_name_new);
            values.put(DBOpenHelper_DB1.COLUMN_K_NUMBER, k_number_new);
            values.put(DBOpenHelper_DB1.COLUMN_T_NUMBER, t_number_new);
            values.put(DBOpenHelper_DB1.COLUMN_ADDRESS, postal_new);

            long id = database.update(DBOpenHelper_DB1.TABLE_CONTACTS, values, "_id = ?", new String[]{col_id});

            database.close();

                k_info.add(k_name_new);
                k_info.add(k_number_new);
                k_info.add(service);



            Intent intent = new Intent(this, Task_start_activity.class);
            intent.putExtra(EXTRA_MESSAGE_4, k_info);

            startActivity(intent);

            //Toast.makeText(this, id+ "hello", Toast.LENGTH_SHORT).show();
        }

    }
}
