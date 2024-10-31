package com.itsp.basicspring.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itsp.basicspring.dto.TeamDTO;
import com.itsp.basicspring.model.Team;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Vincent Zheng
 * @since 2024-10-23
 */
public interface TeamService extends IService<Team> {
    List<TeamDTO> listAllTeams();

    TeamDTO getById(Long id);

}
