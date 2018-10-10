package com.tnbank.microcustomer.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
public class Customer {

    private @Id @Size(min = 8, max = 8) String id;
    private @NotNull @Size(min = 4, max = 25) String lastName;
    private @NotNull @Size(min = 4, max = 25) String firstName;
    private @NotNull @Size(min = 4, max = 25) String street;
    private @NotNull @Size(min = 4, max = 25) String city;
    private @NotNull @Size(min = 4, max = 25) String state;
    private @NotNull @Min(1000) @Max(9999) int zip;
    private @NotNull @Min(10000000) @Max(99999999) int phonePrimary;
    private int phoneSecondary;
    private @NotNull String email;

}
