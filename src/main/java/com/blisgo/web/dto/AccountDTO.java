package com.blisgo.web.dto;

import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@RecordBuilder
public record AccountDTO(
        Integer memNo,
        @NotNull(message = "닉네임을 입력해주세요")
        String nickname,
        @Email(message = "유효하지 않은 메일 값입니다")
        String email,
        String pass,
        @PositiveOrZero(message = "포인트는 0 또는 양수여야 합니다")
        Integer memPoint,
        String profileImage,
        @Null(message = "Controller단에서 계산되는 값입니다.")
        LocalDateTime createdDate,
        // FIXME 실제로 적용되지 않음
        @Null(message = "Controller단에서 계산되는 값입니다.")
        LocalDateTime modifiedDate


) {
    public static AccountDTO addAccount(AccountDTO accountDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String default_profile_img = "https://ui-avatars.com/api/?background=random&name=" + accountDTO.email();
        return AccountDTOBuilder.builder().memNo(accountDTO.memNo()).nickname(accountDTO.nickname()).email(accountDTO.email())
                .pass(bCryptPasswordEncoder.encode(accountDTO.pass())).memPoint(accountDTO.memPoint()).profileImage(default_profile_img).build();
    }
}