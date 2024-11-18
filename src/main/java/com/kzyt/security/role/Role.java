package com.kzyt.security.role;

import com.kzyt.security.role.dto.RoleCreate;
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

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "roles")
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = -3502242916752200971L;

    @Id
    private String id;

    private String name;

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updatedTime;

    public Role(RoleCreate roleCreate) {
        this.name = roleCreate.getName();
    }
}



