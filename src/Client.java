import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private Socket socket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;

    public Client(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            outputStream = new PrintWriter(socket.getOutputStream(), true);
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        // Create a separate thread to read messages from the server
        Thread readerThread = new Thread(new ServerReader());
        readerThread.start();

        try {
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            // Prompt for a username
            System.out.print("Enter your username: ");
            String username = consoleReader.readLine();

            // Send the username to the server
            outputStream.println(username);

            String message;
            while ((message = consoleReader.readLine()) != null) {
                if (message.equals("/ul")) {
                    outputStream.println(message);
                } else if (message.startsWith("/talk")) {
                    String[] parts = message.split(" ");
                    if (parts.length >= 3) {
                        String receiverIndex = parts[1];
                        String receiverMessage = message.substring(message.indexOf(receiverIndex) + receiverIndex.length() + 1);
                        outputStream.println("/talk " + receiverIndex + " " + receiverMessage);
                    } else {
                        System.out.println("Invalid command. Please use the format: /talk <receiverIndex> <message>");
                    }
                } else {
                    outputStream.println(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    private void closeResources() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ServerReader implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = inputStream.readLine()) != null) {
                    if (message.startsWith("Connected Users:")) {
                        System.out.println(message);
                    } else {
                        System.out.println(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeResources();
            }
        }
    }

    public static void main(String[] args) {
        String serverAddress = "localhost"; // Replace with the server address
        int serverPort = 1234; // Replace with the server port

        Client client = new Client(serverAddress, serverPort);
        client.start();
    }
}
