package ru.ginatulin.group.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import ru.ginatulin.group.models.observers.AbstractComponentObservers;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sensor_table")
public class Sensor extends AbstractComponentObservers implements ComponentInt {
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private String id;
    @Column(name = "title")
    private String title;
    @Column(name = "variable")
    private String variable;
    @CreationTimestamp
    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void setVariable(String variable) {
        this.variable = variable;
        notify(variable);
    }

}