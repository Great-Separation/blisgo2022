package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.BaseTimeEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

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
    private final List<Reply> reply = new ArrayList<>();

    @OneToMany(mappedBy = "account", orphanRemoval = true)
    private final List<Board> board = new ArrayList<>();

    @OneToMany(mappedBy = "account", orphanRemoval = true)
    private final List<Dogam> dogam = new ArrayList<>();

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

    public static AccountBuilder builder() {
        return new AccountBuilder();
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

    public static class AccountBuilder {
        private Integer memNo;
        private String nickname;
        private String email;
        private String pass;
        private Integer memPoint;
        private String profileImage;
        private String provider;
        private String providerId;

        AccountBuilder() {
        }

        public AccountBuilder memNo(Integer memNo) {
            this.memNo = memNo;
            return this;
        }

        public AccountBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public AccountBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AccountBuilder pass(String pass) {
            this.pass = pass;
            return this;
        }

        public AccountBuilder memPoint(Integer memPoint) {
            this.memPoint = memPoint;
            return this;
        }

        public AccountBuilder profileImage(String profileImage) {
            this.profileImage = profileImage;
            return this;
        }

        public AccountBuilder provider(String provider) {
            this.provider = provider;
            return this;
        }

        public AccountBuilder providerId(String providerId) {
            this.providerId = providerId;
            return this;
        }

        public Account build() {
            return new Account(this.memNo, this.nickname, this.email, this.pass, this.memPoint, this.profileImage, this.provider, this.providerId);
        }

        public String toString() {
            return "Account.AccountBuilder(memNo=" + this.memNo + ", nickname=" + this.nickname + ", email=" + this.email + ", pass=" + this.pass + ", memPoint=" + this.memPoint + ", profileImage=" + this.profileImage + ", provider=" + this.provider + ", providerId=" + this.providerId + ")";
        }
    }
}