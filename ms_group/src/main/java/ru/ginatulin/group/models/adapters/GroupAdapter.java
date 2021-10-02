package ru.ginatulin.group.models.adapters;

import ru.ginatulin.group.models.Adjuster;
import ru.ginatulin.group.models.Group;
import ru.ginatulin.group.models.Sensor;
import ru.ginatulin.riuting_lib.dto.AdjusterDto;
import ru.ginatulin.riuting_lib.dto.GroupDto;
import ru.ginatulin.riuting_lib.dto.SensorDto;

import java.util.ArrayList;

public class GroupAdapter {
    public static GroupDto castToDto(Group entity) {
        GroupDto dto = new GroupDto();
        dto.setIp(entity.getIp());
        dto.setDescription(entity.getDescription());
        dto.setTitle(entity.getTitle());
        dto.setSensors(new ArrayList<>());
        entity.getSensors()
                .forEach(sensor -> dto.getSensors()
                        .add(new SensorDto(sensor.getId(), sensor.getTitle(), sensor.getVariable())));
        dto.setAdjusters(new ArrayList<>());
        entity.getAdjusters()
                .forEach(adjuster -> dto.getAdjusters()
                        .add(new AdjusterDto(adjuster.getId(), adjuster.getTitle(), adjuster.getCondition())));
        return dto;
    }

    public static Group castToEntity(GroupDto dto) {
        Group entity = new Group();
        entity.setIp(dto.getIp());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setSensors(new ArrayList<>());
        dto.getSensors().forEach(sensorDto -> entity.getSensors().add(Sensor.builder()
                .id(sensorDto.getId())
                .title(sensorDto.getTitle())
                .variable(sensorDto.getVariable())
                .build())
        );
        entity.setAdjusters(new ArrayList<>());
        dto.getAdjusters().forEach(adjusterDto -> entity.getAdjusters().add(Adjuster.builder()
                .id(adjusterDto.getId())
                .title(adjusterDto.getTitle())
                .condition(adjusterDto.getCondition())
                .build()));
        return entity;
    }
}
