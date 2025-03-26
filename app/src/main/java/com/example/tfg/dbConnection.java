package com.example.tfg;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class dbConnection extends SQLiteOpenHelper {

    private static String nombreUsuarios = "nombre";
    private static String emailUsuarios = "email";
    private static String passwordUsuarios = "password";
    private static String nombreActividades = "nombre";
    private static String nombreImagen = "imagen";
    private static String zona = "zona";
    private static String horario = "horario";
    private static String fecha = "fecha";
    private static String nameActividad = "actividad_nombre";
    private static ArrayList<Activities> activities;
    private static ArrayList<Groups> groups;

    public dbConnection(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryUsuarios = "create table usuarios(" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + nombreUsuarios + " TEXT, " + emailUsuarios + " TEXT, " + passwordUsuarios + " TEXT)";
        sqLiteDatabase.execSQL(queryUsuarios);

        String queryActividades = "create table actividades(" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + nombreImagen + " TEXT, " + nombreActividades + " TEXT)";
        sqLiteDatabase.execSQL(queryActividades);

        String queryGrupos = "create table grupos(" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + zona + " TEXT, " + horario + " TEXT, " + fecha + " TEXT, " + nameActividad + " TEXT)";
        sqLiteDatabase.execSQL(queryGrupos);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS usuarios");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS actividades");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS grupos");
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
        activities.add(new Activities("voley", "Vóley"));
        activities.add(new Activities("escalada", "Escalada"));
        activities.add(new Activities("crossfit", "Crossfit"));
        activities.add(new Activities("patinaje", "Patinaje"));
        activities.add(new Activities("yoga", "Yoga"));
        activities.add(new Activities("baloncesto", "Baloncesto"));
        activities.add(new Activities("beisbol", "Béisbol"));
        activities.add(new Activities("gimnasio", "Gimnasio"));
        activities.add(new Activities("zumba", "Zumba"));
        activities.add(new Activities("rugby", "Rugby"));
        activities.add(new Activities("running", "Running"));
        activities.add(new Activities("comba", "Saltar a la comba"));
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

    // Metodo que habrá que borrar/cambiar para solo añadir uno
    public ArrayList<Groups> addGroups() {
        groups = new ArrayList<>();
        groups.add(new Groups("García Noblejas", "27 de marzo", "17:00-18:00", "Senderismo"));
        groups.add(new Groups("Delicias", "10 de abril", "11:00-13:00", "Ciclismo"));
        groups.add(new Groups("Alcobendas", "23 de abril", "15:00-16:30", "Ciclismo"));


        SQLiteDatabase db = this.getWritableDatabase();

        // Comprobar si hay insertados datos en la base de datos (esto habrá que quitarlo)
        String query = "SELECT COUNT(*) FROM grupos";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) { // Insertar solo si la tabla está vacía, así evitamos superposición
            for (int i = 0; i < groups.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(zona, groups.get(i).getZona());
                values.put(fecha, groups.get(i).getFecha());
                values.put(horario, groups.get(i).getHorario());
                values.put(nameActividad, groups.get(i).getName_activity());
                db.insert("grupos", null, values);
            }
        }

        // Esto es para ver si había datos (borrar)
        Cursor c = db.rawQuery("SELECT * FROM grupos", null);
        if (c.moveToFirst()) {
            do {
                @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex("actividad_nombre"));
                Log.d("GRUPOS_EN_BD", "Grupo con actividad: " + nombre);
            } while (c.moveToNext());
        }
        c.close();

        return groups;
    }

    public void addGroup(String Pzona, String Pfecha, String Phorario, String Pactividad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(zona, Pzona);
        values.put(fecha, Pfecha);
        values.put(horario, Phorario);
        values.put(nameActividad, Pactividad);
        db.insert("grupos", null, values);

    }

    public void modifyProfile(String nombre, String email, String password, String emailAnterior) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE usuarios SET nombre = ?, email = ?, password = ? WHERE email = ?";
        db.execSQL(query, new Object[]{nombre, email, password, emailAnterior});
    }

}

