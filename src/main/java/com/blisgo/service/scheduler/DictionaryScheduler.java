package com.blisgo.service.scheduler;

import com.blisgo.service.DictionaryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DictionaryScheduler {
    private final DictionaryService dictionaryService;

    public DictionaryScheduler(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @Scheduled(zone = "Asia/Seoul", cron = "0 */30 * ? * *")// 매 10분 마다 수행
    public void updateDictionaryPopularity() {
        dictionaryService.modifyDictionaryPopularity();
    }
}
