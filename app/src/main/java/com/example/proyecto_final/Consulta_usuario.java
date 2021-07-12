package com.example.proyecto_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Consulta_usuario extends AppCompatActivity {
    EditText et_cedula;
     Button btn_backto_consulta;
     Button btn_consultar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_usuario);

        btn_backto_consulta = findViewById(R.id.btn_volver_consulta);
        btn_consultar = (Button)findViewById(R.id.btn_consulta_caso_ciudadano);
        et_cedula = (EditText)findViewById(R.id.edt_cedula_ciudadano);

        btn_backto_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_volverto_consulta = new Intent(Consulta_usuario.this, Menu_usuario.class);
                startActivity(intent_volverto_consulta);
            }
        });
        btn_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_cedula.getText().toString().isEmpty()) {
                    String cedula = et_cedula.getText().toString();
                    Intent i = new Intent(Consulta_usuario.this, Add_user.class);
                    i.putExtra("previo", "consulta_usuario");
                    i.putExtra("cedula", cedula);
                    startActivity(i);
                }else {
                    Toast.makeText(Consulta_usuario.this, "Ingrese un número de cédula", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}