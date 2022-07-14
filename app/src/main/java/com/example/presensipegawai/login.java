package com.example.presensipegawai;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.presensipegawai.API.API;
import com.example.presensipegawai.API.APIUtility;
import com.example.presensipegawai.Models.TokenAPI;
import com.example.presensipegawai.Services.GPSService;
import com.example.presensipegawai.Services.Utils;
import com.example.presensipegawai.SharePref.SharPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    EditText Username, Password;
    Button Btnlogin;
    private API apiService;
    private SharPref sharPref;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharPref = new SharPref(this);

        Username = findViewById(R.id.et_username);
        Password = findViewById(R.id.et_password);
        Btnlogin = findViewById(R.id.btn_login);
        apiService = APIUtility.getAPI();
        if(!sharPref.getTokenApi().isEmpty()){
            Intent dashboard = new Intent(login.this, com.example.presensipegawai.dashboard.class);
            startActivity(dashboard);
        }

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
                apiService.login(email, password, "android").enqueue(new Callback<TokenAPI>() {
                    @Override
                    public void onResponse(Call<TokenAPI> call, Response<TokenAPI> response) {
                        if(response.body().getCode() == 404){
                            Username.setError("Username Salah");
                            Password.setError("Password salah");
                        }

                        if (response.body().getCode() == 200){
                            sharPref.setTokenApi(response.body().getAccessToken());
                            Log.i("token", response.body().getAccessToken());
                            Intent dashboard = new Intent(login.this, com.example.presensipegawai.dashboard.class);
                            startActivity(dashboard);
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenAPI> call, Throwable t) {
                        Log.i("error android", t.toString());
                    }
                });
            }
        });

    }
}
