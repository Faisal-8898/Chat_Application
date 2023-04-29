
package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;
import java.util.*;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Server {

    private ServerSocket server;
    private Map<String, Socket> clients;
    private MongoCollection<Document> messages;

    public Server(int port) {
        try {
            // Create server socket and MongoDB connection
            server = new ServerSocket(port);
            MongoClient mongoClient = new MongoClient();
            MongoDatabase database = mongoClient.getDatabase("ChatApp");
            messages = database.getCollection("messages");

            // Initialize map of clients
            clients = new HashMap<>();

            System.out.println("Server started on port " + port);
            while (true) {
                // Wait for incoming connections
                Socket socket = server.accept();

                // Start a new thread to handle the client
                ExecutorService executorService = Executors.newFixedThreadPool(10);
                executorService.execute(new ClientHandler(socket, clients, messages));
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int port = 7778;
        Server server = new Server(port);
    }

    private static class ClientHandler implements Runnable {

        private Socket socket;
        private BufferedReader br;
        private PrintWriter out;
        private Map<String, Socket> clients;
        private MongoCollection<Document> messages;
        private String userId;
        private Server server;

        public ClientHandler(Socket socket, Map<String, Socket> clients, MongoCollection<Document> messages, Server server) {
            this.socket = socket;
            this.clients = clients;
            this.messages = messages;
            this.server = server;

            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());

                // Read user ID from client and add it to map
                userId = br.readLine();
                System.out.println("New client connected: " + userId);
                clients.put(userId, socket);
            } catch (IOException e) {
                System.err.println("Error initializing client handler: " + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                // Read messages from client and broadcast to all clients
                String msg;
                while ((msg = br.readLine()) != null) {
                    System.out.println("Received message from " + userId + ": " + msg);
                    Document messageDoc = new Document("sender", userId)
                            .append("content", msg);
                    messages.insertOne(messageDoc);

                    // Pass arguments to insertMessage() function
                    String messageId = /* generate message ID */;
                    String senderId = userId;
                    String recipientId = /* get recipient ID */;
                    String status = /* set message status */;
                    server.insertMessage(messageId, senderId, recipientId, msg, status);

                    for (String recipientId : clients.keySet()) {
                        if (!userId.equals(recipientId)) {
                            Socket recipientSocket = clients.get(recipientId);
                            PrintWriter recipientOut = new PrintWriter(recipientSocket.getOutputStream());
                            recipientOut.println(userId + ": " + msg);
                            recipientOut.flush();
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                // Remove client from map when they disconnect
                clients.remove(userId);
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
                System.out.println("Client disconnected: " + userId);
            }
        }
    }


    @Override
        public void run() {
            try {
                // Read messages from client and broadcast to all clients
                String msg;
                while ((msg = br.readLine()) != null) {
                    System.out.println("Received message from " + userId + ": " + msg);
                    Document messageDoc = new Document("sender", userId)
                            .append("content", msg);
                    messages.insertOne(messageDoc);

                    for (String recipientId : clients.keySet()) {
                        if (!userId.equals(recipientId)) {
                            Socket recipientSocket = clients.get(recipientId);
                            PrintWriter recipientOut = new PrintWriter(recipientSocket.getOutputStream());
                            recipientOut.println(userId + ": " + msg);
                            recipientOut.flush();
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                // Remove client from map when they disconnect
                clients.remove(userId);
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
                System.out.println("Client disconnected: " + userId);
            }
        }
    }


