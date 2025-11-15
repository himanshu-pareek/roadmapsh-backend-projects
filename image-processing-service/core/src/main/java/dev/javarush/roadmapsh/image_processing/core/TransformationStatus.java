package dev.javarush.roadmapsh.image_processing.core;

public enum TransformationStatus {
  REQUESTED,
  QUEUED,
  IN_PROGRESS,
  COMPLETED,
  FAILED,
}
