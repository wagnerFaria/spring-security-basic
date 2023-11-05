package com.alibou.security.utils;

import com.alibou.security.user.User;

public class UserTestUtils {


    public static User returnUserToPersist() {
        return User
                .builder()
                .firstname("User")
                .lastname("Test")
                .email("usertest@gamil.com")
                .build();
    }
}
