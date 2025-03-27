package com.example.tfg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GroupsView extends AppCompatActivity {

    private dbConnection db;
    private AdapterGroups adapter;
    private ListView listView;
    private ArrayList<Groups> grupos;
    private ImageView atras;
    private ImageView agregar;
    private ImageView home;
    private ImageView recents;
    private ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_view);

        home = findViewById(R.id.imgCasa);
        recents = findViewById(R.id.imgRecientes);
        profile = findViewById(R.id.imgPerfil);
        atras = findViewById(R.id.imgAtras);
        agregar = findViewById(R.id.imgAgregar);
        listView = findViewById(R.id.listview);
        db = new dbConnection(this, "PlanMatch", null, 1);
        String activityName = getIntent().getStringExtra("activity_name");

        db.addGroups();
        grupos = seeGroupsForActivity(activityName);

        // Hemos puesto el de activities porque el de groups daba error
        adapter = new AdapterGroups(this, R.layout.item_groups, grupos) {
            @Override
            public void onEntrada(Object entrada, View view) {
                TextView zona = view.findViewById(R.id.txtZona);
                TextView fecha = view.findViewById(R.id.txtDia);
                TextView horario = view.findViewById(R.id.txtHora);
                TextView unete = view.findViewById(R.id.txtBoton);

                zona.setText(((Groups) entrada).getZona());
                fecha.setText(((Groups) entrada).getFecha());
                horario.setText(((Groups) entrada).getHorario());

                unete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String txtZona = zona.getText().toString();
                        String txtFecha = fecha.getText().toString();
                        String txtHora = horario.getText().toString();

                        db.addToGroup(UserSession.correo, txtZona, txtFecha, txtHora);

                        unete.setText("Unido");
                        String asunto = "Bienvenido al grupo de " + activityName;
                        String contenido = "Hola!\n\nGracias por unirte al grupo de " + activityName + ".\n\n"
                                + "📍 Zona: " + txtZona + "\n📅 Fecha: " + txtFecha + "\n🕐 Hora: " + txtHora + "\n\n"
                                + "¡Te esperamos con ganas! 🎉";

                        if (correoValido(UserSession.correo)) {
                            MailSender.enviarCorreo(UserSession.correo, asunto, contenido);
                            Toast.makeText(GroupsView.this, "Correo enviado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(GroupsView.this, "Correo no válido", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }
        };
        listView.setAdapter(adapter);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupsView.this, Home.class);
                startActivity(intent);
            }
        });


        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupsView.this, AddGroups.class);
                intent.putExtra("activity_name", activityName);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupsView.this, Home.class);
                startActivity(intent);
            }
        });

        recents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupsView.this, GroupsRecent.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupsView.this, Profile.class);
                startActivity(intent);
            }
        });
    }
    @SuppressLint("Range")
    public ArrayList<Groups> seeGroupsForActivity(String actividadNombre) {
        ArrayList<Groups> groups = new ArrayList<>();
        SQLiteDatabase db = this.db.getReadableDatabase();

        String query = "SELECT * FROM grupos WHERE actividad_nombre = ?";
        String[] activityName = new String[]{actividadNombre};
        Cursor cursor = db.rawQuery(query, activityName);

        while (cursor.moveToNext()) {
             String zona = cursor.getString(cursor.getColumnIndex("zona"));
             String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
             String horario = cursor.getString(cursor.getColumnIndex("horario"));
             String actividad_nombre = cursor.getString(cursor.getColumnIndex("actividad_nombre"));

             groups.add(new Groups(zona, fecha, horario, actividad_nombre));
        }
        return groups;
    }

    public boolean correoValido(String correo) {
        return !TextUtils.isEmpty(correo) && Patterns.EMAIL_ADDRESS.matcher(correo).matches() &&
                (correo.endsWith(".com") || correo.endsWith(".es"));
    }
}