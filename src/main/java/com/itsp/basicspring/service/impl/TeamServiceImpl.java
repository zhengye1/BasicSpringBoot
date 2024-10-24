package com.itsp.basicspring.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsp.basicspring.dto.ProDTO;
import com.itsp.basicspring.dto.TeamDTO;
import com.itsp.basicspring.exception.NotFoundException;
import com.itsp.basicspring.model.Pro;
import com.itsp.basicspring.model.Team;
import com.itsp.basicspring.dao.TeamMapper;
import com.itsp.basicspring.service.ProService;
import com.itsp.basicspring.service.TeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Vincent Zheng
 * @since 2024-10-23
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    private final ProService proService;

    @Autowired
    public TeamServiceImpl(@Lazy ProService proService) {
        this.proService = proService;
    }

    @Override
    public List<TeamDTO> listAllTeams() {
        List<Team> teams = super.list();  // 获取所有的 Pro 数据
        return teams.stream().map(this::convertToDto).toList();  // 转换为 ProDto
    }

    @Override
    public TeamDTO getById(Long id) {
        Team team = super.getById(id);
        if (team == null) {
            throw new NotFoundException();
        }
        return convertToDto(team);
    }

    private TeamDTO convertToDto(Team team) {
        TeamDTO dto = TeamDTO.builder().id(team.getId()).name(team.getTeamName()).build();
        // 获取属于该队伍的所有选手（Pro）
        List<Pro> pros = proService.list(new QueryWrapper<Pro>().eq("team_id", team.getId()));
        
        List<ProDTO> proDTOs = pros.stream().map(pro -> ProDTO.builder().proName(pro.getProName()).build()).toList();
        dto.setPros(proDTOs);
        return dto;

    }

    @Override
    public List<TeamDTO> getByName(String name) {
        return List.of();
    }
}
