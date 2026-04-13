//package com.devon.moj.service;
//
//
//import com.devon.moj.mapper.QuestionMapper;
//import com.devon.moj.pojo.MaoQuestion;
//import dev.langchain4j.data.document.Metadata;
//import dev.langchain4j.data.embedding.Embedding;
//import dev.langchain4j.data.segment.TextSegment;
//import dev.langchain4j.model.embedding.EmbeddingModel;
//import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
//import dev.langchain4j.store.embedding.EmbeddingSearchResult;
//import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class KnowledgeBaseService {
//    private final EmbeddingModel embeddingModel;
//
//    private final InMemoryEmbeddingStore<TextSegment> embeddingStore;
//
//    private final QuestionMapper questionMapper;
//
//    @PostConstruct
//    public void initKnoledgeBase() {
//        List<MaoQuestion> questions = questionMapper.selectList(null);
//        for (MaoQuestion question : questions) {
//            addQuestionToKnowledgeBase(question);
//        }
//    }
//
//    public void addQuestionToKnowledgeBase(MaoQuestion question) {
//        String content = buildQuestionContent(question);
//        TextSegment segment = TextSegment.from(content, Metadata.from("questionId", question.getId().toString()));
//        embeddingStore.add(embeddingModel.embed(content).content(), segment);
//    }
//
//    public List<TextSegment> retrieveRelativeQuestions(String query, int maxResults) {
//        Embedding queryEmbedding = embeddingModel.embed(query).content();
//
//        EmbeddingSearchRequest request = EmbeddingSearchRequest.builder()
//                .queryEmbedding(queryEmbedding)
//                .maxResults(maxResults)
//                .build();
//
//        EmbeddingSearchResult<TextSegment> result =
//                embeddingStore.search(request);
//
//        return result.matches().stream()
//                .map(match -> match.embedded())
//                .toList();
//    }
//
//    private String buildQuestionContent(MaoQuestion question) {
//        return String.format("""
//                        题目ID: %d
//                        题目类型: %s
//                        题目内容: %s
//                        选项: %s
//                        正确答案: %s
//                        解析: %s
//                        """,
//                question.getId(),
//                question.getType(),
//                question.getQuestion(),
//                question.getOptions(),
//                question.getAnswer(),
//                question.getExplanation() != null ? question.getExplanation() : "暂无解析");
//    }
//
//}
