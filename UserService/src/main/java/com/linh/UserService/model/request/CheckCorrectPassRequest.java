package com.linh.UserService.model.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckCorrectPassRequest {

    private String username;
    private String oldPassword;
    private String newPassword;
}
