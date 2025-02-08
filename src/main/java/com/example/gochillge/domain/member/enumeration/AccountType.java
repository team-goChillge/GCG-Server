package com.example.gochillge.domain.member.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountType {

    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");

    private final String role;

}
