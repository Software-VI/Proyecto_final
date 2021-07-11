package com.example.proyecto_final;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Add_user extends AppCompatActivity {

    private EditText et_cod_unidad, et_username, et_nombre, et_apellido, et_password, et_email, et_telefono, et_rango;
    Button btn_add, btn_delete, btn_consultar, btn_update;
    ProgressDialog progreso;
    RequestQueue queue;
    JsonObjectRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        et_cod_unidad = findViewById(R.id.edt_cod_unidad);
        et_username = findViewById(R.id.edt_username);
        et_nombre = findViewById(R.id.edt_nombre);
        et_apellido = findViewById(R.id.edt_apellido);
        et_password = findViewById(R.id.edt_contrasena);
        et_email = findViewById(R.id.edt_email);
        et_telefono = findViewById(R.id.edt_telefono);
        et_rango = findViewById(R.id.edt_rango);

        queue = Volley.newRequestQueue(this);
        btn_add = (Button)findViewById(R.id.btn_add_usuario);
        btn_consultar = (Button)findViewById(R.id.btn_buscar_usuario);
        btn_update = (Button)findViewById(R.id.btn_modificar_usuario);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServiceRagistarUsuario();
            }
        });

        btn_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServiceBuscarUsuario();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_cod_unidad.getText().toString().isEmpty()||
                        et_nombre.getText().toString().isEmpty()||
                        et_apellido.getText().toString().isEmpty()||
                        et_password.getText().toString().isEmpty()||
                        et_email.getText().toString().isEmpty()||
                        et_telefono.getText().toString().isEmpty()||
                        et_rango.getText().toString().isEmpty()){
                    Toast.makeText(Add_user.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                }else {
                    webServiceEditarUsuario();
                }
            }
        });
     }

    private void webServiceEditarUsuario() {
        btn_consultar.setVisibility(View.GONE);
        btn_update.setVisibility(View.GONE);
        btn_add.setVisibility(View.GONE);
        btn_delete.setVisibility(View.GONE);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Actualizando datos...");
        progreso.show();

        String url = "http://192.168.0.7/appCasos/consulta.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_add.setVisibility(View.VISIBLE);
                btn_delete.setVisibility(View.VISIBLE);
                try {
                    progreso.hide();
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1")){
                        Toast.makeText(Add_user.this, "¡Datos Actualizados!", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(Add_user.this, Menu_usuario.class);
                        startActivity(login);

                    }else {
                        Toast.makeText(Add_user.this, "Error, Datos no Actualizados", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progreso.hide();
                    e.printStackTrace();
                    Toast.makeText(Add_user.this, "Error, Datos no Actualizados"+e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_add.setVisibility(View.VISIBLE);
                btn_delete.setVisibility(View.VISIBLE);
                Toast.makeText(Add_user.this, "ERROR 0:"+error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cod_unidad", et_cod_unidad.getText().toString());
                params.put("usuario", et_username.getText().toString());
                params.put("nombre", et_nombre.getText().toString());
                params.put("apellido", et_apellido.getText().toString());
                params.put("password", et_password.getText().toString());
                params.put("email", et_email.getText().toString());
                params.put("telefono", et_telefono.getText().toString());
                params.put("rango", et_rango.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void webServiceBuscarUsuario() {
        btn_consultar.setVisibility(View.GONE);
        btn_add.setVisibility(View.GONE);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Buscando...");
        progreso.show();

        String url = "http://192.168.0.7/appCasos/consulta.php?type=0&cod_unidad="+et_cod_unidad.getText().toString();

        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_add.setVisibility(View.VISIBLE);
                btn_delete.setVisibility(View.VISIBLE);
                progreso.hide();
                Toast.makeText(Add_user.this, "Usuario Encontrado", Toast.LENGTH_SHORT).show();

                try {
                    et_username.setText(response.getString("usuario"));
                    et_nombre.setText(response.getString("nombre"));
                    et_apellido.setText(response.getString("apellido"));
                    et_password.setText(response.getString("password"));
                    et_email.setText(response.getString("email"));
                    et_telefono.setText(response.getString("telefono"));
                    et_rango.setText(response.getString("rango"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_add.setVisibility(View.VISIBLE);
                btn_delete.setVisibility(View.VISIBLE);
                progreso.hide();
                Toast.makeText(Add_user.this, "Usuario No Encontrado\n"+error.toString(), Toast.LENGTH_SHORT).show();
        }
    });
        queue.add(request);
    }

    private void webServiceRagistarUsuario() {
        btn_add.setVisibility(View.GONE);
        btn_consultar.setVisibility(View.GONE);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Registrando...");
        progreso.show();

        String url = "http://192.168.0.7/appCasos/Registro_usuario.php?cod_unidad="+ et_cod_unidad.getText().toString()+
                "&usuario="+et_username.getText().toString()+
                "&nombre="+et_nombre.getText().toString()+
                "&apellido="+et_apellido.getText().toString()+
                "&contrasena="+et_password.getText().toString()+
                "&email="+ et_email.getText().toString()+
                "&telefono="+et_telefono.getText().toString()+
                "&rango="+et_rango.getText().toString();

        url.replace(" ", "%20");
        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                btn_add.setVisibility(View.VISIBLE);
                btn_consultar.setVisibility(View.VISIBLE);
                progreso.hide();
                Toast.makeText(Add_user.this, "Usuario Resgistrado Correctamente", Toast.LENGTH_SHORT).show();
                et_cod_unidad.setText("");
                et_username.setText("");
                et_nombre.setText("");
                et_apellido.setText("");
                et_password.setText("");
                et_email.setText("");
                et_telefono.setText("");
                et_rango.setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btn_add.setVisibility(View.VISIBLE);
                btn_consultar.setVisibility(View.VISIBLE);
                progreso.hide();
                Toast.makeText(Add_user.this, "¡Error al registrar usuario!"+error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("ERROR",error.toString());
            }
        });
        queue.add(request);
    }
}