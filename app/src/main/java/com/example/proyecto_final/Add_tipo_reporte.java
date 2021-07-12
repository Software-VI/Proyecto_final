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

public class Add_tipo_reporte extends AppCompatActivity {

    private EditText et_cod_tipo_reporte, et_descripcion;
    Button btn_add, btn_volver, btn_consultar, btn_update;
    ProgressDialog progreso;
    RequestQueue queue;
    JsonObjectRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tipo_reporte);

        et_cod_tipo_reporte = findViewById(R.id.edt_add_tipo_reporte_codreporte);
        et_descripcion = findViewById(R.id.edt_add_tipo_reporte_descripcion);

        btn_add = findViewById(R.id.btn_add_tipo_reporte);
        btn_consultar = findViewById(R.id.btn_consultar_tipo_reporte);
        btn_update = findViewById(R.id.btn_editar_tipo_reporte);

        queue = Volley.newRequestQueue(this);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServiceRegistrarTipoReporte();
            }
        });

        btn_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServiceConsultarTipoReporte();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_cod_tipo_reporte.getText().toString().isEmpty()||
                        et_descripcion.getText().toString().isEmpty()){
                    Toast.makeText(Add_tipo_reporte.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                }else {
                    webServiceEditarTipoReporte();
                }
            }
        });
    }

    private void webServiceEditarTipoReporte() {
        btn_consultar.setVisibility(View.GONE);
        btn_update.setVisibility(View.GONE);
        btn_add.setVisibility(View.GONE);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Actualizando datos...");
        progreso.show();

        String url = "http://192.168.0.112:8012/appCasos/update_tipo_reporte.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_add.setVisibility(View.VISIBLE);
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
                        Toast.makeText(Add_tipo_reporte.this, "¡Datos Actualizados!", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(Add_tipo_reporte.this, "Error, Datos no Actualizados", Toast.LENGTH_SHORT).show();
                    }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_add.setVisibility(View.VISIBLE);
                Toast.makeText(Add_tipo_reporte.this, "ERROR 0:"+error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cod_tipo_reporte", et_cod_tipo_reporte.getText().toString());
                params.put("descripcion", et_descripcion.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void webServiceConsultarTipoReporte() {
        btn_consultar.setVisibility(View.GONE);
        btn_add.setVisibility(View.GONE);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Buscando...");
        progreso.show();

        String cod_tipo_reporte = et_cod_tipo_reporte.getText().toString();

        String url = "http://192.168.0.112:8012/appCasos/consulta.php?type=2&cod_tipo_reporte="+cod_tipo_reporte;

        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_add.setVisibility(View.VISIBLE);
                progreso.hide();
                Toast.makeText(Add_tipo_reporte.this, "Tipo Reporte Encontrado", Toast.LENGTH_SHORT).show();

                try {

                    JSONArray jsonArray = response.getJSONArray("set");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    et_cod_tipo_reporte.setText(jsonObject.getString("cod_tipo_reporte"));
                    et_descripcion.setText(jsonObject.getString("descripcion"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Add_tipo_reporte.this, e.toString(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btn_consultar.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.VISIBLE);
                btn_add.setVisibility(View.VISIBLE);
                progreso.hide();

                et_cod_tipo_reporte.setText("");
                Toast.makeText(Add_tipo_reporte.this, "Tipo De Reporte No Encontrado\n"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }


    private void webServiceRegistrarTipoReporte(){
        btn_add.setVisibility(View.GONE);
        btn_consultar.setVisibility(View.GONE);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Registrando...");
        progreso.show();

        String url = "http://192.168.0.112:8012/appCasos/Registro_tipo_reporte.php?cod_tipo_reporte="+ et_cod_tipo_reporte.getText().toString()+
                "&descripcion="+et_descripcion.getText().toString();

        url.replace(" ", "%20");
        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                btn_add.setVisibility(View.VISIBLE);
                btn_consultar.setVisibility(View.VISIBLE);
                progreso.hide();
                Toast.makeText(Add_tipo_reporte.this, "Reporte Resgistrado Correctamente", Toast.LENGTH_LONG).show();
                et_cod_tipo_reporte.setText("");
                et_descripcion.setText("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btn_add.setVisibility(View.VISIBLE);
                btn_consultar.setVisibility(View.VISIBLE);
                progreso.hide();
                Toast.makeText(Add_tipo_reporte.this, "¡Error al registrar Reporte!"+error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("ERROR",error.toString());
            }
        });
        queue.add(request);
    }

    public void atras(View view){
        Intent i = new Intent(Add_tipo_reporte.this, Menu_admin.class);
        startActivity(i);
    }

}