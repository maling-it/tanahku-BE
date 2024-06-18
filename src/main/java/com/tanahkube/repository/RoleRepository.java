package com.tanahkube.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tanahkube.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    @Query(value = "select * from role where is_delete = false", nativeQuery = true)
    List<Role> findByIsDelete();

    @Query(value = "select * from role where is_delete = false and id = ?1", nativeQuery = true)
    Optional<Role> findById(Long id);
}
