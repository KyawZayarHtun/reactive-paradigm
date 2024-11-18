package com.kzyt.security.rolePermission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignPermissions {

    private Set<String> permissionIds = new HashSet<>();

}
