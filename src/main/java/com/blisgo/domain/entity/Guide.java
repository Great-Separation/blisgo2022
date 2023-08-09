package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.Wastes;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Guide {
    @Id
    @Enumerated(EnumType.STRING)
    @Comment("가이드 코드(PK)")
    @Column(name = "guide_code", updatable = false, nullable = false)
    private Wastes guideCode;

    @Comment("폐기물 중분류")
    @Column(nullable = false, length = 50)
    private String guideName;

    @Column(nullable = false, length = 200)
    @Comment("폐기물 처리 안내")
    private String guideContent;

    @Column(nullable = false, length = 100)
    @Comment("폐기물 처리 안내 이미지 url")
    private String imagePath;

    @OneToMany(mappedBy = "guide", orphanRemoval = true)
    private final List<Hashtag> hashtag = new ArrayList<>();

    public Guide(Wastes guideCode, String guideName, String guideContent, String imagePath) {
        this.guideCode = guideCode;
        this.guideName = guideName;
        this.guideContent = guideContent;
        this.imagePath = imagePath;
    }

    public Guide() {
    }

    public static GuideBuilder builder() {
        return new GuideBuilder();
    }

    public Wastes getGuideCode() {
        return this.guideCode;
    }

    public String getGuideName() {
        return this.guideName;
    }

    public String getGuideContent() {
        return this.guideContent;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public List<Hashtag> getHashtag() {
        return this.hashtag;
    }

    public static class GuideBuilder {
        private Wastes guideCode;
        private String guideName;
        private String guideContent;
        private String imagePath;

        GuideBuilder() {
        }

        public GuideBuilder guideCode(Wastes guideCode) {
            this.guideCode = guideCode;
            return this;
        }

        public GuideBuilder guideName(String guideName) {
            this.guideName = guideName;
            return this;
        }

        public GuideBuilder guideContent(String guideContent) {
            this.guideContent = guideContent;
            return this;
        }

        public GuideBuilder imagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Guide build() {
            return new Guide(this.guideCode, this.guideName, this.guideContent, this.imagePath);
        }

        public String toString() {
            return "Guide.GuideBuilder(guideCode=" + this.guideCode + ", guideName=" + this.guideName + ", guideContent=" + this.guideContent + ", imagePath=" + this.imagePath + ")";
        }
    }
}