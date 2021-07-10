package com.example.proyecto_final;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    ImageButton btn_to_admin;
    Button btn_login_user;
    EditText et_usuario, et_pass;


    ProgressDialog progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_to_admin = findViewById(R.id.to_admin);
        btn_login_user = findViewById(R.id.btn_login_usuario);
        et_pass = findViewById(R.id.edt_login_contrasena);
        et_usuario = findViewById(R.id.edt_login_usuario);

        btn_login_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = et_usuario.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();

                if (!user.isEmpty() && !pass.isEmpty()){
                    login(user, pass);
                }
                else{
                    Toast.makeText(MainActivity.this, "Favor Rellene todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_to_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento_to_admin = new Intent(MainActivity.this, Logadmin.class);
                startActivity(intento_to_admin);

            }
        });
   }
    private void login(String user, String pass) {
        progreso = new ProgressDialog(this);
        progreso.setMessage("Validando...");
        progreso.show();
        btn_login_user.setVisibility(View.GONE);

        String URL = "http://192.168.0.7/appCasos/login.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progreso.hide();
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1")){
                        Toast.makeText(MainActivity.this, "¡Inicio de sesión extioso!", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(MainActivity.this, Menu_usuario.class);
                        startActivity(login);

                    }else {
                        Toast.makeText(MainActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progreso.hide();
                    btn_login_user.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error al iniciar sesión:"+e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                btn_login_user.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "ERROR 0:"+error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("usuario", user);
                params.put("contrasena", pass);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}