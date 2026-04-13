package com.devon.moj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devon.moj.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
