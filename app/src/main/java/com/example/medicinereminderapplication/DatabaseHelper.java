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
    private static final String DATABASE_NAME="app.db";
    private static final int DATABASE_VERSION=1;

    private static final String TABLE_USERS="users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";

    private static final String TABLE_MEDICINES = "medicines";
    private static final String COL_MED_ID = "id";
    private static final String COL_MED_NAME = "name";
    private static final String COL_DOSAGE = "dosage";
    private static final String COL_START_DATE = "start_date";
    private static final String COL_END_DATE = "end_date";
    private static final String COL_TIMES = "times";
    private static final String COL_DAYS = "days";
    private static final String COL_NOTES = "notes";
    private static final String COL_OWNER = "username";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsers = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE, " +
                COL_PASSWORD + " TEXT)";
        db.execSQL(createUsers);

        String createMeds = "CREATE TABLE " + TABLE_MEDICINES + " (" +
                COL_MED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_MED_NAME + " TEXT NOT NULL, " +
                COL_DOSAGE + " TEXT, " +
                COL_START_DATE + " TEXT NOT NULL, " +
                COL_END_DATE + " TEXT, " +
                COL_TIMES + " TEXT NOT NULL, " +
                COL_DAYS + " TEXT NOT NULL, " +
                COL_NOTES + " TEXT, " +
                COL_OWNER + " TEXT NOT NULL)";
        db.execSQL(createMeds);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICINES);
        onCreate(db);
    }

    public boolean addUser(String username, String password){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    public boolean checkUserExists(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COL_USER_ID}, COL_USERNAME + "=?",
                new String[]{username}, null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }

    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COL_USER_ID}, COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return valid;
    }

    public void insertMedicine(Medicine med){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_MED_NAME, med.getName());
        values.put(COL_DOSAGE, med.getDosage());
        values.put(COL_START_DATE, med.getStartDate());
        values.put(COL_END_DATE, med.getEndDate());
        values.put(COL_TIMES, String.join(",", med.getTimes()));
        values.put(COL_DAYS, String.join(",", med.getDays()));
        values.put(COL_NOTES, med.getNotes());
        values.put(COL_OWNER, med.getUsername());
        db.insert(TABLE_MEDICINES, null, values);
        db.close();
    }

    public void updateMedicine(Medicine med){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_MED_NAME, med.getName());
        values.put(COL_DOSAGE, med.getDosage());
        values.put(COL_START_DATE, med.getStartDate());
        values.put(COL_END_DATE, med.getEndDate());
        values.put(COL_TIMES, String.join(",", med.getTimes()));
        values.put(COL_DAYS, String.join(",", med.getDays()));
        values.put(COL_NOTES, med.getNotes());
        values.put(COL_OWNER, med.getUsername());
        db.update(TABLE_MEDICINES, values, COL_MED_ID + "=?", new String[]{String.valueOf(med.getId())});
        db.close();
    }

    public void deleteMedicine(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_MEDICINES, COL_MED_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Medicine getMedicineById(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEDICINES, null, COL_MED_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        Medicine med = null;
        if(cursor.moveToFirst()){
            med = buildMedicineFromCursor(cursor);
        }
        cursor.close();
        db.close();
        return med;
    }

    public List<Medicine> getMedicinesForUser(String username){
        List<Medicine> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEDICINES, null, COL_OWNER + "=?", new String[]{username}, null, null, null);
        if(cursor.moveToFirst()){
            do{
                list.add(buildMedicineFromCursor(cursor));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    private Medicine buildMedicineFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_MED_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_MED_NAME));
        String dosage = cursor.getString(cursor.getColumnIndexOrThrow(COL_DOSAGE));
        String start = cursor.getString(cursor.getColumnIndexOrThrow(COL_START_DATE));
        String end = cursor.getString(cursor.getColumnIndexOrThrow(COL_END_DATE));
        String timesStr = cursor.getString(cursor.getColumnIndexOrThrow(COL_TIMES));
        String daysStr = cursor.getString(cursor.getColumnIndexOrThrow(COL_DAYS));
        String notes = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTES));
        String username = cursor.getString(cursor.getColumnIndexOrThrow(COL_OWNER));

        List<String> times = timesStr != null && !timesStr.isEmpty() ? new ArrayList<>(Arrays.asList(timesStr.split(","))) : new ArrayList<>();
        List<String> days = daysStr != null && !daysStr.isEmpty() ? new ArrayList<>(Arrays.asList(daysStr.split(","))) : new ArrayList<>();

        return new Medicine(id, name, dosage, start, end, days, times, notes, username);
    }
}

//Completed
