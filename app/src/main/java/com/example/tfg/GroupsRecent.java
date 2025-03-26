package com.example.tfg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GroupsRecent extends AppCompatActivity {

    private dbConnection db;
    private AdapterRecents adapter;
    private ListView listView;
    private ArrayList<Groups> myGroups;
    private ImageView atras;
    private ImageView home;
    private ImageView recents;
    private ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_recents);

        home = findViewById(R.id.imgCasa);
        recents = findViewById(R.id.imgRecientes);
        profile = findViewById(R.id.imgPerfil);
        atras = findViewById(R.id.imgAtras);
        listView = findViewById(R.id.listview);

        db = new dbConnection(this, "PlanMatch", null, 1);
        myGroups = seeMyGroups(UserSession.correo);

        adapter = new AdapterRecents(this, R.layout.item_recents, myGroups) {
            @Override
            public void onEntrada(Object entrada, View view) {
                TextView zona = view.findViewById(R.id.txtZona);
                TextView fecha = view.findViewById(R.id.txtDia);
                TextView horario = view.findViewById(R.id.txtHora);
                TextView salir = view.findViewById(R.id.txtBoton);

                zona.setText(((Groups) entrada).getZona());
                fecha.setText(((Groups) entrada).getFecha());
                horario.setText(((Groups) entrada).getHorario());

                salir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String txtZona = zona.getText().toString();
                        String txtFecha = fecha.getText().toString();
                        String txtHora = horario.getText().toString();

                        db.deleteGroup(UserSession.correo, txtZona, txtFecha, txtHora);
                        Toast.makeText(GroupsRecent.this, "Has salido del grupo", Toast.LENGTH_SHORT).show();

                        myGroups.remove(entrada);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        listView.setAdapter(adapter);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupsRecent.this, Home.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupsRecent.this, Home.class);
                startActivity(intent);
            }
        });

        recents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupsRecent.this, GroupsRecent.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupsRecent.this, Profile.class);
                startActivity(intent);
            }
        });

    }

    @SuppressLint("Range")
    public ArrayList<Groups> seeMyGroups(String correo) {
        ArrayList<Groups> groups = new ArrayList<>();
        SQLiteDatabase db = this.db.getReadableDatabase();

        String query = "SELECT grupos.zona, grupos.fecha, grupos.horario " +
                "FROM usuarios_grupos " +
                "JOIN usuarios ON usuarios_grupos.usu_correo = usuarios.email " +
                "JOIN grupos ON usuarios_grupos.gru_zona = grupos.zona " +
                "WHERE usuarios.email = ?";

        Cursor cursor = db.rawQuery(query, new String[]{correo});

        while (cursor.moveToNext()) {
            String zona = cursor.getString(cursor.getColumnIndex("zona"));
            String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
            String horario = cursor.getString(cursor.getColumnIndex("horario"));

            groups.add(new Groups(zona, fecha, horario, ""));
        }

        cursor.close();
        return groups;
    }

}