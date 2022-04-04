package com.renting_boat.demo.repository;

import com.renting_boat.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    @Query(value = "select user " +
            "from User user " +
            "left join fetch user.roles " +
            "where user.username = :username")
    User findByUsername(@Param("username") String username);

    boolean existsByUsername(String username);

    @Query(value = "select user from User as user " +
            "join fetch user.roles as role " +
            "where role.id = :id")
    List<User> findAllWithRoleId(Integer id);

    @Query(value = "select user from User user where user.boats is not empty")
    List<User> findAllWithRentedBoats();

    @Query(value = "select user from User user where user.username = :username")
    Optional<User> doesItExistUsername(String username);
}
