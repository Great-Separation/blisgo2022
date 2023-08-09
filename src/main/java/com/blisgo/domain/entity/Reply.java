package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

@Getter
@NoArgsConstructor
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

    @Builder
    public Reply(Integer replyNo, Board board, Account account, String content) {
        this.replyNo = replyNo;
        this.board = board;
        this.account = account;
        this.content = content;
    }

    public static Reply createReply(Integer replyNo, Board board, Account account, String content) {
        return Reply.builder().replyNo(replyNo).board(board).account(account).content(content).build();
    }
}
