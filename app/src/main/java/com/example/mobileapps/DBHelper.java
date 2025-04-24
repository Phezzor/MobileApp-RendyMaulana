package com.example.mobileapps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "shopping.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "items";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_BOUGHT = "isBought";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_BOUGHT + " INTEGER)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertItem(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_BOUGHT, 0);
        db.insert(TABLE_NAME, null, cv);
    }

    public ArrayList<ShoppingItemActivity> getAllItems() {
        ArrayList<ShoppingItemActivity> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COL_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                boolean isBought = cursor.getInt(cursor.getColumnIndexOrThrow(COL_BOUGHT)) == 1;
                list.add(new ShoppingItemActivity(id, name, isBought));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void updateItemStatus(int id, boolean isBought) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_BOUGHT, isBought ? 1 : 0);
        db.update(TABLE_NAME, cv, COL_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(id)});
    }
}
