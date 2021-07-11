package com.example.proyecto_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Menu_usuario extends AppCompatActivity {
    Button btn_mantenimiento, btn_consulta, btn_logout_usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_menu_usuario);

        btn_mantenimiento = findViewById(R.id.btn_mantenimiento_touser);
        btn_logout_usuario = findViewById(R.id.btn_salir_usuario);
        btn_consulta = findViewById(R.id.btn_consulta_usuario_);

        btn_mantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento_mantt_user  = new Intent(Menu_usuario.this, Mantenimiento_usuario.class);
                startActivity(intento_mantt_user);
            }
        });

        btn_logout_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_logout = new Intent(Menu_usuario.this, MainActivity.class);
                startActivity(intent_logout);
            }
        });

        btn_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_consulta = new Intent(Menu_usuario.this, Consulta_usuario.class);
                startActivity(intent_consulta);
            }
        });
    }
}