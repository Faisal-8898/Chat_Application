package Server;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket socket;
    private BufferedReader br;
    private PrintWriter out;
    private String userId;

    public Client(String userId) {
        this.userId = userId;
        try {
            System.out.println("Connecting to server...");
            socket = new Socket("localhost", 7778);
            System.out.println("Connection established.");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            // send user id to server
            out.println(userId);
            out.flush();

            startReading();
            startWriting();
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }

    public void startReading() {
        // Thread for reading
        Runnable r1 = () -> {
            System.out.println("Reader started.");

            try {
                String msg;
                while ((msg = br.readLine()) != null) {
                    if (msg.equals("exit")) {
                        System.out.println("Server has terminated the chat.");
                        socket.close();
                        break;
                    }
                    System.out.println("Server: " + msg);
                }
            } catch (IOException e) {
                System.err.println("Error while reading: " + e.getMessage());
            } finally {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException e) {
                    System.err.println("Error while closing the socket: " + e.getMessage());
                }
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        // Thread for writing and sending to server
        Runnable r2 = () -> {
            System.out.println("Writer started.");
            try {
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content;
                while ((content = br1.readLine()) != null) {
                    out.println(userId + ": " + content);
                    out.flush();
                    if (content.equals("exit")) {
                        System.out.println("Server has terminated the chat.");
                        socket.close();
                        break;
                    }
                }
            } catch (IOException e) {
                System.err.println("Error while writing: " + e.getMessage());
            } finally {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException e) {
                    System.err.println("Error while closing the socket: " + e.getMessage());
                }
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        String userId = "user123"; // replace with user id of your choice
        new Client(userId);
    }
}
