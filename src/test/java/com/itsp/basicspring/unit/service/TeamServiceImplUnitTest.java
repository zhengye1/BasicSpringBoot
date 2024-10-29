package com.itsp.basicspring.unit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsp.basicspring.dao.TeamMapper;
import com.itsp.basicspring.dto.TeamDTO;
import com.itsp.basicspring.exception.NotFoundException;
import com.itsp.basicspring.model.Pro;
import com.itsp.basicspring.model.Team;
import com.itsp.basicspring.service.ProService;
import com.itsp.basicspring.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeamServiceImplUnitTest {

    @InjectMocks
    private TeamServiceImpl teamService;

    @Mock
    private TeamMapper teamMapper;

    @Mock
    private ProService proService;  // Mock ProService
    private Team team;
    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException{
        MockitoAnnotations.openMocks(this);  // 初始化 @Mock 和 @InjectMocks
        // 使用反射机制设置 baseMapper
        Field baseMapperField = TeamServiceImpl.class.getSuperclass().getDeclaredField("baseMapper");
        baseMapperField.setAccessible(true);
        baseMapperField.set(teamService, teamMapper);
        // 初始化 Pro 和 Pro 列表
        List<Pro> pros = new ArrayList<>();
        Pro pro1 = new Pro();
        pro1.setProName("Pro 1");
        pros.add(pro1);

        // 初始化 Team
        team = new Team();
        team.setId(1L);
        team.setTeamName("Test Team");
        team.setTeamCode("Test Team Code");

        // 统一模拟 ProService 的行为
        when(proService.list(any(QueryWrapper.class))).thenReturn(pros);

        // 统一模拟 TeamMapper 的行为（可以在具体测试中复写模拟行为）
        when(teamMapper.selectById(1L)).thenReturn(team);
    }

    // 1. 测试 listAllTeams() 正常返回的情况
    @Test
    void testListAllTeamsSuccess() {
        // 模拟数据库返回的 Team 列表
        List<Team> teamList = new ArrayList<>();
        teamList.add(team);  // 使用 setup 中的 team

        // 模拟 TeamMapper 的行为
        when(teamMapper.selectList(any())).thenReturn(teamList);

        // 调用 service 方法并验证结果
        List<TeamDTO> result = teamService.listAllTeams();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Team", result.get(0).getName());
        assertEquals(1, result.get(0).getPros().size());  // 验证 Pro 列表
        assertEquals("Pro 1", result.get(0).getPros().get(0).getProName());  // 验证选手名

        // 验证 TeamMapper 和 ProService 调用
        verify(teamMapper, times(1)).selectList(any());
        verify(proService, times(1)).list(any(QueryWrapper.class));
    }

    // 2. 测试 getById() 正常返回的情况
    @Test
    void testGetByIdSuccess() {
        // 调用 service 方法并验证结果
        TeamDTO result = teamService.getById(1L);
        assertNotNull(result);
        assertEquals("Test Team", result.getName());
        assertEquals(1, result.getPros().size());  // 验证 Pro 列表
        assertEquals("Pro 1", result.getPros().get(0).getProName());  // 验证选手名

        // 验证 TeamMapper 和 ProService 调用
        verify(teamMapper, times(1)).selectById(1L);
        verify(proService, times(1)).list(any(QueryWrapper.class));
    }

    // 3. 测试 getById() 抛出 NotFoundException 的情况
    @Test
    void testGetByIdNotFound() {
        // 模拟数据库返回 null
        when(teamMapper.selectById(1L)).thenReturn(null);

        // 验证抛出 NotFoundException
        assertThrows(NotFoundException.class, () -> teamService.getById(1L));

        // 验证 TeamMapper 被调用一次
        verify(teamMapper, times(1)).selectById(1L);
    }

}
