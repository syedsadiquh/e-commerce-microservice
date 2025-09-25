package com.gravityer.authservice.services;

import com.gravityer.authservice.Dtos.Request.CustomerCreateRequestDto;
import com.gravityer.authservice.Dtos.Request.UserLoginRequestDto;
import com.gravityer.authservice.Dtos.Response.UserCreateResponseDto;
import com.gravityer.authservice.Dtos.Response.UserLoginResponseDto;
import com.gravityer.authservice.Dtos.UserRegisterDto;
import com.gravityer.authservice.controllers.BaseResponse;
import com.gravityer.authservice.entities.User;
import com.gravityer.authservice.exceptions.InternalErrorException;
import com.gravityer.authservice.models.UserPrincipal;
import com.gravityer.authservice.repositories.UserRepository;
import com.gravityer.authservice.services.proxies.CustomerServiceProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerServiceProxy customerServiceProxy;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public BaseResponse<UserCreateResponseDto> createUser(UserRegisterDto userRegisterDto) {
        try {
            var user = User.builder().username(userRegisterDto.getUsername()).password(passwordEncoder.encode(userRegisterDto.getPassword())).roles(Set.of("USER")).build();

            var savedUser = userRepository.save(user);

            var customer = CustomerCreateRequestDto.builder()
                    .authId(savedUser.getId())
                    .name(userRegisterDto.getName())
                    .email(userRegisterDto.getEmail())
                    .build();

            var future = customerServiceProxy.createCustomerAsync(customer);

            var customerFutureResponse = future.join();

            if (!customerFutureResponse.isSuccess()) {
                log.error("Failed to create customer for user: {}. Reason: {}", savedUser.getUsername(), customerFutureResponse.getMessage());
                userRepository.delete(savedUser);
                throw new InternalErrorException("Failed to create user: " + savedUser.getUsername());
            }

            var userCreateResponseDto = UserCreateResponseDto.builder()
                    .id(savedUser.getId())
                    .username(savedUser.getUsername())
                    .roles(savedUser.getRoles())
                    .name(customerFutureResponse.getData().getName())
                    .email(customerFutureResponse.getData().getEmail())
                    .build();

            return new BaseResponse<>(true, "User registration Successful", userCreateResponseDto);
        } catch (Exception e) {
            log.error("Error during user registration: {}", e.getMessage());
            throw new InternalErrorException("User registration failed");
        }
    }

    public BaseResponse<UserLoginResponseDto> loginUser(UserLoginRequestDto userLoginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequestDto.getUsername(), userLoginRequestDto.getPassword())
        );

        if (authentication.isAuthenticated()) {
            var user = userRepository.findByUsername(userLoginRequestDto.getUsername());
            if (user == null) {
                throw new InternalErrorException("User not found after authentication");
            }
            var userLoginResponseDto = UserLoginResponseDto.builder()
                    .token(jwtService.generateToken((UserPrincipal) authentication.getPrincipal()))
                    .build();

            return new BaseResponse<>(true, "Login successful", userLoginResponseDto);
        }

        return new BaseResponse<>(false, "Login unsuccessful", null);
    }
}
