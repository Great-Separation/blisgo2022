package com.blisgo.domain.repository;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.entity.Dogam;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface AccountRepository extends Repository<Account, Integer> {
    boolean insertAccount(Account accountEntity);

    Account selectAccount(Account accountEntity);

    //public int emailCheck(Account accountEntity);

    boolean updateNickname(Account accountEntity);

    boolean deleteAccount(Account accountEntity);

    boolean updatePassword(Account accountEntity, String newPass);

    List<Dogam> selectDogamList(Account accountEntity, int index, int limit);

    boolean updateProfileImg(Account accountEntity, String profile_img_url);
}
