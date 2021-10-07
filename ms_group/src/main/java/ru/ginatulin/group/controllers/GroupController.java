package ru.ginatulin.group.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ginatulin.group.services.GroupService;
import ru.ginatulin.riuting_lib.dto.GroupDto;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService service;

    @GetMapping
    public List<GroupDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{ip}")
    public List<GroupDto> getByIp(@PathVariable String ip) {
        return service.getByIp(ip);
    }

    @PostMapping
    public GroupDto createGroup(@RequestBody GroupDto dto) {
        return service.saveGroup(dto);
    }

    @PutMapping
    public GroupDto updateGroup(@RequestBody GroupDto dto) {
        return service.updateGroup(dto);
    }

    @DeleteMapping
    public GroupDto deleteGroup(@PathVariable String ip) {
        return service.deleteGroup(ip);
    }
}
