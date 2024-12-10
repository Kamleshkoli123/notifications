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

    public List<NotificationsItem> fetchItemsByPath(String section) {
        logger.info("Fetching items for section: {}", section);
        return notificationsDao.getNotificationsItemsBySection(section);
    }
}
