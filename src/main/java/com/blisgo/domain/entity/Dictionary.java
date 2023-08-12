package com.blisgo.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Entity
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dic_no", updatable = false, nullable = false)
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

    public static DictionaryBuilder builder() {
        return new DictionaryBuilder();
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

    public static class DictionaryBuilder {
        private Integer dicNo;
        private String name;
        private String engName;
        private String category;
        private Integer popularity;
        private Short hit;
        private String thumbnail;
        private String treatment;

        DictionaryBuilder() {
        }

        public DictionaryBuilder dicNo(Integer dicNo) {
            this.dicNo = dicNo;
            return this;
        }

        public DictionaryBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DictionaryBuilder engName(String engName) {
            this.engName = engName;
            return this;
        }

        public DictionaryBuilder category(String category) {
            this.category = category;
            return this;
        }

        public DictionaryBuilder popularity(Integer popularity) {
            this.popularity = popularity;
            return this;
        }

        public DictionaryBuilder hit(Short hit) {
            this.hit = hit;
            return this;
        }

        public DictionaryBuilder thumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public DictionaryBuilder treatment(String treatment) {
            this.treatment = treatment;
            return this;
        }

        public Dictionary build() {
            return new Dictionary(this.dicNo, this.name, this.engName, this.category, this.popularity, this.hit, this.thumbnail, this.treatment);
        }

        public String toString() {
            return "Dictionary.DictionaryBuilder(dicNo=" + this.dicNo + ", name=" + this.name + ", engName=" + this.engName + ", category=" + this.category + ", popularity=" + this.popularity + ", hit=" + this.hit + ", thumbnail=" + this.thumbnail + ", treatment=" + this.treatment + ")";
        }
    }
}
