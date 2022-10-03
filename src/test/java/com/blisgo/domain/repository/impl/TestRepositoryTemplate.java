package com.blisgo.domain.repository.impl;

import com.blisgo.domain.entity.*;
import com.blisgo.domain.entity.cmmn.Wastes;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

@Slf4j
public class TestRepositoryTemplate {
    @Autowired
    EntityManager entityManager;

    enum entityAssistOpt {
        PERSIST, AUTOINCREMEMT
    }

    static Dictionary sampleDictionary;
    static Account sampleAccount;
    static Guide sampleGuide;
    static Hashtag sampleHashtag;
    static Dogam sampleDogam;
    static Board sampleBoard;
    static Reply sampleReply;
    Javers javers;
    Diff diff;

    void entityAssistant(@NotNull entityAssistOpt opt, Object... entities) {
        switch (opt) {
            case PERSIST:
                for (var e : entities)
                    entityManager.persist(e);
                break;

            case AUTOINCREMEMT:
                for (var e : entities)
                    entityManager.createNativeQuery(String.format("ALTER TABLE %s AUTO_INCREMENT = 1", e.getClass().getSimpleName())).executeUpdate();
                break;

            default:
                break;
        }
    }

    void initData() {
        javers = JaversBuilder.javers().build();
        sampleDictionary = Dictionary.builder().name("name").engName("engName").category("category").popularity(0).hit((short) 0).thumbnail("thumbnail").treatment("treatment").build();
        sampleAccount = Account.builder().nickname("nickname").email("email").pass("pass").memPoint(0).profileImage("profileImage").build();
        sampleGuide = Guide.builder().guideCode(Wastes.Ir).guideName("guideName").guideContent("guideContent").imagePath("imagePath").build();
        sampleHashtag = Hashtag.builder().dictionary(sampleDictionary).guide(sampleGuide).build();
        sampleDogam = Dogam.builder().account(sampleAccount).dictionary(sampleDictionary).build();
        sampleBoard = Board.builder().account(sampleAccount).bdTitle("bdTitle").bdCategory("bdCategory").bdContent("bdContent").bdViews(0).bdFavorite(0).bdReplyCount(0).bdThumbnail("bdThumbnail").build();
        sampleReply = Reply.builder().board(sampleBoard).account(sampleAccount).content("content").build();
    }

    void printJaversMonitorResult() {
        if (diff != null) {
            System.out.println("감사 결과>" + diff.changesSummary());
            diff.getChanges().forEach(change -> System.out.println("- " + change));
        } else {
            log.info("해당 테스트에서는 엔티티 감사 안했음");
        }
    }
}
