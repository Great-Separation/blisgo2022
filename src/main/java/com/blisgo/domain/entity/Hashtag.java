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
@IdClass(HashtagPK.class)
public class Hashtag {

	@Id
	@ManyToOne(cascade = CascadeType.MERGE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "dic_no", nullable = false)
	@Comment("사전 번호(FK)")
	private Dictionary dictionary;

	@Id
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "guide_code", nullable = false)
	@Comment("사전 코드(FK)")
	private Guide guide;

	@Builder
	public Hashtag(Dictionary dictionary, Guide guide) {
		this.dictionary = dictionary;
		this.guide = guide;
	}
}

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class HashtagPK implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private Dictionary dictionary;
	private Guide guide;

}