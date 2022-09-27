package com.blisgo.config;

import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchConfig;
import com.algolia.search.SearchIndex;
import com.blisgo.web.dto.DictionaryDTO;

import java.util.List;

public class AlgoliaSearch {
    String appId = "LE02Y9KYYD";
    String adminAPIKey = "099c8b7cda24164e14f001fca26e5b24";
    SearchConfig config = new SearchConfig.Builder(appId, adminAPIKey).build();

    // API keys below contain actual values tied to your Algolia account
    SearchClient client = DefaultSearchClient.create(config);

    public AlgoliaSearch() {
        System.out.println("Algolia Search Loaded");
    }

    public void init(List<DictionaryDTO> products) {
        for (DictionaryDTO product : products) {
            SearchIndex<DictionaryDTO> idx = client.initIndex("dictionary_index", DictionaryDTO.class);
            idx.saveObject(product).waitTask();
        }
    }

}
