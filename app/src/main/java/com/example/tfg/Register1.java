package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register1 extends AppCompatActivity {

    TextView registerRegistrar;
    TextView registerAqui;
    EditText editNombre;
    EditText editCorreo;
    EditText editPassword;
    EditText editRePassword;
    dbConnection db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        registerRegistrar = findViewById(R.id.txtRegistrar);
        registerAqui = findViewById(R.id.txtAqui);
        editNombre = findViewById(R.id.inputUsuario);
        editCorreo = findViewById(R.id.inputEmail);
        editPassword = findViewById(R.id.inputPassword);
        editRePassword = findViewById(R.id.inputRePassword);

        db = new dbConnection(this, "PlanMatch", null, 1);

        registerAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register1.this, MainActivity.class);
                startActivity(intent);
            }
        });

        registerRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = editNombre.getText().toString();
                String correo = editCorreo.getText().toString();
                String password = editPassword.getText().toString();
                String repassword = editRePassword.getText().toString();

                if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Register1.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(repassword)) {
                    Toast.makeText(Register1.this, "Las contraseñas deben coincidir", Toast.LENGTH_SHORT).show();
                } else {
                    Users user = new Users(nombre, correo, password);
                    db.addUser(user);

                    Toast.makeText(Register1.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Register1.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}