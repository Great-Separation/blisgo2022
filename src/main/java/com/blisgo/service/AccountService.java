package com.blisgo.service;

import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.DogamDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    /**
     * 회원가입 회원 추가 메서드
     *
     * @param accountDTO 사용자
     * @return 최원 추가
     */
    boolean addAccount(AccountDTO accountDTO);

    /**
     * 회원 전체 정보 조회 메서드
     *
     * @param accountDTO 사용자
     * @return 단건
     */
    Optional<AccountDTO> findAccount(AccountDTO accountDTO);

    /**
     * 회원 탈퇴 메서드
     *
     * @param accountDTO 사용자
     * @return 회원 탈퇴
     */
    boolean removeAccount(AccountDTO accountDTO);

    /**
     * 회원 비밀번호 변경 메서드
     *
     * @param accountDTO 사용자
     * @param newPass    신규 비밀번호
     * @return 회원 비밀번호 변경
     */
    boolean modifyAccountPass(AccountDTO accountDTO, String newPass);

    /**
     * 도감 목록 조회 메서드
     *
     * @param accountDTO 사용자
     * @return 다건
     */
    List<DogamDTO> findDogam(AccountDTO accountDTO);

    /**
     * 도감 더보기 메서드
     *
     * @param accountDTO 사용자
     * @return 다건
     */
    List<DogamDTO> findDogamMore(AccountDTO accountDTO);

    /**
     * 회원 프로필 이미지 변경 메서드
     *
     * @param accountDTO      사용자
     * @param profile_img 신규 프로필 이미지
     */
    boolean modifyAccountProfileImg(AccountDTO accountDTO, MultipartFile profile_img);

    /**
     * 회원가입시 약관 동의서 불러오는 메서드
     *
     * @return 약관 동의서
     */
    String findTermsOfAgreement();
}
