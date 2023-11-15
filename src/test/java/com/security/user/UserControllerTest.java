package com.security.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserControllerTest {

    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<ChangePasswordRequest> changePasswordRequestJacksonTester;

    @Test
    @DisplayName("changePassword should return 200")
    void changePassword() throws Exception {

        BDDMockito
                .doNothing()
                .when(userService)
                .changePassword(ArgumentMatchers.any(), ArgumentMatchers.any());

        MockHttpServletResponse response = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/users")
                                .with(SecurityMockMvcRequestPostProcessors
                                        .user("user-test@mail.com")
                                        .password("user")
                                        .authorities(new SimpleGrantedAuthority("ROLE_USER")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(changePasswordRequestJacksonTester
                                        .write(
                                                ChangePasswordRequest
                                                        .builder()
                                                        .currentPassword("user")
                                                        .newPassword("1234567")
                                                        .confirmationPassword("1234567")
                                                        .build()
                                        )
                                        .getJson())
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .isOk()
                )
                .andReturn()
                .getResponse();

        Assertions
                .assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("changePassword should return ResponseStatusException when password is wrong")
    void changePasswordShouldReturnResponseStatusExceptionWhenPasswordIsWrong() throws Exception {

        BDDMockito
                .doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password"))
                .when(userService)
                .changePassword(ArgumentMatchers.any(), ArgumentMatchers.any());

        MockHttpServletResponse response = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/users")
                                .with(SecurityMockMvcRequestPostProcessors
                                        .user("user-test@mail.com")
                                        .password("user")
                                        .authorities(new SimpleGrantedAuthority("ROLE_USER")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(changePasswordRequestJacksonTester
                                        .write(
                                                ChangePasswordRequest
                                                        .builder()
                                                        .currentPassword("user-wrong-password")
                                                        .newPassword("1234567")
                                                        .confirmationPassword("1234567")
                                                        .build()
                                        )
                                        .getJson())
                )
                .andReturn()
                .getResponse();

        Assertions
                .assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("changePassword should return ResponseStatusException when password is not the same")
    void changePasswordShouldReturnResponseStatusExceptionWhenPasswordIsNotTheSame() throws Exception {

        BDDMockito
                .doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password are not the same"))
                .when(userService)
                .changePassword(ArgumentMatchers.any(), ArgumentMatchers.any());

        MockHttpServletResponse response = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/api/v1/users")
                                .with(SecurityMockMvcRequestPostProcessors
                                        .user("user-test@mail.com")
                                        .password("user")
                                        .authorities(new SimpleGrantedAuthority("ROLE_USER")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(changePasswordRequestJacksonTester
                                        .write(
                                                ChangePasswordRequest
                                                        .builder()
                                                        .currentPassword("user")
                                                        .newPassword("123456789")
                                                        .confirmationPassword("1234567")
                                                        .build()
                                        )
                                        .getJson())
                )
                .andReturn()
                .getResponse();

        Assertions
                .assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}