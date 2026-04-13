package com.devon.moj.controller;

import com.devon.moj.pojo.LoginRequest;
import com.devon.moj.pojo.RegisterRequest;
import com.devon.moj.pojo.User;
import com.devon.moj.service.UserService;
import com.devon.moj.utilis.JwtUtil;
import com.devon.moj.utilis.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public Result register(@RequestBody RegisterRequest registerRequest) {
        try {
            if (registerRequest.getUsername() == null || registerRequest.getUsername().trim().isEmpty()) {
                return Result.fail(400, "用户名不能为空");

            }
            if (registerRequest.getPassword() == null || registerRequest.getPassword().trim().isEmpty()) {
                return Result.fail(400, "密码不能为空");
            }
            User user = userService.register(
                    registerRequest.getUsername(),
                    registerRequest.getEmail(),
                    registerRequest.getPassword(),
                    registerRequest.getNickname()
            );
            String token = jwtUtil.generateToken(user.getId(), user.getUsername());

            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("user", user);
            return Result.success(data, 0);
        } catch (Exception e) {
            log.error("注册失败", e);
            return Result.fail(400, e.getMessage());
        }
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        try {
            if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().isEmpty()) {
                return Result.fail(400, "用户名不能为空");
            }
            if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
                return Result.fail(400, "密码不能为空");
            }

            User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());

            String token = jwtUtil.generateToken(user.getId(), user.getUsername());

            Map<String, Object> data = new HashMap<>();
            data.put("user", user);
            data.put("token", token);

            return Result.success(data, 0);
        } catch (Exception e) {
            log.error("登录失败", e);
            return Result.fail(400, e.getMessage());
        }
    }

//    @GetMapping("/me")
//    public Result getCurrentUser(HttpServletRequest request) {
//        try {
//            Long userId = (Long) request.getAttribute("userId");
//            if (userId == null) {
//                return Result.fail(401, "未登录");
//            }
//            User user = userService.getUserById(userId);
//            if (user == null) {
//                return Result.fail(404, "用户不存在");
//            }
//            return Result.success(user, 0);
//        } catch (Exception e) {
//            log.error("出错啦啊", e);
//            return Result.fail(400, e.getMessage());
//        }
//    }
//
//    @GetMapping("/statistics")
//    public Result getStatistics(HttpServletRequest request) {
//        try {
//            Long userId = (Long) request.getAttribute("userId");
//            if (userId == null) {
//                return Result.fail(401, "未登录");
//
//            }
//            UserStatistics user = userService.getUserStatistics(userId);
//            if (user == null) {
//                return Result.fail(404, "数据不存在");
//            }
//            return Result.success(user, 0);
//        } catch (Exception e) {
//            log.error("出错啦", e);
//            return Result.fail(400, e.getMessage());
//        }
//    }

}
