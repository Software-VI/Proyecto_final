package com.example.proyecto_final;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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


public class Add_user extends AppCompatActivity {

    private EditText et_cod_unidad, et_username, et_nombre, et_apellido, et_password, et_email, et_telefono, et_rango;
    Button btn_add, btn_llamada, btn_consultar, btn_update;
    ProgressDialog progreso;
    RequestQueue queue;
    JsonObjectRequest request;
    private String aux;

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
        btn_llamada = (Button)findViewById(R.id.btn_llamar);

        btn_llamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telefono = et_telefono.getText().toString();
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+telefono));
                startActivity(i);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServiceRegistarUsuario();
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
        btn_llamada.setVisibility(View.GONE);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Actualizando datos...");
        progreso.show();

        String url = "http://192.168.0.112:8012/appCasos/update_usuario.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_add.setVisibility(View.VISIBLE);
                btn_llamada.setVisibility(View.VISIBLE);
                progreso.hide();
                String respuesta = "";
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    respuesta = jsonObject.getString("res");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (respuesta.equals("success")){
                    Toast.makeText(Add_user.this, "??Datos Actualizados!", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(Add_user.this, "Datos no Actualizados" + response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_add.setVisibility(View.VISIBLE);
                btn_llamada.setVisibility(View.VISIBLE);
                Toast.makeText(Add_user.this, "ERROR 0:"+error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //params.put("type", "0");
                if (et_password.getText().toString().equals(aux)) {
                    params.put("cod_unidad", et_cod_unidad.getText().toString());
                    params.put("usuario", et_username.getText().toString());
                    params.put("nombre", et_nombre.getText().toString());
                    params.put("apellido", et_apellido.getText().toString());
                    params.put("email", et_email.getText().toString());
                    params.put("telefono", et_telefono.getText().toString());
                    params.put("rango", et_rango.getText().toString());
                    return params;
                }else {
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
        String cod = et_cod_unidad.getText().toString();

        String url = "http://192.168.0.112:8012/appCasos/consulta.php?type=0&cod_unidad="+cod;

        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_add.setVisibility(View.VISIBLE);
                btn_llamada.setVisibility(View.VISIBLE);
                progreso.hide();
                Toast.makeText(Add_user.this, "Usuario Encontrado", Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = response.getJSONArray("set");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    et_username.setText(jsonObject.getString("usuario"));
                    et_nombre.setText(jsonObject.getString("nombre"));
                    et_apellido.setText(jsonObject.getString("apellido"));
                    et_password.setText(jsonObject.getString("password"));
                    aux = jsonObject.getString("password");
                    et_email.setText(jsonObject.getString("email"));
                    et_telefono.setText(jsonObject.getString("telefono"));
                    et_rango.setText(jsonObject.getString("rango"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Add_user.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_add.setVisibility(View.VISIBLE);
                btn_llamada.setVisibility(View.VISIBLE);
                progreso.hide();
                et_cod_unidad.setText("");
                Toast.makeText(Add_user.this, "Usuario No Encontrado\n"+error.toString(), Toast.LENGTH_SHORT).show();
        }
    });
        queue.add(request);
    }

    private void webServiceRegistarUsuario() {
        btn_add.setVisibility(View.GONE);
        btn_consultar.setVisibility(View.GONE);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Registrando...");
        progreso.show();

        String url = "http://192.168.0.112:8012/appCasos/Registro_usuario.php?cod_unidad="+ et_cod_unidad.getText().toString()+
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
                Toast.makeText(Add_user.this, "??Error al registrar usuario!"+error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("ERROR",error.toString());
            }
        });
        queue.add(request);
    }

    public void atrasAddUser(View view){
        Intent i = new Intent(Add_user.this, Menu_admin.class);
        startActivity(i);
    }
}