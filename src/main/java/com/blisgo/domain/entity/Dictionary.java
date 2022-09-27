package com.blisgo.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Dictionary {
    @Id
    @GeneratedValue
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
    private final List<Dogam> dogam = new ArrayList<>();

    @OneToMany(mappedBy = "dictionary", orphanRemoval = true)
    private final List<Hashtag> hashtag = new ArrayList<>();

    @Builder
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
}
