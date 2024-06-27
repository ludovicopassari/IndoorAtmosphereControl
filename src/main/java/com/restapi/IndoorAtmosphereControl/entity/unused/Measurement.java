package com.restapi.IndoorAtmosphereControl.entity.unused;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @Column(name = "value")
    private Double measuredValue;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "datestamp")
    private LocalDate datestamp;

}
