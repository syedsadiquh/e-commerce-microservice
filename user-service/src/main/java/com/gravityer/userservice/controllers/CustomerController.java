package com.gravityer.userservice.controllers;

import com.gravityer.userservice.dtos.UserDto;
import com.gravityer.userservice.entities.User;
import com.gravityer.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class CustomerController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<BaseResponse<User>> selfDetails(Principal principal) {
        return ResponseEntity.ok(userService.getSelfDetails(principal));
    }

    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse<List<User>>> getAllCustomers() {
        return ResponseEntity.ok(userService.findAllCustomers());
    }

//    @GetMapping("/get/{id}")
//    public ResponseEntity<BaseResponse<CustomerDto>> get(@PathVariable Long id) {
//        return ResponseEntity.ok(customerService.findCustomerById(id));
//    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<User>> create(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createCustomer(userDto));
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<BaseResponse<Customer>> update(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
//        return ResponseEntity.ok(customerService.updateCustomer(id, customerDto));
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteCustomer(id));
    }
}
