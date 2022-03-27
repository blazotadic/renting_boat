package com.renting_boat.demo.repository;

import com.renting_boat.demo.entity.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BoatRepository extends JpaRepository<Boat, Integer>, JpaSpecificationExecutor<Boat>
{

}
