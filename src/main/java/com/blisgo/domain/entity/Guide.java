package com.blisgo.domain.entity;

import com.blisgo.domain.entity.cmmn.Wastes;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Table(indexes = {
        @Index(columnList = "guideName"),
        @Index(columnList = "guideContent"),
        @Index(columnList = "imagePath")
})
@Entity
public class Guide {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name="guide_code", updatable = false, nullable = false)
    @Comment("가이드 코드(PK)")
    private Wastes guideCode;

    @Column(nullable = false, length = 50)
    @Comment("폐기물 중분류")
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

    private Guide(Builder builder) {
        guideCode = builder.guideCode;
        guideName = builder.guideName;
        guideContent = builder.guideContent;
        imagePath = builder.imagePath;
    }

    public static Builder builder() {
        return new Builder();
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

    public static final class Builder {
        private Wastes guideCode;
        private String guideName;
        private String guideContent;
        private String imagePath;

        private Builder() {
        }

        public Builder guideCode(Wastes val) {
            guideCode = val;
            return this;
        }

        public Builder guideName(String val) {
            guideName = val;
            return this;
        }

        public Builder guideContent(String val) {
            guideContent = val;
            return this;
        }

        public Builder imagePath(String val) {
            imagePath = val;
            return this;
        }

        public Guide build() {
            return new Guide(this);
        }
    }
}