package com.devon.moj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiQuestionResponse {
    private String explanation;
    private String relatedKnowledge;
}
