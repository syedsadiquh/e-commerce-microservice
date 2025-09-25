package com.gravityer.authservice.services.proxies;

import com.gravityer.authservice.Dtos.Request.CustomerCreateRequestDto;
import com.gravityer.authservice.Dtos.Response.CustomerCreateResponseDto;
import com.gravityer.authservice.controllers.BaseResponse;
import com.gravityer.authservice.services.feign.CustomerServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CustomerServiceProxy {

    private final CustomerServiceFeignClient customerServiceFeignClient;

    @Async
    public CompletableFuture<BaseResponse<CustomerCreateResponseDto>> createCustomerAsync(CustomerCreateRequestDto request) {
        BaseResponse<CustomerCreateResponseDto> response = customerServiceFeignClient.createCustomer(request);
        return CompletableFuture.completedFuture(response);
    }
}
