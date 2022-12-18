package com.polinela.inputnilaimahasiswa;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "DatabaseNilai.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE tabel_nilai (npm INTEGER PRIMARY KEY, nama VARCHAR, " +
                "kelas VARCHAR, nilai_tugas REAL, nilai_kuis REAL, nilai_uts REAL, " +
                "nilai_uas REAL, nilai_akhir REAL)");
        sqLiteDatabase.execSQL("CREATE TABLE session(id integer PRIMARY KEY, login text)");
        sqLiteDatabase.execSQL("CREATE TABLE user(id integer PRIMARY KEY AUTOINCREMENT, username text, password text)");
        sqLiteDatabase.execSQL("INSERT INTO session(id, login) VALUES(1, 'kosong')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tabel_nilai");
        db.execSQL("DROP TABLE IF EXISTS session");
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    public boolean insertData(String npm, String nama, String kelas, Double nilai_tugas, Double
            nilai_kuis, Double nilai_uts, Double nilai_uas, Double nilai_akhir) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("npm", npm);
        contentValues.put("nama", nama);
        contentValues.put("kelas", kelas);
        contentValues.put("nilai_tugas", nilai_tugas);
        contentValues.put("nilai_kuis", nilai_kuis);
        contentValues.put("nilai_uts", nilai_uts);
        contentValues.put("nilai_uas", nilai_uas);
        contentValues.put("nilai_akhir", nilai_akhir);
        db.insert("tabel_nilai", null, contentValues);
        return true;
    }

    public Cursor getData(String npm) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM tabel_nilai WHERE npm = " + npm + "", null);
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, "tabel_nilai");
    }

    public boolean updateData(String npm, String nama, String kelas, Double nilai_tugas, Double
            nilai_kuis, Double nilai_uts, Double nilai_uas, Double nilai_akhir) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("npm", npm);
        contentValues.put("nama", nama);
        contentValues.put("kelas", kelas);
        contentValues.put("nilai_tugas", nilai_tugas);
        contentValues.put("nilai_kuis", nilai_kuis);
        contentValues.put("nilai_uts", nilai_uts);
        contentValues.put("nilai_uas", nilai_uas);
        contentValues.put("nilai_akhir", nilai_akhir);
        db.update("tabel_nilai", contentValues, "npm = ? ", new String[]{npm});
        return true;
    }

    public void deleteData(String npm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tabel_nilai", "npm = ? ", new String[]{npm});
    }

    public ArrayList<String> getAllData() {
        ArrayList<String> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor res = db.rawQuery("SELECT * FROM tabel_nilai", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            String npm = res.getString(res.getColumnIndex("npm"));
            String nama = res.getString(res.getColumnIndex("nama"));
            String kelas = res.getString(res.getColumnIndex("kelas"));
            String nilai_tugas = res.getString(res.getColumnIndex("nilai_tugas"));
            String nilai_kuis = res.getString(res.getColumnIndex("nilai_kuis"));
            String nilai_uts = res.getString(res.getColumnIndex("nilai_uts"));
            String nilai_uas = res.getString(res.getColumnIndex("nilai_uas"));
            String nilai_akhir = res.getString(res.getColumnIndex("nilai_akhir"));
            //array_list.add(npm+", "+nama+", "+kelas+", "+nilai_tugas+", "+nilai_kuis+", "+nilai_uts+", "+nilai_uas+", "+nilai_akhir);
            array_list.add(npm + "            " + nilai_akhir);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getSomeData(String kelas) {
        ArrayList<String> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor res = db.rawQuery("SELECT * FROM tabel_nilai " +
                "WHERE kelas = '" + kelas + "'", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            String npm = res.getString(res.getColumnIndex("npm"));
            String nilai_akhir = res.getString(res.getColumnIndex("nilai_akhir"));
            array_list.add(npm + "            " + nilai_akhir);
            res.moveToNext();
        }
        return array_list;
    }

    //check session
    public Boolean checkSession(String sessionValues) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM session WHERE login = ?", new String[]{sessionValues});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //upgrade session
    public Boolean upgradeSession(String sessionValues, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login", sessionValues);
        long update = db.update("session", contentValues, "id="+id, null);
        if (update == -1) {
            return false;
        } else {
            return true;
        }
    }

    //insert user
    public Boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long insert = db.insert("user", null, contentValues);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    //check login
    public Boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username = ? AND password = ?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}


