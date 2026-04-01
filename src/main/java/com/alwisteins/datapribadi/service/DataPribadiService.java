package com.alwisteins.datapribadi.service;

import com.alwisteins.datapribadi.dto.DataPribadiRequestDTO;
import com.alwisteins.datapribadi.dto.DataPribadiResponseDTO;
import com.alwisteins.datapribadi.entity.DataPribadi;
import com.alwisteins.datapribadi.repository.DataPribadiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;

@Service
public class DataPribadiService {

    private final DataPribadiRepository repository;

    public DataPribadiService(DataPribadiRepository repository) {
        this.repository = repository;
    }

    public Page<DataPribadiResponseDTO> getAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(this::toResponseDTO);
    }

    public Page<DataPribadiResponseDTO> search(String keyword, int page, int size) {
        return repository.searchByNikOrNama(keyword, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(this::toResponseDTO);
    }

    public DataPribadiResponseDTO getById(Long id) {
        return toResponseDTO(repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data tidak ditemukan")));
    }

    public DataPribadiResponseDTO create(DataPribadiRequestDTO dto) {
        if (repository.existsByNik(dto.getNik())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NIK sudah terdaftar");
        }
        return toResponseDTO(repository.save(toEntity(dto)));
    }

    public DataPribadiResponseDTO update(Long id, DataPribadiRequestDTO dto) {
        DataPribadi entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data tidak ditemukan"));

        if (!entity.getNik().equals(dto.getNik()) && repository.existsByNik(dto.getNik())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NIK sudah terdaftar");
        }

        entity.setNik(dto.getNik());
        entity.setNamaLengkap(dto.getNamaLengkap());
        entity.setJenisKelamin(parseJenisKelamin(dto.getJenisKelamin()));
        entity.setTanggalLahir(dto.getTanggalLahir());
        entity.setAlamat(dto.getAlamat());
        entity.setNegara(dto.getNegara());
        return toResponseDTO(repository.save(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data tidak ditemukan");
        }
        repository.deleteById(id);
    }

    private DataPribadiResponseDTO toResponseDTO(DataPribadi entity) {
        DataPribadiResponseDTO dto = new DataPribadiResponseDTO();
        dto.setId(entity.getId());
        dto.setNik(entity.getNik());
        dto.setNamaLengkap(entity.getNamaLengkap());
        dto.setJenisKelamin(toDisplayJenisKelamin(entity.getJenisKelamin()));
        dto.setTanggalLahir(entity.getTanggalLahir());
        dto.setAlamat(entity.getAlamat());
        dto.setNegara(entity.getNegara());
        dto.setUmur(Period.between(entity.getTanggalLahir(), LocalDate.now()).getYears());
        return dto;
    }

    private DataPribadi toEntity(DataPribadiRequestDTO dto) {
        DataPribadi entity = new DataPribadi();
        entity.setNik(dto.getNik());
        entity.setNamaLengkap(dto.getNamaLengkap());
        entity.setJenisKelamin(parseJenisKelamin(dto.getJenisKelamin()));
        entity.setTanggalLahir(dto.getTanggalLahir());
        entity.setAlamat(dto.getAlamat());
        entity.setNegara(dto.getNegara());
        return entity;
    }

    private DataPribadi.JenisKelamin parseJenisKelamin(String value) {
        if ("Laki-laki".equalsIgnoreCase(value)) return DataPribadi.JenisKelamin.LAKI_LAKI;
        if ("Perempuan".equalsIgnoreCase(value)) return DataPribadi.JenisKelamin.PEREMPUAN;
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jenis Kelamin tidak valid");
    }

    private String toDisplayJenisKelamin(DataPribadi.JenisKelamin jk) {
        return jk == DataPribadi.JenisKelamin.LAKI_LAKI ? "Laki-laki" : "Perempuan";
    }
}
