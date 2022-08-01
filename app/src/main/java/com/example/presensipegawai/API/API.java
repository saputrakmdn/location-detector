package com.example.presensipegawai.API;

import com.example.presensipegawai.Models.Pegawai;
import com.example.presensipegawai.Models.Presensi;
import com.example.presensipegawai.Models.TokenAPI;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {
    @POST("/api/sanctum/token")
    @FormUrlEncoded
    Call<TokenAPI> login(@Field("username") String username, @Field("password") String password, @Field("device_name") String devices_name);
    @POST("/api/presensi-masuk")
    @FormUrlEncoded
    Call<Presensi> absenMasuk(@Header("Authorization") String token, @Field("latitude") double latitude, @Field("longtitude") double longtitude);
    @POST("/api/presensi-pulang")
    @FormUrlEncoded
    Call<Presensi> absenPulang(@Header("Authorization") String token, @Field("latitude") double latitude, @Field("longtitude") double longtitude);
    @GET("/api/user")
    Call<Pegawai> getProfile(@Header("Authorization") String token);
}
