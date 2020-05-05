package au.edu.utas.ssaadat.assignment2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import au.edu.utas.ssaadat.assignment2.model.ListData;
import au.edu.utas.ssaadat.assignment2.model.PurchasedData;

import static android.content.ContentValues.TAG;

/**
 * Created by User on 6/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "mydb.db";
    private static String TABLE_NAME_LIST= "list";
    private static String TABLE_NAME_PURCHASED= "purchased";
    private static String ID = "id";
    private static String Name = "name";
    private static String Quantity = "quantity";
    private static String Cateagory = "category";
    private static String Image = "image";
    private static String Price = "price";
    private static String Date = "date";
    private static String IsPurchased = "is_purchased";
    String isPurchasedFalse="false";
    String isPurchasedTrue="true";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE_NAME_LIST + " ( " + ID + " integer primary key autoincrement, " + Name + " text not null, " + Quantity + " text not null, " + Cateagory + " text, " + Image + " blob)";
        db.execSQL(query);
       String query2 = "create table " + TABLE_NAME_PURCHASED + " ( " + ID + " integer primary key, " + Name + " text not null, " + Quantity + " text not null, " + Price + " int not null, " + Image + " blob," + Date + " date)";
        db.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = " Drop table if exists " + TABLE_NAME_LIST;
        db.execSQL(query);
        String query2 = " Drop table if exists " + TABLE_NAME_PURCHASED;
        db.execSQL(query2);
    }



    public int insertList(ListData data) {
        SQLiteDatabase sd = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Name, data.getName());
        values.put(Quantity, data.getQuantity());
        values.put(Cateagory, data.getCategory());
        values.put(Image, data.getImage());

        long id = sd.insert(TABLE_NAME_LIST, null, values);
        sd.close();
        Log.d(TAG, String.valueOf(id));
        return (int) id;
    }

    public int updateList(ListData data) {
        SQLiteDatabase sd = getWritableDatabase();
        ContentValues values = new ContentValues();

        int code=data.getId();

        values.put(Name, data.getName());
        values.put(Quantity, data.getQuantity());
        values.put(Cateagory, data.getCategory());
        values.put(Image, data.getImage());

        long id = sd.update(TABLE_NAME_LIST, values,"id=" + code, null );
        sd.close();
        Log.d(TAG, String.valueOf(id));
        return (int) id;
    }

    public ArrayList<ListData> showList() {
        SQLiteDatabase sd = getReadableDatabase();
        String query = "Select * from " + TABLE_NAME_LIST + "  ";
        Cursor cur = sd.rawQuery(query, null);
        ArrayList<ListData> data = new ArrayList<>();

        cur.moveToFirst();

        if (cur.moveToFirst()) {
            do {
                int code = cur.getInt(0);
                String name = cur.getString(1);
                String quantity = cur.getString(2);
                String category = cur.getString(3);
                byte[] image = cur.getBlob(4);
                //String note = cur.getString(5);
                //String active = cur.getString(6);
                data.add(new ListData(code,name,quantity,category,image));

            } while (cur.moveToNext());
        }
        cur.close();
        return data;
    }

    public void deleteList(int id) {
        SQLiteDatabase sd = getWritableDatabase();
        String query = " Delete from " + TABLE_NAME_LIST + " where " + ID + " = '" + id + "'";
       sd.execSQL(query);
        sd.close();
    }

    public int addPurchased(int id, String name,String quantity,String priceX,String date,byte[] image) {
        SQLiteDatabase sd = getWritableDatabase();
        ContentValues values = new ContentValues();

        int price= Integer.parseInt(priceX);

        values.put(ID, id);
        values.put(Name,name);
        values.put(Quantity, quantity);
        values.put(Image, image);
        values.put(Price, price);
        values.put(Date, date);

        long ids = sd.insert(TABLE_NAME_PURCHASED,null, values );
        sd.close();
        Log.d(TAG, String.valueOf(ids));
        return (int) ids;
    }

    public int updatePurchased(PurchasedData data) {
        SQLiteDatabase sd = getWritableDatabase();
        ContentValues values = new ContentValues();

        int code=data.getId();

        int price= Integer.parseInt(data.getPrice());

        values.put(Name, data.getName());
        values.put(Quantity, data.getQuantity());
        values.put(Price, price);
        values.put(Image, data.getImage());

        long id = sd.update(TABLE_NAME_PURCHASED, values,"id=" + code, null );
        sd.close();
        Log.d(TAG, String.valueOf(id));
        return (int) id;
    }

    public ArrayList<PurchasedData> showPurchasedList() {
        SQLiteDatabase sd = getReadableDatabase();
        String query = "Select * from " + TABLE_NAME_PURCHASED + "  ";
        Cursor cur = sd.rawQuery(query, null);
        ArrayList<PurchasedData> data = new ArrayList<>();

        cur.moveToFirst();

        if (cur.moveToFirst()) {
            do {
                int code = cur.getInt(0);
                String name = cur.getString(1);
                String quantity = cur.getString(2);

                String price = String.valueOf(cur.getInt(3));
                byte[] image = cur.getBlob(4);
                String date = cur.getString(5);
                //String note = cur.getString(5);
                //String active = cur.getString(6);
                data.add(new PurchasedData(code,name,quantity,price,image,date));

            } while (cur.moveToNext());
        }
        cur.close();
        return data;
    }

    public ArrayList<ListData> filterPurchase() {
        SQLiteDatabase sd = getReadableDatabase();
        String query = "Select * from " + TABLE_NAME_PURCHASED + " where date   ";
        Cursor cur = sd.rawQuery(query, null);
        ArrayList<au.edu.utas.ssaadat.assignment2.model.ListData> data = new ArrayList<>();

        cur.moveToFirst();

        if (cur.moveToFirst()) {
            do {
                int code = cur.getInt(0);
                String name = cur.getString(1);
                String quantity = cur.getString(2);

                String price = cur.getString(3);
                byte[] image = cur.getBlob(4);
                String date = cur.getString(5);
                //String note = cur.getString(5);
                //String active = cur.getString(6);
                data.add(new au.edu.utas.ssaadat.assignment2.model.ListData(code,name,quantity,price,image,date));

            } while (cur.moveToNext());
        }
        cur.close();
        return data;
    }

    public int getPricePerMonth(String month) {
        SQLiteDatabase sd = getReadableDatabase();
        String query = "Select sum(price) as price from " + TABLE_NAME_PURCHASED + " where date like '%"+month+"%'  ";
        Cursor cur = sd.rawQuery(query, null);
        int price = 0;

        cur.moveToFirst();

        if (cur.moveToFirst()) {
            do {
                price = cur.getInt(0);
                Log.d("check", "getMonth: "+price);

            } while (cur.moveToNext());
        }
        cur.close();
        return price;
    }

    public ArrayList<ListData> getYear() {
        SQLiteDatabase sd = getReadableDatabase();
        String query = "Select * from " + TABLE_NAME_PURCHASED + "  ";
        Cursor cur = sd.rawQuery(query, null);
        ArrayList<ListData> data = new ArrayList<>();

        cur.moveToFirst();

        if (cur.moveToFirst()) {
            do {
                int code = cur.getInt(0);
                String name = cur.getString(1);
                String quantity = cur.getString(2);

                String price = cur.getString(3);
                byte[] image = cur.getBlob(4);
                String date = cur.getString(5);
                //String note = cur.getString(5);
                //String active = cur.getString(6);
                data.add(new ListData(code,name,quantity,price,image,date));

            } while (cur.moveToNext());
        }
        cur.close();
        return data;
    }

    public void deletePurchased(int id) {
        SQLiteDatabase sd = getWritableDatabase();
        String query = " Delete from " + TABLE_NAME_PURCHASED + " where " + ID + " = '" + id + "'";
        sd.execSQL(query);
        sd.close();
    }


}
