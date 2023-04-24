import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;
import java.util.*;

public class Database {
    public static void main(String[] args) {
        // Connecting to the MongoDB host
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        // Creating a database instance which connect to the ChatApp database in Mongodb
        MongoDatabase database = mongoClient.getDatabase("ChatApp");

        MongoCollection<Document> usersCollection = database.getCollection("users");
        MongoCollection<Document> messagesCollection = database.getCollection("messages");

        // Insert a new user document
        Document user = new Document("username", "Alice")
                .append("password", "mypassword")
                .append("email", "alice@example.com");
        usersCollection.insertOne(user);

        Document user2 = new Document("username", "Bob")
                .append("password", "mypasswordd")
                .append("email", "bob@example.com");
        usersCollection.insertOne(user2);

        Document user3 = new Document("username", "Charlie")
                .append("password", "mypassworddd")
                .append("email", "Charlie@example.com");
        usersCollection.insertOne(user3);

        // Create a new message document
        Document message = new Document()
                .append("sender", "Alice")
                .append("recipients", Arrays.asList("Bob", "Charlie"))
                .append("message", "Hello, Bob and Charlie!")
                .append("timestamp", new Date());

        // Insert the message document into the "messages" collection
        messagesCollection.insertOne(message);

        // Query for users with a email address
        Document query = new Document("email", "bob@example.com");
        MongoCursor<Document> cursor = usersCollection.find(query).iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

        // Update a user's password
        Document filter = new Document("username", "alice");
        Document update = new Document("$set", new Document("password", "newpassword"));
        usersCollection.updateOne(filter, update);

        // Delete a user document
        filter = new Document("username", "bob");
        usersCollection.deleteOne(filter);

        // Close the connection
        mongoClient.close();
    }
}
