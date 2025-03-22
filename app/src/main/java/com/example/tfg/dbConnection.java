package com.example.tfg;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class dbConnection extends SQLiteOpenHelper {

    private static String nombreUsuarios = "nombre";
    private static String emailUsuarios = "email";
    private static String passwordUsuarios = "password";
    private static String nombreActividades = "nombre";
    private static String nombreImagen = "imagen";
    private static ArrayList<Activities> activities;

    public dbConnection(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryUsuarios = "create table usuarios(" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + nombreUsuarios + " TEXT, " + emailUsuarios + " TEXT, " + passwordUsuarios + " TEXT)";
        sqLiteDatabase.execSQL(queryUsuarios);

        String queryActividades = "create table actividades(" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + nombreImagen + " TEXT, " + nombreActividades + " TEXT)";
        sqLiteDatabase.execSQL(queryActividades);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS usuarios");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS actividades");
        onCreate(sqLiteDatabase);

    }

    public void addUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nombreUsuarios, user.getNombre());
        values.put(emailUsuarios, user.getEmail());
        values.put(passwordUsuarios, user.getPassword());
        db.insert("usuarios", null, values);
    }

    public boolean verifyUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM usuarios WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    public ArrayList<Activities> addActivities() {
        activities = new ArrayList<>();
        activities.add(new Activities("ciclismo", "Ciclismo"));
        activities.add(new Activities("senderismo", "Senderismo"));
        activities.add(new Activities("parkour", "Parkour"));
        activities.add(new Activities("futbol", "Fútbol"));

        /*
        activities.add(new Activities("voley.svg", "Vóley"));
        activities.add(new Activities("escalada.svg", "Escalada"));
        activities.add(new Activities("crossfit.svg", "Crossfit"));
        activities.add(new Activities("patinaje.svg", "Patinaje"));
        activities.add(new Activities("yoga.svg", "Yoga"));
        activities.add(new Activities("baloncesto.svg", "Baloncesto"));
        activities.add(new Activities("beisbol.svg", "Béisbol"));
        activities.add(new Activities("gimnasio.svg", "Gimnasio"));
        activities.add(new Activities("zumba.svg", "Zumba"));
        activities.add(new Activities("rugby.svg", "Rugby"));
        activities.add(new Activities("running.svg", "Running"));
        activities.add(new Activities("comba.svg", "Saltar a la comba"));
        */

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT COUNT(*) FROM actividades";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) { // Insertar solo si la tabla está vacía, así evitamos superposición
            for (int i = 0; i < activities.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(nombreImagen, activities.get(i).getImg());
                values.put(nombreActividades, activities.get(i).getNombre());
                db.insert("actividades", null, values);
            }
        }
        return activities;
    }

}

