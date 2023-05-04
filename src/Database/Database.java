import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;
import java.util.*;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Updates.*;


public class Database {
    
private MongoDatabase database;
    
    public Database(MongoDatabase database) {
        this.database = database;
    }
    

        
        // Update the senderId in the messages collection
        MongoCollection<Document> messagesCollection = database.getCollection("messages");
        Document messagesFilter = new Document("senderId", userId);
        Document messagesUpdate = new Document("$set", new Document("senderId", userId));
        messagesCollection.updateMany(messagesFilter, messagesUpdate);
    }
    
    public void insertMessage(String messageId, String senderId, String recipientId, String message,String status) {
        MongoCollection<Document> collection = database.getCollection("messages");
        Document doc = new Document("messageId", messageId)
            .append("senderId", senderId)
            .append("recipientId", recipientId)
            .append("message", message)
            .append("status", status)
            .append("timestamp", new Date());
        collection.insertOne(doc);
        System.out.println("Message with ID " + messageId + " sent from " + senderId + " to " + recipientId + " saved in the database.");
    }

}

   
