package com.itsp.basicspring.service;

import com.itsp.basicspring.dto.ProDTO;
import com.itsp.basicspring.model.Pro;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Vincent Zheng
 * @since 2024-10-23
 */
@Service
public interface ProService extends IService<Pro> {
    ProDTO getById(Long id);

    List<ProDTO> getByName(String name);

    List<ProDTO> listAllPros();
}
