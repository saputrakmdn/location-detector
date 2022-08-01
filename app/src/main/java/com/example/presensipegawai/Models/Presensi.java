package com.example.presensipegawai.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Presensi {
    @SerializedName("id_pegawai")
    @Expose
    private int id_pegawai;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("jam_masuk")
    @Expose
    private String jamMasuk;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longtitude")
    @Expose
    private String longtitude;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("id_presensi")
    @Expose
    private int id_presensi;
    @SerializedName("message")
    @Expose
    private String message;

    public void setIdPegawai(int id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public int getIdPegawai() {
        return id_pegawai;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setJamMasuk(String jamMasuk) {
        this.jamMasuk = jamMasuk;
    }

    public String getJamMasuk() {
        return jamMasuk;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setIdPresensi(int id_presensi) {
        this.id_presensi = id_presensi;
    }

    public int getIdPresensi() {
        return id_presensi;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
