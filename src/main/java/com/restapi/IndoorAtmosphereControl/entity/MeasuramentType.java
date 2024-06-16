package com.restapi.IndoorAtmosphereControl.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "measurement_types")
public class MeasuramentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "measurement_type", nullable = false)
    private MeasuramentTypes measuramentType;

    @ManyToMany(mappedBy = "measurementTypes")
    private List<Sensor> sensors;
}
