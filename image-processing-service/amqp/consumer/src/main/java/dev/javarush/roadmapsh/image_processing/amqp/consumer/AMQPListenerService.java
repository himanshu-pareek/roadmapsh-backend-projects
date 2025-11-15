package dev.javarush.roadmapsh.image_processing.amqp.consumer;

import dev.javarush.roadmapsh.image_processing.core.event.EventConsumer;
import dev.javarush.roadmapsh.image_processing.core.event.ImageUploadEvent;
import dev.javarush.roadmapsh.image_processing.core.event.TransformationRequestEvent;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AMQPListenerService {

  private final EventConsumer eventConsumer;

  public AMQPListenerService(EventConsumer eventConsumer) {
    System.out.println("Injecting event consumer " + eventConsumer);
    this.eventConsumer = eventConsumer;
  }

  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(value = "image.upload.queue", durable = "true"),
      exchange = @Exchange(
          value = "amq.direct",
          ignoreDeclarationExceptions = "true"
      ),
      key = "image.upload"
  ), messageConverter = "jsonMessageConverter")
  public void processImageUpload(ImageUploadEvent event) {
    System.out.println("🎪 Received event - " + event);
    this.eventConsumer.consumeEvent(event);
  }

  @RabbitListener(
      bindings = @QueueBinding(
          value = @Queue(
              value = "image.transformation-request.queue",
              durable = "true"
          ),
          exchange = @Exchange(
              value = "amq.direct",
              ignoreDeclarationExceptions = "true"
          ),
          key = "image.transformation.request"
      )
  )
  public void processImageTransformationRequest(TransformationRequestEvent event) {
    System.out.println("🎪 Received event - " + event);
    this.eventConsumer.consumeEvent(event);
  }
}
