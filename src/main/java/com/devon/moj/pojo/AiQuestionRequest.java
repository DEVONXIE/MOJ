package com.devon.moj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiQuestionRequest {
    private Long questionId;
    private String question;
    private String answer;
}
