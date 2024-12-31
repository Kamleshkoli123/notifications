package com.DocMate.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notifications")
public class NotificationsItem {

    @Id
    private String notificationsId;
    private String section;
    private String title;
    private String description;
    private String thumbnail;
    private String status; // Active, Inactive, etc.

    // Getters and Setters
    public String getNotificationsId() {
        return notificationsId;
    }

    public void setNotificationsId(String notificationsId) {
        this.notificationsId = notificationsId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
