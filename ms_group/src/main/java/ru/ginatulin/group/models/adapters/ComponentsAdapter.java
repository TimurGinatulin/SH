package ru.ginatulin.group.models.adapters;

import ru.ginatulin.group.models.ComponentInt;
import ru.ginatulin.riuting_lib.dto.ComponentDtoInt;

public class ComponentsAdapter {
    public static ComponentInt castToEntity(ComponentInt entity, ComponentDtoInt dto) {
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setVariable(dto.getVariable());
        return entity;
    }
}
