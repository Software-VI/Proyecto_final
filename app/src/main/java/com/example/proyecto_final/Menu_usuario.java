package com.example.proyecto_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Menu_usuario extends AppCompatActivity {
    Button btn_add_ciudadano, btn_add_reporte, btn_logout_usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);

        btn_add_ciudadano = findViewById(R.id.btn_agregar_ciudadano_);
        btn_logout_usuario = findViewById(R.id.btn_salir_usuario);
        btn_add_reporte = findViewById(R.id.btn_agregar_reporte);

        btn_add_ciudadano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento_mantt_user  = new Intent(Menu_usuario.this, Add_ciudadano.class);
                intento_mantt_user.putExtra("previo", "usuario");
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

        btn_add_reporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_consulta = new Intent(Menu_usuario.this, Add_reporte.class);
                intent_consulta.putExtra("previo", "usuario");
                startActivity(intent_consulta);
            }
        });
    }
}