package com.gravityer.userservice.mappers;

import com.gravityer.userservice.dtos.CustomerDto;
import com.gravityer.userservice.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    public CustomerDto toCustomerDto(Customer customer);

    @Mapping(target = "id", ignore = true)
    public Customer toCustomer(CustomerDto customerDto);
}
