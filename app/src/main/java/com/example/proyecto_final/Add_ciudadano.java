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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Add_ciudadano extends AppCompatActivity {

    private EditText et_cedula, et_nombre, et_apellido, et_direccion;
    Button btn_registrar, btn_consultar, btn_update;
    ProgressDialog progreso;
    RequestQueue queue;
    JsonObjectRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ciudadano);

        queue = Volley.newRequestQueue(this);
        et_cedula = findViewById(R.id.edt_cedula_ciudadano);
        et_nombre = findViewById(R.id.edt_nombre_ciudadano);
        et_apellido = findViewById(R.id.edt_apellido_ciudadano);
        et_direccion = findViewById(R.id.edt_descripcion_ciudadano);
        btn_consultar = (Button)findViewById(R.id.btn_buscar_ciudadano);
        btn_registrar = (Button)findViewById(R.id.btn_add_ciudadano);
        btn_update = (Button)findViewById(R.id.btn_modificar_ciudadano);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServiceRegistrarCiudadano();
            }
        });

        btn_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServiceBuscarCiudadano();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_cedula.getText().toString().isEmpty()||
                        et_nombre.getText().toString().isEmpty()||
                        et_apellido.getText().toString().isEmpty()||
                        et_direccion.getText().toString().isEmpty()){
                    Toast.makeText(Add_ciudadano.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                }else {
                    webServiceEditarCiudadano();
                }
            }
        });

    }

    private void webServiceEditarCiudadano() {
        btn_update.setVisibility(View.GONE);
        btn_registrar.setVisibility(View.GONE);
        btn_consultar.setVisibility(View.GONE);
        progreso.setMessage("Actualizando...");
        progreso.show();

        String url = "http://192.168.0.112:8012/appCasos/update_ciudadano.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_registrar.setVisibility(View.VISIBLE);
                progreso.hide();
                String respuesta = "";
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response);
                    respuesta = jsonObject.getString("res");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (respuesta.equals("success")){
                    Toast.makeText(Add_ciudadano.this, "¡Datos Actualizados!", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(Add_ciudadano.this, "Datos no Actualizados" + response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_registrar.setVisibility(View.VISIBLE);
                Toast.makeText(Add_ciudadano.this, "ERROR 0:"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cedula", et_cedula.getText().toString());
                params.put("nombre", et_nombre.getText().toString());
                params.put("apellido", et_apellido.getText().toString());
                params.put("direccion", et_direccion.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void webServiceBuscarCiudadano() {
        btn_consultar.setVisibility(View.GONE);
        btn_registrar.setVisibility(View.GONE);
        btn_update.setVisibility(View.GONE);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Buscando...");
        progreso.show();
        String cedula = et_cedula.getText().toString();
        String url = "http://192.168.0.112:8012/appCasos/consulta.php?type=1&cedula="+cedula;
        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_registrar.setVisibility(View.VISIBLE);
                progreso.hide();
                Toast.makeText(Add_ciudadano.this, "Ciudadano Encontrado", Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = response.getJSONArray("set");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    et_nombre.setText(jsonObject.getString("nombre"));
                    et_apellido.setText(jsonObject.getString("apellido"));
                    et_direccion.setText(jsonObject.getString("direccion"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Add_ciudadano.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_registrar.setVisibility(View.VISIBLE);
                progreso.hide();
                et_cedula.setText("");
                Toast.makeText(Add_ciudadano.this, "Usuario No Encontrado\n"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    private void webServiceRegistrarCiudadano() {
        btn_registrar.setVisibility(View.GONE);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Registrando...");
        progreso.show();
        String url = "http://192.168.0.112:8012/appCasos/Registro_ciudadano.php?cedula="+ et_cedula.getText().toString()+
                "&nombre="+et_nombre.getText().toString()+
                "&apellido="+et_apellido.getText().toString()+
                "&direccion="+et_direccion.getText().toString();
        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                btn_registrar.setVisibility(View.VISIBLE);
                et_nombre.setText("");
                et_cedula.setText("");
                et_apellido.setText("");
                et_direccion.setText("");
                Toast.makeText(Add_ciudadano.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                btn_registrar.setVisibility(View.VISIBLE);
                Toast.makeText(Add_ciudadano.this, "Error de Registro"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    public void atrasAddCiudadano(View view) {
        Intent i = new Intent(Add_ciudadano.this, Menu_usuario.class);
        startActivity(i);
    }
}