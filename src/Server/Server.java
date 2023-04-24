package Server;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket server;
    private Socket socket;
    private BufferedReader br;
    private PrintWriter out;
    private ExecutorService threadPool;

    public Server() {
        try {
            server = new ServerSocket(7778);
            System.out.println("Server is ready to accept connections");
            System.out.println("Server is waiting...");
            threadPool = Executors.newCachedThreadPool();
            while (true) {
                socket = server.accept();
                handleClientConnection(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (server != null) {
                    server.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleClientConnection(Socket clientSocket) {
        try {
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
            threadPool.execute(() -> startReading(clientSocket));
            threadPool.execute(() -> startWriting(clientSocket));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReading(Socket clientSocket) {
        try {
            System.out.println("Reader started for client " + clientSocket.getInetAddress());
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String msg;
            while ((msg = br.readLine()) != null) {
                System.out.println("Client (" + clientSocket.getInetAddress() + "): " + msg);
                if (msg.equals("exit")) {
                    System.out.println("Client " + clientSocket.getInetAddress() + " has terminated the chat");
                    clientSocket.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startWriting(Socket clientSocket) {
        try {
            System.out.println("Writer started for client " + clientSocket.getInetAddress());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String content;
            while ((content = br.readLine()) != null) {
                out.println(content);
                out.flush();
                if (content.equals("exit")) {
                    System.out.println("Server has terminated the chat with client " + clientSocket.getInetAddress());
                    clientSocket.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("This is a server -- Welcome to Faiasl's server");
        new Server();
    }
}