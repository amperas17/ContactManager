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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


class DBHelper extends SQLiteOpenHelper {

    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "myContactsDB";
    public final static String TABLE_NAME = "myContacts";
    public final static String COLUMN_ID = "_id";
    public final static String COLUMN_IMAGE_PATH = "image_path";
    public final static String COLUMN_DESCRIPTION = "description";
    public final static String COLUMN_PHONE_NUMBER = "phone_number";
    public final static String COLUMN_IS_FAVORITE = "is_favorite";


    public DBHelper(Context context){
        super(context,DBHelper.DATABASE_NAME,null,DBHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table " + DBHelper.TABLE_NAME + " (" +
                DBHelper.COLUMN_ID + " integer primary key autoincrement, " +
                DBHelper.COLUMN_IMAGE_PATH + " text not null," +
                DBHelper.COLUMN_DESCRIPTION + " text not null," +
                DBHelper.COLUMN_PHONE_NUMBER + " text not null," +
                DBHelper.COLUMN_IS_FAVORITE + " smallint NOT NULL DEFAULT 0);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (DBHelper.DATABASE_VERSION < newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + DBHelper.TABLE_NAME);
            onCreate(db);
        }
    }

    public void addRecord(String image_path,String description,String phone_number,Boolean is_favorite){
        if (description.length()>0 || phone_number.length()>0){
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(DBHelper.COLUMN_IMAGE_PATH,image_path);
            cv.put(DBHelper.COLUMN_DESCRIPTION,description);
            cv.put(DBHelper.COLUMN_PHONE_NUMBER,phone_number);
            cv.put(DBHelper.COLUMN_IS_FAVORITE,is_favorite);

            db.insert(DBHelper.TABLE_NAME, null, cv);
            db.close();
        }

    }

    public void updateRecord(Integer id,String image_path,String description,String phone_number,Boolean is_favorite){

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(DBHelper.COLUMN_IMAGE_PATH,image_path);
            cv.put(DBHelper.COLUMN_DESCRIPTION,description);
            cv.put(DBHelper.COLUMN_PHONE_NUMBER,phone_number);
            cv.put(DBHelper.COLUMN_IS_FAVORITE,is_favorite);

            db.update(DBHelper.TABLE_NAME,cv,DBHelper.COLUMN_ID+"="+id,null);
            db.close();

    }

    public void updateRecordIsFavorite(Integer id,Boolean is_favorite){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_IS_FAVORITE,is_favorite);
        db.update(DBHelper.TABLE_NAME,cv,DBHelper.COLUMN_ID+"="+id,null);
        db.close();

    }

    public void deleteRecord(Integer id){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_ID + " = " + id, null);
            db.close();
    }

    public int getRecordCount(){
        String query = "SELECT * FROM " + DBHelper.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    public ArrayList<DBRecord> getAllContactsArrayList(){
        ArrayList<DBRecord> recordList = new ArrayList<>();
        String query = "SELECT * FROM " + DBHelper.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {

            do {
                DBRecord record = new DBRecord();

                record.setId(Integer.parseInt(cursor.getString(0)));
                record.setImagePath(cursor.getString(1));
                record.setDescription(cursor.getString(2));
                record.setPhoneNumber(cursor.getString(3));
                record.setIsFavorite(Integer.parseInt(cursor.getString(4))==1);


                recordList.add(record);
            } while(cursor.moveToNext());

        }
        return recordList;
    }

    public int getLastID(){
        int lastID=0;
        String query = "SELECT * FROM " + DBHelper.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToLast())
            lastID = Integer.parseInt(cursor.getString(0));

        return lastID;
    }

    public Cursor getAllContactsCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getAllFavoritesCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DBHelper.TABLE_NAME +
                " WHERE " + DBHelper.COLUMN_IS_FAVORITE + " = 1;";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public DBRecord getRecord(Integer _id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DBHelper.TABLE_NAME +
                " WHERE " + DBHelper.COLUMN_ID + " = " + _id + ";";
        Cursor cursor = db.rawQuery(query, null);

        DBRecord record = new DBRecord();
        if(cursor.moveToFirst()) {

            record.setId(Integer.parseInt(cursor.getString(0)));
            record.setImagePath(cursor.getString(1));
            record.setDescription(cursor.getString(2));
            record.setPhoneNumber(cursor.getString(3));
            record.setIsFavorite(Integer.parseInt(cursor.getString(4)) == 1);
        }
        return record;
    }

}