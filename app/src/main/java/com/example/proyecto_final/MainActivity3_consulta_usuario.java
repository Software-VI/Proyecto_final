package com.example.proyecto_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3_consulta_usuario extends AppCompatActivity {
     Button btn_backto_consulta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_consulta_usuario);

        btn_backto_consulta = findViewById(R.id.btn_volver_consulta);

        btn_backto_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_volverto_consulta = new Intent(MainActivity3_consulta_usuario.this, MainActivity2_menu_usuario.class);
                startActivity(intent_volverto_consulta);
            }
        });

    }
}