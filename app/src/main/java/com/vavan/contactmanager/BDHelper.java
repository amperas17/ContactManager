package com.vavan.contactmanager;

/**
 *   Class provide several methods for work with database
 *
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


class DBHelper extends SQLiteOpenHelper {

    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "transHistory";
    public final static String TABLE_NAME = "transRecords";
    public final static String COLUMN_ID = "_id";
    public final static String COLUMN_DESCRIPTION = "description";
    public final static String COLUMN_IS_FAVORITE = "is_favorite";
    public final static String COLUMN_IMAGE_PATH = "image_path";

    public DBHelper(Context context){
        super(context,DBHelper.DATABASE_NAME,null,DBHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table " + DBHelper.TABLE_NAME + " (" +
                DBHelper.COLUMN_ID + " integer primary key autoincrement, " +
                DBHelper.COLUMN_DESCRIPTION + " text not null," +
                DBHelper.COLUMN_IS_FAVORITE + " smallint NOT NULL DEFAULT 0," +
                DBHelper.COLUMN_IMAGE_PATH + " text not null);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (DBHelper.DATABASE_VERSION < newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + DBHelper.TABLE_NAME);
            onCreate(db);
        }
    }

    public void addRecord(String description, Boolean is_favorite,String image_path){
        if (description.length()>0 && image_path.length()>0){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(DBHelper.COLUMN_DESCRIPTION,description);
            cv.put(DBHelper.COLUMN_IS_FAVORITE,Boolean.FALSE);
            cv.put(DBHelper.COLUMN_IMAGE_PATH,image_path);
            db.insert(DBHelper.TABLE_NAME, null, cv);
            db.close();
        }

    }

    public int getRecordCount(){
        String query = "SELECT * FROM " + DBHelper.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    public List<DBRecord> getAllRecord(){
        List<DBRecord> recordList = new ArrayList<>();
        String query = "SELECT * FROM " + DBHelper.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {

            do {
                DBRecord record = new DBRecord();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.setDescription(cursor.getString(1));
                record.setIsFavorite(Integer.parseInt(cursor.getString(2))==1);
                record.setImagePath(cursor.getString(3));
                recordList.add(record);
            } while(cursor.moveToNext());

        }
        return recordList;
    }


}