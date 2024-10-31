package com.itsp.basicspring.controller;

import com.itsp.basicspring.dto.ProDTO;
import com.itsp.basicspring.service.ProService;
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
    ProService proService;

    public ProController(ProService proService) {
        this.proService = proService;
    }

    @GetMapping
    public List<ProDTO> listAllPros(@RequestParam(required = false) String name) {
        List<ProDTO> pros;
        if (name != null) {
            pros = proService.getByName(name);
            log.info("ProController - getProByName: {}", pros);
        } else {
            pros = proService.listAllPros();
            log.info("ProController - listAllPros: {}", pros);
        }

    }

    @GetMapping("/{id}")
    public ProDTO getProById(@PathVariable("id") Long id) {
        ProDTO pro = proService.getById(id);
        log.info("ProController - getProById: {}", pro);
        return pro;
    }
}
