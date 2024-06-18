package com.tanahkube.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tanahkube.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long>{

//     @Query(value = "select u.*\n" + //
//             "from users u\n" + //
//             "join biodata b\n" + //
//             "on u.biodata_id = b.id\n" + //
//             "where u.is_delete = false", nativeQuery = true)
//     Page<Users> findByIsDelete(Pageable pageable);

//     @Query(value = "SELECT *\n" + //
//                         "FROM users\n" + //
//                         "WHERE is_delete = false \n" + //
//                         "AND email = ?1 LIMIT 1", nativeQuery = true)    
// 

    @Query(value = "SELECT *\n" + //
        "FROM users\n" + //
        "WHERE is_delete = false \n" + //
        "AND username = ?1 LIMIT 1", nativeQuery = true)    
    Optional<Users> findByUsername(String username);

    
//     @Query(value = "select u.*\n" + //
//             "from users u\n" + //
//             "join biodata b\n" + //
//             "on u.biodata_id = b.id\n" + //
//             "where u.is_delete = false \n" + //
//             "and (u.email like concat('%',?1,'%') or b.fullname like concat('%',?1,'%'))", nativeQuery = true)
//     Page<Users> findBySearch(Pageable pageable, String search);

//     @Query(value = "select * from users where is_delete = false and id = ?1", nativeQuery = true)
//     Optional<Users> findById(Long id);
}
