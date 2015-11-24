package com.vavan.contactmanager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class AddContactFragment extends Fragment {

    Button btAdd,btSelect;
    TextView tvOutput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Contact");


        tvOutput = (TextView)view.findViewById(R.id.tvDBList);

        btAdd = (Button)view.findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(getActivity());
                dbHelper.addRecord("path","description","phone",Boolean.FALSE);
            }
        });

        btSelect = (Button)view.findViewById(R.id.btSelect);
        btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String output = "";
                DBHelper db = new DBHelper(getActivity());

                if (db.getRecordCount() > 0) {

                    List<DBRecord> recordList = db.getAllRecord();

                    for (DBRecord record : recordList) {
                        output = output +
                                record.getImagePath()   + " - " +
                                record.getDescription() + " - " +
                                record.getPhoneNumber() + " - " +
                                record.getIsFavorite()  + "\n";
                    }
                }
                tvOutput.setText(output);
            }
        });

        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.add_button, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onResume() {
        super.onResume();
        btSelect.callOnClick();
    }

}
