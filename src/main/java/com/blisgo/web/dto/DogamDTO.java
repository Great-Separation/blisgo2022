package com.blisgo.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DogamDTO {
    private AccountDTO account;
    private DictionaryDTO dictionary;

    @Builder
    public DogamDTO(AccountDTO account, DictionaryDTO dictionary) {
        this.account = account;
        this.dictionary = dictionary;
    }
}
