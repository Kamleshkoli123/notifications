package com.DocMate.controller;

import com.DocMate.service.NotificationService;
import com.DocMate.service.JwtService;
import com.DocMate.model.NotificationsItem;
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
    private NotificationService notificationsService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/get")
    public ResponseEntity<?> getNotificationItems(@RequestHeader("Authorization") String token, @RequestParam("section") String section) {
        logger.info("Received request for Notification items with path: {} and token: {}", section, token);

        if (!jwtService.validateToken(token)) {
            logger.warn("Invalid JWT token received.");
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            List<NotificationsItem> items = notificationsService.fetchItemsByPath(section);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            logger.error("Error fetching Notification items", e);
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        logger.info("Received request for test");

        return ResponseEntity.status(200).body("test success");
    }
}
