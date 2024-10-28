package com.itsp.basicspring.unit.controller;

import com.itsp.basicspring.controller.TeamController;
import com.itsp.basicspring.dto.TeamDTO;
import com.itsp.basicspring.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeamController.class)  // 仅加载 TeamController 相关的 Spring 上下文
class TeamControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;  // Mock TeamService

    private List<TeamDTO> teamDTOList;

    @BeforeEach
    public void setup() {
        // 初始化测试数据
        TeamDTO teamDTO = TeamDTO.builder().name("Test Team").build();
        teamDTOList = new ArrayList<>();
        teamDTOList.add(teamDTO);

        MockitoAnnotations.openMocks(this);
    }

    // 1. 测试 listAllTeams() 正常返回的情况
    @Test
    void testListAllTeams() throws Exception {
        // 模拟 TeamService 行为
        when(teamService.listAllTeams()).thenReturn(teamDTOList);

        // 模拟 HTTP GET 请求并验证返回结果
        mockMvc.perform(get("/team"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", hasSize(1)))  // 验证 data 数组大小
            .andExpect(jsonPath("$.data[0].name", is("Test Team")))  // 验证 Team 名称
            .andExpect(jsonPath("$.code", is("0")))  // 验证响应码
            .andExpect(jsonPath("$.msg", is("ok")));  // 验证消息

        // 验证 TeamService 调用
        verify(teamService, times(1)).listAllTeams();
    }
}
