package com.renting_boat.demo.repository;

import com.renting_boat.demo.entity.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoatRepository extends JpaRepository<Boat, Integer>, JpaSpecificationExecutor<Boat>
{
    @Query(value = "select boat from Boat boat " +
            "left join fetch boat.user " +
            "where boat.rentingUntil is not null")
    List<Boat> findAllRentedBoats();
}
