package ru.ginatulin.riuting_lib.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GroupDto implements Serializable {
    private String ip;
    private String title;
    private String description;
    private List<AdjusterDto> adjusters;
    private List<SensorDto> sensors;
}
