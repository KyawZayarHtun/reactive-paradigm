package com.kzyt.security.user;

import com.kzyt.security.user.dto.UserRegistration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User implements Serializable {


    @Serial
    private static final long serialVersionUID = -4504272474912732198L;

    @Id
    private String id;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    private LocalDate birthDate;

    private boolean locked;

    private String roleId;


    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updatedTime;

    public User(UserRegistration registration) {
        this.name = registration.getName();
        this.email = registration.getEmail();
        this.password = registration.getPassword();
        this.phoneNumber = registration.getPhoneNumber();
        this.birthDate = registration.getBirthDate();
        this.locked = registration.getLocked();
        this.roleId = registration.getRoleId();
    }
}
