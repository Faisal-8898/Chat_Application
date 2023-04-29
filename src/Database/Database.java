import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;
import java.util.*;

public class Database {
    
private MongoDatabase database;
    
    public Database(MongoDatabase database) {
        this.database = database;
    }
    
    public void updateUser(String userId, String name, String email, String password) {
        MongoCollection<Document> collection = database.getCollection("users");
        collection.updateOne(Filters.eq("userId", userId),
            Updates.combine(
                Updates.set("name", name),
                Updates.set("email", email),
                Updates.set("password", password)));
        System.out.println("User with userId " + userId + " has been updated.");
        
        // Update the senderId in the messages collection
        MongoCollection<Document> messagesCollection = database.getCollection("messages");
        Document messagesFilter = new Document("senderId", userId);
        Document messagesUpdate = new Document("$set", new Document("senderId", userId));
        messagesCollection.updateMany(messagesFilter, messagesUpdate);
    }
    
    public void insertMessage(String messageId, String senderId, String recipientId, String message, String type, String status) {
        MongoCollection<Document> collection = database.getCollection("messages");
        Document doc = new Document("messageId", messageId)
            .append("senderId", senderId)
            .append("recipientId", recipientId)
            .append("message", message)
            .append("type", type)
            .append("status", status)
            .append("timestamp", new Date());
        collection.insertOne(doc);
        System.out.println("Message with ID " + messageId + " sent from " + senderId + " to " + recipientId + " saved in the database.");
    }

}

   
