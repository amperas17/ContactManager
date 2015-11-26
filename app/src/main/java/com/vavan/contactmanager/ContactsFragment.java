package com.vavan.contactmanager;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContactsFragment extends ListFragment implements LoaderManager.LoaderCallbacks {


    DBHelper db;
    ContactCursorAdapter contactCursorAdapter;


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

        Cursor cursor = db.getAllContactsCursor();
        contactCursorAdapter = new ContactCursorAdapter(getActivity(),cursor,0);
        setListAdapter(contactCursorAdapter);


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

        contactCursorAdapter.swapCursor((Cursor)data);
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
            Cursor cursor = db.getAllContactsCursor();

            return cursor;
        }

    }
}
