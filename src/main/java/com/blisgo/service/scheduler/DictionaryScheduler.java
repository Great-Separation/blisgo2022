package com.blisgo.service.scheduler;

import com.blisgo.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DictionaryScheduler {
    private final DictionaryService dictionaryService;

    @Scheduled(zone = "Asia/Seoul", cron = "0 */30 * ? * *")// 매 30분 마다 수행
    public void updateDictionaryPopularity() {
        dictionaryService.modifyDictionaryPopularity();
    }
}
