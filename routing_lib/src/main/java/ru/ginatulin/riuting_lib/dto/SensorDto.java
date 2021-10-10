package ru.ginatulin.riuting_lib.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorDto implements ComponentDtoInt, Serializable {
    private String id;
    private String title;
    private String variable;

}
