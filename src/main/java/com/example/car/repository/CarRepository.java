package com.example.car.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.car.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

	@Query("from Car c where c.name=:searchWord or manufacture_name=:searchWord or model=:searchWord or manufacturing_year=:searchWord or color=:searchWord")
	List<Car> searchCars(@Param("searchWord") String searchWord);
}
