//package com.devon.moj.controller;
//
//import com.devon.moj.pojo.AiQuestionRequest;
//import com.devon.moj.service.AiQuestionService;
//import com.devon.moj.utilis.JwtUtil;
//import com.devon.moj.utilis.Result;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/ai")
//@RequiredArgsConstructor
//public class AiQuestionController {
//    private final AiQuestionService aiQuestionService;
//    private final JwtUtil jwtUtil;
//
//    @PostMapping("/explain")
//    public Result explainQuestion(@RequestBody AiQuestionRequest aiQuestionRequest, HttpServletRequest httpRequest) {
//        try {
//            String authonrization = httpRequest.getHeader("Authorization");
//            Long userId = jwtUtil.getUserIdFromToken(authonrization);
//
//            String explanation = aiQuestionService.explainQuestion(
//                    aiQuestionRequest.getQuestionId(),
//                    aiQuestionRequest.getQuestion(),
//                    aiQuestionRequest.getAnswer()
//            );
//            return Result.success(explanation, 0);
//        } catch (Exception e) {
//            return Result.fail(500, "AI解析失败: " + e.getMessage());
//        }
//
//
//    }
//}
