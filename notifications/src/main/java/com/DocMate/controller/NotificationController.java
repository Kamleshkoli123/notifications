package com.DocMate.controller;

import com.DocMate.model.NotificationsItem;
import com.DocMate.service.NotificationService;
import com.DocMate.service.JwtService;
import com.DocMate.util.PayloadValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JwtService jwtService;

    // Public: Get Notification Items
    @GetMapping("/getNotifications")
    public ResponseEntity<?> getNotifications(@RequestHeader("Authorization") String token, @RequestParam("section") String section) {
        logger.info("Fetching notifications for section: {}", section);

        if (!jwtService.validateToken(token)) {
            logger.warn("Invalid JWT token.");
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            List<NotificationsItem> items = notificationService.fetchItemsBySection(section);
            logger.info("Fetched {} notification(s) for section: {}", items.size(), section);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            logger.error("Error fetching notifications", e);
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    // Admin: addNotification Notification Item
    @PostMapping("/addNotification")
    public ResponseEntity<?> addNotificationItem(@RequestHeader("Authorization") String token, @RequestBody NotificationsItem item) {
        if (!jwtService.validateAdminToken(token)) {
            logger.warn("Unauthorized access attempt.");
            return ResponseEntity.status(403).body("Access Denied");
        }

        List<String> validationErrors = PayloadValidationUtil.validateNotification(item);
        if (!validationErrors.isEmpty()) {
            logger.error("Validation errors: {}", validationErrors);
            return ResponseEntity.badRequest().body(validationErrors);
        }

        try {
            notificationService.addItem(item);
            logger.info("Notification item added successfully: {}", item);
            return ResponseEntity.status(201).body("Notification Added Successfully");
        } catch (RuntimeException e) {
            logger.error("notificationsId already exists: {}", item.getNotificationsId(), e);
            return ResponseEntity.badRequest().body("notificationsId already exists: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error adding notification", e);
            return ResponseEntity.status(500).body(e);
        }
    }

    // Admin: updateNotification Notification Item
    @PutMapping("/updateNotification")
    public ResponseEntity<?> updateNotificationItem(@RequestHeader("Authorization") String token, @RequestBody NotificationsItem item) {
        if (!jwtService.validateAdminToken(token)) {
            logger.warn("Unauthorized access attempt.");
            return ResponseEntity.status(403).body("Access Denied");
        }

        List<String> validationErrors = PayloadValidationUtil.validateNotification(item);
        if (!validationErrors.isEmpty()) {
            logger.error("Validation errors: {}", validationErrors);
            return ResponseEntity.badRequest().body(validationErrors);
        }

        try {
            notificationService.updateItem(item);
            logger.info("Notification item updated successfully: {}", item);
            return ResponseEntity.ok("Notification Updated Successfully");
        } catch (Exception e) {
            logger.error("Error updating notification", e);
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    // Admin: Delete Notification Item
    @DeleteMapping("/deleteNotification")
    public ResponseEntity<?> deleteNotificationItem(@RequestHeader("Authorization") String token, @RequestParam("notificationsId") String notificationsId) {
        if (!jwtService.validateAdminToken(token)) {
            logger.warn("Unauthorized access attempt.");
            return ResponseEntity.status(403).body("Access Denied");
        }

        try {
            notificationService.deleteItem(notificationsId);
            logger.info("Notification item deleted successfully: {}", notificationsId);
            return ResponseEntity.ok("Notification Deleted Successfully");
        } catch (Exception e) {
            logger.error("Error deleting notification", e);
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    // Test Endpoints for Validation
    @GetMapping("/test")
    public ResponseEntity<?> testEndpoints() {
        logger.info("Testing Notification endpoints.");
        return ResponseEntity.ok("Notification endpoints are operational.");
    }
}
