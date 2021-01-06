package com.acme.core.user;

import com.acme.core.entity.EntityState;
import com.acme.core.user.entity.User;
import com.acme.core.utils.DateUtils;

public class UserHelper {

  public static User build(String email, String password) {
    User user = new User();

    user.setEmail(email);
    user.setPassword(password);
    user.setCreationDate(DateUtils.getCurrentDate());
    user.setLastModificationDate(DateUtils.getCurrentDate());
    user.setState(EntityState.ACTIVE);

    return user;
  }
}
