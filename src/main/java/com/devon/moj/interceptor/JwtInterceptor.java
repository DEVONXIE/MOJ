package com.devon.moj.interceptor;

import com.devon.moj.utilis.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendErrorReasponse(response, 401, "未登录或token无效");
            return false;
        }

        try {
            String token = authHeader.substring(7);

            if (!jwtUtil.isTokenValid(token)) {
                sendErrorReasponse(response, 401, "token已过期或无效");
                return false;
            }

            Long userId = jwtUtil.getUserIdFromToken(token);
            String username = jwtUtil.getUsernameFromToken(token);

            request.setAttribute("userId", userId);
            request.setAttribute("username", username);

            return true;

        } catch (Exception e) {
            e.printStackTrace(); // 一定要打印
            sendErrorReasponse(response, 401, "token解析失败");
            return false;
        }

    }

    private void sendErrorReasponse(HttpServletResponse response, int i, String str) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json;charset=utf-8");

        ObjectNode result = objectMapper.createObjectNode();
        result.put("code", i);
        result.put("msg", str);
        result.putNull("data");
        result.put("count", 0);

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
