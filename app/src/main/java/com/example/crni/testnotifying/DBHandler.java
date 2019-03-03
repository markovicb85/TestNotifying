package com.example.crni.testnotifying;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "faci_alarm.db";
    private static final String TABLE_NOTIFICATIONS = "notifications";
    private static final String COLUMN_ID = "notificationID";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_TEXT = "text";

    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTIFICATIONS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_TEXT + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
    }

    //Add a new row to the database
    public void addnotification(Notification notification){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, notification.get_notificationTitle());
        values.put(COLUMN_TEXT, notification.get_notificationBody());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NOTIFICATIONS, null, values);
            db.close();
    }

    //Get value from table notifications
    public ArrayList<Notification> notificationResults(){
        Notification notify = new Notification();
        ArrayList<Notification> notifications = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NOTIFICATIONS + " WHERE 1";

        //Create cursor and set to first results
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            if (cursor.getString(cursor.getColumnIndex("title")) != null){
                notify.set_notificationTitle(cursor.getString(cursor.getColumnIndex("title")));
                notify.set_notificationTitle(cursor.getString(cursor.getColumnIndex("text")));
                notifications.add(notify);
                cursor.moveToNext();
            }
        }

        db.close();
        return notifications;
    }

    public void deleteAlarms() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NOTIFICATIONS);
        db.close();
    }
}
