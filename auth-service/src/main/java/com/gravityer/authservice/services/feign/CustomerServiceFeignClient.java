package com.gravityer.authservice.services.feign;

import com.gravityer.authservice.Dtos.Request.CustomerCreateRequestDto;
import com.gravityer.authservice.Dtos.Response.CustomerCreateResponseDto;
import com.gravityer.authservice.controllers.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface CustomerServiceFeignClient {

    @PostMapping("/user/create")
    BaseResponse<CustomerCreateResponseDto> createCustomer(@RequestBody CustomerCreateRequestDto customerRequest);
}
