package com.DocMate.repository;

import com.DocMate.model.NotificationsItem;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

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
                
                if(doc.getString("status").equalsIgnoreCase("close")) {
                	continue;
                }
                
                item.setNotificationsId(doc.getString("notificationsId"));
                item.setSection(doc.getString("section"));
                item.setTitle(doc.getString("title"));
                item.setDescription(doc.getString("description"));
                item.setThumbnail(doc.getString("thumbnail"));
//                item.setStatus(doc.getString("status")); // Added 'status'
                notificationsItems.add(item);
            }
            logger.info("Fetched {} items for section: {}", notificationsItems.size(), section);
        } catch (Exception e) {
            logger.error("Error fetching items for section: {}", section, e);
        }
        return notificationsItems;
    }

    public void insertItem(NotificationsItem item) {
        try {
            MongoCollection<Document> collection = mongoDatabase.getCollection("notifications");
                 
            // Check if a document with the same serviceId already exists
            Document existingDoc = collection.find(Filters.eq("notificationsId", item.getNotificationsId())).first();
            
            if (existingDoc != null) {
                // If a document with the same serviceId exists, throw an error
                logger.error("notificationsId {} already exists.", item.getNotificationsId());
                throw new RuntimeException("notificationsId already exists: " + item.getNotificationsId());
            }
            
            Document doc = new Document("notificationsId", item.getNotificationsId())
                    .append("section", item.getSection())
                    .append("title", item.getTitle())
                    .append("description", item.getDescription())
                    .append("thumbnail", item.getThumbnail())
                    .append("status", item.getStatus());
            collection.insertOne(doc);
            logger.info("Inserted notification item: {}", item);
        } catch (Exception e) {
            logger.error("Error inserting item: {}", item, e);
            throw e;
        }
    }

    public void updateItem(NotificationsItem item) {
        try {
            MongoCollection<Document> collection = mongoDatabase.getCollection("notifications");
            Document update = new Document("section", item.getSection())
                    .append("title", item.getTitle())
                    .append("description", item.getDescription())
                    .append("thumbnail", item.getThumbnail())
                    .append("status", item.getStatus());
            collection.updateOne(new Document("notificationsId", item.getNotificationsId()), new Document("$set", update));
            logger.info("Updated notification item: {}", item);
        } catch (Exception e) {
            logger.error("Error updating item: {}", item, e);
        }
    }

    public void deleteItem(String notificationsId) {
        try {
            MongoCollection<Document> collection = mongoDatabase.getCollection("notifications");
            collection.deleteOne(new Document("notificationsId", notificationsId));
            logger.info("Deleted notification item with ID: {}", notificationsId);
        } catch (Exception e) {
            logger.error("Error deleting item with ID: {}", notificationsId, e);
        }
    }
    
}
