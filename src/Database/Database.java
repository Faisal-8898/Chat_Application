import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;

public class Database {
    public static void main(String[] args) {
        // Connect to the MongoDB instance
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        // Get a reference to the database
        MongoDatabase database = mongoClient.getDatabase("ChatApp");

        // Get a reference to the users collection
        MongoCollection<Document> usersCollection = database.getCollection("users");

        // Insert a new user document
        Document user = new Document("username", "alice")
            .append("password", "mypassword")
            .append("email", "alice@example.com");
        usersCollection.insertOne(user);

        // Query for users with a certain email address
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

        // Close the connection to the MongoDB instance
        mongoClient.close();
    }
}
