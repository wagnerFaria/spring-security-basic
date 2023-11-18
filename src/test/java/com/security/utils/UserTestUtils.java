package com.security.utils;

import com.security.user.Role;
import com.security.user.User;

public class UserTestUtils {


    public static User returnUserToPersist() {
        return User
                .builder()
                .firstname("User")
                .lastname("Test")
                .email("usertest@gamil.com")
                .role(Role.USER)
                .build();
    }

    public static User returnPersistedUser() {
        return User
                .builder()
                .id(1)
                .firstname("User")
                .lastname("User")
                .email("user-test@mail.com")
                .role(Role.USER)
                .build();
    }
}
