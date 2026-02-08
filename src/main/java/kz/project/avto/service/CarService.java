package kz.project.avto.service;
import kz.project.avto.utils.AppConfig;
import kz.project.avto.utils.AppLogger;

import kz.project.avto.dto.car.CarCreateRequest;
import kz.project.avto.dto.car.CarResponse;
import kz.project.avto.exception.InvalidInputException;
import kz.project.avto.exception.ResourceNotFoundException;
import kz.project.avto.model.VehicleBase;
import kz.project.avto.patterns.factory.VehicleFactory;
import kz.project.avto.patterns.factory.VehicleType;
import kz.project.avto.repository.CarRepositoryJdbc;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepositoryJdbc carRepository;
    private final AppLogger logger = AppLogger.getInstance();
    private final AppConfig config = AppConfig.getInstance();

    public CarService(CarRepositoryJdbc carRepository) {
        this.carRepository = carRepository;
    }

    public CarResponse create(CarCreateRequest request) {
        validateCreateRequest(request);
        logger.log("Creating car in " + config.getAppName());

        VehicleType type = VehicleType.valueOf(request.getType().toUpperCase());

        VehicleBase car = VehicleFactory.createVehicle(
                type,
                null,
                request.getBrand(),
                request.getModel(),
                request.getPricePerDay()
        );

        VehicleBase saved = carRepository.save(car);

        return toResponse(saved);
    }

    public CarResponse getById(Long id) {
        VehicleBase car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found: id=" + id));

        return toResponse(car);
    }

    public List<CarResponse> getAll() {
        return carRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private void validateCreateRequest(CarCreateRequest request) {
        if (request.getType() == null || request.getType().isBlank()) {
            throw new InvalidInputException("Car type is required (ECONOMY or LUXURY)");
        }
        if (request.getBrand() == null || request.getBrand().isBlank()) {
            throw new InvalidInputException("Brand is required");
        }
        if (request.getModel() == null || request.getModel().isBlank()) {
            throw new InvalidInputException("Model is required");
        }
        if (request.getPricePerDay() <= 0) {
            throw new InvalidInputException("pricePerDay must be > 0");
        }

        String normalized = request.getType().toUpperCase();
        if (!normalized.equals("ECONOMY") && !normalized.equals("LUXURY")) {
            throw new InvalidInputException("Unknown car type: " + request.getType());
        }
    }

    private CarResponse toResponse(VehicleBase car) {
        String type = (car.getClass().getSimpleName().toUpperCase().contains("LUXURY"))
                ? "LUXURY"
                : "ECONOMY";

        return new CarResponse(
                car.getId(),
                type,
                car.getBrand(),
                car.getModel(),
                car.getPricePerDay()
        );
    }
    public CarResponse update(Long id, CarCreateRequest request) {
        validateCreateRequest(request);

        // проверяем, что машина существует
        carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found: id=" + id));

        VehicleType type = VehicleType.valueOf(request.getType().toUpperCase());

        VehicleBase updated = VehicleFactory.createVehicle(
                type,
                id,
                request.getBrand(),
                request.getModel(),
                request.getPricePerDay()
        );

        carRepository.update(id, updated);

        // возвращаем обновлённую версию
        VehicleBase after = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found after update: id=" + id));

        return toResponse(after);
    }

    public void delete(Long id) {
        // проверяем, что машина существует
        carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found: id=" + id));

        carRepository.deleteById(id);
    }

}
