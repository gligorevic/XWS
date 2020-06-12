package com.example.CarService.aggregates;

import com.example.CarService.service.CarService;
import com.example.CoreAPI.commands.CreateCarCommand;
import com.example.CoreAPI.commands.RollbackCarCommand;
import com.example.CoreAPI.events.CarCreatedEvent;
import com.example.CoreAPI.events.CarRollbackEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class CarAggregate {

    @AggregateIdentifier
    private Long carAggregateId;

    public CarAggregate() {
    }

    @CommandHandler
    public CarAggregate(CreateCarCommand createCarCommand) {
        AggregateLifecycle.apply(new CarCreatedEvent(createCarCommand.getCarId(), createCarCommand.getImages()));
    }

    @EventSourcingHandler
    public void on(CarCreatedEvent carCreatedEvent) {
        this.carAggregateId = carCreatedEvent.getCarId();
    }

    @CommandHandler
    public void on(RollbackCarCommand rollbackCarCommand, CarService carService) {
        carService.deleteCarById(rollbackCarCommand.getCarId());
        AggregateLifecycle.apply(new CarRollbackEvent(rollbackCarCommand.getCarId()));
    }
}
