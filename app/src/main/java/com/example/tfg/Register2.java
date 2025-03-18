package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Register2 extends AppCompatActivity {

    TextView registerAqui2;
    TextView registerAtras2;
    TextView registerSiguiente2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        registerAqui2 = findViewById(R.id.txtAqui);
        registerAtras2 = findViewById(R.id.txtAtras);
        registerSiguiente2 = findViewById(R.id.txtSiguiente);

        registerAqui2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register2.this, MainActivity.class);
                startActivity(i);
            }
        });

        registerAtras2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register2.this, Register1.class);
                startActivity(intent);
            }
        });

        registerSiguiente2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Register2.this, MainActivity.class);
                startActivity(intent2);
            }
        });
    }
}