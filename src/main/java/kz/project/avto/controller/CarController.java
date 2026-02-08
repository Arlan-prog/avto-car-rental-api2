package kz.project.avto.controller;

import kz.project.avto.dto.car.CarCreateRequest;
import kz.project.avto.dto.car.CarResponse;
import kz.project.avto.service.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public CarResponse create(@RequestBody CarCreateRequest request) {
        return carService.create(request);
    }

    @GetMapping("/{id}")
    public CarResponse getById(@PathVariable Long id) {
        return carService.getById(id);
    }

    @GetMapping
    public List<CarResponse> getAll() {
        return carService.getAll();
    }
    @PutMapping("/{id}")
    public CarResponse update(@PathVariable Long id, @RequestBody CarCreateRequest request) {
        return carService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        carService.delete(id);
    }

}
