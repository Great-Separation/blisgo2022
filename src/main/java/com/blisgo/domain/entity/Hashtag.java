package com.blisgo.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
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