package com.spla.timer_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Wastl on 09.06.2015.
 */
public class ContactsAdapter extends ArrayAdapter<String> {

    Context context;
    int resource;
    ArrayList<ObjectItem> arrayList1;
    ObjectItem items = null;
    private static LayoutInflater inflater;


    public ContactsAdapter(Context context, int resource, ArrayList<ObjectItem> arrayList1) {
        super(context, resource);
        this.resource = resource;
        this.context = context;
        this.arrayList1 = arrayList1;

        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        if(arrayList1.size() <= 0) {
            return 1;
        }
        return arrayList1.size();

    }

    @Override
    public String getItem(int position) {
        ObjectItem objectItem = new ObjectItem();
        objectItem = arrayList1.get(position);
        return objectItem.getK_number();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactHolder holder;
        View row = convertView;
        if(convertView == null){
            //LayoutInflater inflater = p
            row = inflater.inflate(resource, null);

            holder = new ContactHolder();
            holder.textViewBig = (TextView)row.findViewById(R.id.textViewBig);
            holder.textViewSmall = (TextView)row.findViewById(R.id.textViewSmall);

            row.setTag(holder);
        }else{
            holder = (ContactHolder)row.getTag();
        }

        if(arrayList1.size() == 0){
            holder.textViewBig.setText("No Results");
            holder.textViewSmall.setText("");
        }else {
            items = null;
            items = (ObjectItem) arrayList1.get(position);

            holder.textViewBig.setText(items.getCname());
            holder.textViewSmall.setText(items.getK_number());
        }

        return row;

    }

    static class ContactHolder{
        TextView textViewBig;
        TextView textViewSmall;
    }
}
