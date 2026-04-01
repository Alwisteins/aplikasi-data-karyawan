package com.alwisteins.datapribadi.controller;

import com.alwisteins.datapribadi.dto.DataPribadiRequestDTO;
import com.alwisteins.datapribadi.dto.DataPribadiResponseDTO;
import com.alwisteins.datapribadi.service.DataPribadiService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/karyawan")
@CrossOrigin(origins = "*")
public class DataPribadiController {

    private final DataPribadiService service;

    public DataPribadiController(DataPribadiService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<DataPribadiResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAll(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DataPribadiResponseDTO>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.search(keyword, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataPribadiResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<DataPribadiResponseDTO> create(@Valid @RequestBody DataPribadiRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataPribadiResponseDTO> update(@PathVariable Long id, @Valid @RequestBody DataPribadiRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(Map.of("message", "Data berhasil dihapus"));
    }
}
