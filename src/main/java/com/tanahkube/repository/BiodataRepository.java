package com.tanahkube.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tanahkube.entity.Biodata;

@Repository
public interface BiodataRepository extends JpaRepository<Biodata, Long>{
    @Query(value = "select * from biodata where is_delete = false", nativeQuery = true)
    List<Biodata> findByIsDelete();

    @Query(value = "select * from biodata where is_delete = false and id = ?1", nativeQuery = true)
    Optional<Biodata> findById(Long id);

    @Query(value = "select * from biodata where is_delete = false and fullname = ?1 LIMIT 1", nativeQuery = true)
    Optional<Biodata> findByFullname(String fullname);

    @Query(value = "select * from biodata where is_delete = false and mobile_phone = ?1 LIMIT 1", nativeQuery = true)
    Optional<Biodata> findByMobilePhone(String mobilePhone);
}
