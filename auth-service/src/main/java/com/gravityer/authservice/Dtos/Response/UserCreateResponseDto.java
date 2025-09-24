package com.gravityer.authservice.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateResponseDto {
    private Long id;
    private String name;
    private String username;
    private String email;
    private Set<String> roles;
}
