package com.example.proyecto_final;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Add_tipo_reporte extends AppCompatActivity {

    private EditText et_cod_reporte, et_descripcion;
    Button btn_add, btn_volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity4_add_tipo_reporte);

        et_cod_reporte = findViewById(R.id.edt_add_tipo_reporte_codreporte);
        et_descripcion = findViewById(R.id.edt_add_tipo_reporte_descripcion);

    }
}