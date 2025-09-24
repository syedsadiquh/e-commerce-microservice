package com.gravityer.customerservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private long authId;

    private String name;

    @Column(nullable = false, length = 100)
    private String email;
}
