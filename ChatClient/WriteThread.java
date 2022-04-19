import java.io.*;
import java.net.*;
public class WriteThread extends Thread{
    private Socket socket;
    private ChatClient client;
    private PrintWriter writer;

    public WriteThread(Socket socket, ChatClient cliente){
        this.socket = socket;
        this.client = cliente;

        try {
            OutputStream output = socket.getOutputStream();
            writer =  new PrintWriter(output, true);

        } catch (IOException e) {
            System.out.println("Err: " + e.getMessage());
        }   
    }
    public void run(){
        Console console = System.console();
        String userName =  console.readLine("\ntype your name: ");
        client.setUserName(userName);
        writer.println(userName);

        String text;

        do{
            text = console.readLine("[" + userName + "]");
            writer.println(text);
        }while(!text.equals("bye"));

        try{
            socket.close();
        }catch (IOException e){
            System.out.println("Err: " + e.getMessage());
        }
    }
}