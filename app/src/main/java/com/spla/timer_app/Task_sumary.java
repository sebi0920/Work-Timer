package com.spla.timer_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;


public class Task_sumary extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_sumary);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        ArrayList arrayList = intent.getStringArrayListExtra(Task_start_activity.EXTRA_MESSAGE_2);

        TextView k_name_text = (TextView) findViewById(R.id.textView20);
        TextView k_number_text = (TextView) findViewById(R.id.textView22);
        TextView service_text = (TextView) findViewById(R.id.textView24);
        TextView lengh = (TextView) findViewById(R.id.textView26);
        TextView started_text = (TextView) findViewById(R.id.textView28);
        TextView stopped_text = (TextView) findViewById(R.id.textView30);
        TextView date_text = (TextView)findViewById(R.id.textView31);

        String k_name = arrayList.get(0).toString();
        String k_number = arrayList.get(1).toString();
        String service = arrayList.get(2).toString();
        long time_elapsed = Long.parseLong(arrayList.get(3).toString());
        String started = arrayList.get(4).toString();
        String stopped = arrayList.get(5).toString();
        String date_day = arrayList.get(6).toString();
        String date_id = arrayList.get(7).toString();

        Task_sumary_lv lv = new Task_sumary_lv();

        String time_taken_for_work = lv.time_calc(time_elapsed);
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
}
