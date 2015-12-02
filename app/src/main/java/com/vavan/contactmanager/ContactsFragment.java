package com.vavan.contactmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactsFragment extends ListFragment implements LoaderManager.LoaderCallbacks {


    DBHelper db;
    ContactCursorAdapter contactCursorAdapter;
    ListView listView;
    Boolean checked = false;
    Integer contactForDeletingBDID=0;
    Integer contactForDeletingLVID=0;
    String contactDescriptionForDeleting = null;

    AlertDialog.Builder ad;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        contactForDeletingBDID = Integer.parseInt(((TextView)
                v.findViewById(R.id.tvContactIDListItem)).getText().toString());

        contactDescriptionForDeleting = ((TextView)v
                .findViewById(R.id.tvDescriptionListItem)).getText().toString();
        contactForDeletingLVID = position;

        DBHelper db = new DBHelper(getActivity());
        //db.deleteRecord(Integer.parseInt (((TextView)v.findViewById(R.id.tvContactIDListItem)).getText().toString()));
        Toast.makeText(getActivity(), ((TextView)v.findViewById(R.id.tvContactIDListItem)).getText(),
                Toast.LENGTH_SHORT).show();



        CheckBox chbIsFavorite = (CheckBox)v.findViewById(R.id.chbIsFavoriteListItem);
        if (chbIsFavorite.isChecked())
            chbIsFavorite.setChecked(false);
        else
            chbIsFavorite.setChecked(true);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Contacts");

        db = new DBHelper(getActivity());

        Cursor cursor = db.getAllContactsCursor();
        contactCursorAdapter = new ContactCursorAdapter(getActivity(),cursor,0);

        setListAdapter(contactCursorAdapter);

        ad = new AlertDialog.Builder(getActivity());
        ad.setTitle("Attention!");
        ad.setMessage("Are you sure you want to delete " + contactDescriptionForDeleting +"?"); // сообщение
        ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                DBHelper db = new DBHelper(getActivity());
                db.deleteRecord(contactForDeletingBDID);
                getActivity().getSupportFragmentManager().getFragments().get(0).onResume();

            }
        });
        ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(getActivity(), "No", Toast.LENGTH_LONG)
                        .show();
            }
        });
        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {

            }
        });
        return view;

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.delete_button, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btDeleteMenuItem:
                /*Toast.makeText(getActivity(),
                        ""+getActivity().getSupportFragmentManager()
                                .getFragments().get(0).getView().toString(),
                        ""+((TextView)getListView().getChildAt(contactForDeletingBDID)
                                .findViewById(R.id.tvDescriptionListItem)).getText().toString(),
                        Toast.LENGTH_LONG).show();*/

                //DBHelper db = new DBHelper(getActivity());
                //db.deleteRecord(contactForDeletingBDID);
                //getActivity().getSupportFragmentManager().getFragments().get(0).onResume();
                if (contactDescriptionForDeleting != null ){
                    ad.show();
                } else {
                    Toast.makeText(getActivity(),"Chose contact for delete!", Toast.LENGTH_LONG).show();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);

    }





    static class MyCursorLoader extends CursorLoader {

        DBHelper db;

        public MyCursorLoader(Context context, DBHelper db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllContactsCursor();

            return cursor;
        }

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(getActivity(),db);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        contactCursorAdapter.swapCursor((Cursor) data);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

}
