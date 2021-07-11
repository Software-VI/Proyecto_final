package com.example.proyecto_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Menu_admin extends AppCompatActivity {

    Button btn_salir_admin, btn_add_tipo_reporte, btn_consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        btn_salir_admin = findViewById(R.id.btn_salir_admin);
        btn_consulta = findViewById(R.id.btn_consultar_usuario_admin_to_dialog);
        btn_add_tipo_reporte = findViewById(R.id.btn_agregar_reporte_admin);

        btn_salir_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento_logout_admin = new Intent(Menu_admin.this, Logadmin.class);
                startActivity(intento_logout_admin);
            }
        });

        btn_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent_consulta = new Intent(Menu_admin.this, Add_user.class);
                    startActivity(intent_consulta);

                }

        });

        btn_add_tipo_reporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_add_tipo_reporte = new Intent(Menu_admin.this, Add_tipo_reporte.class);
                startActivity(intent_add_tipo_reporte);

            }
        });
    }
}