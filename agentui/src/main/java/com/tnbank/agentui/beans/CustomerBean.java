package com.tnbank.agentui.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerBean {
    private String id;
    private String lastName;
    private String firstName;
    private String street;
    private String city;
    private String state;
    private int zip;
    private int phonePrimary;
    private int phoneSecondary;
    private String email;
}
