package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.BaseTimeEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Entity
@DynamicInsert
@DynamicUpdate
public class Reply extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_no", updatable = false, nullable = false)
    @Comment("댓글 번호(PK)")
    private Integer replyNo;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "bd_no", nullable = false, updatable = false, referencedColumnName = "bd_no")
    @Comment("게시글 번호(FK)")
    private Board board;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "mem_no", nullable = false, updatable = false, referencedColumnName = "mem_no")
    @Comment("회원 번호(FK)")
    private Account account;

    @Column(nullable = false, length = 1000)
    @Comment("댓글 내용")
    private String content;

    public Reply(Integer replyNo, Board board, Account account, String content) {
        this.replyNo = replyNo;
        this.board = board;
        this.account = account;
        this.content = content;
    }

    public Reply() {
    }

    private Reply(Builder builder) {
        replyNo = builder.replyNo;
        board = builder.board;
        account = builder.account;
        content = builder.content;
        createdDate = builder.createdDate;
        modifiedDate = builder.modifiedDate;
    }

    public static Reply createReply(Integer replyNo, int bdNo, int memNo, String content) {
        return Reply.builder()
                .replyNo(replyNo)
                .board(Board.builder()
                        .bdNo(bdNo)
                        .build())
                .account(Account.builder()
                        .memNo(memNo)
                        .build())
                .content(content).build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getReplyNo() {
        return this.replyNo;
    }

    public Board getBoard() {
        return this.board;
    }

    public Account getAccount() {
        return this.account;
    }

    public String getContent() {
        return this.content;
    }

    public static final class Builder {
        private Integer replyNo;
        private Board board;
        private Account account;
        private String content;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        private Builder() {
        }

        public Builder replyNo(Integer val) {
            replyNo = val;
            return this;
        }

        public Builder board(Board val) {
            board = val;
            return this;
        }

        public Builder account(Account val) {
            account = val;
            return this;
        }

        public Builder content(String val) {
            content = val;
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

        public Reply build() {
            return new Reply(this);
        }
    }
}
