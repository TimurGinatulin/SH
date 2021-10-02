package ru.ginatulin.group.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ginatulin.cor_lib.exception.models.NotFoundException;
import ru.ginatulin.group.models.*;
import ru.ginatulin.group.models.adapters.ComponentsAdapter;
import ru.ginatulin.group.models.adapters.GroupAdapter;
import ru.ginatulin.group.repositories.AdjusterRepository;
import ru.ginatulin.group.repositories.GroupRepository;
import ru.ginatulin.group.repositories.SensorRepository;
import ru.ginatulin.riuting_lib.dto.GroupDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository grouprepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private AdjusterRepository adjusterRepository;

    public List<Group> getAll() {
        return grouprepository.findAll();
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
                                        .save((Adjuster)ComponentsAdapter.castToEntity(new Adjuster(),adjusterDto) ))

        );
        return GroupAdapter.castToDto(grouprepository.save(save));
    }


}
