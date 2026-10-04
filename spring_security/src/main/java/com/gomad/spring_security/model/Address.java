package com.gomad.spring_security.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @NotBlank(message = "Building Name shouldn't be blank.")
    @Size(max = 255, message = "Building Name size shouldn't more than 20 characters.")
    @Column(name = "building_name")
    private String buildingName;
}
