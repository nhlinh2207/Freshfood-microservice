package com.linh.UserService.service;

import com.linh.UserService.model.keycloak.KeycloakCurrentUser;
import com.linh.UserService.model.rponse.UserDto;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public interface IUserService {

    boolean verify(String verificationCode);

    UserDto create(KeycloakCurrentUser req) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException;

    void delete(Long userId);
}
