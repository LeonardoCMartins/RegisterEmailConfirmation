package com.api.login_register_email.services;

import com.api.login_register_email.dtos.RegistrationRequest;
import com.api.login_register_email.email.EmailSender;
import com.api.login_register_email.entities.AppUser;
import com.api.login_register_email.entities.Token;
import com.api.login_register_email.entities.enums.AppUserRole;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class RegistrationService {

    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmailSender emailSender;


    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (isValidEmail == false ) {
            throw new IllegalStateException("email não é válido");
        }

        String token = appUserService.signUpUser(new AppUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER
        ));

        String link = "http://localhost:8080/api/registration/confirm?token=" + token;

        try {
            emailSender.send(request.getEmail(), loadTemplateEmail(request.getUsername(), link));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        Token confirmationToken = tokenService.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email já confirmado");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expirado");
        }

        tokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        return "email confirmado";
    }

    public String loadTemplateEmail(String username, String link) throws IOException {
        String template = new String(Files.readAllBytes(Paths.get("src/main/resources/template-email.html")));
        return template.replace("{username}", username).replace("{link}", link);
    }

}
