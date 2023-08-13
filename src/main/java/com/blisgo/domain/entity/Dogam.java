package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.BaseTimeEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    public static DogamBuilder builder() {
        return new DogamBuilder();
    }

    public Account getAccount() {
        return this.account;
    }

    public Dictionary getDictionary() {
        return this.dictionary;
    }

    public static class DogamBuilder {
        private Account account;
        private Dictionary dictionary;

        DogamBuilder() {
        }

        public DogamBuilder account(Account account) {
            this.account = account;
            return this;
        }

        public DogamBuilder dictionary(Dictionary dictionary) {
            this.dictionary = dictionary;
            return this;
        }

        public Dogam build() {
            return new Dogam(this.account, this.dictionary);
        }

        public String toString() {
            return "Dogam.DogamBuilder(account=" + this.account + ", dictionary=" + this.dictionary + ")";
        }
    }
}