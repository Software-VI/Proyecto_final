package com.example.proyecto_final;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Add_tipo_reporte extends AppCompatActivity {

    private EditText et_cod_reporte, et_descripcion;
    Button btn_add, btn_volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tipo_reporte);

        et_cod_reporte = findViewById(R.id.edt_add_tipo_reporte_codreporte);
        et_descripcion = findViewById(R.id.edt_add_tipo_reporte_descripcion);

    }
}