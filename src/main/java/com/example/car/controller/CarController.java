package com.example.car.controller;

import java.util.List;

import com.example.car.model.Car;
import com.example.car.service.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarController {

	@Autowired
	private CarService carService;

	@GetMapping
	public ResponseEntity<List<Car>> getAllCars() {
		return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<List<Car>> search(@RequestParam() String searchWord) {
		return new ResponseEntity<>(carService.searchCar(searchWord), HttpStatus.OK);
	}

	@GetMapping("/{carId}")
	public ResponseEntity<Car> getCarById(@PathVariable("carId") final long carId) {
		return new ResponseEntity<>(carService.getCar(carId), HttpStatus.OK);
	}

	@DeleteMapping("/{carId}")
	public ResponseEntity<Void> deleteCar(@PathVariable("carId") final long carId) {
		carService.deleteCar(carId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Void> addCar(@RequestBody final Car car) {
		carService.saveCar(car);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<Void> updateCar(@RequestBody final Car car) {
		carService.updateCar(car);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
