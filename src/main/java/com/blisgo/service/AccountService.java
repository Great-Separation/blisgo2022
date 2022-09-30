package com.blisgo.service;

import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.DogamDTO;

import java.util.List;

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
    AccountDTO findAccount(AccountDTO accountDTO);

    /**
     * 이메일 중복 확인 메서드
     * @param userDTO 사용자
     * @return 중복 여부
     */
    // public int emailCheck(AccountDTO userDTO);

    /**
     * 회원 탈퇴 메서드
     *
     * @param accountDTO 사용자
     * @return 회원 탈퇴
     */
    boolean removeAccount(AccountDTO accountDTO);

    /**
     * 회원 닉네임 변경 메서드
     *
     * @param accountDTO 사용자
     * @return 회원정보 수정
     */
    boolean modifyAccountNickname(AccountDTO accountDTO);

    /**
     * 회원 비밀번호 변경 메서드
     *
     * @param accountDTO 사용자
     * @param newPass 신규 비밀번호
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
     * @param accountDTO 사용자
     * @param profile_img_url 신규 프로필 이미지 TODO [modifyUserProfileImg] 실행 결과 반드시 반환
     */
    void modifyAccountProfileImg(AccountDTO accountDTO, String profile_img_url);

    /**
     * 회원가입시 약관 동의서 불러오는 메서드
     *
     * @return 약관 동의서
     */
    String findTermsOfAgreement();
}
