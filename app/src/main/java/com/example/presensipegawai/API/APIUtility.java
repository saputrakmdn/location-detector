package com.example.presensipegawai.API;

public class APIUtility {
    public static final String base_url = "http://192.168.149.94:8000";

    public static API getAPI(){
        return RetrofitClient.getClient(base_url).create(API.class);
    }
}
