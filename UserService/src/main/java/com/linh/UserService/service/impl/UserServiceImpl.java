package com.linh.UserService.service.impl;

import com.linh.UserService.config.keycloak.CurrentUserProvider;
import com.linh.UserService.config.keycloak.KeyCloakUserService;
import com.linh.UserService.entity.User;
import com.linh.UserService.model.keycloak.KeycloakCurrentUser;
import com.linh.UserService.model.rponse.UserDto;
import com.linh.UserService.repository.IUserRepository;
import com.linh.UserService.service.IUserService;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import javax.el.ELException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final KeyCloakUserService keyCloakUserService;
    private final IUserRepository userRepository;
    private final CurrentUserProvider currentUserProvider;
    @Override
    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null) {
            return false;
        }else {
            keyCloakUserService.enableUser(user.getKeyCloakUserId());
            return true;
        }
    }

    @Override
    public UserDto create(KeycloakCurrentUser req) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        try{
            KeycloakCurrentUser currentUser = keyCloakUserService.userRegister(req);
            SimpleDateFormat smf = new SimpleDateFormat("dd-MM-yyyy");
            if(req.getUsername()==null || req.getEmail() == null || req.getPassword() == null || req.getRole() ==null)
            {
                throw new Exception("Missing information");
            }
            if (userRepository.findByEmail(req.getEmail()) != null || userRepository.findByUsername(req.getUsername()) != null || !currentUser.getStatus().equals("Created")) {
                keyCloakUserService.deleteUser(currentUser.getUserId());
                throw new Exception("username or email is duplicate");
            }
            // Create verify code
            String verificationCode = RandomString.make(64);
            User user = this.userRepository.saveAndFlush(
                    User.builder()
                            .username(req.getUsername())
                            .firstName(req.getFirstName())
                            .lastName(req.getLastName())
                            .fullName(req.getFullName()==null? req.getFirstName()+" "+ req.getLastName(): req.getFullName())
                            .birthday(smf.parse(req.getBirthday()))
                            .phoneNumber(req.getMobile())
                            .keyCloakUserId(currentUser.getUserId())
                            .email(currentUser.getEmail())
                            .gender(req.getGender())
                            .isNew(true)
                            .createdTime(new Date())
                            .updateTime(new Date())
                            .verificationCode(verificationCode)
                            .build()
            );

            return UserDto.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .phoneNumber(user.getPhoneNumber())
                    .keycloakUserId(user.getKeyCloakUserId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .gender(user.getGender())
                    .createTime(smf.format(user.getCreatedTime()))
                    .updateTime(smf.format(user.getUpdateTime()))
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void delete(Long userId) {
        try{
            User currentUser = userRepository.findByUsername(currentUserProvider.getCurrentUser().getUsername());
            User userToDelete = userRepository.findById(userId).orElseThrow(
                    () -> new ELException("Can not find user with id : "+userId)
            );
            // Delete in keycloak
            keyCloakUserService.deleteUser(userToDelete.getKeyCloakUserId());
            // Delete in database
            userRepository.deleteById(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
