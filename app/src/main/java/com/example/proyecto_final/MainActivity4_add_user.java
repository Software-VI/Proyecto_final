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


public class MainActivity4_add_user extends AppCompatActivity {

    private EditText et_cod_unidad, et_username, et_nombre, et_apellido, et_password, et_email, et_telefono, et_rango;
    Button btn_add, btn_delete, btn_consultar, btn_update;
    ProgressDialog progreso;
    RequestQueue queue;
    JsonObjectRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity4_add_user);

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
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
     }

    private void cargarWebService() {
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
                progreso.hide();
                Toast.makeText(MainActivity4_add_user.this, "Usuario Resgistrado Correctamente", Toast.LENGTH_SHORT).show();
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
                progreso.hide();
                Toast.makeText(MainActivity4_add_user.this, "¡Error al registrar usuario!"+error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("ERROR",error.toString());
            }
        });
        queue.add(request);
    }
    /*@Override
    public void onErrorResponse(VolleyError error) {

        progreso.hide();
        Toast.makeText(this, "¡Error al registrar usuario!"+error.toString(), Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        Toast.makeText(this, "Usuario Resgistrado Correctamente", Toast.LENGTH_SHORT).show();
        et_cod_unidad.setText("");
        et_username.setText("");
        et_nombre.setText("");
        et_apellido.setText("");
        et_password.setText("");
        et_email.setText("");
        et_telefono.setText("");
        et_rango.setText("");
    }*/
}