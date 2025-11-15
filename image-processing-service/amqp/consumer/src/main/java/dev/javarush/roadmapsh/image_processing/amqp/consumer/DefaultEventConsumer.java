package dev.javarush.roadmapsh.image_processing.amqp.consumer;

import dev.javarush.roadmapsh.image_processing.core.event.EventConsumer;
import dev.javarush.roadmapsh.image_processing.core.event.ImageUploadEvent;
import dev.javarush.roadmapsh.image_processing.core.event.TransformationRequestEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(value = EventConsumer.class)
public class DefaultEventConsumer implements EventConsumer {
  @Override
  public void consumeEvent(ImageUploadEvent event) {
    System.err.println("Missing event handler for " + event);
  }

  @Override
  public void consumeEvent(TransformationRequestEvent event) {
    System.err.println("Missing event handler for " + event);
  }
}
