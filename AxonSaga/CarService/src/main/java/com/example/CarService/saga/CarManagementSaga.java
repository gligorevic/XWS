package com.example.CarService.saga;

import com.example.CoreAPI.commands.AddImagesCommand;
import com.example.CoreAPI.commands.RollbackCarCommand;
import com.example.CoreAPI.events.CarCreatedEvent;
import com.example.CoreAPI.events.CarRollbackEvent;
import com.example.CoreAPI.events.ImagesAddedEvent;
import com.example.CoreAPI.events.ImagesAddedFailedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import javax.inject.Inject;
import java.util.UUID;

@Saga
public class CarManagementSaga {
    @Inject
    private transient CommandGateway commandGateway;


    @StartSaga
    @SagaEventHandler(associationProperty = "carId")
    public void handle(CarCreatedEvent carCreatedEvent) {
        System.out.println("Saga invoked");

        String imageAggregateId = UUID.randomUUID().toString();

        SagaLifecycle.associateWith("imageAggregateId", imageAggregateId);

        commandGateway.send(new AddImagesCommand(imageAggregateId, carCreatedEvent.getCarId(),
                carCreatedEvent.getImages()));
    }

    @SagaEventHandler(associationProperty = "imageAggregateId")
    public void handle(ImagesAddedEvent imagesAddedEvent) {
        System.out.println("Saga finishing, both car and images created!");
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "imageAggregateId")
    public void handle(ImagesAddedFailedEvent imagesAddedFailedEvent) {
        System.out.println("Saga declined, starting compensation transaction! " + imagesAddedFailedEvent.getReason());

        commandGateway.send(new RollbackCarCommand(imagesAddedFailedEvent.getCarId()));
    }

    @SagaEventHandler(associationProperty = "carId")
    public void handle(CarRollbackEvent carRollbackEvent) {
        System.out.println("Saga finishing!");

        SagaLifecycle.end();
    }
}
