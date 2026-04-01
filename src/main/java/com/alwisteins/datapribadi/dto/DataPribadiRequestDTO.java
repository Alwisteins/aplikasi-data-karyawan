package com.alwisteins.datapribadi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public class DataPribadiRequestDTO {

    @NotNull(message = "NIK tidak boleh kosong")
    private Long nik;

    @NotBlank(message = "Nama Lengkap tidak boleh kosong")
    private String namaLengkap;

    @NotBlank(message = "Jenis Kelamin tidak boleh kosong")
    private String jenisKelamin;

    @NotNull(message = "Tanggal Lahir tidak boleh kosong")
    @PastOrPresent(message = "Tanggal Lahir tidak boleh di masa depan")
    private LocalDate tanggalLahir;

    private String alamat;
    private String negara;

    public Long getNik() { return nik; }
    public void setNik(Long nik) { this.nik = nik; }

    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }

    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public LocalDate getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(LocalDate tanggalLahir) { this.tanggalLahir = tanggalLahir; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getNegara() { return negara; }
    public void setNegara(String negara) { this.negara = negara; }
}
