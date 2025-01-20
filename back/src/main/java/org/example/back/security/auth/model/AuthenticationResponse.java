package org.example.back.security.auth.model;

import lombok.*;

/**
 * @author Ezzaim Mohammed
 **/
@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private String token;
}
