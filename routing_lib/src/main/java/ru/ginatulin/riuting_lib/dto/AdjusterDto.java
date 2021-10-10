package ru.ginatulin.riuting_lib.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdjusterDto implements ComponentDtoInt, Serializable {

    private String id;
    private String title;
    private String variable;

}
