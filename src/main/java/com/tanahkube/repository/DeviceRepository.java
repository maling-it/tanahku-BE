package com.tanahkube.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tanahkube.entity.Device;


@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>{
    
    @Query(value = "SELECT * FROM device WHERE is_delete = false AND id = ?1", nativeQuery = true)
    Optional<Device> findById(Long id);

    @Query(value = "SELECT * FROM device WHERE is_delete = false", nativeQuery = true)
    Page<Device> findAllDataPageble(Pageable pageable);

    @Query(value = "SELECT * FROM device WHERE is_delete = false", nativeQuery = true)
    List<Device> findAllData();

    @Query(value = "SELECT * \n" + //
                "FROM device \n" + //
                "WHERE is_delete = false \n" + //
                "AND name LIKE concat('%', ?1, '%') or location LIKE concat('%', ?1, '%')", nativeQuery = true)
    Page<Device> findAllDataBySearch(Pageable pageable, String search);
}
