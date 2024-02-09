import java.io.*;
import java.net.*;

public class Server {

    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Server() {

        try {
            server = new ServerSocket(7777);
            System.out.println("Server is not ready to accept connection");
            System.out.println("Waiting...");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        } catch (Exception e) {
        }
    }

    public void startReading() {

        Runnable r1 = () -> {
            System.out.println("Reader started");

            try {
                while (true) {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the chat");
                        socket.close();
                        break;
                    }

                    System.out.println("Client : " + msg);
                }
            } catch (Exception e) {
                // e.printStackTrace();
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        System.out.println("Writer Started");
        Runnable r2 = () -> {
            try {
            while (!socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String context = br1.readLine();
                    out.println(context);
                    out.flush();

                    if (context.equals("exit")) {
                        socket.close();
                        break;
                    }

                }
                System.out.println("Connection is closed....");
            } catch (Exception e) {
                // e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is Server.. going to Start");
        new Server();

    }
}