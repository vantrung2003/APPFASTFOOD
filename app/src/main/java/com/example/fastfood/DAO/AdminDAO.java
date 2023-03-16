package com.example.fastfood.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fastfood.DbHelper.DbHelper;
import com.example.fastfood.Model.Admin;

import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    private SQLiteDatabase db;

    public AdminDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertadmin() {
        ContentValues values = new ContentValues();
        values.put("id", "admin");
        values.put("password", "admin");
        return db.insert("Admin", null, values);
    }

    public long update(Admin odj) {
        ContentValues values = new ContentValues();
        values.put("id", odj.getId());
        values.put("password", odj.getPassword());
        return db.update("Admin", values, "id=?", new String[]{String.valueOf(odj.getId())});
    }

    public List<Admin> getAll() {
        String sql = "SELECT * FROM Admin";
        return getData(sql);
    }

    @SuppressLint("Range")
    private List<Admin> getData(String sql, String... selectionArgs) {
        List<Admin> list = new ArrayList<Admin>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            Admin obj = new Admin();
            obj.setId(c.getString(c.getColumnIndex("id")));
            obj.setPassword(c.getString(c.getColumnIndex("password")));
            list.add(obj);
        }
        return list;
    }

    public int checkLogin(String id, String password) {
        String sql = "SELECT * FROM Admin WHERE id=? AND password=?";
        List<Admin> list = getData(sql, id, password);
        if (list.size() == 0) {
            return -1;
        }
        return 1;
    }
}
