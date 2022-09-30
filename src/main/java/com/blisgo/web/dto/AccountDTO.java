package com.blisgo.web.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountDTO {
	private Integer memNo;
	@NotNull(message = "닉네임을 입력해주세요")
	private String nickname;
	@Email(message = "유효하지 않은 메일 값입니다")
	private String email;
	@NotNull(message = "비밀번호를 입력해주세요")
	private String pass;
	@PositiveOrZero(message = "포인트는 0 또는 양수여야 합니다")
	private Integer memPoint;
	private String profileImage;
	@Null(message = "Controller단에서 계산되는 값입니다.")
	private LocalDateTime createdDate;
	@Null(message = "Controller단에서 계산되는 값입니다.")
	private LocalDateTime modifiedDate;

	@Builder
	public AccountDTO(Integer memNo, String nickname, String email, String pass, Integer memPoint, String profileImage,
					  LocalDateTime createdDate) {
		this.memNo = memNo;
		this.nickname = nickname;
		this.email = email;
		this.pass = pass;
		this.memPoint = memPoint;
		this.profileImage = profileImage;
		this.createdDate = createdDate;
	}
}
