package dev.javarush.roadmapsh.image_processing.core;

import java.time.LocalDateTime;
import java.util.Collection;

public class Image {
  // Required fields
  private final String id;
  private final String title;
  private final String objectRef;

  // Image Metadata
  private int height;
  private int width;
  private ImageFormat format;
  private long sizeInBytes;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private final String createdBy;
  private String updatedBy;
  private final String owner;

  // Transformations created from this image
  private Collection<ImageTransformation> transformations;

  public Image(
      String id,
      String title, String objectRef,
      LocalDateTime createdAt,
      String createdBy,
      LocalDateTime updatedAt,
      String updatedBy,
      String owner
  ) {
    this.id = id;
    this.title = title;
    this.objectRef = objectRef;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.owner = owner;
    this.updatedAt = updatedAt;
    this.updatedBy = updatedBy;
  }


  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setFormat(ImageFormat format) {
    this.format = format;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setSizeInBytes(long sizeInBytes) {
    this.sizeInBytes = sizeInBytes;
  }

  public void setTransformations(
      Collection<ImageTransformation> transformations) {
    this.transformations = transformations;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public ImageFormat getFormat() {
    return format;
  }

  public long getSizeInBytes() {
    return sizeInBytes;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public Collection<ImageTransformation> getTransformations() {
    return transformations;
  }

  public String getObjectRef() {
    return objectRef;
  }

  public String getOwner() {
    return owner;
  }

  public String getTitle() {
    return title;
  }

  public String getId() {
    return id;
  }

  public void setMetadata(ImageMetadata metadata) {
    this.width = metadata.width();
    this.height = metadata.height();
    this.sizeInBytes = metadata.sizeInBytes();
    this.format = metadata.format();
    this.updatedAt = LocalDateTime.now();
    this.updatedBy = "system";
  }
}
