package com.itsp.basicspring.controller;

import com.itsp.basicspring.dto.ProDTO;
import com.itsp.basicspring.service.ProService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * Pro Controller
 * </p>
 *
 * @author Vincent Zheng
 * @since 2024-10-23
 */
@RestController
@RequestMapping("/pro")
@Slf4j
public class ProController {

    @Resource
    ProService proService;

    @GetMapping
    public List<ProDTO> listAllPros(@RequestParam(required = false) String name) {
        if (name != null) {
            List<ProDTO> pro = proService.getByName(name);
            log.info("ProController - getProByName: {}", pro);
            return pro;
        }else{
            List<ProDTO> pros = proService.listAllPros();
            log.info("ProController - listAllPros: {}", pros);
            return pros;
        }

    }

    @GetMapping("/{id}")
    public ProDTO getProById(@PathVariable("id") Long id) {
        ProDTO pro = proService.getById(id);
        log.info("ProController - getProById: {}", pro);
        return pro;
    }
}
