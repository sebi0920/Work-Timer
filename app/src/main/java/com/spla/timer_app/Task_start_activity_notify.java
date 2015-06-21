package com.spla.timer_app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Task_start_activity_notify extends ActionBarActivity {
    Chronometer chronometer;
    long db_control;

    String date_start;
    String service;
    String k_name;
    String k_number;
    int id_primary;

    long time_elapsed;

    public static final String EXTRA_MESSAGE_2 = "com.spla.timer_app.MESSAGE2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_start_activity);

        //Task_start_activity.k_info_static.clear();


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        k_name = Task_start_activity.k_info_static.get(0).toString();
        k_number = Task_start_activity.k_info_static.get(1).toString();
        service = Task_start_activity.k_info_static.get(2).toString();
        time_elapsed = Long.parseLong(Task_start_activity.k_info_static.get(3));
        date_start = Task_start_activity.k_info_static.get(4).toString();
        long base_for_chrono = Long.parseLong(Task_start_activity.k_info_static.get(5));

        chronometer = (Chronometer)findViewById(R.id.chronometer2);

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String hh = h < 10 ? "0" + h : h + "";
                String mm = m < 10 ? "0" + m : m + "";
                String ss = s < 10 ? "0" + s : s + "";
                cArg.setText(hh + ":" + mm + ":" + ss);
            }
        });

        chronometer.setBase(base_for_chrono);
        chronometer.start();


        TextView text_k_name = (TextView)findViewById(R.id.textView9);
        TextView text_k_number = (TextView)findViewById(R.id.textView11);
        TextView text_service = (TextView)findViewById(R.id.textView13);

        text_k_name.setText(k_name);
        text_k_number.setText(k_number);
        text_service.setText(service);



        //time_elapsed = new Date().getTime();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_start_activity, menu);
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

    boolean firstTime = true;
    @Override
    public void onBackPressed(){


        if(firstTime){
            Toast.makeText(getBaseContext(), "Erneut klicken, um ohne zu speichern, beenden", Toast.LENGTH_SHORT).show();
            firstTime = false;
        }else {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
            Task_start_activity.k_info_static.clear();
            Task_start_activity.notificationManager.cancelAll();
        }
    }

    public void button_finish(View view){

        long time_worked = chronometer.getBase()-1;

        chronometer.stop();

        long time_calc_elapsed = new Date().getTime()-time_elapsed;

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBOpenhelper_DB2.COLUMN_STARTED, date_start);
        contentValues.put(DBOpenhelper_DB2.COLUMN_FINISHED, new SimpleDateFormat("HH:mm:ss").format(new Date()));
        contentValues.put(DBOpenhelper_DB2.COLUMN_CHRONO_BASE,  time_calc_elapsed);
        contentValues.put(DBOpenhelper_DB2.COLUMN_SERVICE, service);
        contentValues.put(DBOpenhelper_DB2.COLUMN_K_NAME, k_name);
        contentValues.put(DBOpenhelper_DB2.COLUMN_K_NUMBER, k_number);
        contentValues.put(DBOpenhelper_DB2.COLUMN_W_DATE, new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        contentValues.put(DBOpenhelper_DB2.COLUMN_ID_DATE, new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date()));

        DBOpenhelper_DB2 dbOpenhelper_db2 = new DBOpenhelper_DB2(this);
        SQLiteDatabase database = dbOpenhelper_db2.getWritableDatabase();

        long insert_id = database.insert(DBOpenhelper_DB2.TABLE_WORKED, null, contentValues);

        database.close();

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(k_name);
        arrayList.add(k_number);
        arrayList.add(service);
        arrayList.add(String.valueOf(time_calc_elapsed));
        arrayList.add(date_start);
        arrayList.add(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        arrayList.add(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        arrayList.add(new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date()));

        //Task_start_activity start_activity = new Task_start_activity();
        //start_activity.chancellor();

        Task_start_activity.notificationManager.cancel(999);

        Intent intent = new Intent(getApplicationContext(), Task_sumary.class);
        intent.putExtra(EXTRA_MESSAGE_2, arrayList);

        startActivity(intent);

    }




}


