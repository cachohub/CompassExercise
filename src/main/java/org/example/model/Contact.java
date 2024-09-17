package org.example.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Contact {
    long id;
    String firstName;
    String lastName;
    String email;
    String zipCode;
    String address;

}
