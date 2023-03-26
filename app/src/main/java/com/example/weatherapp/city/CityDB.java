package com.example.weatherapp.city;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CityDB extends SQLiteOpenHelper {
    public static final String TableName="CityTable";
    public static final String Id="Id";
    public static final String City="City";


    public CityDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "Create table if not exists " + TableName + "("
                + Id + " Integer Primary key,"
                + City + " Text) ";

        //chạy câu truy vấn SQL để tạo bảng
       db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //xóa bảng TableWeather đã có
        db.execSQL("Drop table if exists " + TableName);
        //tạo lại
        onCreate(db);
    }
    public ArrayList<CityData> getAllCity()
    {
        ArrayList<CityData> list = new ArrayList<>();
        //câu truy vấn
        String sql = "Select * from " + TableName;
        //lấy đối tượng csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //chạy câu truy vấn trả về dạng Cursor
        Cursor cursor = db.rawQuery(sql,null);
        //tạo ArrayList<> để trả về;
        if(cursor!=null)
            while (cursor.moveToNext())
            {
                CityData City = new CityData(cursor.getInt(0),
                        cursor.getString(1));
                list.add(City);
            }
        return list;
    }

    public void addCity( CityData cityData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Id, cityData.getId());
        value.put(City, cityData.getCity());
        db.insert(TableName,null,value);
        db.close();
    }
    public void updateCity(int id, CityData cityData)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Id, cityData.getId());
        value.put(City, cityData.getCity());

        db.update(TableName, value,
                Id + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteCity(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Delete From " + TableName + " Where Id=" + id;
        //db.delete(TblName, ID + "=?",new String[]{String.valueOf(id)});

        db.execSQL(sql);
        db.close();
    }
}
