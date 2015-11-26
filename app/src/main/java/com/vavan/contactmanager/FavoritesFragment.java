package com.vavan.contactmanager;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends ListFragment {

    DBHelper db;
    ArrayList<DBRecord> dbRecords = new ArrayList<>();
    ContactAdapter contactAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Favorites");

        db = new DBHelper(getActivity());

        dbRecords = db.getAllRecord();
        contactAdapter = new ContactAdapter(getActivity(),dbRecords);
        setListAdapter(contactAdapter);


        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.delete_button, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
