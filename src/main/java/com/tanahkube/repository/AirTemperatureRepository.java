package com.tanahkube.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tanahkube.entity.AirTemperature;

@Repository
public interface AirTemperatureRepository extends JpaRepository<AirTemperature, Long>{
    @Query(value = "SELECT * FROM air_temperature WHERE is_delete = false AND device_id = ?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<AirTemperature> findLastData(Long deviceId);

    @Query(value = "SELECT * FROM air_temperature WHERE is_delete = false AND device_id = ?1 ORDER BY id DESC LIMIT ?2", nativeQuery = true)
    List<AirTemperature> findLimitData(Long deviceId, Integer limit);

    @Query(value = "SELECT * \n" + //
                "FROM air_temperature \n" + //
                "WHERE device_id = ?1 AND created_on > (SELECT MAX(created_on) FROM air_temperature) - INTERVAL 1 HOUR", nativeQuery = true)
    List<AirTemperature> findAvarageOneHourData(Long deviceId);

    @Query(value = "SELECT * \n" + //
                "FROM air_temperature \n" + //
                "WHERE device_id = ?1 AND created_on > (SELECT MAX(created_on) FROM air_temperature) - INTERVAL 24 HOUR", nativeQuery = true)
    List<AirTemperature> findAvarageOneDayData(Long deviceId);

    @Query(value = "SELECT * FROM air_temperature WHERE is_delete = false AND device_id = ?1 ORDER BY id DESC", nativeQuery = true)
    Page<AirTemperature> findAllData(Pageable pageable, Long deviceId);

    @Query(value = "SELECT * FROM air_temperature WHERE is_delete = false AND device_id = ?1 AND value LIKE concat('%',?2,'%')", nativeQuery = true)
    Page<AirTemperature> findAllBySearch(Pageable pageable, Long deviceId, String search);

    @Query(value = "SELECT * FROM air_temperature WHERE is_delete = false AND device_id = ?1 AND id = ?2", nativeQuery = true)
    Optional<AirTemperature> findById(Long deviceId, Long id);
}
