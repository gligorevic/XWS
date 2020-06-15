package com.example.AgentApplication.domain;


import com.example.AgentApplication.dto.ReportDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private Integer km;

    @OneToOne
    @JsonIgnore
    private Request request;

    public Report(){

    }

    public Report(ReportDTO reportDTO){
        this.km = reportDTO.getKm();
        this.text = reportDTO.getText();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
