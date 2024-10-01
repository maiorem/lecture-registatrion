package com.hhplus.api.registration.presentation;

import com.hhplus.api.registration.application.RegistrationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationFacade facade;



}
