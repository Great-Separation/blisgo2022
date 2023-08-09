package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.BaseTimeEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.*;

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

    public static Reply createReply(Integer replyNo, Board board, Account account, String content) {
        return Reply.builder().replyNo(replyNo).board(board).account(account).content(content).build();
    }

    public static ReplyBuilder builder() {
        return new ReplyBuilder();
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

    public static class ReplyBuilder {
        private Integer replyNo;
        private Board board;
        private Account account;
        private String content;

        ReplyBuilder() {
        }

        public ReplyBuilder replyNo(Integer replyNo) {
            this.replyNo = replyNo;
            return this;
        }

        public ReplyBuilder board(Board board) {
            this.board = board;
            return this;
        }

        public ReplyBuilder account(Account account) {
            this.account = account;
            return this;
        }

        public ReplyBuilder content(String content) {
            this.content = content;
            return this;
        }

        public Reply build() {
            return new Reply(this.replyNo, this.board, this.account, this.content);
        }

        public String toString() {
            return "Reply.ReplyBuilder(replyNo=" + this.replyNo + ", board=" + this.board + ", account=" + this.account + ", content=" + this.content + ")";
        }
    }
}
