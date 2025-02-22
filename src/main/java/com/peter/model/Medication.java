package com.peter.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "medications")
@Data
public class Medication extends BaseEntity {

    @Pattern(regexp = "^[A-Za-z0-9-_]+$")
    @Column(name = "name")
    private String name;

    @Pattern(regexp = "^[A-Z0-9_]+$")
    @Column(name = "code")
    private String code;

    private Double weight;

    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drone_id", referencedColumnName = "id")
    private Drone drone;
}
