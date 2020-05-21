package com.example.CarInfoService.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bodyName;

    public Brand() {
    }

    public Brand(String bodyName) {
        this.bodyName = bodyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBodyName() {
        return bodyName;
    }

    public void setBodyName(String bodyName) {
        this.bodyName = bodyName;
    }
}
