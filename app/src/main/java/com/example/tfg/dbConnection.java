package com.example.tfg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbConnection extends SQLiteOpenHelper {

    private static String nombre = "nombre";
    private static String email = "email";
    private static String password = "password";

    public dbConnection(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table usuarios(" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + nombre + " TEXT, " + email + " TEXT, " + password + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(sqLiteDatabase);

    }

    public void addUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nombre, user.getNombre());
        values.put(email, user.getEmail());
        values.put(password, user.getPassword());
        db.insert("usuarios", null, values);
    }

    public boolean verifyUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM usuarios WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }
}
