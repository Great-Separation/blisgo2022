package com.blisgo.domain.repository.impl;

import com.blisgo.domain.entity.Dictionary;
import com.blisgo.domain.entity.Dogam;
import com.blisgo.domain.entity.User;
import com.blisgo.domain.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.blisgo.domain.entity.QDogam.dogam;
import static com.blisgo.domain.entity.QUser.user;

@Transactional
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JPAQueryFactory jpaQueryFactory;

    private final EntityManager entityManager;

    public UserRepositoryImpl(JPAQueryFactory jpaQueryFactory, EntityManager entityManager) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.entityManager = entityManager;
    }

    @Modifying
    @Override
    public void insertUser(User userEntity) {
        entityManager.persist(userEntity);
    }

    @Override
    public User selectUser(User userEntity) {
        return jpaQueryFactory.selectFrom(user).where(user.email.eq(userEntity.getEmail())).fetchOne();
    }

//	@Override
//	public int emailCheck(User userEntity) {
//		return jpaQueryFactory.selectFrom(user).where(user.email.eq(userEntity.getEmail())).fetch().size();
//	}

    @Modifying
    @Override
    public long updateNickname(User userEntity) {
        return jpaQueryFactory.update(user).where(user.email.eq(userEntity.getEmail()))
                .set(user.nickname, userEntity.getNickname()).execute();
    }

    @Modifying
    @Override
    public void deleteAccount(User userEntity) {
        jpaQueryFactory.delete(user).where(user.memNo.eq(userEntity.getMemNo())).execute();

    }

    @Modifying
    @Override
    public long updatePassword(User userEntity, String newPass) {
        return jpaQueryFactory.update(user).set(user.pass, newPass).where(user.email.eq(userEntity.getEmail()))
                .execute();
    }

    @Override
    public List<Dogam> selectDogamList(User userEntity, int index, int limit) {
        var tuple = jpaQueryFactory
                .select(dogam.user.memNo, dogam.dictionary.dicNo, dogam.dictionary.name,
                        dogam.dictionary.thumbnail)
                .from(dogam).innerJoin(dogam.dictionary).where(dogam.user.memNo.eq(userEntity.getMemNo())).offset(index).limit(limit).fetch();

        List<Dogam> rs = new ArrayList<>();
        for (var row : tuple) {
            User u = User.builder().memNo(row.get(dogam.user.memNo)).build();
            Dictionary d = Dictionary.builder().dicNo(row.get(dogam.dictionary.dicNo))
                    .name(row.get(dogam.dictionary.name)).thumbnail(row.get(dogam.dictionary.thumbnail)).build();

            rs.add(new Dogam(u, d));
        }

        return rs;
    }

    @Modifying
    @Override
    public void updateProfileImg(User userEntity, String profile_img_url) {
        jpaQueryFactory.update(user).set(user.profileImage, profile_img_url).where(user.email.eq(userEntity.getEmail()))
                .execute();
    }

}
