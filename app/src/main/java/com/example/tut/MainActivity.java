package com.example.tut;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    ImageView vista;
    RequestQueue requestQueue;
    EditText textestacion, textph,textORP,textTDS,textturb, textPotable;
    TextView saludo, estac;
    ArrayList<Medicion> medicion ;
    Medicion med = new Medicion(0.0, 785.3, 542.0, 2.63, 1, "b", "POTABLE");
    Switch miswitch,cambioletra;
    Button botonmedir;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view=this.getWindow().getDecorView();
        view.setBackgroundResource(R.color.white);
        saludo=(TextView) findViewById(R.id.saludo);
        saludo.setText("Bienvenido/a, " + Login.getNombre() + " !");

        textestacion=(EditText) findViewById(R.id.editTextTextPersonName);
        estac = (TextView)findViewById(R.id.Estacion);

        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        textph=(EditText) findViewById(R.id.pH);
        textORP=(EditText) findViewById(R.id.ORP);
        textTDS=(EditText) findViewById(R.id.TDS);
        textturb=(EditText) findViewById(R.id.Turb);
        miswitch=(Switch)findViewById(R.id.CambioModo);
        botonmedir=(Button)findViewById(R.id.button);

        botonmedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String estacion, valorph, valorORP, valorTDS, valorturb;
                valorph = String.valueOf(textph.getText());
                valorORP = String.valueOf(textORP.getText());
                valorTDS = String.valueOf(textTDS.getText());
                valorturb = String.valueOf(textturb.getText());
                estacion = String.valueOf(textestacion.getText());
                if(!estacion.equals("") && !valorph.equals("") && !valorORP.equals("") && !valorTDS.equals("") && !valorturb.equals("")) {
                    int esta;
                    esta = Integer.parseInt(estacion);
                    if(esta>0 && esta<99){
                        med("http://192.168.1.48:8080/borja/buscar.php?codigo="+esta);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "El id de la estacion introducida no existe", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(), "El campo no debe estar vacio", Toast.LENGTH_SHORT).show();
                }

            }
        });

        miswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(miswitch.isChecked()){
                    view.setBackgroundResource(R.color.black);
                    textph.setTextColor(Color.rgb(255,255,255));
                    textORP.setTextColor(Color.rgb(255,255,255));
                    textTDS.setTextColor(Color.rgb(255,255,255));
                    textturb.setTextColor(Color.rgb(255,255,255));
                    miswitch.setTextColor(Color.rgb(255,255,255));
                    saludo.setTextColor(Color.rgb(255,255,255));
                    cambioletra.setTextColor(Color.rgb(255,255,255));
                    textestacion.setTextColor(Color.rgb(255,255,255));
                    estac.setTextColor(Color.rgb(255,255,255));
                }
                else{
                    view.setBackgroundResource(R.color.white);
                    textph.setTextColor(Color.rgb(0,0,0));
                    textORP.setTextColor(Color.rgb(0,0,0));
                    textTDS.setTextColor(Color.rgb(0,0,0));
                    textturb.setTextColor(Color.rgb(0,0,0));
                    miswitch.setTextColor(Color.rgb(0,0,0));
                    saludo.setTextColor(Color.rgb(0,0,0));
                    cambioletra.setTextColor(Color.rgb(0,0,0));
                    textestacion.setTextColor(Color.rgb(0,0,0));
                    estac.setTextColor(Color.rgb(0,0,0));
                }
        };

    });

        cambioletra=(Switch)findViewById(R.id.CambioLetra);

        cambioletra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cambioletra.isChecked()){
                    textph.setTextSize(30);
                    textORP.setTextSize(30);
                    textTDS.setTextSize(30);
                    textturb.setTextSize(30);
                    saludo.setTextSize(30);
                    textestacion.setTextSize(30);
                    estac.setTextSize(30);

                }
                else{
                    textph.setTextSize(20);
                    textORP.setTextSize(20);
                    textTDS.setTextSize(20);
                    textturb.setTextSize(20);
                    saludo.setTextSize(20);
                    textestacion.setTextSize(20);
                    estac.setTextSize(20);
                }
            }
        });
        /*savedInstanceState.getString("count");
        textturb.setText(String.valueOf(textturb));*/
    }

    private void med(String URL) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                medicion = new ArrayList<Medicion>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        //med = new Medicion(Double.parseDouble(jsonObject.getString("ph")),Double.parseDouble(jsonObject.getString("ORP")),Double.parseDouble(jsonObject.getString("TDS")),Double.parseDouble(jsonObject.getString("TDS")), Integer.parseInt(jsonObject.getString("Numero")));
                        //med.setNumero_estacion(Integer.parseInt(jsonObject.getString("Numero")));
                        //med.setPh(Double.parseDouble(jsonObject.getString("ph")));
                        //med.setORP(Double.parseDouble(jsonObject.getString("ORP")));
                        //med.setTDS(Double.parseDouble(jsonObject.getString("TDS")));
                        //med.setTurbidez(Double.parseDouble(jsonObject.getString("TDS")));
                        medicion.add(med);

                        /*Log.println(Log.WARN,"MyActivity", Integer.toString(i));
                        if (i == 0){
                            Log.println(Log.WARN,"MyActivity", Double.toString(medicion.get(0).getORP()));
                        }
                        else {
                            Log.println(Log.WARN, "MyActivity", Double.toString(medicion.get(0).getORP()));
                            Log.println(Log.WARN, "MyActivity", Double.toString(medicion.get(1).getORP()));
                        }
                    */
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

               /* textph.setText("PH: " + medicion.get(0).getPh());
                textORP.setText("ORP: " + medicion.get(0).getORP());
                textTDS.setText("TDS: " + medicion.get(0).getTDS());
                textturb.setText("Turbidez: " + medicion.get(0).getTurbidez());*/
                textPotable.setText("POTABLE");
                if(textPotable.getText().equals("POTABLE")){
                    textPotable.setText("       Potable");
                    textPotable.setBackgroundColor(Color.rgb(0,200,0));
                }
                else{
                    textPotable.setText("       Peligroso");
                    textPotable.setBackgroundColor(Color.rgb(200,0,0));

                    SmsManager smsManager = SmsManager.getDefault();
                    //smsManager.sendTextMessage("+34690906436", null, "Valores del agua peligrosos en la estacion numero "+String.valueOf(textestacion.getText()),null, null);


                }
                textPotable.setTextColor(Color.rgb(0,0,0));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
        requestQueue.start();//Comentario//
    }

}