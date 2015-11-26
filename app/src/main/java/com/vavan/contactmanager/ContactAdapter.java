package com.vavan.contactmanager;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<DBRecord> dbRecords;

    ContactAdapter(Context _context, ArrayList<DBRecord> _dbRecords){
        context = _context;
        dbRecords = _dbRecords;
        layoutInflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dbRecords.size();
    }

    @Override
    public Object getItem(int position) {
        return dbRecords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
        view = layoutInflater.inflate(R.layout.contact_list_item,parent,false);
        }

        DBRecord dbRecord = getRecord(position);
        ((TextView)view.findViewById(R.id.tvDescription)).setText(dbRecord.getDescription());
        ((TextView)view.findViewById(R.id.tvPhoneNumber)).setText(dbRecord.getPhoneNumber());

        CheckBox chbIsFavorite = (CheckBox)view.findViewById(R.id.chbIsFavorite);
        chbIsFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        chbIsFavorite.setChecked(dbRecord.getIsFavorite());
        chbIsFavorite.setTag(position);



        return view;
    }

    DBRecord getRecord(int _position){
        return ((DBRecord)getItem(_position));
    }

}
