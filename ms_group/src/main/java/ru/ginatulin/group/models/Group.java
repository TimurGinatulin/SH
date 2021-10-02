package ru.ginatulin.group.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "group_table")
public class Group {
    @Id
    @Column(name = "ip")
    private String ip;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @OneToMany
    @JoinTable(
            name = "group_sensor",
            joinColumns = @JoinColumn(name = "ip_group"),
            inverseJoinColumns = @JoinColumn(name = "id_sensor")
    )
    private List<Sensor> sensors;
    @OneToMany
    @JoinTable(
            name = "group_adjuster",
            joinColumns = @JoinColumn(name = "ip_group"),
            inverseJoinColumns = @JoinColumn(name = "id_adjuster")
    )
    private List<Adjuster> adjusters;
    @CreationTimestamp
    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
