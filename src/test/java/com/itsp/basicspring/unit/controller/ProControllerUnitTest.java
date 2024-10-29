package com.itsp.basicspring.unit.controller;

import com.itsp.basicspring.controller.ProController;
import com.itsp.basicspring.dto.ProDTO;
import com.itsp.basicspring.service.ProService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

//@WebMvcTest(ProController.class)  // 仅加载 ProController 相关的 Spring 上下文
class ProControllerUnitTest {

    //@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProService proService;  // Mock ProService

    private List<ProDTO> proDTOList;
    private ProDTO proDTO;

    @BeforeEach
    public void setup() {
        // 初始化测试数据
        proDTO = ProDTO.builder().proName("Test Pro").build();
        proDTOList = new ArrayList<>();
        proDTOList.add(proDTO);
    }

    // 1. 测试 listAllPros() 在没有提供 name 参数时
    @Test
    void testListAllProsWithoutName() throws Exception {
        // 模拟 ProService 行为
        when(proService.listAllPros()).thenReturn(proDTOList);

        // 模拟 HTTP GET 请求并验证返回结果
        mockMvc.perform(get("/pro"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", hasSize(1)))  // 验证 data 数组大小
            .andExpect(jsonPath("$.data[0].proName", is("Test Pro")))  // 验证 Pro 名称
            .andExpect(jsonPath("$.code", is("0")))  // 验证响应码
            .andExpect(jsonPath("$.msg", is("ok")));  // 验证消息

        // 验证 ProService 调用
        verify(proService, times(1)).listAllPros();
    }

    // 2. 测试 listAllPros() 在提供 name 参数时
    @Test
    void testGetProByName() throws Exception {
        // 模拟 ProService 行为
        when(proService.getByName("Test Pro")).thenReturn(proDTOList);

        // 模拟 HTTP GET 请求并验证返回结果
        mockMvc.perform(get("/pro").param("name", "Test Pro"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", hasSize(1)))  // 验证 data 数组大小
            .andExpect(jsonPath("$.data[0].proName", is("Test Pro")))  // 验证 Pro 名称
            .andExpect(jsonPath("$.code", is("0")))  // 验证响应码
            .andExpect(jsonPath("$.msg", is("ok")));  // 验证消息

        // 验证 ProService 调用
        verify(proService, times(1)).getByName("Test Pro");
    }

    // 3. 测试 getProById() 返回单个 ProDTO
    @Test
    void testGetProById() throws Exception {
        // 模拟 ProService 行为
        when(proService.getById(1L)).thenReturn(proDTO);

        // 模拟 HTTP GET 请求并验证返回结果
        mockMvc.perform(get("/pro/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.proName", is("Test Pro")))  // 验证 Pro 名称
            .andExpect(jsonPath("$.code", is("0")))  // 验证响应码
            .andExpect(jsonPath("$.msg", is("ok")));  // 验证消息

        // 验证 ProService 调用
        verify(proService, times(1)).getById(1L);
    }
}
