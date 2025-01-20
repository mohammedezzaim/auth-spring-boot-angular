package org.example.back.security.auth.service;



import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.back.security.auth.model.AuthenticationRequest;
import org.example.back.security.auth.model.AuthenticationResponse;
import org.example.back.security.auth.model.RegistrationRequest;
import org.example.back.security.bean.UserDetailsImpl;
import org.example.back.security.bean.VerificationCode;
import org.example.back.security.dao.GrantedAuthorityDao;
import org.example.back.security.dao.UserDetailsImplDao;
import org.example.back.security.dao.VerificationCodeDao;
import org.example.back.security.email.EmailService;
import org.example.back.security.email.EmailTemplateName;
import org.example.back.security.handler.EmailAlreadyExistsException;
import org.example.back.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @author Ezzaim Mohammed
 **/

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final GrantedAuthorityDao grantedAuthorityDao;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsImplDao userDetailsImplDao;
    private final VerificationCodeDao verificationCodeDao;
    private final EmailService emailservice;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${application.security.mailing.frontend.activation-url}")
    private String activationUrl;

    public void register(RegistrationRequest request) throws MessagingException {

        if (userDetailsImplDao.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("The email " + request.getEmail() + " is already in use.");
        }

        var userRole = grantedAuthorityDao.findByRole("USER")
                .orElseThrow(() -> new IllegalArgumentException("Role USER was not initialized"));

        UserDetailsImpl user = UserDetailsImpl.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();

        userDetailsImplDao.save(user);
        sendValidationEmail(user);
    }


    public void registerForAdmin(RegistrationRequest request) throws MessagingException {

        if (userDetailsImplDao.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("The email " + request.getEmail() + " is already in use.");
        }

        var userRole = grantedAuthorityDao.findByRole("USER")
                .orElseThrow(() -> new IllegalArgumentException("Role USER was not initialized"));

        var adminRole = grantedAuthorityDao.findByRole("ADMIN")
                .orElseThrow(() -> new IllegalArgumentException("Role ADMIN was not initialized"));

        UserDetailsImpl user = UserDetailsImpl.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole,adminRole))
                .build();



        userDetailsImplDao.save(user);
        sendValidationEmail(user);
    }


    private void sendValidationEmail(UserDetailsImpl user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailservice.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activated"
        );
    }

    private String generateAndSaveActivationToken(UserDetailsImpl user) {
        // generate a token
        String generatedToken = generateActivationCode(6);
        var token = VerificationCode.builder()
                .code(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        verificationCodeDao.save(token);
        return generatedToken;
    }

    private String generateActivationCode( int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i<length; i++){
            int randomIndex = secureRandom.nextInt(characters.length()); // 0..9
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        System.out.println(auth.getPrincipal());
        var claims = new HashMap<String,Object>();
        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
        claims.put("fullName",user.fullName());

        var jwtToken = jwtService.generateToken(claims,user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    //@Transactional
    public void activateAccount(String code) throws MessagingException {
        VerificationCode savedToken = verificationCodeDao.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation tok" +
                    "en has expired. A new token has benn sent to the same mail address");
        }
        UserDetailsImpl user = userDetailsImplDao.findById(savedToken.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        user.setEnabled(true);
        userDetailsImplDao.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        verificationCodeDao.save(savedToken);
    }
    
}
