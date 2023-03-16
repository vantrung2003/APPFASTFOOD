package com.example.fastfood.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {

    static final String dbName = "FastFood";
    static final int dbVersion = 1;

    public DbHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableAdmin = "create table Admin (" +
                "id text PRIMARY KEY, " +
                "password text NOT NULL)";
        db.execSQL(createTableAdmin);

        String createTableFood = "create table Food (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name text NOT NULL, " +
                "Img BLOB NOT NULL, " +
                "price INTEGER NOT NULL, " +
                "delay INTEGER NOT NULL, " +
                "status INTEGER NOT NULL)";
        db.execSQL(createTableFood);

        String createTableDrink = "create table Drink (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name text NOT NULL, " +
                "Img BLOB NOT NULL, " +
                "price INTEGER NOT NULL, " +
                "figure INTEGER NOT NULL, " +
                "status INTEGER NOT NULL)";
        db.execSQL(createTableDrink);

        String createTableBaskets = "create table Baskets (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idFood INTEGER REFERENCES Food(id), " +
                "idDrink INTEGER REFERENCES Drink(id), " +
                "figureFood INTEGER, " +
                "priceFood INTEGER, " +
                "figureDrink INTEGER, " +
                "priceDrink INTEGER)";
        db.execSQL(createTableBaskets);

        String createTableBill = "create table Bill (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date DATE NOT NULL, " +
                "price INTEGER NOT NULL, " +
                "status INTEGER NOT NULL)";
        db.execSQL(createTableBill);

        String createTableBillFood = "create table BillFood (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idBill INTEGER REFERENCES Bill(id), " +
                "idFood INTEGER REFERENCES Food(id), " +
                "figure INTEGER NOT NULL, " +
                "price INTEGER NOT NULL )";
        db.execSQL(createTableBillFood);

        String createTableBillDrink = "create table BillDrink (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idBill INTEGER REFERENCES Bill(id), " +
                "idDrink INTEGER REFERENCES Drink(id), " +
                "figure INTEGER NOT NULL, " +
                "price INTEGER NOT NULL )";
        db.execSQL(createTableBillDrink);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropTableAdmin = "drop table if exists Admin";
        db.execSQL(dropTableAdmin);
        String dropTableFood = "drop table if exists Food";
        db.execSQL(dropTableFood);
        String dropTableDrink = "drop table if exists Drink";
        db.execSQL(dropTableDrink);
        String dropTableBill = "drop table if exists Bill";
        db.execSQL(dropTableBill);
        String dropTableBillFood = "drop table if exists BillFood";
        db.execSQL(dropTableBillFood);
        String dropTableBillDrink = "drop table if exists BillDrink";
        db.execSQL(dropTableBillDrink);
        onCreate(db);
    }
}
