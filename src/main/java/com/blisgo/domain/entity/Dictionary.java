package com.blisgo.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Table(indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "engName"),
        @Index(columnList = "thumbnail")
})
@Entity
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dic_no",updatable = false, nullable = false)
    @Comment("폐기물 번호(PK)")
    private Integer dicNo;

    @Column(nullable = false, length = 45)
    @Comment("폐기물 이름")
    private String name;

    @Column(nullable = false, length = 45)
    @Comment("폐기물 영어 이름")
    private String engName;

    @Column(nullable = false, length = 45)
    @Comment("폐기물 대분류")
    private String category;

    @Column(nullable = false)
    @Comment("폐기물 인지도")
    private Integer popularity;

    @Column(nullable = false)
    @Comment("폐기물 조회 수")
    private Short hit;

    @Column(nullable = false, length = 200)
    @Comment("폐기물 대표 이미지")
    private String thumbnail;

    @Column(nullable = false, length = 200)
    @Comment("폐기물 처리 안내")
    private String treatment;

    @OneToMany(mappedBy = "dictionary", orphanRemoval = true)
    private List<Dogam> dogam = new ArrayList<>();

    @OneToMany(mappedBy = "dictionary", orphanRemoval = true)
    private List<Hashtag> hashtag = new ArrayList<>();

    public Dictionary(Integer dicNo, String name, String engName, String category, Integer popularity, Short hit,
                      String thumbnail, String treatment) {
        this.dicNo = dicNo;
        this.name = name;
        this.engName = engName;
        this.category = category;
        this.popularity = popularity;
        this.hit = hit;
        this.thumbnail = thumbnail;
        this.treatment = treatment;
    }

    public Dictionary() {
    }

    private Dictionary(Builder builder) {
        dicNo = builder.dicNo;
        name = builder.name;
        engName = builder.engName;
        category = builder.category;
        popularity = builder.popularity;
        hit = builder.hit;
        thumbnail = builder.thumbnail;
        treatment = builder.treatment;
        dogam = builder.dogam;
        hashtag = builder.hashtag;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getDicNo() {
        return this.dicNo;
    }

    public String getName() {
        return this.name;
    }

    public String getEngName() {
        return this.engName;
    }

    public String getCategory() {
        return this.category;
    }

    public Integer getPopularity() {
        return this.popularity;
    }

    public Short getHit() {
        return this.hit;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public String getTreatment() {
        return this.treatment;
    }

    public List<Dogam> getDogam() {
        return this.dogam;
    }

    public List<Hashtag> getHashtag() {
        return this.hashtag;
    }

    public static final class Builder {
        private Integer dicNo;
        private String name;
        private String engName;
        private String category;
        private Integer popularity;
        private Short hit;
        private String thumbnail;
        private String treatment;
        private List<Dogam> dogam;
        private List<Hashtag> hashtag;

        private Builder() {
        }

        public Builder dicNo(Integer val) {
            dicNo = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder engName(String val) {
            engName = val;
            return this;
        }

        public Builder category(String val) {
            category = val;
            return this;
        }

        public Builder popularity(Integer val) {
            popularity = val;
            return this;
        }

        public Builder hit(Short val) {
            hit = val;
            return this;
        }

        public Builder thumbnail(String val) {
            thumbnail = val;
            return this;
        }

        public Builder treatment(String val) {
            treatment = val;
            return this;
        }

        public Builder dogam(List<Dogam> val) {
            dogam = val;
            return this;
        }

        public Builder hashtag(List<Hashtag> val) {
            hashtag = val;
            return this;
        }

        public Dictionary build() {
            return new Dictionary(this);
        }
    }
}
