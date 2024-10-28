package com.api.login_register_email.controllers;

import com.api.login_register_email.dtos.RegistrationRequest;
import com.api.login_register_email.services.AppUserService;
import com.api.login_register_email.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService service;

    @PostMapping()
    public String register(@RequestBody RegistrationRequest request){
        return service.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return service.confirmToken(token);
    }
}
