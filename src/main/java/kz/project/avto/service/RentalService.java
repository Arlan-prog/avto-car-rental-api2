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
        if (request.getCustomerId() == null) throw new InvalidInputException("customerId is required");
        if (request.getCarId() == null) throw new InvalidInputException("carId is required");
        if (request.getDays() <= 0) throw new InvalidInputException("days must be > 0");

        // проверяем существование customer и car
        customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: id=" + request.getCustomerId()));
        carRepository.findById(request.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Car not found: id=" + request.getCarId()));

        // BUILDER PATTERN
        Rental rental = new Rental.Builder()
                .customerId(request.getCustomerId())
                .carId(request.getCarId())
                .days(request.getDays())
                .build();

        Rental saved = rentalRepository.save(rental);

        return new RentalResponse(saved.getId(), saved.getCustomerId(), saved.getCarId(), saved.getDays());
    }

    public List<RentalResponse> getAll() {
        return rentalRepository.findAll()
                .stream()
                .map(r -> new RentalResponse(r.getId(), r.getCustomerId(), r.getCarId(), r.getDays()))
                .toList();
    }

    public RentalResponse getById(Long id) {
        Rental r = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found: id=" + id));
        return new RentalResponse(r.getId(), r.getCustomerId(), r.getCarId(), r.getDays());
    }
}
