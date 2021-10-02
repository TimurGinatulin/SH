package ru.ginatulin.riuting_lib.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdjusterDto implements ComponentDtoInt {

    private String id;
    private String title;
    private String condition;

    @Override
    public String getVariable() {
        return condition;
    }

    @Override
    public void setVariable(String variable) {
        this.condition = variable;
    }
}
