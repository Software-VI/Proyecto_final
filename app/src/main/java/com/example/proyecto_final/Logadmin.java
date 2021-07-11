package com.example.proyecto_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Logadmin<DBHelper> extends AppCompatActivity {

    Button btn_login_admin, btn_tomain_admin;
    EditText et_cod_seguridad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logadmin);

        btn_login_admin = findViewById(R.id.btn_login_admin);
        btn_tomain_admin = findViewById(R.id.btn_tomain_admin);
        et_cod_seguridad = findViewById(R.id.edt_codig_admin);

        btn_login_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intento_login_admin = new Intent(Logadmin.this, Menu_admin.class);
                startActivity(intento_login_admin);
            }
        });

        btn_tomain_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento_tomain_admin = new Intent(Logadmin.this, MainActivity.class);
                startActivity(intento_tomain_admin);

            }
        });
    }
}