package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.BaseTimeEntity;
import com.blisgo.web.dto.AccountDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@DynamicInsert
@Entity
public class Account extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mem_no", updatable = false, nullable = false)
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

    @Column(name = "mem_point")
    @ColumnDefault("0")
    @Comment("회원 포인트")
    private Integer memPoint;

    @Column(length = 100)
    @Comment("회원 프로필 이미지")
    private String profileImage;

    @OneToMany(mappedBy = "account", orphanRemoval = true)
    private List<Reply> reply = new ArrayList<>();

    @OneToMany(mappedBy = "account", orphanRemoval = true)
    private List<Board> board = new ArrayList<>();

    @OneToMany(mappedBy = "account", orphanRemoval = true)
    private List<Dogam> dogam = new ArrayList<>();

    // oauth
    @Comment("OAuth2 제공자")
    private String provider;
    @Comment("OAuth2 제공 ID")
    private String providerId;

    public Account(Integer memNo, String nickname, String email, String pass, Integer memPoint, String profileImage, String provider, String providerId) {
        this.memNo = memNo;
        this.nickname = nickname;
        this.email = email;
        this.pass = pass;
        this.memPoint = memPoint;
        this.profileImage = profileImage;
        this.provider = provider;
        this.providerId = providerId;
    }

    public Account() {
    }

    private Account(Builder builder) {
        memNo = builder.memNo;
        nickname = builder.nickname;
        email = builder.email;
        pass = builder.pass;
        memPoint = builder.memPoint;
        setProfileImage(builder.profileImage);
        reply = builder.reply;
        board = builder.board;
        dogam = builder.dogam;
        provider = builder.provider;
        providerId = builder.providerId;
        createdDate = builder.createdDate;
        modifiedDate = builder.modifiedDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getMemNo() {
        return this.memNo;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPass() {
        return this.pass;
    }

    public Integer getMemPoint() {
        return this.memPoint;
    }

    public String getProfileImage() {
        return this.profileImage;
    }

    public List<Reply> getReply() {
        return this.reply;
    }

    public List<Board> getBoard() {
        return this.board;
    }

    public List<Dogam> getDogam() {
        return this.dogam;
    }

    public String getProvider() {
        return this.provider;
    }

    public String getProviderId() {
        return this.providerId;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public static Account addAccount(AccountDTO accountDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String default_profile_img = "https://ui-avatars.com/api/?background=random&name=" + accountDTO.email();
        return new Builder().memNo(accountDTO.memNo()).nickname(accountDTO.nickname()).email(accountDTO.email())
                .pass(bCryptPasswordEncoder.encode(accountDTO.pass())).memPoint(accountDTO.memPoint()).profileImage(default_profile_img).build();
    }

    public static final class Builder {
        private Integer memNo;
        private String nickname;
        private String email;
        private String pass;
        private Integer memPoint;
        private String profileImage;
        private List<Reply> reply;
        private List<Board> board;
        private List<Dogam> dogam;
        private String provider;
        private String providerId;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        private Builder() {
        }

        public Builder memNo(Integer val) {
            memNo = val;
            return this;
        }

        public Builder nickname(String val) {
            nickname = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder pass(String val) {
            pass = val;
            return this;
        }

        public Builder memPoint(Integer val) {
            memPoint = val;
            return this;
        }

        public Builder profileImage(String val) {
            profileImage = val;
            return this;
        }

        public Builder reply(List<Reply> val) {
            reply = val;
            return this;
        }

        public Builder board(List<Board> val) {
            board = val;
            return this;
        }

        public Builder dogam(List<Dogam> val) {
            dogam = val;
            return this;
        }

        public Builder provider(String val) {
            provider = val;
            return this;
        }

        public Builder providerId(String val) {
            providerId = val;
            return this;
        }

        public Builder createdDate(LocalDateTime val) {
            createdDate = val;
            return this;
        }

        public Builder modifiedDate(LocalDateTime val) {
            modifiedDate = val;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}