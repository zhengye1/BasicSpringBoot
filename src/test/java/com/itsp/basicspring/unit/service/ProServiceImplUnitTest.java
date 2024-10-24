package com.itsp.basicspring.unit.service;

import com.itsp.basicspring.dao.ProMapper;
import com.itsp.basicspring.dto.ProDTO;
import com.itsp.basicspring.dto.TeamDTO;
import com.itsp.basicspring.exception.NotFoundException;
import com.itsp.basicspring.model.Pro;
import com.itsp.basicspring.service.impl.ProServiceImpl;
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

class ProServiceImplUnitTest {
    @InjectMocks
    private ProServiceImpl proService;

    @Mock
    private TeamServiceImpl teamService;

    @Mock
    private ProMapper proMapper;

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);  // 初始化 @Mock 和 @InjectMocks

        // 使用反射机制设置 baseMapper
        Field baseMapperField = ProServiceImpl.class.getSuperclass().getDeclaredField("baseMapper");
        baseMapperField.setAccessible(true);
        baseMapperField.set(proService, proMapper);
    }

    @Test
    void testGetById() {
        Pro pro = new Pro();
        pro.setId(1L);
        pro.setProName("Test Pro");

        when(proMapper.selectById(1L)).thenReturn(pro);
        // Mock TeamService.getById() 的行为
        when(teamService.getById(any())).thenReturn(null);

        ProDTO result = proService.getById(1L);
        assertNotNull(result);
        assertEquals("Test Pro", result.getProName());

        verify(proMapper, times(1)).selectById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        // 模拟 `ProMapper.selectById` 返回 null
        when(proMapper.selectById(1L)).thenReturn(null);

        // 验证调用 `proService.getById()` 时抛出 `NotFoundException`
        assertThrows(NotFoundException.class, () -> proService.getById(1L));

        // 验证 `proMapper.selectById()` 被调用一次
        verify(proMapper, times(1)).selectById(1L);
    }

    @Test
    void testProWithTeams(){
        // 模拟存在的 Pro 对象
        Pro pro = new Pro();
        pro.setId(1L);
        pro.setProName("Test Pro");
        pro.setTeamId(1L);  // 模拟 Pro 有 teamId

        when(proMapper.selectById(1L)).thenReturn(pro);

        // 模拟 TeamService.getById() 返回一个 Team
        TeamDTO team = TeamDTO.builder().id(1L).name("Test Team").build();

        when(teamService.getById(1L)).thenReturn(team);  // 模拟 teamService 行为

        // 调用 proService.getById() 并验证返回的 ProDTO
        ProDTO result = proService.getById(1L);
        assertNotNull(result);
        assertEquals("Test Pro", result.getProName());
        assertEquals("Test Team", result.getTeamName());  // 验证 teamName

        // 验证 ProMapper 和 TeamService 调用次数
        verify(proMapper, times(1)).selectById(1L);
        verify(teamService, times(1)).getById(1L);  // 验证 teamService 调用
    }
    @Test
    void testGetByName() {
        // 模拟数据库返回的 Pro 列表
        List<Pro> proList = new ArrayList<>();
        Pro pro = new Pro();
        pro.setId(1L);
        pro.setProName("Test Pro");
        proList.add(pro);

        // 模拟 ProMapper 的行为
        when(proMapper.selectList(any())).thenReturn(proList);

        // 调用 service 方法并验证结果
        List<ProDTO> result = proService.getByName("Test Pro");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Pro", result.get(0).getProName());

        // 验证 mapper 调用
        verify(proMapper, times(1)).selectList(any());
    }
    @Test
    void testGetByNameNotFound() {
        // 模拟 ProMapper 返回空列表
        when(proMapper.selectList(any())).thenReturn(new ArrayList<>());

        // 验证抛出 NotFoundException
        assertThrows(NotFoundException.class, () -> proService.getByName("NonExisting Pro"));

        // 验证 mapper 调用
        verify(proMapper, times(1)).selectList(any());
    }
    @Test
    void testListAllProsSuccess() {
        // 模拟数据库返回的 Pro 列表
        List<Pro> proList = new ArrayList<>();
        Pro pro = new Pro();
        pro.setId(1L);
        pro.setProName("Test Pro");
        proList.add(pro);

        // 模拟 ProMapper 的行为
        when(proMapper.selectList(any())).thenReturn(proList);

        // 调用 service 方法并验证结果
        List<ProDTO> result = proService.listAllPros();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Pro", result.get(0).getProName());

        // 验证 mapper 调用
        verify(proMapper, times(1)).selectList(any());
    }

    // 2. 测试返回空列表时
    @Test
    void testListAllProsEmpty() {
        // 模拟 ProMapper 返回空列表
        when(proMapper.selectList(any())).thenReturn(new ArrayList<>());

        // 调用 service 方法并验证结果
        List<ProDTO> result = proService.listAllPros();
        assertNotNull(result);
        assertTrue(result.isEmpty());

        // 验证 mapper 调用
        verify(proMapper, times(1)).selectList(any());
    }

}
