package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.BaseTimeEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serial;
import java.io.Serializable;

@Entity
@IdClass(DogamPK.class)
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

class DogamPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Account account;
    private Dictionary dictionary;

    public DogamPK(Account account, Dictionary dictionary) {
        this.account = account;
        this.dictionary = dictionary;
    }

    public DogamPK() {
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DogamPK)) return false;
        final DogamPK other = (DogamPK) o;
        if (!other.canEqual(this)) return false;
        final Object this$account = this.account;
        final Object other$account = other.account;
        if (this$account == null ? other$account != null : !this$account.equals(other$account)) return false;
        final Object this$dictionary = this.dictionary;
        final Object other$dictionary = other.dictionary;
        if (this$dictionary == null ? other$dictionary != null : !this$dictionary.equals(other$dictionary))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DogamPK;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $account = this.account;
        result = result * PRIME + ($account == null ? 43 : $account.hashCode());
        final Object $dictionary = this.dictionary;
        result = result * PRIME + ($dictionary == null ? 43 : $dictionary.hashCode());
        return result;
    }
}