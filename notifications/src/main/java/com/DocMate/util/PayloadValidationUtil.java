package com.DocMate.util;

import com.DocMate.model.NotificationsItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PayloadValidationUtil {

    private static final Logger logger = LoggerFactory.getLogger(PayloadValidationUtil.class);

    private static final String STATUS_PATTERN = "^(open|close)$";

    public static List<String> validateNotification(NotificationsItem item) {
        List<String> errors = new ArrayList<>();

        if (item == null) {
            logger.error("Notification object is null");
            errors.add("Notification object cannot be null.");
            return errors;
        }

        if (item.getNotificationsId() == null || item.getNotificationsId().isEmpty()) {
            logger.error("notificationsId is null or empty");
            errors.add("notificationsId cannot be null or empty.");
        }

        if (item.getSection() == null || item.getSection().isEmpty()) {
            logger.error("Section is null or empty");
            errors.add("Section cannot be null or empty.");
        }

        if (item.getTitle() == null || item.getTitle().isEmpty()) {
            logger.error("Title is null or empty");
            errors.add("Title cannot be null or empty.");
        }

        if (item.getDescription() == null || item.getDescription().isEmpty()) {
            logger.error("Description is null or empty");
            errors.add("Description cannot be null or empty.");
        }

        if (item.getThumbnail() == null || item.getThumbnail().isEmpty()) {
            logger.error("Thumbnail is null or empty");
            errors.add("Thumbnail cannot be null or empty.");
        }

        if (item.getStatus() == null || !Pattern.matches(STATUS_PATTERN, item.getStatus())) {
            logger.error("Invalid or missing status: {}", item.getStatus());
            errors.add("Status must be 'open' or 'close'.");
        }

        return errors;
    }
}
