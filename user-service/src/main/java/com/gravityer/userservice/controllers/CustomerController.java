package com.gravityer.userservice.controllers;

import com.gravityer.userservice.dtos.CustomerDto;
import com.gravityer.userservice.entities.Customer;
import com.gravityer.userservice.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse<List<Customer>>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

//    @GetMapping("/get/{id}")
//    public ResponseEntity<BaseResponse<CustomerDto>> get(@PathVariable Long id) {
//        return ResponseEntity.ok(customerService.findCustomerById(id));
//    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<Customer>> create(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customerDto));
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<BaseResponse<Customer>> update(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
//        return ResponseEntity.ok(customerService.updateCustomer(id, customerDto));
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.deleteCustomer(id));
    }
}
