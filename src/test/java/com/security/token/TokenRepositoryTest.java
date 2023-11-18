package com.security.token;

import com.security.user.User;
import com.security.user.UserRepository;
import com.security.utils.UserTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class TokenRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;

    @Test
    void findAllValidTokenByUserShouldReturnListOfTokensWithOneRegister() {
        User savedUser = userRepository.save(UserTestUtils.returnUserToPersist());

        Token token = Token
                .builder()
                .token("findAllValidTokenByUser-token")
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .user(savedUser)
                .build();
        Token savedToken = tokenRepository.save(token);

        List<Token> allValidTokenByUser = tokenRepository.findAllValidTokenByUser(savedUser.getId());

        Assertions
                .assertThat(allValidTokenByUser)
                .isNotEmpty();
        Assertions
                .assertThat(allValidTokenByUser
                        .get(0)
                        .getToken())
                .isEqualTo(savedToken.getToken());
    }

    @Test
    void findByTokenShouldReturnTheToken() throws RuntimeException {
        User savedUser = userRepository.save(UserTestUtils.returnUserToPersist());

        Token token = Token
                .builder()
                .token("findByTokenShouldReturnTheToken-token")
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .user(savedUser)
                .build();

        Token savedToken = tokenRepository.save(token);

        Token tokenFound = tokenRepository
                .findByToken(savedToken.getToken())
                .orElseThrow(() -> new RuntimeException("Token not found"));

        Assertions
                .assertThat(tokenFound.getToken())
                .isEqualTo(savedToken.getToken());
        Assertions
                .assertThat(tokenFound.getId())
                .isEqualTo(savedToken.getId());
    }
}