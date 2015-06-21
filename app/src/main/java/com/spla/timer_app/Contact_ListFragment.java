package com.spla.timer_app;

import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class Contact_ListFragment extends ListFragment {

    static ListView listView;
    ArrayList<ObjectItem> arrayList1 = new ArrayList<ObjectItem>();
    ContactsAdapter arrayAdapter;
    ObjectItem objectItem = new ObjectItem();

    static int long_position;




    ArrayList<ObjectItem> itemArrayList = new ArrayList<ObjectItem>();

    public static final String EXTRA_MESSAGE_3 = "com.spla.timer_app.MESSAGE3";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.arrayAdapter = new ContactsAdapter(getActivity().getBaseContext(), R.layout.list_item_double, arrayList1);

        //Populating the contact-list
        DBOpenHelper_DB1 dbOpenHelperDB1 = new DBOpenHelper_DB1(getActivity().getApplicationContext());
        SQLiteDatabase database = dbOpenHelperDB1.getWritableDatabase();

        Cursor cursor = database.query(DBOpenHelper_DB1.TABLE_CONTACTS, new String[]{DBOpenHelper_DB1.COLUMN_K_NAME, DBOpenHelper_DB1.COLUMN_K_NUMBER}, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                    objectItem.setCname(cursor.getString(0));
                    objectItem.setK_number(cursor.getString(1));

                    arrayList1.add(objectItem);
                    arrayAdapter.notifyDataSetChanged();
                    objectItem = new ObjectItem();

            }while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        setListAdapter(arrayAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        listView = getListView();


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (arrayList1.size() != 0) {
                    String name_cust = listView.getAdapter().getItem(position).toString();

                    Intent intent = new Intent(getActivity().getBaseContext(), Task_before_start.class);
                    intent.putExtra(EXTRA_MESSAGE_3, name_cust);
                    //Toast.makeText(getActivity().getBaseContext(), "sth "+ ,Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                }
                }

                //Toast.makeText(getActivity().getBaseContext(), "Name: " + listView.getAdapter().getItem(position).toString(), Toast.LENGTH_SHORT).show();
                //}
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    if (arrayList1.size() != 0) {
                        DialogFragment dialogFragment = new DialogOptionsFragment();
                        dialogFragment.show(getFragmentManager(), null);

                        long_position = position;
                        return true;
                    }else{
                        return false;
                    }
                    //arrayAdapter = new ContactsAdapter(getActivity().getBaseContext(), R.layout.list_item_double_multi, arrayList1);
                    //listView.setAdapter(arrayAdapter);

                }
            });
        }


    public void search(String newText, Context context){

        itemArrayList.clear();

        ArrayList<String> name_search = new ArrayList<String>();
        ArrayList<String> number_search = new ArrayList<String>();

        DBOpenHelper_DB1 dbOpenHelperDB1 = new DBOpenHelper_DB1(context);
        SQLiteDatabase database = dbOpenHelperDB1.getWritableDatabase();

        Cursor cursor = database.query(DBOpenHelper_DB1.TABLE_CONTACTS, new String[]{DBOpenHelper_DB1.COLUMN_K_NAME, DBOpenHelper_DB1.COLUMN_K_NUMBER}, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                name_search.add(cursor.getString(0).toString());
                number_search.add(cursor.getString(1).toString());
            }while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        this.arrayList1.clear();


        ObjectItem item = new ObjectItem();
        String iwas;

        arrayAdapter = new ContactsAdapter(context, R.layout.list_item_double, itemArrayList);

        Contact_ListFragment.listView.setAdapter(arrayAdapter);


        for(int i = 0; name_search.size()>i; i++){
            iwas = name_search.get(i).toLowerCase();

            if(iwas.contains(newText)){
                item.setCname(name_search.get(i).toString());
                item.setK_number(number_search.get(i).toString());

                this.itemArrayList.add(item);
                this.arrayList1.add(item);

                arrayAdapter.notifyDataSetChanged();

                item = new ObjectItem();

            }
        }
    }

    public void deleteLvItem(Context context) {
        String k_name = listView.getItemAtPosition(long_position).toString();
        ArrayList<ObjectItem> obList = new ArrayList<ObjectItem>();

        DBOpenHelper_DB1 dbOpenHelperDB1 = new DBOpenHelper_DB1(context);
        SQLiteDatabase database = dbOpenHelperDB1.getWritableDatabase();

        database.delete(DBOpenHelper_DB1.TABLE_CONTACTS, DBOpenHelper_DB1.COLUMN_K_NUMBER + " = ?", new String[]{k_name});

        Cursor cursor = database.query(DBOpenHelper_DB1.TABLE_CONTACTS, new String[]{DBOpenHelper_DB1.COLUMN_K_NAME, DBOpenHelper_DB1.COLUMN_K_NUMBER}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                objectItem.setCname(cursor.getString(0));
                objectItem.setK_number(cursor.getString(1));

                obList.add(objectItem);

                objectItem = new ObjectItem();

            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        ContactsAdapter adapter = new ContactsAdapter(context, R.layout.list_item_double, obList);
        listView.setAdapter(adapter);

        if (obList.size()==0){
            listView.setOnItemClickListener(null);
            listView.setOnItemLongClickListener(null);
        }
    }


    public void editLvItem(Context context){
        Intent intent = new Intent(context, EditContact.class);
        intent.putExtra(EXTRA_MESSAGE_3, listView.getAdapter().getItem(long_position).toString());
        context.startActivity(intent);

    }

}