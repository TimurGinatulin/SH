package ru.ginatulin.riuting_lib.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdjusterDto implements ComponentDtoInt {

    private String id;
    private String title;
    private String variable;

}
