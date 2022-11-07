package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.BaseTimeEntity;
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
public class Dogam extends BaseTimeEntity {
    @Id
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "mem_no", nullable = false)
    @Comment("회원 번호(PK, FK)")
    private Account account;

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "dic_no", nullable = false)
    @Comment("사전 번호(PK, FK)")
    private Dictionary dictionary;

    @Builder
    public Dogam(Account account, Dictionary dictionary) {
        this.account = account;
        this.dictionary = dictionary;
    }
}

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class DogamPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Account account;
    private Dictionary dictionary;
}