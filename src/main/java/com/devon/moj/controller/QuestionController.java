package com.devon.moj.controller;

import com.devon.moj.pojo.JudgeRequest;
import com.devon.moj.pojo.MaoQuestion;
import com.devon.moj.service.QuestionImportService;
import com.devon.moj.service.QuestionShowService;
import com.devon.moj.utilis.JwtUtil;
import com.devon.moj.utilis.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final JwtUtil jwtUtil;
    private final QuestionImportService qis;
    private final QuestionShowService qshs;

    @PostMapping("/import")
    public Result importQuestions(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.fail(400, "文件不能为空");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.endsWith(".json")) {
            return Result.fail(400, "只支持json文件");
        }
        try {
            int count = qis.importMaoQuestion(file);
            return Result.success(count);
        } catch (IOException e) {
            return Result.fail(500, "导入失败：" + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable int id) {

        MaoQuestion q = qshs.getQuestionById(id);
        if (q == null) {
            return Result.fail(404, "未找到该题目");
        }
        log.info("{}", q);
        return Result.success(q, 1);
    }

    @PostMapping("/judge")
    public Result judge(@RequestBody JudgeRequest request, HttpServletRequest httpRequest) {
        log.info("判题请求 - 题目ID: {}, 用户答案: {}", request.getId(), request.getAnswer());

        try {
            String authorization = httpRequest.getHeader("Authorization");
            Long userId = jwtUtil.getUserIdFromToken(authorization);
            boolean isCorrect = qshs.jude(request, userId);
            MaoQuestion question = qshs.getQuestionById(request.getId());
            if (isCorrect) {
                return Result.success(null, 0);
            } else {
                return Result.fail(400, "答案错误", question.getAnswer());
            }
        } catch (Exception e) {
            log.error("判题失败", e);
            return Result.fail(500, "判题失败：" + e.getMessage());
        }
    }

    @GetMapping("/count")
    public Result getCount() {
        return Result.success(qshs.getCount());
    }
}

