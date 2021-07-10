package com.example.proyecto_final;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Add_ciudadano extends AppCompatActivity {

    private EditText et_cedula, et_nombre, et_apellido, et_direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity4_add_ciudadano);

        et_cedula = findViewById(R.id.edt_cedula_ciudadano);
        et_nombre = findViewById(R.id.edt_nombre_ciudadano);
        et_apellido = findViewById(R.id.edt_apellido_ciudadano);
        et_direccion = findViewById(R.id.edt_descripcion_ciudadano);

    }
}