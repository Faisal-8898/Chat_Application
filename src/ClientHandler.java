import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;
    private Server server;
    private String username;

    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;

        try {
            outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
            inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // Prompt the client for username
            outputStream.println("Enter your username:");
            username = inputStream.readLine();

            // Notify the server about the new connection
            server.addClient(this);

            String message;
            while ((message = inputStream.readLine()) != null) {
                if (message.startsWith("/talk")) {
                    String[] parts = message.split(" ");
                    if (parts.length == 2) {
                        String receiver = parts[1];
                        server.sendMessageToClient(this, receiver, message);
                    }
                } else {
                    // Handle other commands or general messages here
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Remove the client from the server upon disconnection
            server.removeClient(this);
            closeResources();
        }
    }

    public void sendMessage(String message) {
        outputStream.println(message);
    }

    public String getUsername() {
        return username;
    }

    private void closeResources() {
        try {
            inputStream.close();
            outputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
