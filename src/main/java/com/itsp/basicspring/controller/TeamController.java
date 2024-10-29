package com.itsp.basicspring.controller;

import com.itsp.basicspring.dto.TeamDTO;
import com.itsp.basicspring.service.TeamService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Vincent Zheng
 * @since 2024-10-23
 */
@RestController
@RequestMapping("/team")
public class TeamController {
    @Resource
    TeamService teamService;

    // list all the teams
    @GetMapping
    public List<TeamDTO> listAllTeams(@RequestParam(required = false) String name) {
        return teamService.listAllTeams();
    }
}
