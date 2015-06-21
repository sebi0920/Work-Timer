package com.spla.timer_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Task_sumary_lv extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_sumary);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        String primary_date = intent.getStringExtra(History_ListFragment.EXTRA_MESSAGE_2);

        TextView k_name_text = (TextView) findViewById(R.id.textView20);
        TextView k_number_text = (TextView) findViewById(R.id.textView22);
        TextView service_text = (TextView) findViewById(R.id.textView24);
        TextView lengh = (TextView) findViewById(R.id.textView26);
        TextView started_text = (TextView) findViewById(R.id.textView28);
        TextView stopped_text = (TextView) findViewById(R.id.textView30);
        TextView date_text = (TextView) findViewById(R.id.textView31);

        DBOpenhelper_DB2 dbOpenhelperDb2 = new DBOpenhelper_DB2(this);
        SQLiteDatabase database = dbOpenhelperDb2.getReadableDatabase();

        Cursor cursor = database.query(DBOpenhelper_DB2.TABLE_WORKED, new String[]{DBOpenHelper_DB1.COLUMN_K_NAME,
                DBOpenhelper_DB2.COLUMN_K_NUMBER, DBOpenhelper_DB2.COLUMN_SERVICE, DBOpenhelper_DB2.COLUMN_CHRONO_BASE,
                DBOpenhelper_DB2.COLUMN_STARTED, DBOpenhelper_DB2.COLUMN_FINISHED, DBOpenhelper_DB2.COLUMN_W_DATE}, DBOpenhelper_DB2.COLUMN_ID_DATE + " = ?", new String[]{primary_date}, null, null, null);

        cursor.moveToFirst();
        String k_name = cursor.getString(0);
        String k_number = cursor.getString(1);
        String service = cursor.getString(2);
        long time_elapsed = cursor.getLong(3);
        String started = cursor.getString(4);
        String stopped = cursor.getString(5);
        String date_day = cursor.getString(6);


        cursor.close();
        database.close();

        //getting the time in the correct format
        String time_taken_for_work = time_calc(time_elapsed);
        //populate the Textfields
        k_name_text.setText(k_name);
        k_number_text.setText(k_number);
        service_text.setText(service);
        lengh.setText(time_taken_for_work);
        started_text.setText(started);
        stopped_text.setText(stopped);
        date_text.setText(date_day);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_sumary, menu);
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

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    public String time_calc(long time_elapsed){
        String time_taken = null;
        long cal_var = time_elapsed/1000;


        long hours = cal_var / 3600;
        long mod_1 = cal_var % 3600;

        long mins = mod_1 / 60;
        long mod_2 = cal_var % 60;

        long sec = mod_2;

        if(hours<10){
            if(mins<10 && sec<10){time_taken = String.valueOf("0"+hours+":0"+mins+":0"+sec);
                }else if(mins<10){
                    time_taken = String.valueOf("0"+hours+":0"+mins+":"+sec);
                }else if(sec<10){
                    time_taken = String.valueOf("0"+hours+":"+mins+":0"+sec);
                }
        }else if(hours>=10 && mins < 10){
            if(sec<10){
                time_taken = String.valueOf(hours+":0"+mins+":0"+sec);
            }
        }else if(hours>=10 && mins>=10 && sec<10){
            time_taken = String.valueOf(hours+":"+mins+":0"+sec);
        }else{
            time_taken = String.valueOf(hours+":"+mins+":"+sec);
        }


        return time_taken;

    }
}
