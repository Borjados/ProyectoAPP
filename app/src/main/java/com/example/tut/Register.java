package com.example.tut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Register extends AppCompatActivity {

    TextView TextFullname, TextUsername, TextPassword, TextEmail;
    Button botonRegister;
    TextView clickAqui;
    //TextView textViewRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextFullname = findViewById(R.id.Fullname);
        TextUsername = findViewById(R.id.Username);
        TextPassword = findViewById(R.id.PasswordReg);
        TextEmail = findViewById(R.id.Email);
        botonRegister = findViewById(R.id.Register);
        clickAqui = findViewById(R.id.ClickReg);

        clickAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });


        botonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname, username, password, email;
                fullname = String.valueOf(TextFullname.getText());
                username = String.valueOf(TextUsername.getText());
                password = String.valueOf(TextPassword.getText());
                email = String.valueOf(TextEmail.getText());
                if (!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("")) {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "email";

                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = password;
                            data[3] = email;
                            PutData putdata = new PutData("http://192.168.1.48:8080/borja/signup.php", "POST", field, data);
                            if (putdata.startPut()) {
                                if (putdata.onComplete()) {
                                    String result = putdata.getResult();
                                    if(result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
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
                    Toast.makeText(getApplicationContext(), "Ningún campo debe estar vacioA", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


}