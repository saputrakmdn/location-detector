package com.example.presensipegawai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.presensipegawai.API.API;
import com.example.presensipegawai.API.APIUtility;
import com.example.presensipegawai.Models.IzinKeluar;
import com.example.presensipegawai.SharePref.SharPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class izin_keluar extends AppCompatActivity {
    private Button btnKirim;
    private SharPref sharPref;
    private API apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izin_keluar);
        btnKirim = findViewById(R.id.btn_kirim);
        sharPref = new SharPref(this);
        apiService = APIUtility.getAPI();
        TextView keterangan = findViewById(R.id.keterangan);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kt = keterangan.getText().toString();
               apiService.izinKeluar("Bearer "+sharPref.getTokenApi(), sharPref.getIdPresensi(), kt).enqueue(new Callback<IzinKeluar>() {
                   @Override
                   public void onResponse(Call<IzinKeluar> call, Response<IzinKeluar> response) {
                       Toast.makeText(izin_keluar.this, "izin Keluar sukkes", Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onFailure(Call<IzinKeluar> call, Throwable t) {

                   }
               });
            }
        });
    }
}