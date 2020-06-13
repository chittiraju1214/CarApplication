package com.example.car.service;

import java.util.List;
import java.util.Optional;

import com.example.car.exception.CarServiceException;
import com.example.car.model.Car;
import com.example.car.repository.CarRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);

	@Autowired
	private CarRepository carRepository;

	public List<Car> getAllCars() throws CarServiceException {
		LOGGER.info("Get All Cars");
		try {
			return carRepository.findAll();
		} catch (Exception ex) {
			LOGGER.error("Error while getting all cars: {}", ex.getMessage());
			throw new CarServiceException(ex.getMessage());
		}
	}

	public Car getCar(final Long id) throws CarServiceException {
		LOGGER.info("Get Car for ID: {}", id);
		try {
			Optional<Car> car = carRepository.findById(id);
			if (car.isPresent()) {
				return car.get();
			} else {
				throw new Exception("No record exist for given id");
			}
		} catch (Exception ex) {
			LOGGER.error("Error while fetching car: and carId{} {}", ex.getMessage(), id);
			throw new CarServiceException(ex.getMessage());
		}
	}

	public void saveCar(final Car car) throws CarServiceException {
		LOGGER.info("Saving Car {}", car);
		try {
			carRepository.save(car);
		} catch (Exception ex) {
			LOGGER.error("Error while saving car: {}", ex.getMessage(), car);
			throw new CarServiceException(ex.getMessage());
		}
	}

	public void updateCar(final Car car) throws CarServiceException {
		LOGGER.info("Updating Car for card ID: {}", car.getId());
		try {
			long id = car.getId();
			Optional<Car> car1 = carRepository.findById(id);
			if (car1.isPresent()) {
				Car newCar = car1.get();
				newCar.setColor(car.getColor());
				newCar.setManufactureName(car.getManufactureName());
				newCar.setManufacturingYear(car.getManufacturingYear());
				newCar.setModel(car.getModel());
				newCar.setName(car.getName());
				carRepository.save(newCar);
			} else {
				throw new Exception("No car record exist for given id");
			}

		} catch (Exception ex) {
			LOGGER.error("Error while updating car: and carId{} {}", ex.getMessage(), car.getId());
			throw new CarServiceException(ex.getMessage());
		}
	}

	public void deleteCar(final Long id) throws CarServiceException {
		LOGGER.info("Deleting Car for card ID: {}", id);
		try {
			carRepository.deleteById(id);
		} catch (Exception ex) {
			LOGGER.error("Error while deleting car: and carId{} {}", ex.getMessage(), id);
			throw new CarServiceException(ex.getMessage());
		}
	}

	public List<Car> searchCar(String searchWord) {
		LOGGER.info("Searching Cars");
		try {
			return carRepository.searchCars(searchWord);
		} catch (Exception ex) {
			LOGGER.error("Error while Searching all cars: {}", ex.getMessage());
			throw new CarServiceException(ex.getMessage());
		}
	}
}
