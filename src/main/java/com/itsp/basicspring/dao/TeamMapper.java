package com.itsp.basicspring.dao;

import com.itsp.basicspring.model.Team;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Vincent Zheng
 * @since 2024-10-23
 */
@Mapper
public interface TeamMapper extends BaseMapper<Team> {

}
