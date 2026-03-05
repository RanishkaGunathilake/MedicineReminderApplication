package com.example.medicinereminderapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createUsersTable = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE," +
                "password TEXT)";

        String createMedicinesTable = "CREATE TABLE medicines (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "dosage TEXT," +
                "start_date TEXT NOT NULL," +
                "end_date TEXT," +
                "times TEXT NOT NULL," +
                "days TEXT NOT NULL," +
                "notes TEXT," +
                "username TEXT NOT NULL)";

        db.execSQL(createUsersTable);
        db.execSQL(createMedicinesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS medicines");

        onCreate(db);
    }

    // ---------------- USER METHODS ----------------

    public boolean addUser(String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);

        db.close();

        return result != -1;
    }

    public boolean checkUserExists(String username) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "users",
                null,
                "username=?",
                new String[]{username},
                null,
                null,
                null
        );

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return exists;
    }

    public boolean validateUser(String username, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "users",
                null,
                "username=? AND password=?",
                new String[]{username, password},
                null,
                null,
                null
        );

        boolean valid = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return valid;
    }

    // ---------------- MEDICINE METHODS ----------------

    public void insertMedicine(Medicine med) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", med.getName());
        values.put("dosage", med.getDosage());
        values.put("start_date", med.getStartDate());
        values.put("end_date", med.getEndDate());
        values.put("times", String.join(",", med.getTimes()));
        values.put("days", String.join(",", med.getDays()));
        values.put("notes", med.getNotes());
        values.put("username", med.getUsername());

        db.insert("medicines", null, values);

        db.close();
    }

    public void updateMedicine(Medicine med) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", med.getName());
        values.put("dosage", med.getDosage());
        values.put("start_date", med.getStartDate());
        values.put("end_date", med.getEndDate());
        values.put("times", String.join(",", med.getTimes()));
        values.put("days", String.join(",", med.getDays()));
        values.put("notes", med.getNotes());
        values.put("username", med.getUsername());

        db.update(
                "medicines",
                values,
                "id=?",
                new String[]{String.valueOf(med.getId())}
        );

        db.close();
    }

    public void deleteMedicine(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                "medicines",
                "id=?",
                new String[]{String.valueOf(id)}
        );

        db.close();
    }

    public Medicine getMedicineById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "medicines",
                null,
                "id=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        Medicine medicine = null;

        if (cursor.moveToFirst()) {
            medicine = buildMedicineFromCursor(cursor);
        }

        cursor.close();
        db.close();

        return medicine;
    }

    public List<Medicine> getMedicinesForUser(String username) {

        List<Medicine> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "medicines",
                null,
                "username=?",
                new String[]{username},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                list.add(buildMedicineFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    // ---------------- HELPER METHOD ----------------

    private Medicine buildMedicineFromCursor(Cursor cursor) {

        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String dosage = cursor.getString(cursor.getColumnIndexOrThrow("dosage"));
        String start = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
        String end = cursor.getString(cursor.getColumnIndexOrThrow("end_date"));
        String timesStr = cursor.getString(cursor.getColumnIndexOrThrow("times"));
        String daysStr = cursor.getString(cursor.getColumnIndexOrThrow("days"));
        String notes = cursor.getString(cursor.getColumnIndexOrThrow("notes"));
        String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));

        List<String> times = new ArrayList<>(Arrays.asList(timesStr.split(",")));
        List<String> days = new ArrayList<>(Arrays.asList(daysStr.split(",")));

        return new Medicine(id, name, dosage, start, end, days, times, notes, username);
    }
}