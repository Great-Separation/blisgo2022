package com.blisgo.domain.repository;

import com.blisgo.domain.entity.Dogam;
import com.blisgo.domain.entity.User;

import java.util.List;

public interface UserRepository {
    void insertUser(User userEntity);

    User selectUser(User userEntity);

    //public int emailCheck(User userEntity);

    long updateNickname(User userEntity);

    void deleteAccount(User userEntity);

    long updatePassword(User userEntity, String newPass);

    List<Dogam> selectDogamList(User userEntity, int index, int limit);

    void updateProfileImg(User userEntity, String profile_img_url);
}
