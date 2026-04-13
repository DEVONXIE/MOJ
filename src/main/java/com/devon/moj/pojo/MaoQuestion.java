package com.devon.moj.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName("mao_questions")
@AllArgsConstructor
@NoArgsConstructor
public class MaoQuestion {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer num;

    private String question;

    private String options;  // JSON 数组

    private String answer;   // JSON 数组

    private String type;

    private String explanation;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
