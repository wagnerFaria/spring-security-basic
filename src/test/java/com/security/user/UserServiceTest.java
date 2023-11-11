package com.security.user;

import ch.qos.logback.core.CoreConstants;
import com.security.config.ApplicationConfig;
import com.security.utils.UserTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    //    @MockBean
//    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationConfig applicationConfig;

    @BeforeEach
    void setUp() {
//        BDDMockito
//                .when(userRepository.findByEmail(ArgumentMatchers.anyString()))
//                .thenReturn(Optional.ofNullable(UserTestUtils.returnPersistedUser()));
    }

    @Test
    @WithUserDetails("user-test@mail.com")
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
}