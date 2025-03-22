package com.example.tfg;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Inicio extends AppCompatActivity {

    private dbConnection db;
    private ArrayList<Activities[]> actividadesEnPares;
    private Adaptador adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        listView = findViewById(R.id.listview);
        db = new dbConnection(this, "PlanMatch", null, 1);

        ArrayList<Activities> listaOriginal = db.addActivities();
        actividadesEnPares = new ArrayList<>();

        // Solo se agregarán pares completos
        for (int i = 0; i + 1 < listaOriginal.size(); i += 2) {
            Activities a1 = listaOriginal.get(i);
            Activities a2 = listaOriginal.get(i + 1);
            actividadesEnPares.add(new Activities[]{a1, a2});
        }

        adapter = new Adaptador(this, R.layout.entrada, actividadesEnPares) {
            @Override
            public void onEntrada(Object entrada, View view) {
                Activities[] par = (Activities[]) entrada;

                TextView texto1 = view.findViewById(R.id.nombreActividad1);
                TextView texto2 = view.findViewById(R.id.nombreActividad2);
                ImageView icono1 = view.findViewById(R.id.iconoActividad1);
                ImageView icono2 = view.findViewById(R.id.iconoActividad2);

                Activities act1 = par[0];
                Activities act2 = par[1];

                texto1.setText(act1.getNombre());
                texto2.setText(act2.getNombre());

                int idImagen1 = getResources().getIdentifier(act1.getImg(), "drawable", getPackageName());
                int idImagen2 = getResources().getIdentifier(act2.getImg(), "drawable", getPackageName());

                icono1.setImageResource(idImagen1);
                icono2.setImageResource(idImagen2);
            }
        };

        listView.setAdapter(adapter);
    }


@SuppressLint("Range")
    public void seeActivities() {
        SQLiteDatabase db = this.db.getReadableDatabase();
        String query = "SELECT * FROM actividades";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String imagen = cursor.getString(cursor.getColumnIndex("imagen"));
            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
        }
    }
}