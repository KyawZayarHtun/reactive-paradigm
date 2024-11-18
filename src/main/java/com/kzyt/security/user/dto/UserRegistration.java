package com.kzyt.security.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistration {

    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Email is required!")
    @Email
    private String email;

    @NotBlank(message = "Password is required!")
    private String password;

    private String phoneNumber;

    private LocalDate birthDate;

    @NotNull(message = "Locked is required!")
    private Boolean locked;

    @NotBlank(message = "Role is required!")
    private String roleId;

}
