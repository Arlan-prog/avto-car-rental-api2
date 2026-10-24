package kz.project.avto.service;

import kz.project.avto.dto.car.CarCreateRequest;
import kz.project.avto.dto.car.CarResponse;
import kz.project.avto.exception.InvalidInputException;
import kz.project.avto.exception.ResourceNotFoundException;
import kz.project.avto.model.LuxuryCar;
import kz.project.avto.model.VehicleBase;
import kz.project.avto.patterns.factory.VehicleFactory;
import kz.project.avto.patterns.factory.VehicleType;
import kz.project.avto.repository.CarRepositoryJdbc;
import kz.project.avto.utils.AppConfig;
import kz.project.avto.utils.AppLogger;
import kz.project.avto.utils.SimpleCache;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CarService {
    private static final double MAX_DAILY_PRICE = 9_999_999.99;

    private final CarRepositoryJdbc carRepository;
    private final AppLogger logger = AppLogger.getInstance();
    private final AppConfig config = AppConfig.getInstance();
    private final SimpleCache cache = SimpleCache.getInstance();

    public CarService(CarRepositoryJdbc carRepository) {
        this.carRepository = carRepository;
    }

    public CarResponse create(CarCreateRequest request) {
        validateCreateRequest(request);
        VehicleBase car = createVehicle(null, request);
        VehicleBase saved = carRepository.save(car);
        cache.remove("cars");
        logger.log("Created car in " + config.getAppName());
        return toResponse(saved);
    }

    public CarResponse getById(Long id) {
        requirePositiveId(id, "Car");
        VehicleBase car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found: id=" + id));
        return toResponse(car);
    }

    @SuppressWarnings("unchecked")
    public List<CarResponse> getAll() {
        List<CarResponse> cached = (List<CarResponse>) cache.get("cars");
        if (cached != null) {
            return cached;
        }
        List<CarResponse> cars = carRepository.findAll().stream().map(this::toResponse).toList();
        cache.put("cars", cars);
        return cars;
    }

    public CarResponse update(Long id, CarCreateRequest request) {
        requirePositiveId(id, "Car");
        validateCreateRequest(request);
        carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found: id=" + id));

        carRepository.update(id, createVehicle(id, request));
        cache.remove("cars");
        VehicleBase updated = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found after update: id=" + id));
        return toResponse(updated);
    }

    public void delete(Long id) {
        requirePositiveId(id, "Car");
        carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found: id=" + id));
        carRepository.deleteById(id);
        cache.remove("cars");
    }

    private VehicleBase createVehicle(Long id, CarCreateRequest request) {
        VehicleType type = VehicleType.valueOf(request.getType().trim().toUpperCase(Locale.ROOT));
        return VehicleFactory.createVehicle(
                type, id, request.getBrand().trim(), request.getModel().trim(), request.getPricePerDay()
        );
    }

    private void validateCreateRequest(CarCreateRequest request) {
        if (request == null) {
            throw new InvalidInputException("Car request is required.");
        }
        if (request.getType() == null || request.getType().isBlank()) {
            throw new InvalidInputException("Car type is required (ECONOMY or LUXURY).");
        }
        String type = request.getType().trim().toUpperCase(Locale.ROOT);
        if (!type.equals("ECONOMY") && !type.equals("LUXURY")) {
            throw new InvalidInputException("Unknown car type: " + request.getType());
        }
        if (request.getBrand() == null || request.getBrand().isBlank() || request.getBrand().length() > 60) {
            throw new InvalidInputException("Brand must contain 1 to 60 characters.");
        }
        if (request.getModel() == null || request.getModel().isBlank() || request.getModel().length() > 60) {
            throw new InvalidInputException("Model must contain 1 to 60 characters.");
        }
        double price = request.getPricePerDay();
        if (!Double.isFinite(price) || price <= 0 || price > MAX_DAILY_PRICE) {
            throw new InvalidInputException("pricePerDay must be positive and fit the database range.");
        }
    }

    private void requirePositiveId(Long id, String entity) {
        if (id == null || id <= 0) {
            throw new InvalidInputException(entity + " id must be positive.");
        }
    }

    private CarResponse toResponse(VehicleBase car) {
        String type = car instanceof LuxuryCar ? "LUXURY" : "ECONOMY";
        return new CarResponse(car.getId(), type, car.getBrand(), car.getModel(), car.getPricePerDay());
    }
}
