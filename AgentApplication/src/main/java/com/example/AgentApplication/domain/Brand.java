package com.example.AgentApplication.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brandName;

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<Model> models = new HashSet<>();

    public Brand() {
    }

    public Brand(String brandName) {
        this.brandName = brandName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Collection<Model> getModels() {
        return models;
    }

    public void setModels(Collection<Model> models) {
        this.models = models;
    }
}
