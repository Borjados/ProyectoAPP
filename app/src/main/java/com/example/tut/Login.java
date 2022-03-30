package com.example.tut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {

    TextView TextUsername1, TextPassword1;
    Button botonLogin;
    TextView clickLogin;
    private static String nombre;

    public static String getNombre() {
        return nombre;
    }

    public static void setNombre(String nombre) {
        Login.nombre = nombre;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextUsername1 = findViewById(R.id.LoginUser);
        TextPassword1 = findViewById(R.id.PassLogin);
        botonLogin = findViewById(R.id.Login);
        clickLogin = findViewById(R.id.ClickLog);

        clickLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username1, password1;


                username1 = String.valueOf(TextUsername1.getText());
                password1 = String.valueOf(TextPassword1.getText());

                if (!username1.equals("") && !password1.equals("")) {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];

                            field[0] = "username";
                            field[1] = "password";


                            String[] data = new String[2];

                            data[0] = username1;
                            data[1] = password1;

                            PutData putdata = new PutData("http://192.168.1.48:8080/borja/login.php", "POST", field, data);
                            if (putdata.startPut()) {
                                if (putdata.onComplete()) {
                                    String result = putdata.getResult();
                                    if(result.equals("Login Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        setNombre(username1);
                                        Intent intent = new Intent(getApplicationContext(), Menu.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "Ningún campo debe estar vacio", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}