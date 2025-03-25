package com.example.tfg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private dbConnection db;
    private ArrayList<Activities[]> actividadesEnPares;
    private AdapterActivities adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        adapter = new AdapterActivities(this, R.layout.item_activities, actividadesEnPares) {
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

                icono1.setOnClickListener(v -> {
                    Intent intent = new Intent(Home.this, GroupsView.class);
                    intent.putExtra("activity_name", act1.getNombre());
                    startActivity(intent);
                });

                icono2.setOnClickListener(v -> {
                    Intent intent = new Intent(Home.this, GroupsView.class);
                    intent.putExtra("activity_name", act2.getNombre());
                    startActivity(intent);
                });
            }
        };

        listView.setAdapter(adapter);

    }
}