package com.devon.moj.controller;

import com.devon.moj.pojo.UserStatistics;
import com.devon.moj.service.UserService;
import com.devon.moj.utilis.JwtUtil;
import com.devon.moj.utilis.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/statistics")
    public Result getStatistics(HttpServletRequest request) {
        try {
            String authorization = request.getHeader("Authorization");
            Long userIdFromToken = jwtUtil.getUserIdFromToken(authorization);
            if (userIdFromToken == null) {
                return Result.fail(401, "未登录");

            }
            UserStatistics userStatistics = userService.getUserStatistics(userIdFromToken);
            if (userStatistics == null) {
                return Result.fail(404, "数据不存在");
            }
            return Result.success(userStatistics, 0);
        } catch (Exception e) {
            return Result.fail(400, e.getMessage());
        }


    }
}
