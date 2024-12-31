package com.DocMate.service;

import com.DocMate.model.NotificationsItem;
import com.DocMate.repository.NotificationsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationsDao notificationsDao;

    // Fetch items by section
    public List<NotificationsItem> fetchItemsBySection(String section) {
        logger.info("Fetching notifications for section: {}", section);
        try {
            return notificationsDao.getNotificationsItemsBySection(section);
        } catch (Exception e) {
            logger.error("Error fetching notifications for section: {}", section, e);
            throw new RuntimeException("Failed to fetch notifications", e);
        }
    }

    // Add a new notification
    public void addItem(NotificationsItem item) {
        logger.info("Adding new notification with ID: {}", item.getNotificationsId());
        try {
            notificationsDao.insertItem(item);
            logger.info("Notification added successfully: {}", item.getNotificationsId());
        } catch (Exception e) {
            logger.error("Error adding notification with ID: {}", item.getNotificationsId(), e);
            throw new RuntimeException("Failed to add notification", e);
        }
    }

    // Update an existing notification
    public void updateItem(NotificationsItem item) {
        logger.info("Updating notification with ID: {}", item.getNotificationsId());
        try {
            notificationsDao.updateItem(item);
            logger.info("Notification updated successfully: {}", item.getNotificationsId());
        } catch (Exception e) {
            logger.error("Error updating notification with ID: {}", item.getNotificationsId(), e);
            throw new RuntimeException("Failed to update notification", e);
        }
    }

    // Delete a notification by notificationsId
    public void deleteItem(String notificationsId) {
        logger.info("Deleting notification with ID: {}", notificationsId);
        try {
            notificationsDao.deleteItem(notificationsId);
            logger.info("Notification deleted successfully with ID: {}", notificationsId);
        } catch (Exception e) {
            logger.error("Error deleting notification with ID: {}", notificationsId, e);
            throw new RuntimeException("Failed to delete notification", e);
        }
    }
}
