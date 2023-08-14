package com.starlight.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starlight.model.UserModel;
import org.springframework.stereotype.Repository;

/**
 * @Author: shilian
 * @CreateTime: 2023-02-19  22:00
 * @Description: 用户数据库查询
 */
@Repository
public interface UserMapper extends BaseMapper<UserModel> {

}
