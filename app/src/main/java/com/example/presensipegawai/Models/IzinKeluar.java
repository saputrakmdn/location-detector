package com.example.presensipegawai.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IzinKeluar {
    @SerializedName("id_izin_keluar")
    @Expose
    private int id_izin_keluar;
    @SerializedName("id_presensi")
    @Expose
    private int id_presensi;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("status_izin")
    @Expose
    private int status_izin;

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setId_izin_keluar(int id_izin_keluar) {
        this.id_izin_keluar = id_izin_keluar;
    }

    public int getId_izin_keluar() {
        return id_izin_keluar;
    }

    public void setId_presensi(int id_presensi) {
        this.id_presensi = id_presensi;
    }

    public int getId_presensi() {
        return id_presensi;
    }

    public void setStatus_izin(int status_izin) {
        this.status_izin = status_izin;
    }

    public int getStatus_izin() {
        return status_izin;
    }
}
