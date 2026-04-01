package com.alwisteins.datapribadi.repository;

import com.alwisteins.datapribadi.entity.DataPribadi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DataPribadiRepository extends JpaRepository<DataPribadi, Long> {

    boolean existsByNik(Long nik);

    Optional<DataPribadi> findByNik(Long nik);

    @Query("SELECT d FROM DataPribadi d WHERE LOWER(d.namaLengkap) LIKE LOWER(CONCAT('%', :keyword, '%')) OR CAST(d.nik AS string) LIKE CONCAT('%', :keyword, '%')")
    Page<DataPribadi> searchByNikOrNama(@Param("keyword") String keyword, Pageable pageable);
}
