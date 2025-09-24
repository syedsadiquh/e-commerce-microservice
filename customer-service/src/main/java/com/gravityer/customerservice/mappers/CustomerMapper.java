package com.gravityer.customerservice.mappers;

import com.gravityer.customerservice.dtos.CustomerDto;
import com.gravityer.customerservice.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    public CustomerDto toCustomerDto(Customer customer);

    @Mapping(target = "id", ignore = true)
    public Customer toCustomer(CustomerDto customerDto);
}
