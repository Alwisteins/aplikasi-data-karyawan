package com.alwisteins.datapribadi.dto;

import java.time.LocalDate;

public class DataPribadiResponseDTO {

    private Long id;
    private Long nik;
    private String namaLengkap;
    private String jenisKelamin;
    private LocalDate tanggalLahir;
    private String alamat;
    private String negara;
    private Integer umur;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Integer getUmur() { return umur; }
    public void setUmur(Integer umur) { this.umur = umur; }
}
