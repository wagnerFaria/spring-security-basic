package com.security.user;

import com.security.config.ApplicationConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationConfig applicationConfig;

    @Test
    @WithUserDetails("user-changepassword-test@mail.com")
    void changePassword() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        var request = ChangePasswordRequest
                .builder()
                .currentPassword("user")
                .newPassword("1234567")
                .confirmationPassword("1234567")
                .build();

        userService.changePassword(request, authentication);
    }

    @Test
    @WithUserDetails("user-test@mail.com")
    void changePasswordWithWrongPasswordShouldReturnIllegalStateException() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        var request = ChangePasswordRequest
                .builder()
                .currentPassword("user1")
                .newPassword("1234567")
                .confirmationPassword("1234567")
                .build();

        Assertions
                .assertThatThrownBy(() -> userService.changePassword(request, authentication))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @WithUserDetails("user-test@mail.com")
    void changePasswordWithWrongConfirmationPasswordShouldReturnIllegalStateException() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        var request = ChangePasswordRequest
                .builder()
                .currentPassword("user")
                .newPassword("1234567")
                .confirmationPassword("12345678")
                .build();

        Assertions
                .assertThatThrownBy(() -> userService.changePassword(request, authentication))
                .isInstanceOf(IllegalStateException.class);
    }
}