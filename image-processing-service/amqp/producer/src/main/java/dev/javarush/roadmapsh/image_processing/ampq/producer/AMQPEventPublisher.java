package dev.javarush.roadmapsh.image_processing.ampq.producer;

import dev.javarush.roadmapsh.image_processing.core.event.EventPublisher;
import dev.javarush.roadmapsh.image_processing.core.event.ImageUploadEvent;
import dev.javarush.roadmapsh.image_processing.core.event.TransformationRequestEvent;
import org.springframework.stereotype.Component;

@Component
public class AMQPEventPublisher implements EventPublisher {

  private final FileUploadAMQPPublisher fileUploadPublisher;
  private final TransformationRequestAMQPPublisher transformationReqPublisher;

  public AMQPEventPublisher(
      FileUploadAMQPPublisher fileUploadPublisher,
      TransformationRequestAMQPPublisher transformationReqPublisher
  ) {
    this.fileUploadPublisher = fileUploadPublisher;
    this.transformationReqPublisher = transformationReqPublisher;
  }

  @Override
  public void publishEvent(ImageUploadEvent event) {
    fileUploadPublisher.publish(event);
  }

  @Override
  public void publishEvent(TransformationRequestEvent event) {
    transformationReqPublisher.publish(event);
  }
}
