package ru.ginatulin.group.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ginatulin.cor_lib.exception.models.NotFoundException;
import ru.ginatulin.group.models.*;
import ru.ginatulin.group.models.adapters.ComponentsAdapter;
import ru.ginatulin.group.models.adapters.GroupAdapter;
import ru.ginatulin.group.models.observers.ComponentDisplay;
import ru.ginatulin.group.repositories.AdjusterRepository;
import ru.ginatulin.group.repositories.GroupRepository;
import ru.ginatulin.group.repositories.SensorRepository;
import ru.ginatulin.riuting_lib.dto.GroupDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {
    @Autowired
    private GroupRepository grouprepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private AdjusterRepository adjusterRepository;

    public List<GroupDto> getAll() {
        List<Group> groups = grouprepository.findAll();
        return groups.stream().map(GroupAdapter::castToDto).collect(Collectors.toList());
    }

    public List<GroupDto> getByIp(String ip) {
        grouprepository.findById(ip).orElseThrow(() -> new NotFoundException("Group with ip: " + ip + " not found"));
        return null;
    }

    public GroupDto saveGroup(GroupDto dto) {
        Group save = GroupAdapter.castToEntity(dto);
        save.setAdjusters(new ArrayList<>());
        save.setSensors(new ArrayList<>());
        dto.getSensors().forEach(sensorDto ->
                save.getSensors()
                        .add(sensorRepository.save((Sensor) ComponentsAdapter.castToEntity(new Sensor(), sensorDto)))
        );
        save.setAdjusters(new ArrayList<>());
        dto.getAdjusters().forEach(adjusterDto ->
                save.getAdjusters()
                        .add(adjusterRepository
                                .save((Adjuster) ComponentsAdapter.castToEntity(new Adjuster(), adjusterDto)))

        );
        return GroupAdapter.castToDto(grouprepository.save(save));
    }

    public GroupDto updateGroup(GroupDto dto) {
        Group group = grouprepository.findById(
                dto.getIp()).orElseThrow(() -> new NotFoundException("Group with ip: " + dto.getIp() + " not found"));
        ComponentDisplay display = new ComponentDisplay();
        dto.getSensors().forEach(sensorDto -> {
            Sensor updatedSensor = sensorRepository.findById(sensorDto.getId()).orElse(null);
            if (updatedSensor == null) {
                Sensor newSensor = sensorRepository
                        .save((Sensor) ComponentsAdapter.castToEntity(new Sensor(), sensorDto));
                group.getSensors().add(newSensor);
            } else {
                int index = group.getSensors().indexOf(updatedSensor);
                Sensor sensor = group.getSensors().get(index);
                sensor.attach(display);
                sensor.setVariable(sensorDto.getVariable());
                group.getSensors().set(index, sensor);

            }
        });
        dto.getAdjusters().forEach(adjusterDto -> {
            Adjuster updatedAdjuster = adjusterRepository.findById(adjusterDto.getId()).orElse(null);
            if (updatedAdjuster == null) {
                Adjuster newAdjuster = adjusterRepository
                        .save((Adjuster) ComponentsAdapter.castToEntity(new Adjuster(), adjusterDto));
                group.getAdjusters().add(newAdjuster);
            } else {
                int index = group.getAdjusters().indexOf(updatedAdjuster);
                Adjuster adjuster = group.getAdjusters().get(index);
                adjuster.attach(display);
                adjuster.setVariable(adjusterDto.getVariable());
                group.getAdjusters().set(index, adjuster);

            }
        });
        return GroupAdapter.castToDto(grouprepository.save(group));
    }

    public GroupDto deleteGroup(String ip) {
        Group group = grouprepository.findById(ip)
                .orElseThrow(() -> new NotFoundException("group with ip: " + ip + " , not found"));
        group.setDeletedAt(LocalDateTime.now());
        return GroupAdapter.castToDto(grouprepository.save(group));
    }
}
