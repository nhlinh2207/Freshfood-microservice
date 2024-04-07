package com.linh.UserService.controller;

import com.linh.UserService.config.keycloak.CurrentUserProvider;
import com.linh.UserService.config.keycloak.KeyCloakUserService;
import com.linh.UserService.model.keycloak.KeycloakCurrentUser;
import com.linh.UserService.model.request.LoginRequest;
import com.linh.UserService.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/user")
@Slf4j
public class UserController {

    private final KeyCloakUserService keyCloakUserService;
    private final IUserService userService;
    private final CurrentUserProvider currentUserProvider;

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(keyCloakUserService.login(request));
    }

    @PostMapping(path = "/refreshToken")
    public ResponseEntity<?> getRefreshToken(@RequestBody JSONObject request){
         log.info("Get new Access Token");
        return ResponseEntity.ok(keyCloakUserService.refreshToken(request.get("refreshToken").toString()));
    }

    @GetMapping(path = "/verify")
    public ResponseEntity<?> verify(@RequestParam("code") String verificationCode) {
        log.info("Controller: Xác thực tài khoản người dùng");
        boolean verified = userService.verify(verificationCode);
        if (verified) {
            log.info("Verify successfully !");
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://sec.cmcati.vn/ssa/pages/authentication/active-account-v2")).build();
        }
        log.info("Verification failed !");
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://sec.cmcati.vn/ssa/pages/miscellaneous/error")).build();
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createUser(@RequestBody KeycloakCurrentUser request) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        log.info("Create user");
        return ResponseEntity.ok(userService.create(request));
    }

    @GetMapping(path = "/current")
    public ResponseEntity<?> getCurrentUser(){
        return ResponseEntity.ok(currentUserProvider.getCurrentUser());
    }
}
