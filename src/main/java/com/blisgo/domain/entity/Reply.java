package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
public class Reply extends BaseTimeEntity {
	@Id
	@GeneratedValue
	@Column(name = "reply_no")
	@Comment("댓글 번호(PK)")
	private Integer replyNo;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "bd_no", nullable = false)
	@Comment("게시글 번호(FK)")
	private Board board;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "mem_no", nullable = false)
	@Comment("회원 번호(FK)")
	private Account account;

	@Column(nullable = false, length = 300)
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
