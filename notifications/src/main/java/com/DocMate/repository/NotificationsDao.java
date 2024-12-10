package com.DocMate.repository;

import com.DocMate.model.NotificationsItem;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NotificationsDao {

    private static final Logger logger = LoggerFactory.getLogger(NotificationsDao.class);
    
    @Autowired
    private MongoDatabase mongoDatabase;
    
    public List<NotificationsItem> getNotificationsItemsBySection(String section) {
        List<NotificationsItem> notificationsItems = new ArrayList<>();
        try {
            MongoCollection<Document> collection = mongoDatabase.getCollection("notifications");
            List<Document> documents = collection.find(new Document("section", section)).into(new ArrayList<>());

            for (Document doc : documents) {
            	NotificationsItem item = new NotificationsItem();
                item.setId(doc.getObjectId("_id").toString());
                item.setSection(doc.getString("section"));
                item.setTitle(doc.getString("title"));
                item.setDescription(doc.getString("description")); // Fetching 'identifier'
                item.setThumbnail(doc.getString("thumbnail")); // Fetching 'thumbnail'
                notificationsItems.add(item);
            }
            logger.info("Fetched {} items for path: {}", notificationsItems.size(), section);
        } catch (Exception e) {
            logger.error("Error fetching dashboard items for path: {}", section, e);
        }
        return notificationsItems;
    }
}
