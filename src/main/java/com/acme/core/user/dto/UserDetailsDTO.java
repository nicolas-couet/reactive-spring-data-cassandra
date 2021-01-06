package com.acme.core.user.dto;

import com.acme.core.user.entity.User;
import com.acme.core.utils.DateUtils;

public class UserDetailsDTO extends UserDTO{

    private String state;

    private String creationDate;

    public UserDetailsDTO(User user){
        super(user.getEmail(), user.getPassword());

        this.state = user.getState().name();
        this.creationDate = DateUtils.format(user.getCreationDate());
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
