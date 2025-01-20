package org.example.back.security.auth.model;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * @author Ezzaim Mohammed
 **/
@Getter
@Setter
@Builder
public class AuthenticationRequest {
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is Not Formatted")
    private String email;

    @NotEmpty(message = "password is mandatory")
    @NotBlank(message = "password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters minimum")
    private String password;
}
