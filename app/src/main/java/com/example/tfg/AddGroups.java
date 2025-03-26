package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddGroups extends AppCompatActivity {

    private EditText zona;
    private EditText fecha;
    private EditText hora;
    private dbConnection db;
    private ImageView atras;
    private ImageView confirmar;
    private ImageView home;
    private ImageView recents;
    private ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_groups);

        db = new dbConnection(this, "PlanMatch", null, 1);
        String activityName = getIntent().getStringExtra("activity_name");
        Log.d("DEBUG", "Nombre recibido por intent en agregar: " + activityName);

        home = findViewById(R.id.imgCasa);
        recents = findViewById(R.id.imgRecientes);
        profile = findViewById(R.id.imgPerfil);
        zona = findViewById(R.id.inputZona);
        fecha = findViewById(R.id.inputFecha);
        hora = findViewById(R.id.inputHora);
        atras = findViewById(R.id.imgAtras);
        confirmar = findViewById(R.id.imgConfirmar);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddGroups.this, GroupsView.class);
                startActivity(intent);
            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtZona = zona.getText().toString();
                String txtFecha = fecha.getText().toString();
                String txtHora = hora.getText().toString();

                db.addGroup(txtZona, txtFecha, txtHora, activityName);

                Toast.makeText(AddGroups.this, "Grupo creado correctamente", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddGroups.this, GroupsView.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddGroups.this, Home.class);
                startActivity(intent);
            }
        });

        recents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddGroups.this, GroupsRecent.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddGroups.this, Profile.class);
                startActivity(intent);
            }
        });
    }
}