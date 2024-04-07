package com.linh.EmailService.model;

import lombok.*;
import org.springframework.stereotype.Service;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailDto {

    private String firstName;
    private String lastName;
    private String emailContent;

}
