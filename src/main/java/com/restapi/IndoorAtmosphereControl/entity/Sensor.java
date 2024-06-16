package com.restapi.IndoorAtmosphereControl.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sensors")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "sensors")
    private List<Device> devices;

    @ManyToMany
    @JoinTable(name = "sensor_measurement_types",
               joinColumns = @JoinColumn(name = "sensor_id"),
               inverseJoinColumns = @JoinColumn(name = "measurement_type_id"))
    private List<MeasuramentType> measurementTypes;

    // Relazione One-to-Many con Misurazione
    @OneToMany(mappedBy = "sensor")
    private List<Measurement> measurements;

    @Column(name = "created_at", nullable = false)
    @CreatedDate // Indica che questo campo rappresenta la data di creazione
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate // Indica che questo campo rappresenta l'ultima data di modifica
    private LocalDateTime updatedAt;

}
