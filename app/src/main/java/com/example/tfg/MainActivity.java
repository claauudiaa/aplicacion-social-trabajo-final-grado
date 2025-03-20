package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView loginAqui;
    EditText loginInputEmail;

    EditText loginInputPassword;

    TextView changePassword;
    TextView loginIniciar;
    dbConnection db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginAqui = findViewById(R.id.txtAqui);
        loginInputEmail = findViewById(R.id.inputEmail);
        loginInputPassword = findViewById(R.id.inputPassword);
        changePassword = findViewById(R.id.txtCambio);
        loginIniciar = findViewById(R.id.txtIniciar);
        db = new dbConnection(this, "PlanMatch", null, 1);


        loginAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Register1.class);
                startActivity(i);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PasswordChange.class);
                startActivity(intent);
            }
        });

        loginIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginInputEmail.getText().toString();
                String password = loginInputPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                } else {
                    boolean resultado = db.verifyUser(email, password);
                    if (resultado) {
                        Intent intent2 = new Intent(MainActivity.this, Inicio.class);
                        startActivity(intent2);
                        Toast.makeText(MainActivity.this, "Bienvenido a PlanMatch", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}