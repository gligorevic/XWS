package com.example.PriceListService.domain;

import javax.persistence.*;

@Entity
public class Discount {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Integer minNumberDays;

    private Integer percentage;

    @OneToOne
    private PriceListItem priceListItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMinNumberDays() {
        return minNumberDays;
    }

    public void setMinNumberDays(Integer minNumberDays) {
        this.minNumberDays = minNumberDays;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public PriceListItem getPriceListItem() {
        return priceListItem;
    }

    public void setPriceListItem(PriceListItem priceListItem) {
        this.priceListItem = priceListItem;
    }
}
