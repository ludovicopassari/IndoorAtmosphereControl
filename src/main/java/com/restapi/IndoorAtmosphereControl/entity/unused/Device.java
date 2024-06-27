package com.restapi.IndoorAtmosphereControl.entity.unused;

import com.restapi.IndoorAtmosphereControl.entity.UserInfo;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    @ManyToMany
    @JoinTable(name = "device_sensors",
                joinColumns = @JoinColumn(name = "device_id"),
                inverseJoinColumns = @JoinColumn(name = "sensor_id"))
    private List<Sensor> sensors;

    @Column(name = "created_at", nullable = false)
    @CreatedDate // Indica che questo campo rappresenta la data di creazione
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate // Indica che questo campo rappresenta l'ultima data di modifica
    private LocalDateTime updatedAt;


}
