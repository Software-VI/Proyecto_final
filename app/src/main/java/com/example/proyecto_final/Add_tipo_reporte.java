package com.example.proyecto_final;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Add_tipo_reporte extends AppCompatActivity {

    private EditText et_cod_reporte, et_descripcion;
    Button btn_add, btn_volver;
    ProgressDialog progreso;
    RequestQueue queue;
    JsonObjectRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tipo_reporte);

        et_cod_reporte = findViewById(R.id.edt_add_tipo_reporte_codreporte);
        et_descripcion = findViewById(R.id.edt_add_tipo_reporte_descripcion);
        btn_add = findViewById(R.id.btn_add_tipo_reporte);

        queue = Volley.newRequestQueue(this);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
    }

    private void cargarWebService(){

        progreso = new ProgressDialog(this);
        progreso.setMessage("Registrando...");
        progreso.show();

        String url = "http://192.168.0.7/appCasos/Registro_tipo_reporte.php?cod_tipo_reporte="+ et_cod_reporte.getText().toString()+
                "&descripcion="+et_descripcion.getText().toString();

        url.replace(" ", "%20");
        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Toast.makeText(Add_tipo_reporte.this, "Reporte Resgistrado Correctamente", Toast.LENGTH_LONG).show();
                et_cod_reporte.setText("");
                et_descripcion.setText("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(Add_tipo_reporte.this, "¡Error al registrar Reporte!"+error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("ERROR",error.toString());
            }
        });
        queue.add(request);
    }
}