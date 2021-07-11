package com.example.proyecto_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Mantenimiento_usuario extends AppCompatActivity {

    Button btn_volver_mantenimiento_usuario, btn_add_ciudadano, btn_menu_reporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_mantenimiento_usuario);

        btn_volver_mantenimiento_usuario = findViewById(R.id.btn_volver_mantenimiento);
        btn_add_ciudadano = findViewById(R.id.btn_agregar_ciudadano_);
        btn_menu_reporte = findViewById(R.id.btn_agregar_reporte);

        btn_add_ciudadano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento_add_ciudadano = new Intent(Mantenimiento_usuario.this, Add_ciudadano.class);
                startActivity(intento_add_ciudadano);
            }
        });

        btn_volver_mantenimiento_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intento_logout_admin = new Intent(Mantenimiento_usuario.this, Menu_usuario.class);
                startActivity(intento_logout_admin);
            }
        });

        btn_menu_reporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_menu_reporte = new Intent(Mantenimiento_usuario.this, Add_reporte.class);
                startActivity(intent_menu_reporte);
            }
        });

    }
}