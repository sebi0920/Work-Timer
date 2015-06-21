package com.spla.timer_app;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    public static final String EXTRA_MESSAGE1 = "com.spla.timer_app.MESSAGE";
    public static final String EXTRA_MESSAGE5 = "com.spla.timer_app.MESSAGE5";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;




    //Database Stuff
    private String[] allColumns = {DBOpenHelper_DB1.COLUMN_ID, DBOpenHelper_DB1.COLUMN_K_NAME,
            DBOpenHelper_DB1.COLUMN_K_NUMBER, DBOpenHelper_DB1.COLUMN_T_NUMBER,
            DBOpenHelper_DB1.COLUMN_ADDRESS};



    static ArrayList list = new ArrayList<String>();


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DB stuff #2
        DBOpenHelper_DB1 dbOpenHelperDB1 = new DBOpenHelper_DB1(this);
        SQLiteDatabase database = dbOpenHelperDB1.getWritableDatabase();

        Cursor cursor = database.query(DBOpenHelper_DB1.TABLE_CONTACTS, new String[]{DBOpenHelper_DB1.COLUMN_K_NUMBER}, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                list.add(cursor.getString(0));

            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        actionBar.addTab(
                actionBar.newTab()
                        .setText("Kontakte")
                        .setTabListener(this));

        actionBar.addTab(
                actionBar.newTab()
                        .setText("Aufträge")
                        .setTabListener(this));
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new contact_fragment();
                case 1:
                    return new task_history();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);

            }
            return null;
        }
    }

    public static class contact_fragment extends Fragment{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState){
            View rootView = inflater.inflate(R.layout.fragment_contact_layout, container, false);
            return rootView;

        }

        @Override
        public void onCreate(Bundle savedInstanceState){

            setHasOptionsMenu(true);

            super.onCreate(savedInstanceState);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
            // Inflate the menu; this adds items to the action bar if it is present.

            menuInflater.inflate(R.menu.menu_main, menu);

            final Contact_ListFragment contactListFragment = new Contact_ListFragment();

            // Associate searchable configuration with the SearchView
            final SearchManager searchManager = (SearchManager)getActivity().getBaseContext().getSystemService(Context.SEARCH_SERVICE);
            final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    //Contact_ListFragment.listView.setFilterText(newText.toString());
                    contactListFragment.search(newText.toLowerCase(), getActivity().getBaseContext());
                    //Toast.makeText(getApplicationContext(), "Hola", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            switch (id){
                case R.id.action_settings:
                    return true;
                case R.id.impressum:
                    Intent intent = new Intent(getActivity().getBaseContext(), Impressum.class);
                    startActivity(intent);
                    return true;
            }

            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onDestroyView() {

            FragmentManager fm = getActivity().getSupportFragmentManager();

            if (fm != null) {

                Fragment fragment = (fm.findFragmentById(R.id.view5));

                FragmentTransaction ft = fm.beginTransaction();

                ft.remove(fragment);

                //ft.commit();
            }
            super.onDestroyView();
        }
    }

    /*public static class task_fragment extends Fragment{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
            View rootView = inflater.inflate(R.layout.activity_task_before_start, container, false);
            return rootView;

        }

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
        }
    }*/

    public static class task_history extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.task_history_layout, container, false);

            setHasOptionsMenu(true);
            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
            // Inflate the menu; this adds items to the action bar if it is present.

            menuInflater.inflate(R.menu.menu_history, menu);

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            switch (id){
                case R.id.action_settings:
                    return true;
                case R.id.impressum:
                    Intent intent = new Intent(getActivity().getBaseContext(), Impressum.class);
                    startActivity(intent);
                    return true;
            }

            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onDestroyView() {

            FragmentManager fm = getActivity().getSupportFragmentManager();

            if (fm != null) {

                Fragment fragment = (fm.findFragmentById(R.id.fragment));

                FragmentTransaction ft = fm.beginTransaction();

                ft.remove(fragment);

                //ft.commit();
            }
            super.onDestroyView();
        }
    }




    public void knopf(View view){
        EditText k_name_text = (EditText)findViewById(R.id.editText);
        String k_name = k_name_text.getText().toString();

        EditText k_number_text = (EditText)findViewById(R.id.editText2);
        String k_number = k_number_text.getText().toString();

        EditText t_number_text = (EditText)findViewById(R.id.editText3);
        String t_number = t_number_text.getText().toString();

        EditText address_text = (EditText)findViewById(R.id.editText4);
        String address = address_text.getText().toString();

        EditText service_text = (EditText)findViewById(R.id.editText5);
        String service = service_text.getText().toString();


        if(!list.contains(k_number)) {
            DBOpenHelper_DB1 dbOpenHelperDB1 = new DBOpenHelper_DB1(this);
            SQLiteDatabase database = dbOpenHelperDB1.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DBOpenHelper_DB1.COLUMN_K_NAME, k_name);
            values.put(DBOpenHelper_DB1.COLUMN_K_NUMBER, k_number);
            values.put(DBOpenHelper_DB1.COLUMN_T_NUMBER, t_number);
            values.put(DBOpenHelper_DB1.COLUMN_ADDRESS, address);

            long insertId = database.insert(DBOpenHelper_DB1.TABLE_CONTACTS, null, values);

            database.close();

            Toast.makeText(getApplicationContext(), "Number:"+insertId, Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, Task_start_activity.class);

        ArrayList data_Cust = new ArrayList<String>();

        data_Cust.add(k_name);
        data_Cust.add(k_number);
        data_Cust.add(service);

        intent.putExtra(EXTRA_MESSAGE1, data_Cust);
        startActivity(intent);

    }

    public void floatingButton(View view){

        Intent intent = new Intent(this, AddContact.class);
        intent.putExtra(EXTRA_MESSAGE5, list);
        startActivity(intent);
    }


}
