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
        if (request == null) {
            throw new InvalidInputException("Customer request is required.");
        }
        String name = request.getFullName() == null ? "" : request.getFullName().trim();
        String phone = request.getPhone() == null ? "" : request.getPhone().trim();
        if (name.isEmpty() || name.length() > 130) {
            throw new InvalidInputException("fullName must contain 1 to 130 characters.");
        }
        if (phone.isEmpty() || phone.length() > 30) {
            throw new InvalidInputException("phone must contain 1 to 30 characters.");
        }

        Customer saved = customerRepository.save(new Customer(null, name, phone));
        return toResponse(saved);
    }

    public CustomerResponse getById(Long id) {
        requirePositiveId(id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: id=" + id));
        return toResponse(customer);
    }

    public List<CustomerResponse> getAll() {
        return customerRepository.findAll().stream().map(this::toResponse).toList();
    }

    private void requirePositiveId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidInputException("Customer id must be positive.");
        }
    }

    private CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getFullName(), customer.getPhone());
    }
}
