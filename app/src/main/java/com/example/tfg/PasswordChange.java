package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PasswordChange extends AppCompatActivity {

    TextView txtAtras;
    TextView txtConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        txtAtras = findViewById(R.id.txtAtras);
        txtConfirmar = findViewById(R.id.txtConfirmar);

        txtAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PasswordChange.this, MainActivity.class);
                startActivity(i);
            }
        });

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
                Intent intent = new Intent(PasswordChange.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}