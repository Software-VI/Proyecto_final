package com.example.proyecto_final;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4_add_reporte extends AppCompatActivity {

    private EditText et_id_reporte, et_cedula, et_fecha, et_tipo_reporte, et_observacion, et_activo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity4_add_reporte);

        et_id_reporte = findViewById(R.id.edt_id_reporte);
        et_cedula = findViewById(R.id.edt_cedula_reporte);
        et_fecha = findViewById(R.id.edt_fecha_reporte);
        et_tipo_reporte = findViewById(R.id.edt_tipo_de_reporte);
        et_observacion = findViewById(R.id.edt_observacion);
        et_activo = findViewById(R.id.edt_activo);

           }
}