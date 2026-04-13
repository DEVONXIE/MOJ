//package com.devon.moj.configuratoin;
//
//import dev.langchain4j.data.segment.TextSegment;
//import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class LangChain4jConfig {
//    @Value("${langchain4j.openai.char-model.api-key")
//    private String apiKey;
//
//
//    @Bean
//    public InMemoryEmbeddingStore<TextSegment> embeddingStore() {
//        return new InMemoryEmbeddingStore<>();
//    }
//
//}
