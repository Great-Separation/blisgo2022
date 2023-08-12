package com.blisgo.domain.repository;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.entity.Dogam;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends Repository<Account, Long> {
    boolean insertAccount(Account accountEntity);

    Optional<Account> selectAccount(String email);

    long deleteAccount(int memNo);

    long updatePassword(String email, String passNew);

    List<Dogam> selectDogamList(int memNo, int index, int limit);

    long updateProfileImg(String email, String profile_img_url);
}
