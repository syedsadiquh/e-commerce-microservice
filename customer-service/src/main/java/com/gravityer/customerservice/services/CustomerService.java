package com.gravityer.customerservice.services;

import com.gravityer.customerservice.controllers.BaseResponse;
import com.gravityer.customerservice.dtos.CustomerDto;
import com.gravityer.customerservice.entities.Customer;
import com.gravityer.customerservice.exceptions.InternalErrorException;
import com.gravityer.customerservice.exceptions.NotFoundException;
import com.gravityer.customerservice.mappers.CustomerMapper;
import com.gravityer.customerservice.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public BaseResponse<List<Customer>> findAllCustomers() {
        try {
            var result = customerRepository.findAll();
            if (result.isEmpty()) {
                return new BaseResponse<>(false, "No customers found");
            }
            return new BaseResponse<>(true, "Customers retrieved successfully", result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalErrorException("An error occurred while retrieving customers");
        }
    }

    public BaseResponse<CustomerDto> findCustomerById(Long id) {
        try {
            var customer = customerRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Customer with id " + id + " not found")
            );
            var customerDto = new CustomerDto(customer.getName(), customer.getEmail(), customer.getPassword(), customer.getRoles());
            return new BaseResponse<>(true, "Customer retrieved successfully", customerDto);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalErrorException("An error occurred while retrieving the customer");
        }
    }

    public BaseResponse<Customer> createCustomer(CustomerDto customerDto) {
        try {
            var customer = customerMapper.toCustomer(customerDto);
            var savedCustomer = customerRepository.save(customer);
            return new BaseResponse<>(true, "Customer created successfully", savedCustomer);
        } catch (DataIntegrityViolationException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalErrorException("An error occurred while creating the customer");
        }
    }

    public BaseResponse<Customer> updateCustomer(Long id, CustomerDto customerDto) {
        try {
            var existingCustomer = customerRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Customer with id " + id + " not found")
            );
            existingCustomer.setName(customerDto.getName());
            existingCustomer.setEmail(customerDto.getEmail());
            existingCustomer.setPassword(customerDto.getPassword());
            existingCustomer.setRoles(customerDto.getRoles());
            var updatedCustomer = customerRepository.save(existingCustomer);
            return new BaseResponse<>(true, "Customer updated successfully", updatedCustomer);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalErrorException("An error occurred while updating the customer");
        }
    }

    public BaseResponse<Void> deleteCustomer(Long id) {
        try {
            var existingCustomer = customerRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Customer with id " + id + " not found")
            );
            customerRepository.delete(existingCustomer);
            return new BaseResponse<>(true, "Customer deleted successfully");
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalErrorException("An error occurred while deleting the customer");
        }
    }
}
