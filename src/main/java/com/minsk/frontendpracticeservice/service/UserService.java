package com.minsk.frontendpracticeservice.service;

import com.minsk.frontendpracticeservice.domain.entity.User;
import com.minsk.frontendpracticeservice.domain.response.UserRequisitesProjection;


public interface UserService {

    User findByLogin(String login);

    UserRequisitesProjection findUserRequisitesProjectionById(String userId);

}
