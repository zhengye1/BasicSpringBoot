package com.itsp.basicspring.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsp.basicspring.dao.TeamMapper;
import com.itsp.basicspring.dto.ProDTO;
import com.itsp.basicspring.exception.NotFoundException;
import com.itsp.basicspring.model.Pro;
import com.itsp.basicspring.dao.ProMapper;
import com.itsp.basicspring.model.Team;
import com.itsp.basicspring.service.ProService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsp.basicspring.service.TeamService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
public class ProServiceImpl extends ServiceImpl<ProMapper, Pro> implements ProService {

    private final TeamService teamService;

    @Autowired
    public ProServiceImpl(@Lazy TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public ProDTO getById(Long id) {
        Pro pro = super.getById(id);
        if (pro == null) {
            throw new NotFoundException();
        }
        return convertToDto(pro);
    }

    @Override
    public List<ProDTO> getByName(String name) {
        List<Pro> result = list(new QueryWrapper<Pro>().eq("pro_name", name));
        if (result.isEmpty()) {
            throw new NotFoundException();
        }
        // 将 Pro 转换为 ProDto
        return result.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<ProDTO> listAllPros() {
        List<Pro> pros = super.list();  // 获取所有的 Pro 数据
        return pros.stream().map(this::convertToDto).toList();  // 转换为 ProDto
    }

    // 将 Pro 转换为 ProDto 并填充 teamName
    private ProDTO convertToDto(Pro pro) {
        ProDTO dto = ProDTO.builder()
            .id(pro.getId())
            .proName(pro.getProName())
            .birth(pro.getBirth())
            .birthPlace(pro.getBirthPlace())
            .proYear(pro.getProYear())
            .org(pro.getOrg())
            .build();

        // 根据 teamId 查询队伍名称
        Team team = teamService.getById(pro.getTeamId());
        if (team != null) {
            dto.setTeamName(team.getTeamName());
        }
        return dto;
    }
}
