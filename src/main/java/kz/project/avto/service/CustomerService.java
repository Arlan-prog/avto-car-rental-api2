package kz.project.avto.service;

import kz.project.avto.dto.customer.CustomerCreateRequest;
import kz.project.avto.dto.customer.CustomerResponse;
import kz.project.avto.exception.InvalidInputException;
import kz.project.avto.exception.ResourceNotFoundException;
import kz.project.avto.model.Customer;
import kz.project.avto.repository.CustomerRepositoryJdbc;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepositoryJdbc customerRepository;

    public CustomerService(CustomerRepositoryJdbc customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse create(CustomerCreateRequest request) {
        if (request.getFullName() == null || request.getFullName().isBlank()) {
            throw new InvalidInputException("fullName is required");
        }
        if (request.getPhone() == null || request.getPhone().isBlank()) {
            throw new InvalidInputException("phone is required");
        }

        Customer saved = customerRepository.save(new Customer(null, request.getFullName(), request.getPhone()));
        return new CustomerResponse(saved.getId(), saved.getFullName(), saved.getPhone());
    }

    public CustomerResponse getById(Long id) {
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: id=" + id));
        return new CustomerResponse(c.getId(), c.getFullName(), c.getPhone());
    }

    public List<CustomerResponse> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(c -> new CustomerResponse(c.getId(), c.getFullName(), c.getPhone()))
                .toList();
    }
}
