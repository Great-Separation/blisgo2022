package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@DynamicInsert
@Entity
public class Account extends BaseTimeEntity {
	@Id
	@GeneratedValue
	@Comment("회원 번호(PK)")
	private Integer memNo;

	@Column(nullable = false, length = 45)
	@Comment("회원 닉네임")
	private String nickname;

	@Column(nullable = false, length = 45)
	@Comment("회원 이메일")
	private String email;

	@Column(nullable = false, length = 200)
	@Comment("회원 비밀번호")
	private String pass;

	@ColumnDefault("0")
	@Comment("회원 포인트")
	private Integer memPoint;

	@Column(length = 100)
	@Comment("회원 프로필 이미지")
	private String profileImage;

	@OneToMany(mappedBy = "account", orphanRemoval = true)
	private final List<Reply> reply = new ArrayList<>();

	@OneToMany(mappedBy = "account", orphanRemoval = true)
	private final List<Board> board = new ArrayList<>();

	@OneToMany(mappedBy = "account", orphanRemoval = true)
	private final List<Dogam> dogam = new ArrayList<>();

	@Builder
	public Account(Integer memNo, String nickname, String email, String pass, Integer memPoint, String profileImage) {
		this.memNo = memNo;
		this.nickname = nickname;
		this.email = email;
		this.pass = pass;
		this.memPoint = memPoint;
		this.profileImage = profileImage;
	}

	public static Account createAccount(Account account, String default_profile_img) {
		return Account.builder().memNo(account.getMemNo()).nickname(account.getNickname()).email(account.getEmail())
				.pass(account.getPass()).memPoint(account.getMemPoint()).profileImage(default_profile_img).build();
	}

}
