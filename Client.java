import java.io.*;
import java.net.Socket;

public class Client {

Socket socket;
BufferedReader br;
PrintWriter out;

    public Client(){
        try {
            System.out.println("Sending Request to server...");
           socket = new Socket("127.0.0.1",7777);
           System.out.println("Connection Done.."); 
           
           
          br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

          out = new PrintWriter(socket.getOutputStream());

          startReading();
          startWriting();

        } catch (Exception e) {   }
    }

public void startReading(){
        
        Runnable r1 =()->{
            System.out.println("Reader started");
            try {    
            while(true){
                String msg = br.readLine();
                if(msg.equals("exit")){
                    System.out.println("Server terminated the chat");
                    break;
                }
    
                System.out.println("Server : "+ msg);
            }
        }    
     catch (Exception e) {
        // e.printStackTrace();
     }
     System.out.println("Connection is closed....");

            };
        new Thread(r1).start();   
        }
    
    public void startWriting(){
System.out.println("Writer Started");
        Runnable r2 =()->{
            try {
            while (!socket.isClosed()) {    
                    
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String  context = br1.readLine();
                    if(context.equals("exit")){
                        socket.close();
                        break;
                    }
                    out.println(context);
                    out.flush();

                }
                System.out.println("Connection closed....");
            }catch (Exception e) {
                // e.printStackTrace();
            }
            System.out.println("Connection is closed....");

          
            };
            new Thread(r2).start();
    }
    

    public static void main(String[] args) {
        System.out.println("This is Client");
       new Client();
        
    }
}
