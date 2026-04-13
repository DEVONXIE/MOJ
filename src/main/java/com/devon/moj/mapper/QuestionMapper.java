package com.devon.moj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devon.moj.pojo.MaoQuestion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper extends BaseMapper<MaoQuestion> {
    @Delete("TRUNCATE TABLE mao_questions")
    void truncateTable();
}
