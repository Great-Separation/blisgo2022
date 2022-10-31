package com.blisgo.domain.repository.impl;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.entity.Dictionary;
import com.blisgo.domain.entity.Dogam;
import com.blisgo.domain.repository.AccountRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.blisgo.domain.entity.QAccount.account;
import static com.blisgo.domain.entity.QDogam.dogam;

@Repository
@Transactional
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final EntityManager entityManager;


    @Modifying
    @Override
    public boolean insertAccount(Account accountEntity) {
        entityManager.persist(accountEntity);
        return true;
    }

    @Override
    public Account selectAccount(Account accountEntity) {
        return jpaQueryFactory.selectFrom(account).where(account.email.eq(accountEntity.getEmail())).fetchOne();
    }

//	@Override
//	public int emailCheck(Account accountEntity) {
//		return jpaQueryFactory.selectFrom(account).where(account.email.eq(accountEntity.getEmail())).fetch().size();
//	}

    @Modifying
    @Override
    public boolean updateNickname(Account accountEntity) {
        return jpaQueryFactory.update(account).where(account.email.eq(accountEntity.getEmail()))
                .set(account.nickname, accountEntity.getNickname()).execute() > 0;
    }

    @Modifying
    @Override
    public boolean deleteAccount(Account accountEntity) {
        return jpaQueryFactory.delete(account).where(account.memNo.eq(accountEntity.getMemNo())).execute() > 0;
    }

    @Modifying
    @Override
    public boolean updatePassword(Account accountEntity, String newPass) {
        return jpaQueryFactory.update(account).set(account.pass, newPass).where(account.email.eq(accountEntity.getEmail()))
                .execute() > 0;
    }

    @Override
    public List<Dogam> selectDogamList(Account accountEntity, int index, int limit) {
        var tuple = jpaQueryFactory
                .select(dogam.account.memNo, dogam.dictionary.dicNo, dogam.dictionary.name,
                        dogam.dictionary.thumbnail)
                .from(dogam).innerJoin(dogam.dictionary).where(dogam.account.memNo.eq(accountEntity.getMemNo())).offset(index).limit(limit).fetch();

        List<Dogam> rs = new ArrayList<>();
        for (var row : tuple) {
            Account u = Account.builder().memNo(row.get(dogam.account.memNo)).build();
            Dictionary d = Dictionary.builder().dicNo(row.get(dogam.dictionary.dicNo))
                    .name(row.get(dogam.dictionary.name)).thumbnail(row.get(dogam.dictionary.thumbnail)).build();

            rs.add(new Dogam(u, d));
        }

        return rs;
    }

    @Modifying
    @Override
    public boolean updateProfileImg(Account accountEntity, String profile_img_url) {
        return jpaQueryFactory.update(account).set(account.profileImage, profile_img_url).where(account.email.eq(accountEntity.getEmail()))
                .execute() > 0;
    }

}
