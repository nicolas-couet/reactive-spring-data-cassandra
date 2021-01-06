package com.acme.core.user.controller;

import com.acme.core.user.UserHelper;
import com.acme.core.user.dto.UserDTO;
import com.acme.core.user.dto.UserDetailsDTO;
import com.acme.core.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDetailsDTO> create(@RequestBody UserDTO dto) {
        logger.info("Receive request - create user {}/{}", dto.getEmail(), dto.getPassword());

        return userService.create(UserHelper.build(dto.getEmail(), dto.getPassword()))
                .map(user -> new UserDetailsDTO(user));
    }
}
