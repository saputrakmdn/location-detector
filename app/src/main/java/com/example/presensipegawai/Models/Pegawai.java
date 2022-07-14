package com.example.presensipegawai.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pegawai {
    @SerializedName("id_pegawai")
    @Expose
    private Integer id_pegawai;
    @SerializedName("nip")
    @Expose
    private Integer nip;
    @SerializedName("nama_pegawai")
    @Expose
    private String nama_pegawai;
    @SerializedName("jenis_kelamin")
    @Expose
    private Integer jenis_kelamin;
    @SerializedName("tempat_lahir")
    @Expose
    private String tempat_lahir;
    @SerializedName("tanggal_lahir")
    @Expose
    private String tanggal_lahir;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("username")
    @Expose
    private String username;

    public Integer getIdPegawai(){
        return id_pegawai;
    }

    public void setIdPegawai(Integer idPegawai){
        this.id_pegawai = id_pegawai;
    }

    public Integer getNip(){
        return nip;
    }

    public void setNip(Integer nip){
        this.nip = nip;
    }

    public String getNamaPegawai(){
        return nama_pegawai;
    }

    public void setNamaPegawai(String nama){
        this.nama_pegawai = nama;
    }

    public Integer getJenisKelamin(){
        return jenis_kelamin;
    }

    public void setJenisKelamin(Integer jenis_kelamin){
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getTempatLahir(){
        return tempat_lahir;
    }

    public void setTempatLahir(String tempat_lahir){
        this.tempat_lahir = tempat_lahir;
    }

    public String getTanggalLahir(){
        return tanggal_lahir;
    }

    public void setTanggalahir(String tanggal_lahir){
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
