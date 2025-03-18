package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Register1 extends AppCompatActivity {

    TextView registerSiguiente;
    TextView registerAqui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        registerSiguiente = findViewById(R.id.txtSiguiente);
        registerAqui = findViewById(R.id.txtAqui);

        registerSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register1.this, Register2.class);
                startActivity(i);
            }
        });

        registerAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register1.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}