//package com.devon.moj.service;
//
//import dev.langchain4j.data.segment.TextSegment;
//import dev.langchain4j.model.chat.ChatLanguageModel;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class AiQuestionService {
//    private final ChatLanguageModel chatModel;
//    private final KnowledgeBaseService knowledgeBaseService;
//
//    public String explainQuestion(Long questionId, String question, String answer) {
//        List<TextSegment> relatedKnowledge = knowledgeBaseService.retrieveRelativeQuestions(question, 5);
//
//        StringBuilder promptBuilder = new StringBuilder();
//        promptBuilder.append("你是一个专业的思政题目解析助手。请根据以下题目信息提供详细的解析。\n\n");
//        promptBuilder.append("题目: ").append(question).append("\n");
//        promptBuilder.append("正确答案: ").append(answer).append("\n\n");
//
//        if (!relatedKnowledge.isEmpty()) {
//            promptBuilder.append("相关题目参考:\n");
//            for (int i = 0; i < relatedKnowledge.size(); i++) {
//                promptBuilder.append(i + 1).append(". ").append(relatedKnowledge.get(i).text()).append("\n\n");
//            }
//        }
//
//        promptBuilder.append("请按照以下格式提供解析:\n");
//        promptBuilder.append("1. 考点分析: 分析本题考察的核心知识点\n");
//        promptBuilder.append("2. 答案详解: 解释为什么这是正确答案\n");
//        promptBuilder.append("3. 知识拓展: 相关的知识点和背景信息\n");
//        promptBuilder.append("4. 学习建议: 如何更好地掌握这类知识点\n");
//
//
//        return chatModel.generate(promptBuilder.toString());
//    }
//}
