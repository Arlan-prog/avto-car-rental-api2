package kz.project.avto.controller;

import kz.project.avto.dto.rental.RentalCreateRequest;
import kz.project.avto.dto.rental.RentalResponse;
import kz.project.avto.service.RentalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public RentalResponse create(@RequestBody RentalCreateRequest request) {
        return rentalService.create(request);
    }

    @GetMapping
    public List<RentalResponse> getAll() {
        return rentalService.getAll();
    }

    @GetMapping("/{id}")
    public RentalResponse getById(@PathVariable Long id) {
        return rentalService.getById(id);
    }
}
