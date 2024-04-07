package com.linh.CloudGateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class TestKeyCloakController {

    @GetMapping("/test")
    public String getLoginUserName(Principal principal){
        return principal.getName();
    }
}
