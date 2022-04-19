import java.io.*;
import java.net.*;
public class ReadThread extends Thread{
    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;

    public ReadThread(Socket socket, ChatClient client){
        this.client = client;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (Exception e) {
            System.out.println("Err: " + e.getMessage());
        }
    }

    public void run(){
        while(true){
            try{
                String response = reader.readLine();
                System.out.println("\n" + response);

                if(client.getUserName() != null){
                    System.out.println("[" + client.getUserName() + "]");
                }

            }catch(Exception e){
                System.out.println("Connection finished");
                break;
            }
        }
    }
}
