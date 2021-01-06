package com.acme.core.user.service;

import com.acme.core.configuration.test.EmbeddedCassandraCoreIntegrationTest;
import com.acme.core.entity.EntityState;
import com.acme.core.entity.exception.PrimaryKeyConflictException;
import com.acme.core.user.UserHelper;
import com.acme.core.user.entity.User;
import com.acme.core.utils.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest extends EmbeddedCassandraCoreIntegrationTest {
    @Autowired
    private UserService userService;

    private final String expectedEmail="email@acme.com";
    private final String expectedPassword = "password";

    private final String invalidEmail = "email.acme.com";
    private final String unknownEmail = "unknown@acme.com";

    private User user;

    @BeforeAll
    public void init(){
        if (!isTestInitialized()){
            user = userService.create(UserHelper.build(
                    expectedEmail,
                    expectedPassword)).log().block();

            Assert.notNull(user, "User must not be null");

            setTestInitialized(true);
        }
    }

    @Test
    public void findByEmail(){
        StepVerifier.create(userService.findByEmail(expectedEmail).log())
        .assertNext(this::validate)
        .verifyComplete();
    }

    @Test
    public void createFailOnDuplicateEmail(){
        StepVerifier.create(userService.create(UserHelper.build("dup@acme.com", expectedPassword)).log()
            .then(userService.create(UserHelper.build("dup@acme.com", expectedPassword))).log()
        ).expectErrorMatches(PrimaryKeyConflictException.class::isInstance)
        .verify();
    }

    @Test
    public void createWithInvalidEmail(){
        assertThrows(IllegalArgumentException.class,
                () -> userService.create(UserHelper.build(
                invalidEmail,
                expectedPassword)).block()
        );
    }

    @Test
    public void deactivate(){
        StepVerifier.create(userService.deactivate(user).log())
                .assertNext(user -> Assert.isTrue(EntityState.INACTIVE.equals(user.getState()), "Unexpected user state"))
                .verifyComplete();
    }

    @Test
    public void delete(){
        StepVerifier.create(userService.delete(user).log())
                .assertNext(user -> Assert.isTrue(EntityState.DELETED.equals(user.getState()), "Unexpected user state"))
                .verifyComplete();
    }

    @Test
    public void emailExist(){
        StepVerifier.create(userService.emailExists(expectedEmail).log())
                .assertNext(bool -> Assert.isTrue(bool, "Unexpected value, email exists"))
                .verifyComplete();
    }

    @Test
    public void emailNotExist(){
        StepVerifier.create(userService.emailExists(unknownEmail).log())
                .assertNext(bool -> Assert.isTrue(!bool, "Unexpected value, email does not exist"))
                .verifyComplete();
    }
    
    private void validate(User user){
        Assert.notNull(user, "User must not be null");
        Assert.notEmpty(user.getEmail());
        Assert.isTrue(expectedEmail.equals(user.getEmail()), "Unexpected email");
        Assert.isTrue(expectedPassword.equals(user.getPassword()), "Unexpected password");
    }
}
