package org.o7planning.sqlite.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import org.o7planning.sqlite.Model.Customer;

public class DatabaseManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="customer_list";
    private static final String TABLE_NAME ="customer";
    private static final String ID ="id";
    private static final String NAME ="name";
    private static final String EMAIL ="email";
    private static final String NUMBER ="number";
    private static final String ADDRESS ="address";

    private Context context;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME,null, 1);
        Log.d("DBManager", "DBManager: ");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE "+TABLE_NAME +" (" +
                ID +" integer primary key, "+
                NAME + " TEXT, "+
                EMAIL +" TEXT, "+
                NUMBER+" TEXT," +
                ADDRESS +" TEXT)";
        db.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "Drop successfully", Toast.LENGTH_SHORT).show();
    }

    //Add new a customer
    public void addCustomer(Customer customer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, customer.getName());
        values.put(NUMBER, customer.getNumber());
        values.put(EMAIL, customer.getEmail());
        values.put(ADDRESS, customer.getAddress());
        //Neu de null thi khi value bang null thi loi

        db.insert(TABLE_NAME,null,values);

        db.close();
    }

    /*
    Select a customer by ID
     */

    public Customer getCustomertById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { ID,
                        NAME, EMAIL,NUMBER,ADDRESS }, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Customer customer = new Customer(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
        cursor.close();
        db.close();
        return customer;
    }

    /*
    Update name of customer
     */

    public int updateCustomer (Customer customer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME,customer.getName());

        return db.update(TABLE_NAME,values,ID +"=?",new String[] { String.valueOf(customer.getId())});
    }

    /*
     Getting All Customer
      */

    public List<Customer> getAllCustomer() {
        List<Customer> listCustomer = new ArrayList<Customer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setId(cursor.getInt(0));
                customer.setName(cursor.getString(1));
                customer.setEmail(cursor.getString(2));
                customer.setNumber(cursor.getString(3));
                customer.setAddress(cursor.getString(4));
                listCustomer.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listCustomer;
    }
    /*
    Delete a customer by ID
     */
    public int deleteCustomer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,ID+"=?",new String[] {String.valueOf(id)});
    }
    /*
    Get Count Student in Table Customer
     */
    public int getCustomersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
