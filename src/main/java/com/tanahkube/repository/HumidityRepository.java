package com.tanahkube.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tanahkube.entity.Humidity;

@Repository
public interface HumidityRepository extends JpaRepository<Humidity, Long>{
    @Query(value = "SELECT * FROM humidity WHERE is_delete = false AND device_id = ?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<Humidity> findLastData(Long deviceId);

    @Query(value = "SELECT * FROM humidity WHERE is_delete = false AND device_id = ?1 ORDER BY id DESC LIMIT ?2", nativeQuery = true)
    List<Humidity> findLimitData(Long deviceId, Integer limit);

    @Query(value = "SELECT * \n" + //
                "FROM humidity \n" + //
                "WHERE device_id = ?1 AND created_on > (SELECT MAX(created_on) FROM air_temperature) - INTERVAL 1 HOUR", nativeQuery = true)
    List<Humidity> findAvarageOneHourData(Long deviceId);

    @Query(value = "SELECT * FROM humidity WHERE is_delete = false AND device_id = ?1 ORDER BY id DESC", nativeQuery = true)
    Page<Humidity> findAllData(Pageable pageable, Long deviceId);

    @Query(value = "SELECT * FROM humidity WHERE is_delete = false AND device_id = ?1 AND value LIKE concat('%',?1,'%')", nativeQuery = true)
    Page<Humidity> findAllBySearch(Pageable pageable, Long deviceId, String search);


    @Query(value = "SELECT * FROM humidity WHERE is_delete = false AND device_id = ?1 AND id = ?2", nativeQuery = true)
    Optional<Humidity> findById(Long deviceId, Long id);
}
