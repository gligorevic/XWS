package com.example.ImageService.aggregates;

import com.example.CoreAPI.commands.AddImagesCommand;
import com.example.CoreAPI.events.ImagesAddedEvent;
import com.example.CoreAPI.events.ImagesAddedFailedEvent;
import com.example.ImageService.service.ImageService;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Aggregate
public class ImageAggregate {
    @AggregateIdentifier
    private String imageAggregateId;

    public ImageAggregate() {
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final Logger log = LoggerFactory.getLogger(ImageAggregate.class);

    @CommandHandler
    public ImageAggregate(AddImagesCommand addImagesCommand, ImageService imageService) {
        try {
            imageService.saveImages(addImagesCommand.getImages(), addImagesCommand.getCarId());
            log.info("User successfully added images for car {}", bCryptPasswordEncoder.encode(addImagesCommand.getCarId().toString()));

            AggregateLifecycle.apply(new ImagesAddedEvent(addImagesCommand.getImageAggregateId()));
        } catch (Exception e) {
            log.error(e.getMessage());
            AggregateLifecycle.apply(new ImagesAddedFailedEvent(addImagesCommand.getImageAggregateId(), addImagesCommand.getCarId(), e.getMessage()));
        }
    }

    @EventSourcingHandler
    protected void on(ImagesAddedEvent imagesAddedEvent) {
        this.imageAggregateId = imagesAddedEvent.getImageAggregateId();
    }

    @EventSourcingHandler
    protected void on(ImagesAddedFailedEvent imagesAddedFailedEvent) {
        this.imageAggregateId = imagesAddedFailedEvent.getImageAggregateId();
    }

}
