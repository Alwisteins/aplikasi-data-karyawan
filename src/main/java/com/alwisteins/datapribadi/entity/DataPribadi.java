package com.alwisteins.datapribadi.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "data_pribadi")
public class DataPribadi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long nik;

    @Column(name = "nama_lengkap", nullable = false)
    private String namaLengkap;

    public enum JenisKelamin {
        LAKI_LAKI, PEREMPUAN
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "jenis_kelamin", nullable = false)
    private JenisKelamin jenisKelamin;

    @Column(name = "tanggal_lahir", nullable = false)
    private LocalDate tanggalLahir;

    @Column(columnDefinition = "TEXT")
    private String alamat;

    private String negara;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getNik() { return nik; }
    public void setNik(Long nik) { this.nik = nik; }

    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }

    public JenisKelamin getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(JenisKelamin jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public LocalDate getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(LocalDate tanggalLahir) { this.tanggalLahir = tanggalLahir; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getNegara() { return negara; }
    public void setNegara(String negara) { this.negara = negara; }
}
