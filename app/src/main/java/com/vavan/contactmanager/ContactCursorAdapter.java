package com.vavan.contactmanager;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Вова on 26.11.2015.
 */
public class ContactCursorAdapter extends CursorAdapter{
    Picasso picasso;

    public ContactCursorAdapter(Context context, Cursor cursor,int flags) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.contact_list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvContactID = (TextView)view.findViewById(R.id.tvContactIDListItem);
        tvContactID.setText(""+cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)));

        ImageView ivPhoto = (ImageView)view.findViewById(R.id.ivPhotoListItem);
        String imageUri = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_IMAGE_PATH));
        File file = new File(imageUri);
        picasso.with(context)
                .load(file)
                .resizeDimen(R.dimen.image_size, R.dimen.image_size)
                .placeholder(R.drawable.contact_image)
                .error(R.drawable.contact_image)
                .centerInside()
                .into(ivPhoto);

        TextView tvDescription = (TextView)view.findViewById(R.id.tvDescriptionListItem);
        tvDescription.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DESCRIPTION)));

        TextView tvPhone = (TextView)view.findViewById(R.id.tvPhoneNumberListItem);
        tvPhone.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PHONE_NUMBER)));

        CheckBox chbIsFavorite = (CheckBox)view.findViewById(R.id.chbIsFavoriteListItem);
        chbIsFavorite.setChecked(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_IS_FAVORITE))==1);
    }
}
