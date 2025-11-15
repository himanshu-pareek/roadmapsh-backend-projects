package dev.javarush.roadmapsh.image_processing.core.event;

public interface EventConsumer {
  void consumeEvent(ImageUploadEvent imageUploadEvent);

  void consumeEvent(TransformationRequestEvent transformationRequestEvent);
}
