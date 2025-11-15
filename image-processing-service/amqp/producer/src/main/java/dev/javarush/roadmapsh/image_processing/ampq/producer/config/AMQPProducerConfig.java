package dev.javarush.roadmapsh.image_processing.ampq.producer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "dev.javarush.roadmapsh.image_processing.amqp.common" })
public class AMQPProducerConfig {
}
