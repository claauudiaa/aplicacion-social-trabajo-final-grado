package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    private EditText inputNombre;
    private EditText inputCorreo;
    private EditText inputPwd;
    private ImageView home;
    private ImageView recents;
    private ImageView profile;
    private dbConnection db;
    private ImageView confirmar;
    private ImageView atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new dbConnection(this, "PlanMatch", null, 1);
        home = findViewById(R.id.imgCasa);
        recents = findViewById(R.id.imgRecientes);
        profile = findViewById(R.id.imgPerfil);
        inputNombre = findViewById(R.id.inputNombre);
        inputCorreo = findViewById(R.id.inputCorreo);
        inputPwd = findViewById(R.id.inputPwd);
        confirmar = findViewById(R.id.imgConfirmar);
        atras = findViewById(R.id.imgAtras);

        String correoAnterior = UserSession.correo;

        inputNombre.setText(UserSession.nombre);
        inputCorreo.setText(UserSession.correo);
        inputPwd.setText(UserSession.password);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Home.class);
                startActivity(intent);
            }
        });

        recents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, GroupsRecent.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Profile.class);
                startActivity(intent);
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Home.class);
                startActivity(intent);
            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombreN = inputNombre.getText().toString();
                String correoN = inputCorreo.getText().toString();
                String pwdN = inputPwd.getText().toString();

                if (nombreN.isEmpty() || correoN.isEmpty() || pwdN.isEmpty()) {
                    Toast.makeText(Profile.this, "Perfil modificado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    db.modifyProfile(nombreN, correoN, pwdN, correoAnterior);
                    Log.d("PERFIL", " Nombre " + nombreN + " Correo " + correoN + " Contrasena " + pwdN + " Correo antiguo " + correoAnterior);

                    Intent intent = new Intent(Profile.this, Home.class);
                    startActivity(intent);
                }
            }
        });
    }
}