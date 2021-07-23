
package com.example.sorting;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "sort.db";

    public DBHelper(@Nullable Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //데이터 베이스가 생성이 될 때 호출
        // 데이터베이스 -> 테이블 -> 컬럼 -> 값
        db.execSQL("CREATE TABLE IF NOT EXISTS AddressList (id INTEGER PRIMARY KEY AUTOINCREMENT, number TEXT NOT NULL, address TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onCreate(db);
    }

    // SELECT 문 (주소 목록들을 조회)
    public ArrayList<AddressItem> getAddressList(){
        ArrayList<AddressItem> addressItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM AddressList ORDER BY id ASC ", null);
        if(cursor.getCount() != 0) {
            //조회 데이터가 있을때 내부 수행
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String number = cursor.getString(cursor.getColumnIndex("number"));
                String address = cursor.getString(cursor.getColumnIndex("address"));

                AddressItem addressItem = new AddressItem();
                addressItem.setId(id);
                addressItem.setNumber(number);
                addressItem.setAddress(address);
                addressItems.add(addressItem);
            }
        }
        cursor.close();

        return addressItems;
    }


    //INSERT 문 (주소 목록을 DB에 넣는다.)
    public void InsertAddress(String _number, String _address) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO AddressList (number, address) VALUES('"+ _number +"','"+ _address + "');");

    }

    // DELETE 문 (주소 목록을 제거 한다.)
    public void deleteAddress(int _id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM AddressList WHERE id = '"+ _id + "'");
    }



}