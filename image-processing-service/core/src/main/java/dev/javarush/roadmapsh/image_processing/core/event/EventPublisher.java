package dev.javarush.roadmapsh.image_processing.core.event;

public interface EventPublisher {
  void publishEvent(ImageUploadEvent imageUploadEvent);

  void publishEvent(TransformationRequestEvent transformationRequestEvent);
}
