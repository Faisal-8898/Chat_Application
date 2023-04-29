import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;
import java.util.*;

public class Database {
    
    private MongoDatabase database;
    
    public UserManager(MongoDatabase database) {
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
    }
    
    
    
}
