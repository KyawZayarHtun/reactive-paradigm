package com.kzyt.security.permission;

import com.kzyt.security.permission.dto.PermissionCreate;
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
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "permissions")
public class Permission implements Serializable {

    @Serial
    private static final long serialVersionUID = -6998879458629651736L;

    @Id
    private String id;

    private String name;

    private String description;

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updatedTime;

    public Permission(PermissionCreate permissionCreate) {
        this.name = permissionCreate.getName();
        this.description = permissionCreate.getDescription();
    }

}
