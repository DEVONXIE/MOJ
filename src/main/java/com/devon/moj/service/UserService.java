package com.devon.moj.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.devon.moj.mapper.UserMapper;
import com.devon.moj.mapper.UserProgressMapper;
import com.devon.moj.mapper.UserStatisticsMapper;
import com.devon.moj.pojo.User;
import com.devon.moj.pojo.UserProgress;
import com.devon.moj.pojo.UserStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserStatisticsMapper userStatisticsMapper;
    private final UserProgressMapper userProgressMapper;
    private final PasswordEncoder passwordEncoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    public User register(String username, String email, String password, String nickname) throws Exception {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(User::getUsername)
                .eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            throw new Exception("用户名已存在");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setNickName(nickname);
        newUser.setPasswordHash(passwordEncoder.encode(password));
        newUser.setIsActive(1);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        userMapper.insert(newUser);

        UserStatistics userStatistics = new UserStatistics();
        userStatistics.setUserId(newUser.getId());
        userStatistics.setTotalAnswered(0L);
        userStatistics.setTotalCorrect(0L);
        userStatistics.setTotalWrong(0L);
        userStatistics.setCorrectRate(0);
        userStatistics.setCreatedAt(LocalDateTime.now());
        userStatistics.setUpdatedAt(LocalDateTime.now());
        userStatistics.setStreak_days(1L);

        userStatisticsMapper.insert(userStatistics);

        UserProgress userProgress = new UserProgress();
        userProgress.setUserId(newUser.getId());
        userProgress.setLastQuestionId(1L);
        userProgressMapper.insert(userProgress);

        newUser.setPasswordHash(null);
        return newUser;
    }

    public User login(String username, String password) throws Exception {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);

        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            throw new Exception("用户不存在");
        }
        if (user.getIsActive() == 0) {
            throw new Exception("用户已被禁用");
        }
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new Exception("用户密码错误");
        }

        user.setLastLoginAt(LocalDateTime.now());
        LambdaQueryWrapper<UserStatistics> statisticsQuery = new LambdaQueryWrapper<>();
        statisticsQuery.eq(UserStatistics::getUserId, user.getId());
        UserStatistics userStatistics = userStatisticsMapper.selectOne(statisticsQuery);
        Duration duration = Duration.between(userStatistics.getUpdatedAt(), LocalDateTime.now());

        long hours = duration.toHours();

        if (hours >= 24 && hours < 48) {
            userStatistics.setStreak_days(userStatistics.getStreak_days() + 1);
        } else {
            userStatistics.setStreak_days(1L);
        }
        userMapper.updateById(user);

        user.setPasswordHash(null);

        return user;

    }

    public User getUserById(Long id) throws Exception {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        User user = userMapper.selectOne(queryWrapper);

        if (user != null) {
            user.setPasswordHash(null);
        }
        return user;
    }

    public UserStatistics getUserStatistics(Long id) throws Exception {
        LambdaQueryWrapper<UserStatistics> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserStatistics::getUserId, id);
        return userStatisticsMapper.selectOne(queryWrapper);
    }
}
