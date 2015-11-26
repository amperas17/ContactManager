package com.vavan.contactmanager;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Вова on 26.11.2015.
 */
public class ContactCursorAdapter extends CursorAdapter{
    public ContactCursorAdapter(Context context, Cursor cursor,int flags) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.contact_list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvDescription = (TextView)view.findViewById(R.id.tvDescription);
        tvDescription.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DESCRIPTION)));

        TextView tvPhone = (TextView)view.findViewById(R.id.tvPhoneNumber);
        tvPhone.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PHONE_NUMBER)));

        CheckBox chbIsFavorite = (CheckBox)view.findViewById(R.id.chbIsFavorite);
        chbIsFavorite.setChecked(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_IS_FAVORITE))==1);
    }
}
