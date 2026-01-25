package com.tf.core_service.model.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


@NoArgsConstructor
@Data
public class User {

    private String userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private boolean isEmailVerified;
    private String issuer;
    private String picture;
    private String phoneNumber;
    private Map<String, Object> claims;
    private List<String> roles;
    private String displayName;

}