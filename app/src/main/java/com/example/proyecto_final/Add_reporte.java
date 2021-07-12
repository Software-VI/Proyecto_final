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

public class Add_reporte extends AppCompatActivity {

    private EditText et_cod_reporte, et_cedula, et_fecha, et_tipo_reporte, et_observacion, et_activo;
    Button btn_add, btn_llamada, btn_consultar, btn_update;
    ProgressDialog progreso;
    RequestQueue queue;
    JsonObjectRequest request;
    private String aux;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reporte);

        et_cod_reporte = findViewById(R.id.edt_id_reporte);
        et_cedula = findViewById(R.id.edt_cedula_reporte);
        et_fecha = findViewById(R.id.edt_fecha_reporte);
        et_tipo_reporte = findViewById(R.id.edt_tipo_de_reporte);
        et_observacion = findViewById(R.id.edt_observacion);
        et_activo = findViewById(R.id.edt_activo);

        queue = Volley.newRequestQueue(this);
        btn_add = findViewById(R.id.btn_add_reporte);
        btn_consultar = findViewById(R.id.btn_buscar_reporte);
        btn_update = findViewById(R.id.btn_modificar_reporte);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServiceRegistarReporte();
            }
        });

        btn_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServiceBuscarReporte();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_cod_reporte.getText().toString().isEmpty()||
                        et_cedula.getText().toString().isEmpty()||
                        et_fecha.getText().toString().isEmpty()||
                        et_tipo_reporte.getText().toString().isEmpty()||
                        et_observacion.getText().toString().isEmpty() ||
                        et_activo.getText().toString().isEmpty()){
                    Toast.makeText(Add_reporte.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                }else {
                    webServiceEditarReporte();
                }
            }
        });

    }

    private void webServiceEditarReporte() {
        btn_consultar.setVisibility(View.GONE);
        btn_update.setVisibility(View.GONE);
        btn_add.setVisibility(View.GONE);
        btn_llamada.setVisibility(View.GONE);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Actualizando datos...");
        progreso.show();

        String url = "http://192.168.0.112:8012/appCasos/update_reporte.php";

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
                    Toast.makeText(Add_reporte.this, "¡Datos Actualizados!", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(Add_reporte.this, "Datos no Actualizados" + response, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Add_reporte.this, "ERROR 0:"+error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("cod_reporte", et_cod_reporte.getText().toString());
                params.put("cedula", et_cedula.getText().toString());
                params.put("fecha", et_fecha.getText().toString());
                params.put("tipo_reporte", et_tipo_reporte.getText().toString());
                params.put("observacion", et_observacion.getText().toString());
                params.put("activo", et_activo.getText().toString());

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void webServiceBuscarReporte() {
        btn_consultar.setVisibility(View.GONE);
        btn_add.setVisibility(View.GONE);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Buscando...");
        progreso.show();
        String cod = et_cod_reporte.getText().toString();

        String url = "http://192.168.0.112:8012/appCasos/consulta.php?type=3&cod_reporte="+cod;

        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_add.setVisibility(View.VISIBLE);
                btn_llamada.setVisibility(View.VISIBLE);
                progreso.hide();
                Toast.makeText(Add_reporte.this, "Reporte Encontrado", Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = response.getJSONArray("set");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    et_cedula.setText(jsonObject.getString("cedula"));
                    et_fecha.setText(jsonObject.getString("fecha"));
                    et_tipo_reporte.setText(jsonObject.getString("tipo_reporte"));
                    et_observacion.setText(jsonObject.getString("observacion"));
                    et_activo.setText(jsonObject.getString("activo"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Add_reporte.this, e.toString(), Toast.LENGTH_SHORT).show();
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
                et_cod_reporte.setText("");
                Toast.makeText(Add_reporte.this, "Reporte No Encontrado\n"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);

    }

    private void webServiceRegistarReporte() {
        btn_add.setVisibility(View.GONE);
        btn_consultar.setVisibility(View.GONE);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Registrando...");
        progreso.show();

        String url = "http://192.168.0.112:8012/appCasos/Registro_reporte.php?cod_reporte="+ et_cod_reporte.getText().toString()+
                "&cedula="+et_cedula.getText().toString()+
                "&fecha="+et_fecha.getText().toString()+
                "&tipo_reporte="+et_tipo_reporte.getText().toString()+
                "&observacion="+et_observacion.getText().toString()+
                "&activo="+ et_activo.getText().toString();

        url.replace(" ", "%20");
        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                btn_add.setVisibility(View.VISIBLE);
                btn_consultar.setVisibility(View.VISIBLE);
                progreso.hide();
                Toast.makeText(Add_reporte.this, "Reporte Resgistrado Correctamente", Toast.LENGTH_SHORT).show();
                et_cod_reporte.setText("");
                et_cedula.setText("");
                et_fecha.setText("");
                et_tipo_reporte.setText("");
                et_observacion.setText("");
                et_activo.setText("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btn_add.setVisibility(View.VISIBLE);
                btn_consultar.setVisibility(View.VISIBLE);
                progreso.hide();
                Toast.makeText(Add_reporte.this, "¡Error al registrar Reporte!"+error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("ERROR",error.toString());
            }
        });
        queue.add(request);
    }

    public void atrasAddReporte(View view) {
        Intent i = new Intent(Add_reporte.this, Menu_usuario.class);
        startActivity(i);
    }
}