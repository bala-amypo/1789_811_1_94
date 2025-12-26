package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String zoneName;

    private String description;

    private Boolean active = true;

    // getters & setters
    public Long getId() { return id; }
    public String getZoneName() { return zoneName; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}


// package com.example.demo.model;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;

// @Entity
// public class Zone {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(unique = true)
//     private String zoneName;

//     private String description;

//     private Boolean active=true;

//     public Zone() {
//     }

//     public Zone(String zoneName, String description, Boolean active) {
//         this.zoneName = zoneName;
//         this.description = description;
//         this.active = active;
//     }

//     // Getters and setters
    
//     public Long getId() {
//         return id;
//     }

//     public String getZoneName() {
//         return zoneName;
//     }

//     public void setZoneName(String zoneName) {
//         this.zoneName = zoneName;
//     }

//     public String getDescription() {
//         return description;
//     }

//     public void setDescription(String description) {
//         this.description = description;
//     }

//     public Boolean getActive() {
//         return active;
//     }

//     public void setActive(Boolean active) {
//         this.active = active;
//     }
// }
