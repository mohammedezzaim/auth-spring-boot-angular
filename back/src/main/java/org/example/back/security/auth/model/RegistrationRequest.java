package org.example.back.security.auth.model;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * @author Ezzaim Mohammed
 **/
@Getter
@Setter
public class RegistrationRequest {

    @NotEmpty(message = "Firstname is mandatory")
    @NotBlank(message = "Firstname is mandatory")
    private String firstname;

    @NotEmpty(message = "Lastname is mandatory")
    @NotBlank(message = "Lastname is mandatory")
    private String lastname;

    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is Not Formatted")
    private String email;

    @NotEmpty(message = "password is mandatory")
    @NotBlank(message = "password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters minimum")
    private String password;

}
