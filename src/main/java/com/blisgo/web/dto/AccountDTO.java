package com.blisgo.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

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

    // TODO 실제로 적용되지 않음
    @Null(message = "Controller단에서 계산되는 값입니다.")
    private LocalDateTime modifiedDate;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Builder
    public AccountDTO(Integer memNo, String nickname, String email, String pass, Integer memPoint, String profileImage,
                      LocalDateTime createdDate) {
        this.memNo = memNo;
        this.nickname = nickname;
        this.email = email;
        this.pass = bCryptPasswordEncoder.encode(pass);
        this.memPoint = memPoint;
        this.profileImage = profileImage;
        this.createdDate = createdDate;
    }

    public static AccountDTO addAccount(AccountDTO accountDTO) {
        String default_profile_img = "https://ui-avatars.com/api/?background=random&name=" + accountDTO.getEmail();
        return AccountDTO.builder().memNo(accountDTO.getMemNo()).nickname(accountDTO.getNickname()).email(accountDTO.getEmail())
                .pass(accountDTO.getPass()).memPoint(accountDTO.getMemPoint()).profileImage(default_profile_img).build();
    }
}
