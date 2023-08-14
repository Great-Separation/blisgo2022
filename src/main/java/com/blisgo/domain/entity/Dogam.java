package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.BaseTimeEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
public class Dogam extends BaseTimeEntity {
    @Id
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "mem_no", nullable = false, updatable = false, referencedColumnName = "mem_no")
    @Comment("회원 번호(PK, FK)")
    private Account account;

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "dic_no", nullable = false, updatable = false, referencedColumnName = "dic_no")
    @Comment("사전 번호(PK, FK)")
    private Dictionary dictionary;

    public Dogam(Account account, Dictionary dictionary) {
        this.account = account;
        this.dictionary = dictionary;
    }

    public Dogam() {
    }

    private Dogam(Builder builder) {
        account = builder.account;
        dictionary = builder.dictionary;
        createdDate = builder.createdDate;
        modifiedDate = builder.modifiedDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Account getAccount() {
        return this.account;
    }

    public Dictionary getDictionary() {
        return this.dictionary;
    }

    public static final class Builder {
        private Account account;
        private Dictionary dictionary;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        private Builder() {
        }

        public Builder account(Account val) {
            account = val;
            return this;
        }

        public Builder dictionary(Dictionary val) {
            dictionary = val;
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

        public Dogam build() {
            return new Dogam(this);
        }
    }
}