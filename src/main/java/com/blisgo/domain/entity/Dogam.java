package com.blisgo.domain.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Entity
@IdClass(DogamPK.class)
public class Dogam {
	@Id
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "mem_no", nullable = false)
	@Comment("회원 번호(PK, FK)")
	private User user;

	@Id
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "dic_no", nullable = false)
	@Comment("사전 번호(PK, FK)")
	private Dictionary dictionary;

	@Builder
	public Dogam(User user, Dictionary dictionary) {
		this.user = user;
		this.dictionary = dictionary;
	}
}

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class DogamPK implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private User user;
	private Dictionary dictionary;
}