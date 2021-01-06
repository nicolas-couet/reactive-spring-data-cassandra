package com.acme.core.user.repository;

import com.acme.core.user.entity.User;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveCassandraRepository<User, UUID> {
    @Query("SELECT * FROM user WHERE email=:email")
    Mono<User> findByEmail(@Param("email") String email);
}
