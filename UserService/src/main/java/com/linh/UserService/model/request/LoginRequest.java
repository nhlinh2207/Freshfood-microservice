package com.linh.UserService.model.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String username;
    private String password;
}
