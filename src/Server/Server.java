package Server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Server implements Runnable {
    Socket socket;
public Server (Socket socket){
    try{
        this.socket=socket;
    } catch (Exception e){
        e.printStackTrace();
    }
}

 int i=0;

public static HashMap client = new HashMap();

public void run(){
    try{
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket. getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket. getOutputStream()));



        client.put(i,writer);

        i++;

        while(true){
            String msg = reader.readLine().trim();

            for(int i=0; i<client.size(); i++) {
                try {
                    BufferedWriter bw = (BufferedWriter) client.get(i);
                    bw.write(msg);
                    bw.flush();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            System.out.println("Client " + i + ": " + msg);
        }

    }
    catch(Exception e){
        e.printStackTrace();
    }
}


    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(1234);
            while (true) {
                Socket socket = s.accept();
                Server server = new Server(socket);
                Thread thread = new Thread(server);
                thread.start();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
