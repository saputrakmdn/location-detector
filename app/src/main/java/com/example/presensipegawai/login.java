package com.example.presensipegawai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class login extends AppCompatActivity {

    EditText Username, Password;
    Button Btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.et_username);
        Password = findViewById(R.id.et_password);
        Btnlogin = findViewById(R.id.btn_login);

        Btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //isi action button

                String email = Username.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (email.isEmpty()){
                    Username.setError("Username Wajib Diisi");
                    return;
                }
                if (password.isEmpty()){
                    Password.setError("Password Wajib Diisi");
                    return;
                }
            }
        });

    }
}