package com.devon.moj.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.devon.moj.mapper.QuestionMapper;
import com.devon.moj.mapper.UserStatisticsMapper;
import com.devon.moj.pojo.JudgeRequest;
import com.devon.moj.pojo.MaoQuestion;
import com.devon.moj.pojo.UserStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionShowService {
    private final QuestionMapper questionMapper;
    private final UserStatisticsMapper userStatisticsMapper;


    public MaoQuestion getQuestionById(Integer id) {

        MaoQuestion q = questionMapper.selectById(id);
        return q;
    }

    public boolean jude(JudgeRequest request, Long userId) {
        MaoQuestion question = getQuestionById(request.getId());
        if (question == null) {
            return false;
        }

        String correctAnswer = question.getAnswer();
        String userAnswer = request.getAnswer();
        boolean isCorrect = false;

        if ("singleSelect".equals(question.getType())) {
            String parsedCorrect = correctAnswer.replace("[", "").replace("]", "").replace("\"", "").trim();
            isCorrect = parsedCorrect.equals(userAnswer.trim());
        } else {
            String[] correctAnswers = correctAnswer.replace("[", "").replace("]", "").replace("\"", "").split(",");
            String[] userAnswers = userAnswer.split(",");

            java.util.Set<String> correctSet = new java.util.HashSet<>();
            java.util.Set<String> userSet = new java.util.HashSet<>();

            for (String s : correctAnswers) {
                correctSet.add(s.trim());
            }
            for (String s : userAnswers) {
                userSet.add(s.trim());
            }

            isCorrect = correctSet.equals(userSet);
        }
        LambdaQueryWrapper<UserStatistics> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserStatistics::getUserId, userId);
        UserStatistics userStatistics = userStatisticsMapper.selectOne(queryWrapper);
        userStatistics.setLastAnswerDate(LocalDate.now());
        userStatistics.setTotalAnswered(userStatistics.getTotalAnswered() + 1);
        if (isCorrect) {

            userStatistics.setTotalCorrect(userStatistics.getTotalCorrect() + 1);
            userStatistics.setCorrectRate((double) userStatistics.getTotalCorrect() / userStatistics.getTotalAnswered());


        } else {
            userStatistics.setTotalWrong(userStatistics.getTotalWrong() + 1);
            userStatistics.setCorrectRate((double) userStatistics.getTotalCorrect() / userStatistics.getTotalAnswered());

        }
        userStatistics.setUpdatedAt(LocalDateTime.now());
        userStatisticsMapper.updateById(userStatistics);
        return isCorrect;

    }

    public String getTrueAnswer(Integer id) {
        return questionMapper.selectById(id).getAnswer();
    }

    public Long getCount() {
        return questionMapper.selectCount(null);
    }
}
