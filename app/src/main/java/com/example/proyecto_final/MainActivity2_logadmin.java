package com.example.proyecto_final;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2_logadmin<DBHelper> extends AppCompatActivity {

    Button btn_login_admin, btn_tomain_admin;
    EditText et_cod_seguridad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_logadmin);

        btn_login_admin = findViewById(R.id.btn_login_admin);
        btn_tomain_admin = findViewById(R.id.btn_tomain_admin);
        et_cod_seguridad = findViewById(R.id.edt_codig_admin);

        btn_login_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intento_login_admin = new Intent(MainActivity2_logadmin.this, MainActivity3_menu_admin.class);
                startActivity(intento_login_admin);
            }
        });

        btn_tomain_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento_tomain_admin = new Intent(MainActivity2_logadmin.this, MainActivity.class);
                startActivity(intento_tomain_admin);

            }
        });
    }
}