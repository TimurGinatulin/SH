package ru.ginatulin.riuting_lib.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorDto implements ComponentDtoInt {
    private String id;
    private String title;
    private String variable;

}
