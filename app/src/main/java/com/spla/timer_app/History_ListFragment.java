package com.spla.timer_app;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by splangger on 01.06.2015.
 */
public class History_ListFragment extends ListFragment {
    ArrayList<ObjectItem> arrayList = new ArrayList<ObjectItem>();
    ContactsAdapter adapter;
    ListView listView;
    ObjectItem objectItem = new ObjectItem();

    String sortOrder = DBOpenhelper_DB2.COLUMN_ID + " DESC";

    ArrayList<String> bananenkiste_date = new ArrayList<String>();

    public static final String EXTRA_MESSAGE_2 = "com.spla.timer_app.MESSAGE2";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        adapter = new ContactsAdapter(getActivity().getBaseContext(), R.layout.list_item_double, arrayList);

        DBOpenhelper_DB2 dbOpenhelper_db2 = new DBOpenhelper_DB2(getActivity().getBaseContext());
        SQLiteDatabase database = dbOpenhelper_db2.getReadableDatabase();

        Cursor cursor = database.query(DBOpenhelper_DB2.TABLE_WORKED, new String[]{DBOpenhelper_DB2.COLUMN_K_NAME, DBOpenhelper_DB2.COLUMN_W_DATE, DBOpenhelper_DB2.COLUMN_FINISHED}, null, null, null, null, sortOrder);

        if(cursor.moveToFirst()){
            do{
                objectItem.setCname(cursor.getString(0));
                objectItem.setK_number(cursor.getString(1));
                    arrayList.add(objectItem);

                bananenkiste_date.add(cursor.getString(2));
                objectItem = new ObjectItem();
                adapter.notifyDataSetChanged();
            }while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        setListAdapter(adapter);

        //listView = getListView();

        return super.onCreateView(inflater, container, savedInstanceState);


    }

    @Override
    public void onStart(){
        super.onStart();

        listView = getListView();
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Task_sumary_lv.class);
                if(arrayList.size() != 0){
                String toOpen = bananenkiste_date.get(position)+" "+adapter.getItem(position).toString();
                intent.putExtra(EXTRA_MESSAGE_2, toOpen);

                startActivity(intent);


                }
            }
        });

        final ListAdapter contactsAdapter = listView.getAdapter();
        ArrayList<String> stringArrayList = new ArrayList<String>();

        DBOpenhelper_DB2 dbOpenhelper_db2 = new DBOpenhelper_DB2(getActivity().getBaseContext());
        SQLiteDatabase database = dbOpenhelper_db2.getReadableDatabase();

        final Cursor cursor = database.query(DBOpenhelper_DB2.TABLE_WORKED, new String[]{DBOpenhelper_DB2.COLUMN_K_NAME}, null, null, null, null, sortOrder);

        if(cursor.moveToFirst()){
            do{
                stringArrayList.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        final ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getBaseContext(), android.R.layout.simple_list_item_multiple_choice, stringArrayList);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (arrayList.size() != 0) {
                    listView.setOnItemClickListener(null);
                    listView.setOnItemLongClickListener(null);


                    listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

                    listView.setItemChecked(position, true);

                    return true;
                } else {
                    return false;
                }
            }
        });



        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                listView.setAdapter(arrayAdapter);

                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.cab_history, menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if(item.getItemId() == R.id.item_delete){


                        DBOpenhelper_DB2 dbOpenhelperDb2 = new DBOpenhelper_DB2(getActivity().getBaseContext());
                        SQLiteDatabase sqLiteDatabase = dbOpenhelperDb2.getWritableDatabase();

                        Cursor del_key = sqLiteDatabase.query(DBOpenhelper_DB2.TABLE_WORKED, new String[]{DBOpenhelper_DB2.COLUMN_STARTED}, null, null, null, null, sortOrder);
                        ArrayList toDelArray = new ArrayList<>();
                        if (del_key.moveToFirst()) {
                            do {
                                toDelArray.add(del_key.getString(0));

                            } while (del_key.moveToNext());
                        }

                        del_key.close();


                        int ammount_del = 0;

                        //Toast.makeText(getActivity().getBaseContext(), "Smooth", Toast.LENGTH_LONG).show();
                        SparseBooleanArray checked = listView.getCheckedItemPositions();
                        int siz = checked.size();
                        for (int i = 0; i < siz; i++) {
                            int key = checked.keyAt(i);
                            boolean value = checked.get(key);
                            if (value) {
                                String del_keyString = toDelArray.get(key).toString();

                                long sth = sqLiteDatabase.delete(DBOpenhelper_DB2.TABLE_WORKED, DBOpenhelper_DB2.COLUMN_STARTED + " = ?", new String[]{del_keyString});

                                ammount_del++;
                            }
                        }


                        Toast.makeText(getActivity().getBaseContext(), ammount_del + " Tasks have been deleted", Toast.LENGTH_SHORT).show();

                        arrayList.clear();
                        bananenkiste_date.clear();

                        Cursor cursor = sqLiteDatabase.query(DBOpenhelper_DB2.TABLE_WORKED, new String[]{DBOpenhelper_DB2.COLUMN_K_NAME, DBOpenhelper_DB2.COLUMN_W_DATE, DBOpenhelper_DB2.COLUMN_FINISHED}, null, null,
                                null, null, sortOrder);

                        if (cursor.moveToFirst()) {
                            do {
                                objectItem.setCname(cursor.getString(0));
                                objectItem.setK_number(cursor.getString(1));
                                arrayList.add(objectItem);

                                bananenkiste_date.add(cursor.getString(2));
                                objectItem = new ObjectItem();
                            } while (cursor.moveToNext());
                        }
                        cursor.close();
                        sqLiteDatabase.close();

                        ContactsAdapter contactsAdapter1 = new ContactsAdapter(getActivity().getBaseContext(), R.layout.list_item_double, arrayList);
                        listView.setAdapter(contactsAdapter1);

                        mode.finish();

                        return true;
                    }

                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

                listView.setAdapter(contactsAdapter);
                //listView.setChoiceMode(0);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (arrayList.size() != 0){
                        Intent intent = new Intent(getActivity().getApplicationContext(), Task_sumary_lv.class);

                        String toOpen = bananenkiste_date.get(position)+" "+contactsAdapter.getItem(position).toString();

                        intent.putExtra(EXTRA_MESSAGE_2, toOpen);

                        startActivity(intent);
                        }
                    }
                });

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        ArrayList<String> stringArrayList = new ArrayList<String>();

                        DBOpenhelper_DB2 dbOpenhelper_db2 = new DBOpenhelper_DB2(getActivity().getBaseContext());
                        SQLiteDatabase database = dbOpenhelper_db2.getReadableDatabase();

                        Cursor cursor = database.query(DBOpenhelper_DB2.TABLE_WORKED, new String[]{DBOpenhelper_DB2.COLUMN_K_NAME}, null, null, null, null, sortOrder);

                        if(cursor.moveToFirst()){
                            do{
                                stringArrayList.add(cursor.getString(0));
                            }while (cursor.moveToNext());
                        }

                        cursor.close();
                        database.close();

                        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getBaseContext(), android.R.layout.simple_list_item_multiple_choice, stringArrayList);

                        listView.setAdapter(arrayAdapter);

                        listView.setOnItemClickListener(null);

                        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

                        listView.setItemChecked(position, true);

                        return true;
                    }
                });

            }
        });


    }



}
