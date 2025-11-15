package dev.javarush.roadmapsh.image_processing.ampq.producer;

import dev.javarush.roadmapsh.image_processing.core.event.ImageUploadEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

@Component
class FileUploadAMQPPublisher {
  private static final String EXCHANGE = "amq.direct";
  private static final String ROUTING_KEY = "image.upload";

  private final RabbitTemplate template;

  FileUploadAMQPPublisher(RabbitTemplate template) {
    this.template = template;
    this.template.setMessageConverter(new Jackson2JsonMessageConverter());
  }

  public void publish(ImageUploadEvent event) {
    System.out.println("Publishing event - " + event);
    this.template.convertAndSend(EXCHANGE, ROUTING_KEY, event);
  }
}
