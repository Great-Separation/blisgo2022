package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
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

    @Builder
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

    public static Board createBoardWithThumbnail(Account account, Board board, String boardThumbnail) {
        return Board.builder().bdNo(board.getBdNo()).account(account).bdTitle(board.getBdTitle())
                .bdCategory(board.getBdCategory()).bdContent(board.getBdContent()).bdViews(board.getBdViews())
                .bdFavorite(board.getBdFavorite()).bdReplyCount(board.getBdReplyCount()).bdThumbnail(boardThumbnail)
                .build();
    }

}