package com.akondi.booksnippets.mvp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class Storage extends SQLiteOpenHelper {

    private static final String TAG = Storage.class.getSimpleName();

    @Inject
    public Storage(Context context) {
        super(context, "cars_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        }catch (SQLException e){
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addCar(Car car){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, car.getName());
        values.put(VIN, car.getVin());

        try{
           db.insert(TABLE_NAME, null, values);
        }catch (SQLException e){
            Log.d(TAG,  e.getMessage());
        }

        db.close();
    }

    public List<Car> getSavedCars(){
        List<Car> carList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery(SELECT_QUERY, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Car car = new Car();
                            car.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                            car.setVin(cursor.getString(cursor.getColumnIndex(VIN)));
                            carList.add(car);
                        } while (cursor.moveToNext());
                    }
                }
            }
        }catch(SQLException e){
            Log.d(TAG, e.getMessage());
        }
        return carList;
    }

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String VIN = "vin";
    private static final String TABLE_NAME = "cars";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME;

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
            ID + " integer primary key autoincrement not null," +
            NAME + " text not null," +
            VIN + " text not null)";
}
