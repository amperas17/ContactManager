package com.vavan.contactmanager;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContactsFragment extends ListFragment implements LoaderManager.LoaderCallbacks {


    SimpleCursorAdapter scAdapter;
    DBHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Contacts");

        db = new DBHelper(getActivity());

        String[] from = new String[]{db.COLUMN_DESCRIPTION,
                db.COLUMN_PHONE_NUMBER
        };
        int[] to = new int[]{R.id.tvDescription,R.id.tvPhoneNumber};



        scAdapter = new SimpleCursorAdapter(getActivity(),R.layout.contact_list_item,null,from,to,0);

        setListAdapter(scAdapter);

        getActivity().getSupportLoaderManager().initLoader(0, null, this);


        return view;

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.delete_button, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(getActivity(),db);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        scAdapter.swapCursor((Cursor)data);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    static class MyCursorLoader extends CursorLoader {

        DBHelper db;

        public MyCursorLoader(Context context, DBHelper db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();

            return cursor;
        }

    }
}
