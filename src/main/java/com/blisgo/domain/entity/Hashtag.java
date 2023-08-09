package com.blisgo.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serial;
import java.io.Serializable;

@Entity
@IdClass(HashtagPK.class)
public class Hashtag {

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "dic_no", nullable = false, updatable = false, referencedColumnName = "dic_no")
    @Comment("사전 번호(FK)")
    private Dictionary dictionary;

    @Id
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "guide_code", nullable = false, updatable = false, referencedColumnName = "guide_code", columnDefinition = "enum('Bu','C','Ca','Cl','Gl','HA','Ir','Me','NF','OB','PB','PF','Pa','Pl','Pr','SM','St','Vi')")
    @Comment("가이드 코드(FK)")
    private Guide guide;

    public Hashtag(Dictionary dictionary, Guide guide) {
        this.dictionary = dictionary;
        this.guide = guide;
    }

    public Hashtag() {
    }

    public static HashtagBuilder builder() {
        return new HashtagBuilder();
    }

    public Dictionary getDictionary() {
        return this.dictionary;
    }

    public Guide getGuide() {
        return this.guide;
    }

    public static class HashtagBuilder {
        private Dictionary dictionary;
        private Guide guide;

        HashtagBuilder() {
        }

        public HashtagBuilder dictionary(Dictionary dictionary) {
            this.dictionary = dictionary;
            return this;
        }

        public HashtagBuilder guide(Guide guide) {
            this.guide = guide;
            return this;
        }

        public Hashtag build() {
            return new Hashtag(this.dictionary, this.guide);
        }

        public String toString() {
            return "Hashtag.HashtagBuilder(dictionary=" + this.dictionary + ", guide=" + this.guide + ")";
        }
    }
}

class HashtagPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Dictionary dictionary;
    private Guide guide;

    public HashtagPK(Dictionary dictionary, Guide guide) {
        this.dictionary = dictionary;
        this.guide = guide;
    }

    public HashtagPK() {
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof HashtagPK)) return false;
        final HashtagPK other = (HashtagPK) o;
        if (!other.canEqual(this)) return false;
        final Object this$dictionary = this.dictionary;
        final Object other$dictionary = other.dictionary;
        if (this$dictionary == null ? other$dictionary != null : !this$dictionary.equals(other$dictionary))
            return false;
        final Object this$guide = this.guide;
        final Object other$guide = other.guide;
        if (this$guide == null ? other$guide != null : !this$guide.equals(other$guide)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof HashtagPK;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $dictionary = this.dictionary;
        result = result * PRIME + ($dictionary == null ? 43 : $dictionary.hashCode());
        final Object $guide = this.guide;
        result = result * PRIME + ($guide == null ? 43 : $guide.hashCode());
        return result;
    }
}