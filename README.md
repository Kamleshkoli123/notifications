# Notifications Microservice - Documentation

This document provides detailed information about the Notifications Microservice, including functionalities, API endpoints, request and response formats, validation rules, and sample payloads.

---

## **Functionalities**

1. **Fetch Notifications**:
   - Fetch notifications based on the `section` parameter.
   - Publicly accessible.

2. **Add Notification**:
   - Admin functionality to add a new notification.
   - Validates the payload before adding to the database.

3. **Update Notification**:
   - Admin functionality to update an existing notification.
   - Validates the payload and updates fields.

4. **Delete Notification**:
   - Admin functionality to delete a notification based on `notificationsId`.

5. **Test Endpoint**:
   - Simple endpoint to test API health.

---

## **API Endpoints**

### 1. **Get Notifications**
   - **URL**: `/notifications/getNotifications`
   - **Method**: `GET`
   - **Headers**:
     - `Authorization`: Bearer JWT token
   - **Query Parameters**:
     - `section`: Section of the notifications (e.g., `jobs`, `government`, etc.).
   - **Response**:
     ```json
     [
         {
             "notificationsId": "army_recruitment",
             "section": "jobs",
             "title": "Army Recruitment 2024",
             "description": "Details about army recruitment opportunities.",
             "thumbnail": "army.png",
             "status": "active"
         }
     ]
     ```

### 2. **Add Notification**
   - **URL**: `/notifications/addNotification`
   - **Method**: `POST`
   - **Headers**:
     - `Authorization`: Bearer JWT token (Admin access required).
   - **Request Body**:
     ```json
     {
         "notificationsId": "army_recruitment",
         "section": "jobs",
         "title": "Army Recruitment 2024",
         "description": "Details about army recruitment opportunities.",
         "thumbnail": "army.png",
         "status": "active"
     }
     ```
   - **Response**:
     - **Success**: `201 Created`
       ```json
       "Notification Added Successfully"
       ```
     - **Error**: `400 Bad Request`
       ```json
       [
           "notificationsId cannot be null or empty",
           "Status must be 'active' or 'inactive'"
       ]
       ```

### 3. **Update Notification**
   - **URL**: `/notifications/updateNotification`
   - **Method**: `PUT`
   - **Headers**:
     - `Authorization`: Bearer JWT token (Admin access required).
   - **Request Body**:
     ```json
     {
         "notificationsId": "army_recruitment",
         "section": "jobs",
         "title": "Updated Army Recruitment",
         "description": "Updated details about army recruitment.",
         "thumbnail": "updated_army.png",
         "status": "inactive"
     }
     ```
   - **Response**:
     - **Success**: `200 OK`
       ```json
       "Notification Updated Successfully"
       ```
     - **Error**: `400 Bad Request`
       ```json
       [
           "Title cannot be null or empty",
           "Status must be 'active' or 'inactive'"
       ]
       ```

### 4. **Delete Notification**
   - **URL**: `/notifications/deleteNotification`
   - **Method**: `DELETE`
   - **Headers**:
     - `Authorization`: Bearer JWT token (Admin access required).
   - **Query Parameters**:
     - `id`: The `notificationsId` of the notification to delete.
   - **Response**:
     - **Success**: `200 OK`
       ```json
       "Notification Deleted Successfully"
       ```
     - **Error**: `404 Not Found`
       ```json
       "Notification with id 'army_recruitment' not found."
       ```

### 5. **Test Endpoint**
   - **URL**: `/notifications/test`
   - **Method**: `GET`
   - **Response**:
     ```json
     "Notification endpoints are operational."
     ```

---

## **Validation Rules**

### Payload Validation Rules:
1. **`notificationsId`**:
   - Cannot be null or empty.
   - Example: `army_recruitment`, `gov_scheme`.
2. **`section`**:
   - Cannot be null or empty.
   - Example: `jobs`, `government`.
3. **`title`**:
   - Cannot be null or empty.
4. **`description`**:
   - Cannot be null or empty.
5. **`thumbnail`**:
   - Cannot be null or empty.
   - Example: `army.png`.
6. **`status`**:
   - Must be either `active` or `inactive`.

---

## **Dependencies**

Add the following dependencies in `pom.xml`:

```xml
<dependencies>
    <!-- MongoDB Driver -->
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongodb-driver-sync</artifactId>
        <version>4.9.0</version>
    </dependency>

    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Boot Starter Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <!-- Logging -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>2.0.7</version>
    </dependency>

    <!-- Validation -->
    <dependency>
        <groupId>javax.validation</groupId>
        <artifactId>validation-api</artifactId>
        <version>2.0.1.Final</version>
    </dependency>
</dependencies>
```

---

## **Sample Payloads**

### Add Notification
```json
{
    "notificationsId": "army_recruitment",
    "section": "jobs",
    "title": "Army Recruitment 2024",
    "description": "Details about army recruitment opportunities.",
    "thumbnail": "army.png",
    "status": "active"
}
```

### Update Notification
```json
{
    "notificationsId": "army_recruitment",
    "section": "jobs",
    "title": "Updated Army Recruitment",
    "description": "Updated details about army recruitment.",
    "thumbnail": "updated_army.png",
    "status": "inactive"
}
```

---

This documentation ensures all information about the Notifications Microservice is centralized and easy to follow. Let me know if additional details are needed!

