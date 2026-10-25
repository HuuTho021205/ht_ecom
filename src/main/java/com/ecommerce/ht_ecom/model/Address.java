package com.ecommerce.ht_ecom.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @NotBlank
    @Size(min = 3, message = "Street name must be at least 3 characters")
    private String street;

    @NotBlank
    @Size(min = 3, message = "Building name must be at least 3 characters")
    @Column(name = "building_name")
    private String buildingName;

    @NotBlank
    @Size(min = 3, message = "City name must be at least 3 characters")
    private String city;

    @NotBlank
    @Size(min = 3, message = "State name must be at least 3 characters")
    private String state;

    @NotBlank
    @Size(min = 3, message = "Country name must be at least 3 characters")
    private String country;

    @NotBlank
    @Size(min = 5, message = "Postal Code must be at least 5 characters")
    @Column(name = "postal_code")
    private String postalCode;

    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

    public Address(String street, String buildingName, String city, String state, String country, String postalCode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
    }
}
