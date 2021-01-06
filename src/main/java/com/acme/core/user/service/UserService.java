package com.acme.core.user.service;

import com.acme.core.entity.EntityState;
import com.acme.core.entity.exception.PrimaryKeyConflictException;
import com.acme.core.manager.EntityService;
import com.acme.core.user.entity.User;
import com.acme.core.user.repository.UserRepository;
import com.acme.core.utils.Assert;
import com.acme.core.utils.DateUtils;
import com.acme.core.utils.QueryOptionUtils;
import com.acme.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.EntityWriteResult;
import org.springframework.data.cassandra.core.WriteResult;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserService extends EntityService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private UserRepository userRepository;

    public Mono<User> create(User user){
        Assert.notNull(user, "User must not be null");
        Assert.notEmpty(user.getEmail());
        Assert.notEmpty(user.getPassword());
        Assert.notNull(user.getState(), "User state must not be null");
        Assert.notNull(user.getCreationDate(), "User creation date must not be null");
        Assert.isTrue(User.VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail()).matches(),
                "Unexpected email value, validation failed");

        // normalize email case before writing
        user.setEmail(StringUtils.toLowerCase(user.getEmail()));

        logger.info("Create user with email={}", user.getEmail());

        // insert user using lightweight transaction to make sure that no duplicate can be inserted
        return getCassandraOperations().insert(user, QueryOptionUtils.strongConsistencyInsertOptions())
                .filter(WriteResult::wasApplied)
                .switchIfEmpty(Mono.error(new PrimaryKeyConflictException("Duplicate key on user creation with " +
                        "email="+user.getEmail())))
                .map(EntityWriteResult::getEntity);
    }

    public Mono<User> findByEmail(String email){
        Assert.notEmpty(email);
        return userRepository.findByEmail(email);
    }

    public Mono<Boolean> emailExists(String email){
        return findByEmail(email).flatMap(result -> Mono.just(new Boolean(true)))
                .switchIfEmpty(Mono.just(new Boolean(false)));
    }

    public Mono<User> update(User user){
        Assert.notNull(user, "User must not be null");
        user.setLastModificationDate(DateUtils.getCurrentDate());
        return userRepository.save(user);
    }

    public Mono<User> deactivate(User user){
        Assert.notNull(user, "User must not be null");
        user.setState(EntityState.INACTIVE);
        return update(user);
    }

    public Mono<User> delete(User user){
        Assert.notNull(user, "User must not be null");
        user.setState(EntityState.DELETED);
        return update(user);
    }
}
