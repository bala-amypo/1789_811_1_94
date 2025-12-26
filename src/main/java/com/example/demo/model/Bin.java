package com.example.demo.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Bin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;
    private Double capacityLiters;
    private Double latitude;
    private Double longitude;
    private Boolean active = true;
    private String locationDescription;

    @ManyToOne
    private Zone zone;

    public Bin() {}

    public Bin(Long id, String identifier, Double capacityLiters, Double latitude, Double longitude,
               Boolean active, String locationDescription, Zone zone) {
        this.id = id;
        this.identifier = identifier;
        this.capacityLiters = capacityLiters;
        this.latitude = latitude;
        this.longitude = longitude;
        this.active = active;
        this.locationDescription = locationDescription;
        this.zone = zone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }

    public Double getCapacityLiters() { return capacityLiters; }
    public void setCapacityLiters(Double capacityLiters) { this.capacityLiters = capacityLiters; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public String getLocationDescription() { return locationDescription; }
    public void setLocationDescription(String locationDescription) { this.locationDescription = locationDescription; }

    public Zone getZone() { return zone; }
    public void setZone(Zone zone) { this.zone = zone; }
}
