package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.BaseTimeEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("글 번호(PK)")
    @Column(name = "bd_no", updatable = false, nullable = false)
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
    private final List<Reply> reply = new ArrayList<>();

    public Board(Integer bdNo, Account account, String bdTitle, String bdCategory, String bdContent, Integer bdViews,
                 Integer bdFavorite, Integer bdReplyCount, String bdThumbnail) {
        this.bdNo = bdNo;
        this.account = account;
        this.bdTitle = bdTitle;
        this.bdCategory = bdCategory;
        this.bdContent = bdContent;
        this.bdViews = bdViews;
        this.bdFavorite = bdFavorite;
        this.bdReplyCount = bdReplyCount;
        this.bdThumbnail = bdThumbnail;
    }

    public Board() {
    }

    public static Board createBoardWithThumbnail(Account account, Board board, String boardThumbnail) {
        return Board.builder().bdNo(board.getBdNo()).account(account).bdTitle(board.getBdTitle())
                .bdCategory(board.getBdCategory()).bdContent(board.getBdContent()).bdViews(board.getBdViews())
                .bdFavorite(board.getBdFavorite()).bdReplyCount(board.getBdReplyCount()).bdThumbnail(boardThumbnail)
                .build();
    }

    public static BoardBuilder builder() {
        return new BoardBuilder();
    }

    public Integer getBdNo() {
        return this.bdNo;
    }

    public Account getAccount() {
        return this.account;
    }

    public String getBdTitle() {
        return this.bdTitle;
    }

    public String getBdCategory() {
        return this.bdCategory;
    }

    public String getBdContent() {
        return this.bdContent;
    }

    public Integer getBdViews() {
        return this.bdViews;
    }

    public Integer getBdFavorite() {
        return this.bdFavorite;
    }

    public Integer getBdReplyCount() {
        return this.bdReplyCount;
    }

    public String getBdThumbnail() {
        return this.bdThumbnail;
    }

    public List<Reply> getReply() {
        return this.reply;
    }

    public static class BoardBuilder {
        private Integer bdNo;
        private Account account;
        private String bdTitle;
        private String bdCategory;
        private String bdContent;
        private Integer bdViews;
        private Integer bdFavorite;
        private Integer bdReplyCount;
        private String bdThumbnail;

        BoardBuilder() {
        }

        public BoardBuilder bdNo(Integer bdNo) {
            this.bdNo = bdNo;
            return this;
        }

        public BoardBuilder account(Account account) {
            this.account = account;
            return this;
        }

        public BoardBuilder bdTitle(String bdTitle) {
            this.bdTitle = bdTitle;
            return this;
        }

        public BoardBuilder bdCategory(String bdCategory) {
            this.bdCategory = bdCategory;
            return this;
        }

        public BoardBuilder bdContent(String bdContent) {
            this.bdContent = bdContent;
            return this;
        }

        public BoardBuilder bdViews(Integer bdViews) {
            this.bdViews = bdViews;
            return this;
        }

        public BoardBuilder bdFavorite(Integer bdFavorite) {
            this.bdFavorite = bdFavorite;
            return this;
        }

        public BoardBuilder bdReplyCount(Integer bdReplyCount) {
            this.bdReplyCount = bdReplyCount;
            return this;
        }

        public BoardBuilder bdThumbnail(String bdThumbnail) {
            this.bdThumbnail = bdThumbnail;
            return this;
        }

        public Board build() {
            return new Board(this.bdNo, this.account, this.bdTitle, this.bdCategory, this.bdContent, this.bdViews, this.bdFavorite, this.bdReplyCount, this.bdThumbnail);
        }

        public String toString() {
            return "Board.BoardBuilder(bdNo=" + this.bdNo + ", account=" + this.account + ", bdTitle=" + this.bdTitle + ", bdCategory=" + this.bdCategory + ", bdContent=" + this.bdContent + ", bdViews=" + this.bdViews + ", bdFavorite=" + this.bdFavorite + ", bdReplyCount=" + this.bdReplyCount + ", bdThumbnail=" + this.bdThumbnail + ")";
        }
    }
}