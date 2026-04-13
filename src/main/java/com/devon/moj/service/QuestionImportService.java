package com.devon.moj.service;

import com.devon.moj.mapper.QuestionMapper;
import com.devon.moj.pojo.MaoQuestion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class QuestionImportService {
    private final QuestionMapper questionMapper;
    private final ObjectMapper objectMapper;

    public QuestionImportService(QuestionMapper questionMapper,
                                 ObjectMapper objectMapper) {
        this.questionMapper = questionMapper;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public int importMaoQuestion(MultipartFile file) throws IOException {
        questionMapper.truncateTable();
        List<Map<String, Object>> rawList = objectMapper.readValue(
                file.getInputStream(), new TypeReference<List<Map<String, Object>>>() {
                }
        );


        List<MaoQuestion> questions = rawList.stream()
                .map(this::converToMaoQuestion)
                .toList();
        for (MaoQuestion q : questions) {
            questionMapper.insert(q);
        }

        return rawList.size();


    }

    private MaoQuestion converToMaoQuestion(Map<String, Object> map) {
        MaoQuestion q = new MaoQuestion();
        Object numObj = map.get("num");
        q.setNum(numObj != null ? Integer.parseInt(numObj.toString()) : null);
        q.setQuestion((String) map.get("question"));
        q.setAnswer(toJson(map.get("answer")));
        q.setType((String) map.get("type"));
        q.setOptions(toJson(map.get("options")));
        q.setExplanation(String.valueOf(map.get("explanation")));
        return q;
    }


    private String toJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            return "[]";
        }
    }

}
