package user;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class UserAuthentication {

    private static final String DB_NAME = "user_database";
    private static final String USER_COLLECTION_NAME = "users";

    public static void main(String[] args) {
        // Not needed if running the code from a Swing application
    }

    public static boolean login(String email, String password) {
        MongoClient mongoClient = MongoClients.create(); // Connect to local MongoDB server
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<Document> userCollection = database.getCollection(USER_COLLECTION_NAME);

        Document userDoc = userCollection.find(Filters.eq("email", email)).first();

        if (userDoc == null) {
            mongoClient.close();
            return false; // User not found
        } else {
            String savedPassword = userDoc.getString("password");

            if (savedPassword.equals(password)) {
                String userId = userDoc.getObjectId("_id").toString();
                mongoClient.close();
                return true; // Login successful
            } else {
                mongoClient.close();
                return false; // Incorrect password
            }
        }
    }

    public static boolean createUser(String name, String email, String password) {
        MongoClient mongoClient = MongoClients.create(); // Connect to local MongoDB server
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<Document> userCollection = database.getCollection(USER_COLLECTION_NAME);

        Document userDoc = userCollection.find(Filters.eq("email", email)).first();

        if (userDoc == null) {
            Document newUser = new Document("name", name)
                    .append("email", email)
                    .append("password", password);
            userCollection.insertOne(newUser);

            mongoClient.close();
            return true; // User created successfully
        } else {
            mongoClient.close();
            return false; // User already exists
        }
    }
}
