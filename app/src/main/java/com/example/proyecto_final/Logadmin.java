package com.example.proyecto_final;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Logadmin extends AppCompatActivity {

    Button btn_login_admin, btn_tomain_admin;
    EditText et_cod_seguridad;
    ProgressDialog progreso;


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

                String cod_seguridad = et_cod_seguridad.getText().toString().trim();

                //ponle en comentarios si quieres pasar a la otra vaina del tipo de reporte y add user y se los quitas ps pa probarlo  xd
             /*   if (!cod_seguridad.isEmpty()){
                    login_admin(cod_seguridad);
                }
                else{
                    Toast.makeText(Logadmin.this, "Favor Rellene todos los campos", Toast.LENGTH_SHORT).show();
                }
               */
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

    private void login_admin(String cod_seguridad) {
        progreso = new ProgressDialog(this);
        progreso.setMessage("Validando...");
        progreso.show();
        btn_login_admin.setVisibility(View.GONE);

        String URL = "http://192.168.0.7/appCasos/login_admin.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progreso.hide();


                    JSONObject jsonObject = new JSONObject(response);

                    String success = jsonObject.getString("success");

                    if (success.equals("1")){
                        Toast.makeText(Logadmin.this, "¡Inicio de sesión extioso!", Toast.LENGTH_SHORT).show();


                    }else {
                        Toast.makeText(Logadmin.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progreso.hide();
                    btn_login_admin.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                    Toast.makeText(Logadmin.this, "Error al iniciar sesión:"+e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                btn_login_admin.setVisibility(View.VISIBLE);
                Toast.makeText(Logadmin.this, "ERROR 0:"+error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("codseguridad", cod_seguridad);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}