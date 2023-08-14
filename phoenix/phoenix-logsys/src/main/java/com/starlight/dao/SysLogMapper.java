package com.starlight.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.starlight.model.LogInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志接口
 *
 * @author wenyunfei
 */
@Mapper
public interface SysLogMapper extends BaseMapper<LogInfo> {

}
