package org.example.back.security.auth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ezzaim Mohammed
 **/
@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private String token;
}
