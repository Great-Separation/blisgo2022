package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.BaseTimeEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Entity
@DynamicInsert
@DynamicUpdate
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bd_no", updatable = false, nullable = false)
    @Comment("글 번호(PK)")
    private Integer bdNo;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "mem_no", nullable = false, updatable = false, referencedColumnName = "mem_no")
    @Comment("회원 번호(FK)")
    private Account account;

    @Column(nullable = false, length = 45)
    @Comment("글 제목")
    private String bdTitle;

    @Column(length = 45)
    @Comment("글 분류(enum 일반, 공지)")
    private String bdCategory;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    @Comment("글 내용")
    private String bdContent;

    @ColumnDefault("0")
    @Comment("글 조회수")
    private Integer bdViews;

    @ColumnDefault("0")
    @Comment("글 좋아요")
    private Integer bdFavorite;

    @ColumnDefault("0")
    @Comment("댓글 수")
    private Integer bdReplyCount;

    @Comment("글 썸네일")
    private String bdThumbnail;

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Reply> reply = new ArrayList<>();

    public Board(Integer bdNo, Account account, String bdTitle, String bdCategory, String bdContent, Integer bdViews, Integer bdFavorite, Integer bdReplyCount, String bdThumbnail, List<Reply> reply) {
        this.bdNo = bdNo;
        this.account = account;
        this.bdTitle = bdTitle;
        this.bdCategory = bdCategory;
        this.bdContent = bdContent;
        this.bdViews = bdViews;
        this.bdFavorite = bdFavorite;
        this.bdReplyCount = bdReplyCount;
        this.bdThumbnail = bdThumbnail;
        this.reply = reply;
    }

    public Board() {
    }

    private Board(Builder builder) {
        bdNo = builder.bdNo;
        account = builder.account;
        bdTitle = builder.bdTitle;
        bdCategory = builder.bdCategory;
        bdContent = builder.bdContent;
        bdViews = builder.bdViews;
        bdFavorite = builder.bdFavorite;
        bdReplyCount = builder.bdReplyCount;
        bdThumbnail = builder.bdThumbnail;
        reply = builder.reply;
        createdDate = builder.createdDate;
        modifiedDate = builder.modifiedDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getBdNo() {
        return bdNo;
    }

    public Account getAccount() {
        return account;
    }

    public String getBdTitle() {
        return bdTitle;
    }

    public String getBdCategory() {
        return bdCategory;
    }

    public String getBdContent() {
        return bdContent;
    }

    public Integer getBdViews() {
        return bdViews;
    }

    public Integer getBdFavorite() {
        return bdFavorite;
    }

    public Integer getBdReplyCount() {
        return bdReplyCount;
    }

    public String getBdThumbnail() {
        return bdThumbnail;
    }

    public List<Reply> getReply() {
        return reply;
    }


    public static final class Builder {
        private Integer bdNo;
        private Account account;
        private String bdTitle;
        private String bdCategory;
        private String bdContent;
        private Integer bdViews;
        private Integer bdFavorite;
        private Integer bdReplyCount;
        private String bdThumbnail;
        private List<Reply> reply;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        private Builder() {
        }

        public Builder bdNo(Integer val) {
            bdNo = val;
            return this;
        }

        public Builder account(Account val) {
            account = val;
            return this;
        }

        public Builder bdTitle(String val) {
            bdTitle = val;
            return this;
        }

        public Builder bdCategory(String val) {
            bdCategory = val;
            return this;
        }

        public Builder bdContent(String val) {
            bdContent = val;
            return this;
        }

        public Builder bdViews(Integer val) {
            bdViews = val;
            return this;
        }

        public Builder bdFavorite(Integer val) {
            bdFavorite = val;
            return this;
        }

        public Builder bdReplyCount(Integer val) {
            bdReplyCount = val;
            return this;
        }

        public Builder bdThumbnail(String val) {
            bdThumbnail = val;
            return this;
        }

        public Builder reply(List<Reply> val) {
            reply = val;
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

        public Board build() {
            return new Board(this);
        }
    }
}