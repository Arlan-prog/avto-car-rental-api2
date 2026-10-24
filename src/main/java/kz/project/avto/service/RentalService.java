package kz.project.avto.service;

import kz.project.avto.dto.rental.RentalCreateRequest;
import kz.project.avto.dto.rental.RentalResponse;
import kz.project.avto.exception.InvalidInputException;
import kz.project.avto.exception.ResourceNotFoundException;
import kz.project.avto.model.Rental;
import kz.project.avto.repository.CarRepositoryJdbc;
import kz.project.avto.repository.CustomerRepositoryJdbc;
import kz.project.avto.repository.RentalRepositoryJdbc;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {
    private final RentalRepositoryJdbc rentalRepository;
    private final CustomerRepositoryJdbc customerRepository;
    private final CarRepositoryJdbc carRepository;

    public RentalService(RentalRepositoryJdbc rentalRepository,
                         CustomerRepositoryJdbc customerRepository,
                         CarRepositoryJdbc carRepository) {
        this.rentalRepository = rentalRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }

    public RentalResponse create(RentalCreateRequest request) {
        if (request == null) {
            throw new InvalidInputException("Rental request is required.");
        }
        Long customerId = request.getCustomerId();
        Long carId = request.getCarId();
        if (customerId == null || customerId <= 0) {
            throw new InvalidInputException("customerId must be positive.");
        }
        if (carId == null || carId <= 0) {
            throw new InvalidInputException("carId must be positive.");
        }
        if (request.getDays() <= 0) {
            throw new InvalidInputException("days must be positive.");
        }

        customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: id=" + customerId));
        carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found: id=" + carId));

        Rental rental = new Rental.Builder()
                .customerId(customerId)
                .carId(carId)
                .days(request.getDays())
                .build();
        Rental saved = rentalRepository.save(rental);
        return toResponse(saved);
    }

    public List<RentalResponse> getAll() {
        return rentalRepository.findAll().stream().map(this::toResponse).toList();
    }

    public RentalResponse getById(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidInputException("Rental id must be positive.");
        }
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found: id=" + id));
        return toResponse(rental);
    }

    private RentalResponse toResponse(Rental rental) {
        return new RentalResponse(rental.getId(), rental.getCustomerId(), rental.getCarId(), rental.getDays());
    }
}
