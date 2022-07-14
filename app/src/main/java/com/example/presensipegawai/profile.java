package com.example.presensipegawai;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.presensipegawai.API.API;
import com.example.presensipegawai.API.APIUtility;
import com.example.presensipegawai.Models.Pegawai;
import com.example.presensipegawai.SharePref.SharPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profile extends AppCompatActivity {
    TextView nip, nama, jenis_kelamin, tempat_tanggal_lahir, alamat;
    private SharPref sharPref;
    private API apiService;
    private String jenisKelaminText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharPref = new SharPref(this);
        apiService = APIUtility.getAPI();
        setContentView(R.layout.activity_profil);
        nip = findViewById(R.id.textView1);
        nama = findViewById(R.id.textView2);
        jenis_kelamin = findViewById(R.id.textView3);
        tempat_tanggal_lahir = findViewById(R.id.textView4);
        alamat = findViewById(R.id.textView5);
        apiService.getProfile("Bearer "+sharPref.getTokenApi()).enqueue(new Callback<Pegawai>() {
            @Override
            public void onResponse(Call<Pegawai> call, Response<Pegawai> response) {
                nip.setText(response.body().getNip().toString());
                nama.setText(response.body().getNamaPegawai());
                if(response.body().getJenisKelamin().equals(1))
                    jenisKelaminText = "Laki-laki";
                else
                    jenisKelaminText = "Perempuan";
                jenis_kelamin.setText(jenisKelaminText);
                tempat_tanggal_lahir.setText(response.body().getTempatLahir()+", "+response.body().getTanggalLahir());
                alamat.setText(response.body().getAlamat());
            }

            @Override
            public void onFailure(Call<Pegawai> call, Throwable t) {

            }
        });
    }
}
