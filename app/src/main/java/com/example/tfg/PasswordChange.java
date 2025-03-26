package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PasswordChange extends AppCompatActivity {

    private TextView txtAtras;
    private TextView txtConfirmar;
    private EditText inputPwd;
    private EditText inputRePwd;
    private dbConnection db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        db = new dbConnection(this, "PlanMatch", null, 1);
        txtAtras = findViewById(R.id.txtAtras);
        txtConfirmar = findViewById(R.id.txtConfirmar);
        inputPwd = findViewById(R.id.inputNewPassword);
        inputRePwd = findViewById(R.id.inputReNewPassword);

        txtAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PasswordChange.this, MainActivity.class);
                startActivity(i);
            }
        });

        txtConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = inputPwd.getText().toString();
                String rePassword = inputRePwd.getText().toString();

                if (password.isEmpty() || rePassword.isEmpty()) {
                    Toast.makeText(PasswordChange.this, "Los campos están vacíos", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(rePassword)) {
                    Toast.makeText(PasswordChange.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }   else {
                    db.modifyPassword(password, UserSession.correo);
                    Intent intent = new Intent(PasswordChange.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}