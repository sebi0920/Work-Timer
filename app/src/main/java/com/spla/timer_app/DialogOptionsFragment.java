package com.spla.timer_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Wastl on 13.06.2015.
 */
public class DialogOptionsFragment extends DialogFragment {

    String[] array_items = {"Edit Item", "Delete Item"};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick an option:")
                .setItems(array_items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Contact_ListFragment contactListFragment = new Contact_ListFragment();
                        switch (i) {
                            case 0:
                                contactListFragment.editLvItem(getActivity());
                                break;
                            case 1:
                                contactListFragment.deleteLvItem(getActivity());
                                //Toast.makeText(getActivity(), "Delete Item", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });



        return builder.create();
    }

}
