package com.blisgo.domain;

import com.blisgo.domain.entity.Dogam;
import com.blisgo.domain.entity.User;
import com.blisgo.domain.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class UserRepositoryTest {

	@Autowired
	UserRepositoryImpl userRepository;

	static User user;
	static User user_new;

	@BeforeAll
	static void createUser() {
		user = User.builder().memNo(1).email("okjaeook98@gmail.com").pass("dongyang").build();
		user_new = User.builder().email("test@test.com").nickname("test").pass("test1234").build();
	}

	@Test
	@DisplayName("회원가입 잘 되는가")
	void insertUserTest() {
		userRepository.insertUser(user_new);
	}

	@Test
	@DisplayName("회원가입 했을 때 memPoint 값이 삽입되었는가?")
	void selectUserTest() {
		User result = userRepository.selectUser(user);
		System.out.println("memPoint>" + result.getMemPoint());
		assertThat(result.getMemPoint()).isNotNull();
	}

	@Test
	@DisplayName("사용자 도감 부분 컬럼 조회 되는가")
	void selectDogamTest() {
		List<Dogam> result = userRepository.selectDogamList(user, 0, 12);
		var rs = result.get(0);
		System.out.println("dogam dic_no>" + rs.getDictionary().getDicNo());
		System.out.println("dogam name>" + rs.getDictionary().getName());
		System.out.println("dogam thumbnail>" + rs.getDictionary().getThumbnail());
	}

}
