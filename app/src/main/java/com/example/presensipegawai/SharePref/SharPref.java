package com.example.presensipegawai.SharePref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharPref {
    Context mContext;
    SharedPreferences sharedPreferences;
    private String token = "TOKEN_API";
    private String id_presensi = "ID_PRESENSI";

    public SharPref(Context context){
        this.mContext = context;
        this.sharedPreferences = mContext.getSharedPreferences("absensi", Context.MODE_PRIVATE);
    }

    public void setTokenApi(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(this.token, token);
        editor.apply();
    }

    public String getTokenApi(){
        return sharedPreferences.getString(this.token, "");
    }

    public void setIdPresensi(Integer id_presensi) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(this.id_presensi, id_presensi);
        editor.apply();
    }

    public Integer getIdPresensi(){
        return sharedPreferences.getInt(this.id_presensi, 0);
    }
}
