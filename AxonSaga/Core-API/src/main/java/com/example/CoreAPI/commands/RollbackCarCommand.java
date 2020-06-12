package com.example.CoreAPI.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class RollbackCarCommand {
    @TargetAggregateIdentifier
    private Long carId;

    public RollbackCarCommand() {
    }

    public RollbackCarCommand(Long carId) {
        this.carId = carId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}
