package dev.javarush.roadmapsh.image_processing.core;

import java.time.LocalDateTime;
import java.util.UUID;

public class ImageTransformation {
    private final UUID id;
    private final ImageResizeTransformation resize;
    private final ImageCropTransformation crop;
    private final int rotate;
    private final ImageFormat format;
    private final ImageFiltersTransformation filters;
    private TransformationStatus status;
    private String message;
    private final LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private final String createdBy;
    private String updatedBy;
    private String objectRef;
    private final String owner;

    public ImageTransformation(
            UUID id,
            ImageResizeTransformation resize,
            ImageCropTransformation crop,
            int rotate,
            ImageFormat format,
            ImageFiltersTransformation filters,
            LocalDateTime createdAt,
            LocalDateTime updateAt,
            String createdBy,
            String updatedBy,
            String owner
    ) {
        this.id = id;
        this.resize = resize;
        this.crop = crop;
        this.rotate = rotate;
        this.format = format;
        this.filters = filters;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public ImageResizeTransformation getResize() {
        return resize;
    }

    public ImageCropTransformation getCrop() {
        return crop;
    }

    public int getRotate() {
        return rotate;
    }

    public ImageFormat getFormat() {
        return format;
    }

    public ImageFiltersTransformation getFilters() {
        return filters;
    }

    public TransformationStatus getStatus() {
        return status;
    }

    public void setStatus(TransformationStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getObjectRef() {
        return objectRef;
    }

    public void setObjectRef(String objectRef) {
        this.objectRef = objectRef;
    }
}
