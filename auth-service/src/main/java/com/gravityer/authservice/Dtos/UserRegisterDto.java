package com.gravityer.authservice.Dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDto {
    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    @Length(min = 6)
    private String password;
    @NotNull
    private String email;
    private Set<String> roles;
}
