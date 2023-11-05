package com.alibou.security.user;

import com.alibou.security.utils.UserTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void persistUserShouldReturnUser() {
        var user = UserTestUtils
                .returnUserToPersist()
                .toBuilder()
                .password(passwordEncoder.encode("123456"))
                .role(Role.USER)
                .build();
        user = userRepository.save(user);
        Assertions
                .assertThat(user)
                .isNotNull();
        Assertions
                .assertThat(user.getId())
                .isNotNull();
        Assertions
                .assertThat(user.getFirstname())
                .isEqualTo(UserTestUtils
                        .returnUserToPersist()
                        .getFirstname());
        Assertions
                .assertThat(user.getLastname())
                .isEqualTo(UserTestUtils
                        .returnUserToPersist()
                        .getLastname());
        Assertions
                .assertThat(user.getEmail())
                .isEqualTo(UserTestUtils
                        .returnUserToPersist()
                        .getEmail());
        Assertions
                .assertThat(passwordEncoder.matches("123456", user.getPassword()));

    }

    @Test
    void findByEmail() {
        var user = userRepository.save(UserTestUtils
                .returnUserToPersist()
                .toBuilder()
                .email("findByEmailTest@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .role(Role.USER)
                .build());

        var userFound = userRepository.findByEmail(user.getEmail());
        Assertions
                .assertThat(userFound)
                .isPresent()
                .get()
                .isEqualTo(user);
    }

    @Test
    void updateUserShouldReturnUpdatedUser() {
        var user = userRepository.save(UserTestUtils
                .returnUserToPersist()
                .toBuilder()
                .email("updateUserShouldReturnUpdatedUser@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .role(Role.USER)
                .build());

        var userToUpdate = user
                .toBuilder()
                .firstname("Updated")
                .lastname("Updated")
                .build();

        var updatedUser = userRepository.save(userToUpdate);

        Assertions
                .assertThat(updatedUser)
                .isNotNull()
                .isEqualTo(userToUpdate);
    }

    @Test()
    void insertTwoRegistersWithTheSameEmailShouldReturnException() {
        var user = userRepository.save(UserTestUtils
                .returnUserToPersist()
                .toBuilder()
                .email("insertTwoRegistersWithTheSameEmailShouldReturnException@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .role(Role.USER)
                .build());
        Assertions
                .assertThatThrownBy(() -> userRepository.save(UserTestUtils
                        .returnUserToPersist()
                        .toBuilder()
                        .email("insertTwoRegistersWithTheSameEmailShouldReturnException@gmail.com")
                        .password(passwordEncoder.encode("654987"))
                        .role(Role.ADMIN)
                        .build()))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}